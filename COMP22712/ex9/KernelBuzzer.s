;-----------------------------------------------------------------------------;
;---                 LAB KERNEL V1.0                                       ---;
;---                 ---------------                                       ---;
;---                 Button io                                             ---;
;--- Author: James W Peach                                                 ---;
;-----------------------------------------------------------------------------;

;-- this include defines procedures to handle sending notes to the buzzer
;--                                  SendNote             : called periodically
;--                                                       : to handle key read
;--                                  BuzzInit             : initialize buzzer

;L------O-----A-----------------C----------------------------------------------;

NOTEPORTHI    EQU   0x2000_0000
NOTEPORTLO_O  EQU   0x1
NOT_NONE      EQU   0x0
;-------------------------------
;procedure SendNote(R0)
SendNote
        PUSH  {LR}
        ADRL  r14, NOTEPORTHI
        STRB  r0, [r14, #NOTEPORTLO_O]  
        ; shift right by 8 bits (one byte)
        LSR    R0, R0, #8
        STRB  r0, [r14]  
        POP   {LR}
        MOV   PC, LR