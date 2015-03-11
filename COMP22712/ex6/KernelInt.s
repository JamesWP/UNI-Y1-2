;-----------------------------------------------------------------------------;
;---                 LAB KERNEL V1.0                                       ---;
;---                 ---------------                                       ---;
;---                 Button io                                             ---;
;--- Author: James W Peach                                                 ---;
;-----------------------------------------------------------------------------;
;-- this include defines procedures to handle interrupts:
;--                                  InitialiseInterrupts : inits interrupts
;--                                  InterruptHandler     : handles interupt

;-- LITERALS
TIMER_COMP_BYTE   EQU   0x1000000C        ; timer compare mem loc
INTERUPT_BITS_O   EQU   0xC               ; interrupt flags offset
INTERUPT_ENABLE_O EQU   0x10              ; interrupt enable offset
TIMER_ENABLE_BIT  EQU   0b00000001        ; CPSR timer enable bit

TIMER_DELAY       EQU   100

INTTERUPT_TIMER_MASK EQU 0b00000001

;-------------------------
;-- procedure  InitialiseInterrupts
;-- 
;-- initialises hardware interrupts
;-- sets timer interrupt to interrupt @100ms 
InitialiseInterrupts
      PUSH {r0,r1}

      ADR   r1, TIMER_COMP_BYTE
      BL    GetTimer
      ; set timer interrupt to be NOW+@100ms
      ADD   r0, r0, #TIMER_DELAY
      STR   r0, [r1]

      ; disable all interrupts except timer
      MOV   r0, #(TIMER_ENABLE_BIT)
      STR   r0, [r1, #INTERUPT_ENABLE_O]

      POP  {r0,r1}
      MOV   PC, LR

InterruptHandler
      ; load interrupt port into r0
      PUSH {LR,r0}
      ADR  r0, TIMER_COMP_BYTE
      LDRB r0, [r0, INTERUPT_BITS_O]
      ; work out what was triggered interupt

      ; try handler 1
      BL    InterruptTimer

      ; exit handler
      POP {LR,r0}
      SUBS  PC, LR, #4

InterruptTimer
      ; test r0 for timer flags
      TST   r0, #(INTTERUPT_TIMER_MASK)
      MOVNE PC, LR                              ; if no flag return
      ; else do timer stuff

      ; set next timer point
      BL    GetTimer
      ADD   r0, r0, #TIMER_DELAY
      ; wrap timer compare value
      STR   r0, [r1]
      ; return
      MOV   PC, LR