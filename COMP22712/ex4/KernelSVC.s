;-----------------------------------------------------------------------------;
;---                 LAB KERNEL V1.0                                       ---;
;---                 ---------------                                       ---;
;---                 SVC calls                                             ---;
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
SVCRoutines_END
SVCMax EQU (SVCRoutines_END - SVCRoutines)/4


SVCRoutine0 
      B    .                                         ; user program end     

SVCRoutine1                                          ; print char r0 is char
      ;procedure PrintChar(R0=char)
      BL    PrintChar
      B     vSupervisor_return                        

SVCRoutine2                                          ; print string r0 is
      BL    PrintString                              ; string pointer
      B     vSupervisor_return                       

SVCRoutine3
      B     vSupervisor_return     

SVCUnknown
      B     vSupervisor_return