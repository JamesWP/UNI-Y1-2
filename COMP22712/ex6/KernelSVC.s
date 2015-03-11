;-----------------------------------------------------------------------------;
;---                 LAB KERNEL V1.0                                       ---;
;---                 ---------------                                       ---;
;---                 SVC calls                                             ---;
;--- Author: James W Peach                                                 ---;
;-----------------------------------------------------------------------------;

;-- this include should define symbols:
;--                                    SVCRoutines   ; a table of routines
;--                                                  ; each should return to: 
;--                                                  ; vSupervisor_return
;--                                    SVCMax        ; the max number of opcode
;--                                    SVCUnknown    ; report the error proc

SVCRoutines
      DEFW SVCRoutine0
      DEFW SVCRoutine1
      DEFW SVCRoutine2
      DEFW SVCRoutine3
      DEFW SVCRoutine4
      DEFW SVCRoutine5
SVCRoutines_END

SVCMax EQU (SVCRoutines_END - SVCRoutines)/4

;-- Routines


SVC_HALT  EQU  0
SVCRoutine0 
      B    .                                         ; user program end     

SVC_CHAR  EQU  1
SVCRoutine1                                          ; print char r0 is char
      ;procedure PrintChar(R0=char)
      BL    PrintChar
      B     vSupervisor_return                        

SVC_STR   EQU  2
SVCRoutine2                                          ; print string r0 is
      BL    PrintString                              ; string pointer
      B     vSupervisor_return                       


SVC_TIME  EQU  3
SVCRoutine3                                          ; get curent timer value
      BL    GetTimer                                 ; into r0
      B     vSupervisor_return

SVC_CLER  EQU  4
SVCRoutine4                                          ; get curent timer value
      BL    ClearScreen                              ; into r0
      B     vSupervisor_return

SVC_BUTT  EQU  5
SVCRoutine5                                          ; get curent timer value
      BL    GetButton                                ; into r0
      B     vSupervisor_return

SVCUnknown
      B     .                                        ; unknown SVC call, hang..