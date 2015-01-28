;------------------------------------------------------------------------
;            IO Library
;           JWP 2015 - COMP227
;           VERSION 1.0
;
; contains io utilities
;
;
; Last modified 28/Jan
;
;
; Known bugs: None
;
;------------------------------------------------------------------------


;---------------------------
;procedure PrintChar(R0=char)
; prints a single char on the LCD
;---------------------------
PrintChar
        PUSH{}
        ;check char in range
        ;print char
        ;wait for device ready
        POP{}
        MOV   PC,LR
;---------------------------


;---------------------------
;procedure PrintString(R0=string-pointer)
; prints a \0 terminated string pointed to by string-pointer
;---------------------------
PrintString
        PUSH{}
        ;load char + post increment
        ;check for termination char -> jump to end
        ;print char
        ;loop back
PrintString_end
        POP{}
        MOV   PC,LR
;---------------------------
