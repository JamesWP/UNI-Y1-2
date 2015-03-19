;------------------------------------------------------------------------
;            IO Library
;           JWP 2015 - COMP227
;           VERSION 1.1 for use in kernal
;
; contains io utilities
;  incl write string, char, clear screen, convert control char to op
;
; Last modified 28/Jan, 04/Feb
;
;
; Known bugs: None
;
;------------------------------------------------------------------------


LCD_Data    EQU 0x10000000
LCD_Control EQU 0x10000004

ENABLE  EQU   0x01
REGSEL  EQU   0x02
READNW  EQU   0x04
              
BACKLIGHT  EQU   0x20
CONTROL_CHAR EQU 0x20
CLEAR   EQU   0x01

;---------------------------
;procedure PrintChar(R0=char)
; prints a single char on the LCD
;---------------------------
PrintChar
        PUSH{LR,r1,r8,r9}
        ;load Bdata and control pointers        
        MOV   r8, #LCD_Data
        MOV   r9, #LCD_Control
        ; wait for device
        BL    IOWait

        ; load control reg
        LDR   r1, [r9]
        ; setup = set write set REGSEL unset READNW
        BIC   r1, r1, #(READNW)

        CMP   r0, #CONTROL_CHAR

        ORRGE r1, r1, #(REGSEL) ; for data reg
        BICLT r1, r1, #(REGSEL) ; for control reg
        BLLT  ConvertControlChar; convert control char to operation
        
        STR   r1, [r9]

        ; set data
        STR   r0, [r8]

        ; strobe enable
        ORR   r1, r1, #(ENABLE)
        STR   r1, [r9]

        ; strobe off        
        BIC   r1, r1, #(ENABLE)
        STR   r1, [r9]

        ;print char
        POP{LR,r1,r8,r9}
        MOV   PC,LR
;---------------------------

;---------------------------
;procedure PrintString(R0=string-pointer)
; prints a \0 terminated string pointed to by string-pointer
;---------------------------
PrintString
        PUSH{LR,r1}
        MOV   r1, r0
PrintString_repeat
        LDRB  r0, [r1], #1     ;load char + post increment
        ;check for termination char -> jump to end
        CMP   r0,#0

        BEQ PrintString_end
        BL PrintChar           ; PrintChar(R0=curent-char)
        B  PrintString_repeat
PrintString_end
        POP{LR,r1}
        MOV   PC,LR
;---------------------------

EnableBacklight
        PUSH{LR,r0,r8}
        
        ;load Bdata and control pointers        
        MOV   r8, #LCD_Data
        MOV   r9, #LCD_Control       
 
        ; wait for io to be ready 
        BL    IOWait
        
        LDR   r0, [r9]
        ORR   r0, r0, #(BACKLIGHT)
        STR   r0, [r9]

        POP{LR,r0,r8}
        MOV   PC,LR
      
;---------------------------
;procedure ConvertControlChar(r0=char OUTPUT)
; converts the char in r0 to an operation
;
CURSOR_POS_MASK EQU 0x7F

MOVE_CUR_OFFSET EQU 0x80
LINE      EQU 0x40
BEGIN_LINE_CLEAR EQU 0x1F

LINE_FEED EQU 0x0A
CARR_RET  EQU 0x0D
;
;---------------------------
ConvertControlChar
        PUSH{LR,r1,r2}
      
        BL    IOWait    ; wait for io to be ready to read
        MOV   r2, r0

        ; load curent control
        LDR   r0, [r9]
        ; set read&control, unset enable
        ORR   r0, r0, #(READNW)
        BIC   r0, r0, #(ENABLE | REGSEL)
        STR   r0, [r9]        
        ; enable bus too
        ORR   r0, r0, #(ENABLE)
        STR   r0, [r9]
        ; read data
        LDR   r1, [r8]      
        ; disable bus
        BIC   r0, r0, #(ENABLE)
        STR   r0, [r9]

        ; mask data to get curent cursor position
        AND   r1, r1, #CURSOR_POS_MASK
 
        ; if line feed char then add line length to pos
        CMP   r2, #LINE_FEED
        ADDEQ r0, r1, #(LINE)
        
        ; if carrege return clear line pos bits XXXX0000 
        CMP   r2, #CARR_RET
        BICEQ r0, r1, #(BEGIN_LINE_CLEAR)        

        ; add command to position
        ADD   r0, r0, #(MOVE_CUR_OFFSET)

        POP{LR,r1,r2}
        MOV PC,LR
;---------------------------

;---------------------------
;procedure ClearScreen
; clears the screen and places cursor in top left corner
;---------------------------
ClearScreen
        PUSH{LR,r0,r9,r8}
        ;load data and control pointers        
        MOV   r8, #LCD_Data
        MOV   r9, #LCD_Control
        ;wait for device
        BL    IOWait
        ;clear screen
        
        LDR   r0, [r9]
        ; set control
        BIC   r0, r0, #(READNW | REGSEL)
        STR   r0, [r9]
        ; set data
        MOV   r0, #CLEAR
        STR   r0, [r8]
        
        ; strobe enable on
        LDR   r0, [r9]
        ORR   r0, r0, #(ENABLE)
        STR   r0, [r9]

        ; strobe enable off
        BIC   r0, r0, #(ENABLE)
        STR   r0, [r9]

        POP{LR,r0,r9,r8}
        MOV   PC,LR
;---------------------------

;---------------------------
;procedure IOWait
; waits for the io to be ready
;---------------------------
IOWait
        PUSH{r0,r1}

IOWait_repeat

        ; load curent control
        LDR   r0, [r9]

        ; set read&control, unset enable
        ORR   r0, r0, #(READNW)
        BIC   r0, r0, #(ENABLE | REGSEL)
        STR   r0, [r9]
        
        ; enable bus too
        ORR   r0, r0, #(ENABLE)
        STR   r0, [r9]
    
        ; read data
        LDR   r1, [r8]      
            
        ; disable bus
        BIC   r0, r0, #(ENABLE)
        STR   r0, [r9]

        ; test bit 7 is low else repeat
        ANDS  r1, r1, #0x80
        BNE   IOWait_repeat

        POP{r0,r1}
        MOV   PC,LR

;--------------------------
;procedure LCDInit
; initialises control signals
;--------------------------
LCDInit
        PUSH{r0,r8,r9}
        MOV   r8, #LCD_Data
        MOV   r9, #LCD_Control

        MOV   r0, #0
        STR   r0, [r8] ; init data
        MOV   r0, #0x00000010
        STR   r0, [r9] ; init control

        POP{r0,r8,r9}
        MOV   PC, LR
;--------------------------