;------------------------------------------------------------------------
;            LCD Display
;           JWP 2015 - COMP227
;           VERSION 1.0
;
; prints strings on display and reacts to user input
;
;
; Last modified 28/Jan
;
;
; Known bugs: None
;
;------------------------------------------------------------------------
        B		Main

;---------------------------
; includes
;---------------------------
GET     "io.s"

;---------------------------
; literals/globals
;---------------------------


;---------------------------
; main entry of program
;
; r1 : curent state
;---------------------------
Main
        ;- set up stack pointer
        ADRL  SP, Stack_start

        MOV   r1, #0
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
        PUSH{}
        ; clear display
        ; switch state and set output
        POP{}
        MOV   PC,LR
;---------------------------

;---------------------------
;procedure Delay(R0=state)
; delays the correct amount for this state
; can be interrupted with a button press
;---------------------------
Delay
        PUSH{}
        ; clear display
        ; switch state and set output
        POP{}
        MOV   PC,LR
;---------------------------

;---------------------------
;procedure GetNextState(R0=state OUTPUT)
; gets the next state and returns value in r0
;---------------------------
Display
        PUSH{}
        ; clear display
        ; switch state and set output
        POP{}
        MOV   PC,LR
;---------------------------
