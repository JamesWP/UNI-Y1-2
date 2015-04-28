;-----------------------------------------------------------------------------;
;-----                Final Project - Multi octave keyboard               ----;
;--- Author: James W Peach                                                 ---;
;-----------------------------------------------------------------------------;

;------------------------
;-- literals
OCTAVE_LOW      EQU  0
OCTAVE_HIGH     EQU  1
SOUND_NEUTERAL  EQU  0
SOUND_SHARP     EQU  1
NOKEY           EQU  -1
;------------------------
;-- variables memory
MODE_OCTAVE DEFW 0x0
MODE_SOUND  DEFW 0x0
;------------------------
;- UserProgram
;- 
;L------O-----A-----------------C----------------------------------------------;
Main
        ; initialise variables: 
        MOV   r0, #OCTAVE_LOW
        STR   r0, MODE_OCTAVE

        MOV   r0, #SOUND_NEUTERAL
        STR   r0, MODE_SOUND

MainL   ; main loop:

        BL    DrawScreen

        SVC   SVC_RKEY          ; returns key or NOKEY
        CMP   r0, #NOKEY
        MOVEQ r0, #NOT_NONE     ; if none then dont sound note anymore
        ;SVCEQ SVC_NOT
        BEQ   MainL
        
        ;if key is * then enter lower octave
        CMP   r0, #KEY_H
        BEQ   SharpToggle
        ;if key is 0 then enter upper octave
        CMP   r0, #KEY_0
        BEQ   UpperOctave
        ;if key is # then toggle sharp mode      
        CMP   r0, #KEY_S
        BEQ   LowerOctave
        
        ;if none of the above then change note to the key #('0') to #('8')
        ; calculate freq val
        ; sound correct key

        SUB   r0, r0, #('1')
        CMP   r0, #('8')
        BHI   MainL

        ; load modes
        LDR   r1, MODE_SOUND
        LDR   r2, MODE_OCTAVE
        ; calculate note value to send to buzzer
        BL    GetNoteVal
        ; send note to buzzer hardware
        SVC   SVC_NOT


        ; repeat
        B     MainL             ; repeat main loop

SharpToggle
        LDR   r0, MODE_SOUND
        CMP   r0, #SOUND_NEUTERAL
        MOVEQ r0, #SOUND_SHARP
        MOVNE r0, #SOUND_NEUTERAL
        STR   r0, MODE_SOUND
        B     MainL             ; repeat main loop

UpperOctave
        MOV   r0,  #OCTAVE_HIGH
        B     ChangeOctave
LowerOctave 
        MOV   r0,  #OCTAVE_LOW
ChangeOctave
        ; change octave to the one in r0
        STR   r0, MODE_OCTAVE
        B     MainL             ; repeat main loop

DrawScreen
        PUSH{r0,r1,r2}

        ;SVC   SVC_CLER

        ; load modes
        ;LDR   r1, MODE_SOUND
        ;CMP   r1, #SOUND_SHARP
        ;ADRLEQ r0, SHARP_TAG
        ;ADRLNE r0, NORM_TAG
        ;SVC   SVC_STR

        ;LDR   r2, MODE_OCTAVE
        ;CMP   r2, #OCTAVE_HIGH
        ;ADRLEQ r0, HIGH_TAG
        ;ADRLNE r0, LOW_TAG
        ;SVC   SVC_STR

        POP{r0,r1,r2}
        MOV   PC,LR


;-------------------------------
;procedure GetNoteVal(R0=note / value OUT,R1=sharp,R2=octave)
; returns the value to be sent to the buzzer hardware
;-
;- low+0    sharp+7
;- high+12  not+0
;-
;-------------------------------
GetNoteVal
        PUSH  {r1}
        CMP   r1, #SOUND_SHARP
        ADDEQ r0, r0, #SHARP_OFFSET 
        CMP   r2, #OCTAVE_HIGH
        ADDEQ r0, r0, #OCTHI_OFFSET
        ADR   r1, NotesTable
        LDR   r0, [r1, r0, LSL #0x1]
        POP   {r1}
        MOV   PC, LR
;-------------------------------

; auto generated code from: 
;https://docs.google.com/spreadsheets/d/1umkPg9fAEzA4-UB3L3uZDeU6IOwrI8WIphsAInxvuwo/edit?usp=sharing
SHARP_OFFSET  EQU 7
OCTHI_OFFSET  EQU 12
NotesTable
        DEFB 0x1DDD
        DEFB 0x1A9A
        DEFB 0x17B3
        DEFB 0x165F
        DEFB 0x13EE
        DEFB 0x11C1
        DEFB 0x0FD2
        DEFB 0x1C2F
        DEFB 0x0000
        DEFB 0x0000
        DEFB 0x151D
        DEFB 0x0000
        DEFB 0x10C2
        DEFB 0x0000
        DEFB 0x0EEE
        DEFB 0x0D4D
        DEFB 0x0BDA
        DEFB 0x0B2F
        DEFB 0x09F7
        DEFB 0x08E1
        DEFB 0x07E9
        DEFB 0x0E18
        DEFB 0x0000
        DEFB 0x0000
        DEFB 0x0A8F
        DEFB 0x0968
        DEFB 0x0861
        DEFB 0x0000

ALIGN
SHARP_TAG
        DEFB "Sharp "
NORM_TAG
        DEFB "Norm "
LOW_TAG
        DEFB "Low "
HIGH_TAG
        DEFB "High "