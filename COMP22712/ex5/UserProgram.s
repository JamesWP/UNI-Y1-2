;-----------------------------------------------------------------------------;
;-----                    UserProgram Test                                ----;
;-----                    a program to test svc calls                     ----;
;--- Author: James W Peach                                                 ---;
;-----------------------------------------------------------------------------;

;------------------------
;-- literals
TIMER_MAX_VAL   EQU     256

;------------------------
;-- variables memory
my_counter                ; number of 100ms passed scince bootup
        DEFW   0x0

;------------------------
;- UserProgram
;- 
;-  Psudo code: 
;-     read timer value
;-     calculate difference from last read
;-     accumulate difference in counter
;-     if difference > 0 
;-       update display
;-       if countdown is reached
;-         reset countodwn and timer to 0
;-       if countdown is active
;-         if button still pressed: continue
;-         else reset countdown to 0
;-       else if reset button is pressed
;-         set countdown = gettime + 0xFF000
;-      

Main    
        ; init timer
        MOV   r10, #0           ; r10 is miliseconds not counted ; prev remainder
        MOV   r9 , #0           ; last value of counter
        MOV   r8 , #0           ; reset countdown
        STR   r8 , my_counter

        ADRL  r0 , MESSAGE
        SVC   SVC_STR
1
        ; load curent counter
        SVC   SVC_TIME
        ; find difference
        
        ; if new < old
        CMP   r0, r9
        ;  time passed is ( 256 - (old - new) )
        SUBLT r1, r9, r0
        RSBLT r1, r1, #TIMER_MAX_VAL
        ; else 
        ;  time passed is ( new - old )
        SUBGE r1, r0, r9

        ; save old timer
        MOV   r9, r0

        ; miliseconds passed is += r0:(time passed / 100)
        LDR   r3, my_counter
        ADD   r3, r3, r1
        STR   r3, my_counter

        ; if delta is >0 then display new value else loop
        CMP   r1, #0
        BEQ   %b1

        MOV   r7, #0
        MOV   r0, r3
        BL    DisplayTimer

        ; read button state
2       SVC   SVC_BUTT
        CMP   r0, #1
        BEQ   %b2

        ; if(contdown!=0)
        CMP   r8, #0
        BEQ   %f3
        
        CMP   r8, r3
        ; if limit reached save counter 0 and remove countdown
        MOVLO r8, #0
        MOVLO r0, #0
        STRLO r0, my_counter
        ; if button not pressed remove countdown
        SVC   SVC_BUTT
        CMP   r0, #2
        MOVNE r8, #0
        B     %b1     ;; repeat timer loop
3
        ; else if (button pressed)
        SVC   SVC_BUTT
        CMP   r0, #2
        ; store limit 
        ADDEQ r8, r3, #0xFF000
        B     %b1     ;; repeat timer loop

        
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


;------------------------
;-procedure DisplayTimer(R0=decimal time)
;- displays the passed value on the LCD in the curent position
tenthou DEFW 10000
hundthou DEFW 100000

DisplayTimer
        PUSH {r0,r1,r2,r3,LR}

        ; pre devide value and get remainder to wrap value if large
        LDR     r1, hundthou
        BL      Divide
        MOV     r3, r2

        ; move cursor to beginning of line
        MOV     r0, #CARR_RET
        SVC     SVC_CHAR

        MOV     r0, r3

        ; display values

        ;---- tenthou   -----
        ;Divide r0 / 10000
        LDR     r1, tenthou
        BL      Divide
        ;Print quotient
        ADD     r0, r0, #('0')
        SVC     SVC_CHAR

        ;---- thousands -----
        ;Divide r0 / 1000
        MOV     r1, #1000
        MOV     r0, r2
        BL      Divide
        ;Print quotient
        ADD     r0, r0, #('0')
        SVC     SVC_CHAR

        ;--- decimal point --
        MOV     r0, #('.')
        SVC     SVC_CHAR

        ;---- hundreds  -----
        ;Divide r0 / 100
        MOV     r1, #100
        MOV     r0, r2
        BL      Divide
        ;Print quotient
        ADD     r0, r0, #('0')
        SVC     SVC_CHAR
        
        ;---- tens      -----
        ;Divide r0 / 10
        MOV     r1, #10
        MOV     r0, r2
        BL      Divide
        ;Print quotient
        ADD     r0, r0, #('0')
        SVC     SVC_CHAR

        ;---- units -----
        ;print remainder
        MOV     r0, r2
        ADD     r0, r0, #('0')
        SVC     SVC_CHAR

        POP {r0,r1,r2,r3,LR}
        MOV PC, LR
;------------------------

MESSAGE DEFB 'Stopwatch v1.1\r\n', 0x0