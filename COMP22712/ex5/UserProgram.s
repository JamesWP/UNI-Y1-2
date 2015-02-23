;-----------------------------------------------------------------------------;
;-----                    UserProgram Test                                ----;
;-----                    a program to test svc calls                     ----;
;--- Author: James W Peach                                                 ---;
;-----------------------------------------------------------------------------;

;------------------------
;-- literals
TIMER_MAX_VAL   EQU     0xFF

;------------------------
;-- variables memory
my_counter                ; number of 100ms passed scince bootup
        DEFW   0x0

;------------------------
;- UserProgram
Main    
        ; init timer
        MOV   r10, #0           ; r10 is miliseconds not counted ; prev remainder
        MOV   r9 , #0           ; last value of counter
1
        ; load curent counter
        SVC   3
        ; find difference
        
        ; if new < old
        CMP   r0, r9
        ;  time passed is ( 255 - (old - new) )
        SUBLS r1, r9, r0
        RSBLS r1, r1, #TIMER_MAX_VAL
        ; else 
        ;  time passed is ( new - old )
        SUBHI r1, r0, r9

        ; save old timer
        MOV   r9, r0

        ;divide time passed by 100 and get quotient: r0 and remainder: r2

        ; first add previous remainder
        ADD r1, r1, r10

        ;r1 time passed
        MOV   r0, r1
        MOV   r1, #100
        BL    Divide            ; r0: numerator, r1: divisor
                                ; r0: quotient, r2: remainder 

        ; save new remainder
        MOV   r10, r2


        ; miliseconds passed is += r0:(time passed / 100)
        LDR   r3, my_counter
        ADD   r3, r3, r0
        STR   r3, my_counter

        ;  display new value

        B    %b1
;------------------------

;------------------------
;- procedure Divide(R0=numerator IN/quotient OUT, R1=denominator IN
;-      ,R2=remainder OUT) 
;-  computed integer division R0/R1
;-  returns quotient in R0 and remainder in R2
;- 
;- code adapted from bcd_conver.s @ /opt/info/courses/COMP227...
Divide
        MOV     r2, #0                  ; AccH
        MOV     r3, #32                 ; Number of bits in division
        ADDS    r0, r0, r0              ; Shift dividend

1       ADC     r2, r2, r2              ; Shift AccH, carry into LSB
        CMP     r2, r1                  ; Will it go?
        SUBHS   r2, r2, r1              ; If so, subtract
        ADCS    r0, r0, r0              ; Shift dividend & Acc. result
        SUB     r3, r3, #1              ; Loop count
        TST     r3, r3                  ; Leaves carry alone
        BNE     %b1                     ; Repeat as required

        MOV     pc, lr                  ; Return
;------------------------
