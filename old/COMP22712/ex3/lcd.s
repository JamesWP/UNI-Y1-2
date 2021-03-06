;------------------------------------------------------------------------
;            LCD Display
;           JWP 2015 - COMP227
;           VERSION 1.0
;
; prints strings on display and reacts to user input
;  top button displays string Top Button, and bottom button displays
;  string \r\nBottom Button 
;
;
; Last modified 28/Jan, 04/Feb
;
;
; Known bugs: None
;
;------------------------------------------------------------------------
        B     Main

;---------------------------
; includes
;---------------------------
GET     io.s

;---------------------------
; literals/globals
;---------------------------
BUTTON_STATE_NONE   EQU 0
BUTTON_STATE_TOP    EQU 1
BUTTON_STATE_BOTTOM EQU 2
G_BUTTON_FLAG       DEFW 0        ; see button states above
DELAY_ITRS          DEFW 0x10000
IOBASE              EQU 0x10000000
IO_BUTTON_OFFSET    EQU 0x4
BUTTON_TOP_MASK     EQU 0x40
BUTTON_BOT_MASK     EQU 0x80

;---------------------------
; main entry of program
;
; r1 : curent state
;---------------------------
Main
        ;- set up stack pointer
        ADRL  SP, Stack_start

        MOV   r1, #0
      
        BL    EnableBacklight
Main_loop
        ; display curent message
        BL    Display
        ; delay with button interupt
        BL    Delay
        ; change state
        BL    GetNextState
        B     Main_loop


;---------------------------
;procedure Display(R0=state)
; sets the display to the correct message for this state
;---------------------------
Display
        PUSH{r0,r1,LR}
        ; switch state and set output

        ADR   r1, G_state0string     ; default string        

        ;state 0 G_state0string
        CMP   r0, #0
        ADREQ r1, G_state0string 

        ;state 1 G_state1string
        CMP   r0, #1
        ADREQ r1, G_state1string 

        ;state 2 G_state2string
        CMP   r0, #2
        ADREQ r1, G_state2string 

        ;state 3 G_state3string
        CMP   r0, #3
        ADREQ r1, G_state3string 

        BL    ClearScreen             ; call clear screen

        MOV   r0,r1                   ; call print string
        BL    PrintString

        POP{r0,r1,LR}
        MOV   PC,LR
;---------------------------

;---------------------------
;procedure Delay(R0=state)
; delays the correct amount for this state
; can be interrupted with a button press
;---------------------------
Delay
        PUSH{r0,r2,r3}
        LDR   r0, DELAY_ITRS
Delay_repeat
        LDR   r3, G_BUTTON_FLAG
        CMP   r3, #BUTTON_STATE_NONE
        BNE   Delay_skip

        ; test state of button and update flags
        MOV   r2, #IOBASE
        MOV   r3, #BUTTON_STATE_NONE

        ; load button value
        LDRB  r2, [r2,#IO_BUTTON_OFFSET]

        ;test for top button
        ANDS r3, r2, #BUTTON_TOP_MASK
        MOVNE r3, #BUTTON_STATE_TOP
        BNE  Delay_store_btn

        ANDS r3, r2, #BUTTON_BOT_MASK
        MOVNE r3, #BUTTON_STATE_BOTTOM

Delay_store_btn
        STRNE r3, G_BUTTON_FLAG
        SUBS  r0, r0, #1
        BGT   Delay_repeat
Delay_skip
        POP{r0,r2,r3}
        MOV   PC,LR
;---------------------------

;---------------------------
;procedure GetNextState(R0=state OUTPUT)
; gets the next state and returns value in r0
;---------------------------
GetNextState
        PUSH{r1,r2}

        LDR   r1, G_BUTTON_FLAG       ; if button is none then skip .. else
        CMP   r1, #BUTTON_STATE_NONE
        BEQ   GetNextState_next
        
        MOV   r2, #BUTTON_STATE_NONE  ; reset flags
        STR   r2, G_BUTTON_FLAG

        CMP   r1, #BUTTON_STATE_TOP   ; if button is top...
        MOVEQ r0, #2                  ; goto state  2
        MOVNE r0, #3                  ; else goto state 3
        B     GetNextState_end        ; return

GetNextState_next
        CMP   r0, #0                  ; state 0 -> 1
        MOVEQ r0, #1                  ; state 1 -> 0 
        MOVNE r0, #0                  ; state 2 -> 0
                                      ; state 3 -> 0
        
GetNextState_end
        POP{r1,r2}
        MOV   PC,LR
;---------------------------


G_state0string
        DEFB  "Hello World",0x0
G_state1string
        DEFB  "Hi COMP22712!",0x0
G_state2string
        DEFB  "Top Button",0x0
G_state3string
        DEFB  "\r\nBottom Button",0x0
align

Stack_end
        DEFS 0x1000
Stack_start
