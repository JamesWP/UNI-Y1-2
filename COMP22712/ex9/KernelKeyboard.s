;-----------------------------------------------------------------------------;
;---                 LAB KERNEL V1.0                                       ---;
;---                 ---------------                                       ---;
;---                 Button io                                             ---;
;--- Author: James W Peach                                                 ---;
;-----------------------------------------------------------------------------;

;-- this include defines procedures to handle keyboard scanning:
;--                                  KeyboardScan         : called periodically
;--                                                       : to handle key read
;--                                  KeyboardInit         : called to init the
;--                                                       : keyboard

LAST_KEY_PRESSED    DEFW    -1
CURENT_KEY          DEFW    -1
CURENT_KEY_TIME     DEFW    0

;-- LITERALS
KEY_DAT     EQU 0x2000_0002
KEY_CON_O   EQU 0x1
KEY_CON_VAL EQU 0x1F
KEYHLD_TIME EQU 2

;--- KEYMAP   : a mapping from digits to keys and vice versa

;-- line 0
KEY_LINE_0    EQU 0b0010_0000
KEY_3   EQU   ('3')
KEY_6   EQU   ('6')
KEY_9   EQU   ('9')
KEY_H   EQU   ('#')

;-- line 1
KEY_LINE_1    EQU 0b0100_0000
KEY_2   EQU   ('2')
KEY_5   EQU   ('5')
KEY_8   EQU   ('8')
KEY_0   EQU   ('0')

;-- line 2
KEY_LINE_2    EQU 0b1000_0000
KEY_1   EQU   ('1')
KEY_4   EQU   ('4')
KEY_7   EQU   ('7')
KEY_S   EQU   ('*')

;-- line keys
LINE_KEY_0 EQU 0b0000_0001
LINE_KEY_1 EQU 0b0000_0010
LINE_KEY_2 EQU 0b0000_0100
LINE_KEY_3 EQU 0b0000_1000

KeyboardInit
        PUSH{r0,r1}
        MOV   r0, #-1
        STRB  r0, LAST_KEY_PRESSED
        STRB  r0, CURENT_KEY

        ADRL  r0, KEY_DAT
        MOV   r1, #KEY_CON_VAL
        STRB  r1, [r0, #KEY_CON_O]

        POP{r0,r1}
        MOV   PC, LR


;---------------------
;--procedure KeyboardScan
;-- scans the keys for presses and updates LAST_KEY_PRESSED
;-
;- puudeocode
;-
;-  SCAN
;---  SCAN LINE 1
;-----  if any key is pressed save key in reg key_down
;---  SCAN LINE 2
;-----  if any key is pressed save key in reg key_down
;---  SCAN LINE 3
;-----  if any key is pressed save key in reg key_down
;-
;-  ACCUMULATE
;---  IF key_down is CURENT_KEY
;-----  INCR  CURENT_KEY_TIME
;---  ELSE
;-----  ZERO CURENT_KEY_TIME
;-----  SET CURENT_KEY = key_down
;-
;-  IF CURENT_KEY_TIME > threshold
;---  UPDATE #LAST_KEY_PRESSED
;---  CLEAR CURENT_KEY and CURENT_KEY
;---------------------


KeyboardScan
        PUSH{LR,r1,r2}
        BL    GetKeyDown                ; get current key
        LDR   r2, CURENT_KEY_TIME       ; store and update CURENT_KEY_TIME
        LDR   r1, CURENT_KEY            ; and CURENT_KEY
        CMP   r0, r1
        ADDEQ r2, r2, #1
        MOVNE r2, #0
        MOVNE r1, r0
        STR   r2, CURENT_KEY_TIME
        STR   r1, CURENT_KEY

        CMP   r2, #KEYHLD_TIME
        STRHI r1, LAST_KEY_PRESSED      ; store new key if down for longer than
        MOVHI r2, #-1                   ;... LAST_KEY_PRESSED
        STRHI r2, CURENT_KEY

        POP{LR,r1,r2}
        MOV   PC, LR
;---------------------


;---------------------
;--procedure GetKeyDown(R0=key out)
;-- returns the key that is currently down or -1 if no key is down
;-- 
;-- scans each row in turn and gets the last key pressed 
GetKeyDown
        PUSH{r1,r2}
        MOV   r2, #-1
        ADRL  r0, KEY_DAT

        ; scan line 0
        MOV   r1, #KEY_LINE_0
        STRB  r1, [r0]
        LDRB  r1, [r0]

        TST   r1, #LINE_KEY_0
        MOVNE r2, #KEY_3
        TST   r1, #LINE_KEY_1
        MOVNE r2, #KEY_6
        TST   r1, #LINE_KEY_2
        MOVNE r2, #KEY_9
        TST   r1, #LINE_KEY_3
        MOVNE r2, #KEY_H

        ; scan line 1
        MOV   r1, #KEY_LINE_1
        STRB  r1, [r0]
        LDRB  r1, [r0]

        TST   r1, #LINE_KEY_0
        MOVNE r2, #KEY_2
        TST   r1, #LINE_KEY_1
        MOVNE r2, #KEY_5
        TST   r1, #LINE_KEY_2
        MOVNE r2, #KEY_8
        TST   r1, #LINE_KEY_3
        MOVNE r2, #KEY_0

        ; scan line 2
        MOV   r1, #KEY_LINE_2
        STRB  r1, [r0]
        LDRB  r1, [r0]

        TST   r1, #LINE_KEY_0
        MOVNE r2, #KEY_1
        TST   r1, #LINE_KEY_1
        MOVNE r2, #KEY_4
        TST   r1, #LINE_KEY_2
        MOVNE r2, #KEY_7
        TST   r1, #LINE_KEY_3
        MOVNE r2, #KEY_S

        ; move result into r0
        MOV   r0, r2

        POP{r1,r2}
        MOV   PC, LR

;----------------------------
;--ReadKey(r0=key pressed)
;-- reads a key from the buffer and marks it read
;----------------------------
ReadKey 
        PUSH{r1}
        LDR   r0, LAST_KEY_PRESSED
        MOV   r1, #-1
        STR   r1, LAST_KEY_PRESSED
        POP{r1}
        MOV PC, LR