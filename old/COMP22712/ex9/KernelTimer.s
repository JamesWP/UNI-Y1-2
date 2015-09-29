;-----------------------------------------------------------------------------;
;---                 LAB KERNEL V1.0                                       ---;
;---                 ---------------                                       ---;
;---                 Timer                                                 ---;
;--- Author: James W Peach                                                 ---;
;-----------------------------------------------------------------------------;

;-- this include should define symbols :
;--                                      GetTimer    ; a procedure to return 
;--                                                  ; curent timer value in r0 

TimerMemLoc   EQU   0x10000008
GetTimer
        ADRL  r0, TimerMemLoc
        LDRB  r0, [r0]
        MOV   PC, LR
