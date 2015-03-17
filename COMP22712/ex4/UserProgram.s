;-----------------------------------------------------------------------------;
;-----                    UserProgram Test                                ----;
;-----                    a program to test svc calls                     ----;
;-----------------------------------------------------------------------------;

Main    
        ADR   r0, StringTest  ; load string
        SVC   2               ; print string
        SVC   0               ; user code end

StringTest      DEFB    "Hello Kernel 1.0\r\nGood Bye",0x00
