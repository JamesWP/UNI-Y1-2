;-----------------------------------------------------------------------------;
;---                 LAB KERNEL V1.0                                       ---;
;---                 ---------------                                       ---;
;---                 Button io                                             ---;
;--- Author: James W Peach                                                 ---;
;-----------------------------------------------------------------------------;
;-- this include defines procedures to interface the buttons


;-- LITERALS
ButtonsMem    EQU 0x10000004
ButtonsMask   EQU 0x3
ButtonsShift  EQU 0x6

GetButton
      ;load
      ADRL  r0, ButtonsMem
      LDRB  r0, [r0]
      MOV   r0, r0, LSR #ButtonsShift
      AND   r0, r0, #ButtonsMask
      MOV   PC,LR
      