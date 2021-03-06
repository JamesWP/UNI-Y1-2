;-----------------------------------------------------------------------------;
;---                 LAB KERNEL V1.0                                       ---;
;---                 ---------------                                       ---;
;--- Author: James W Peach                                                 ---;
;-----------------------------------------------------------------------------;

;---------------
;---  EXCEPTION VECTOR TABLE
      B     vReset
      B     vUndef
      B     vSupervisor    
      B     vPreAbort
      B     vDataAbort
      B     .           ; unused vector
      B     vIRQ        ; interrupt request
      B     vFIQ        ; fast interrupt request
;---------------

;---------------
;- KERNEL MODE LITERALS
SPSR_SYSMODENI  EQU  0xDF
SPSR_USERNI     EQU  0xD0
;---------------


;---------------
;-    deals with resets and initialises kernel and then calls user code @main
vReset
      ;- initialise supervisor mode stack
      ADRL  SP,  sSVC
      ;- switch to system mode
      MOV   LR, #(SPSR_SYSMODENI)     ; system mode no interrupts
      MSR   CPSR_c, LR
      ;- initialise user mode stack
      ADRL  SP,  sUSR
      ;- initialise perhiperals
      BL    PeripheralInitialise
      ;- switch to user mode
      MOV   LR, #(SPSR_USERNI)      ; user mode no interrupts
      MSR   CPSR_c, LR
      ;- call user code
      ADR   LR,  Main
      MOVS  PC,  LR
;---------------
      
;---------------    
;-    deals with undefined instructions
vUndef
      B   .
;---------------
      
;---------------    
;-    deals with supervisor calls
SVC_OP_MASK EQU 0xFF000000
vSupervisor
      PUSH  {LR,r12,r11}

      ; save spsr incase nested SVC calls
      MRS   r11, SPSR ;save spsr in reg
      PUSH  {r11}

      LDR   r12, [LR, #-4]  ; load actual SVC instruction
      BIC   r12,  r12,  #(SVC_OP_MASK) ; mask off opcode
      ; supervisor call 
      CMP   r12, #SVCMax
      BHI   SVCUnknown

      ADR   r11, SVCRoutines
      LDR   PC, [r11,r12 LSL #2]

vSupervisor_return
      ; return to user code

      ; return saved value of saved flags
      POP   {r11}; containing saved spsr
      MSR   SPSR, r11

      POP   {LR,r12,r11}
      MOVS  PC, LR ; return to user code here

;-- this include should define symbols:
;--                                    SVCRoutines   ; a table of routines
;--                                                  ; each should return to: 
;--                                                  ; vSupervisor_return
;--                                    SVCMax        ; the max number of opcode
;--                                    SVCUnknown    ; report the error proc
GET   KernelSVC.s

;-- this include should define symbols :
;--                                      GetTimer    ; a procedure to return 
;--                                                  ; curent timer value in r0 
GET   KernelTimer.s

;-- this include defines procedures to interface the KernelLCD
GET   KernelLCD.s

;-- this include defines procedures to interface the buttons
GET   KernelButtons.s

;---------------
;-- procedure PeripheralInitialise initialies perhiperals
PeripheralInitialise
      PUSH  {LR}
      BL    LCDInit           ; init control signals
      BL    EnableBacklight   ; enable backlight
      BL    ClearScreen       ; clear screen
      POP   {LR}
      MOV   PC, LR
;---------------

;---------------    
;-    deals with prefetch aborts
vPreAbort
      B   .
;---------------
      
;---------------    
;-    deals with data aborts
vDataAbort
      B   .
;---------------
      
;---------------    
;-    deals with interrupt handling
vIRQ  
      B   .
;---------------
      
;---------------    
;-    deals with fast interrupt handling
vFIQ
      B   .
;---------------
     

GET   UserProgram.s
 
;---------------
;-    stack areas
      ALIGN
      DEFS  4096
sUSR  ; user mode stack area
      DEFS  4096
sSVC  ; svc mode stack area
      DEFS  4096
sABO  ; abort mode stack area
      DEFS  4096
sUDE  ; undefined mode stack area
      DEFS  4096
sIRQ  ; interrupt mode stack area
      DEFS  4096
sFIQ  ; fast interrupt mode stack area
;---------------


; FIN
