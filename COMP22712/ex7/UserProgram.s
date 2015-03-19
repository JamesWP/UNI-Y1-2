;-----------------------------------------------------------------------------;
;-----                    UserProgram Keyboard Test                       ----;
;-----                    a program to test keyboard                      ----;
;--- Author: James W Peach                                                 ---;
;-----------------------------------------------------------------------------;

;------------------------
;-- literals
NOKEY   EQU   -1
SML_DLY EQU   0x14000

;------------------------
;-- variables memory

;------------------------
;- UserProgram
;- 
;- psudocode:
;-- read key
;-- print key if not NOKEY 
;-- if # then clear screen
;-- small delay loop SML_DLY
Main    
        SVC   SVC_RKEY          ; returns key or NOKEY
        CMP   r0, #NOKEY
        BEQ   Main

        CMP   r0, #('#')        ; check for newline char
        BNE   PRN

        SVC   SVC_CLER
        B     Main              ; repeat main loop
        
PRN     SVC   SVC_CHAR
        MOV   r0, #SML_DLY      ; small delay loop
1       SUBS  r0, r0, #1        ; loop and decrease till 0
        BNE   %b1
        B     Main              ; repeat main loop