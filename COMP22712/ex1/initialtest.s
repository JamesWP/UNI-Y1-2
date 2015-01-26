;------------------------------------------------------------------------
;            Traffic lights
;           JWP 2015 - COMP227
;           VERSION 1.0
;
; shows the traffic lights on the board working with no input
;
;
; Last modified today.
;
;
; Known bugs: None
;
;------------------------------------------------------------------------
				B		Main
;---------------------------
; literals
;---------------------------
NUM_STATES  EQU   8
IOBASE      EQU   0x10000000
DATA_MASK   EQU   0x000000FF
DELAY_SHIFT EQU   16
DELAY_ITTERATIONS EQU 0x40000 

;---------------------------
; main entry of program
;---------------------------
Main
        ;- set up stack pointer
        ADRL  SP, Stack_start
        ;- initialise state to state 0
        MOV   r0,#0
Main_o  BL    Output             	  ; Output(R0=0)
				;- go to next state 
				ADD   r0,r0,#1
				CMP   r0,#NUM_STATES
        MOVGE r0,#0				
				B     Main_o

;---------------------------
;procedure Output(R0=state)
;---------------------------
Output
        PUSH  {LR,r0,r1,r2,r3,r4}
        ADR   r1, Output_table
        MOV   r4, #IOBASE
        AND   r0, r0, #0xF
        LDR   r2, [r1, r0 LSL #2]  ; load in state record
        AND   r0, r2, #DATA_MASK    ; seperate delay
        MOV   r2, r2, LSR #16
        AND   r3, r2, #DATA_MASK    ; seperate lights
        
        STRB  r3, [r4]             ; update lights
        BL    Delay                 ; delay for r3 seconds Delay(R0=delay)
        POP   {LR,r0,r1,r2,r3,r4}
        MOV   PC, LR
;---------------------------
;table base each record two bytes, one for lights and one for delay
Output_table
        DEFW  0x00440001
				DEFW  0x00640001
				DEFW  0x00140003
				DEFW  0x00240001
				DEFW	0x00440001
				DEFW  0x00460001
				DEFW  0x00410003
				DEFW  0x00420001


LITERALS
;---------------------------
;procedure Delay(R0=seconds)
;---------------------------
Delay
				PUSH  {R0,R1}
Delay_start
				CMP		R0,#1
				BLT 	Delay_end 
			  ADRL  r1, DELAY_ITTERATIONS
Delay_loop
        SUBS  R1, R1, #1
				BNE   Delay_loop
				SUB   R0, R0, #1
				B     Delay_start
Delay_end
				POP		{R0,R1}
        MOV   PC, LR
;--------------------------

Stack_end
        DEFS  1024
Stack_start
