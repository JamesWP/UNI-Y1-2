;-----------------------------------------------------------------------------;
;---                 LAB KERNEL V1.0                                       ---;
;---                 ---------------                                       ---;
;---                                                                       ---;
;--- Author: James W Peach                                                 ---;
;-----------------------------------------------------------------------------;
;-- this include defines procedures to handle interrupts:
;--                                  InitialiseInterrupts : inits interrupts
;--                                  InterruptHandler     : handles interupt

;-- LITERALS
TIMER_COMP_BYTE   EQU   0x1000_000C        ; timer compare mem loc
INTERUPT_BITS_O   EQU   0xC                ; interrupt flags offset
INTERUPT_ENABLE_O EQU   0x10               ; interrupt enable offset
TIMER_ENABLE_BIT  EQU   0b0000_0001        ; CPSR timer enable bit

TIMER_DELAY       EQU   1
TIMER_MAX         EQU   0xFF

INTTERUPT_TIMER_MASK EQU 0b0000_0001

;-- VARIABLES
TIMER_CURENT_VALUE      DEFW  0 

;-------------------------
;-- procedure  InitialiseInterrupts
;-- 
;-- initialises hardware interrupts
;-- sets timer interrupt to interrupt @100ms 
InitialiseInterrupts
      PUSH {r0,r1,LR}

      ADRL  r1, TIMER_COMP_BYTE
      BL    GetTimer
      ; set timer interrupt to be NOW+@100ms
      ADD   r0, r0, #TIMER_DELAY
      STR   r0, [r1]

      ; disable all interrupts except timer
      MOV   r0, #(TIMER_ENABLE_BIT)
      STR   r0, [r1, #INTERUPT_ENABLE_O]

      POP  {r0,r1,LR}
      MOV   PC, LR

; called when an interrupt happens
; should pass to each handler in turn to decide who should process event
InterruptHandler
      ; load interrupt port into r0
      PUSH {LR,r0}
      ADRL r0, TIMER_COMP_BYTE
      LDRB r0, [r0, #INTERUPT_BITS_O]
      ; work out what was triggered interupt

      ; BL list of handlers  ( only one handler at the moment )
      BL    InterruptTimer    ; handle timer intterupt if triggered

; called at the end of the list of handlers if none found or
; or jumped to if a handler successfully completes
InterruptComplete
      ; exit handler
      POP {LR,r0}
      SUBS  PC, LR, #4        ; return to user land

InterruptTimer
      ; test r0 for timer flags
      TST   r0, #(INTTERUPT_TIMER_MASK)
      MOVEQ PC, LR                              ; if no flag return
      ; else do timer stuff
      PUSH{LR,r1}

      ; load offset point
      ADRL  r1, TIMER_COMP_BYTE

      ;clear interrupt bit
      BIC   r0, r0, #(INTTERUPT_TIMER_MASK)
      STRB  r0, [r1, #INTERUPT_BITS_O]

      ; store incrament in timer
      LDR   r0, TIMER_CURENT_VALUE
      ADD   r0, r0, #1
      STR   r0, TIMER_CURENT_VALUE

      ; set next timer point
      BL    GetTimer
      ADD   r0, r0, #TIMER_DELAY
      ; if timer is too large subtract MAX
      CMP   r0, #TIMER_MAX
      SUBHI r0, r0, #TIMER_MAX
      ; wrap timer compare value
      STR   r0, [r1]

      BL    KeyboardScan

      ; return
      POP{LR,r1}
      B     InterruptComplete