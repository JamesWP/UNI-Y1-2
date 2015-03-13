;-----------------------------------------------------------------------------;
;---                 LAB KERNEL V1.0                                       ---;
;---                 ---------------                                       ---;
;---                 Button io                                             ---;
;--- Author: James W Peach                                                 ---;
;-----------------------------------------------------------------------------;

;-- this include defines procedures to handle keyboard scanning:
;--                                  KeyboardScan         : called periodically
;--                                                       : to handle key read

LAST_KEY_PRESSED    DEFW    -1
CURENT_KEY          DEFW    -1
CURENT_KEY_TIME     DEFW    0
;-- LITERALS

;--- KEYMAP   : a mapping from digits to keys and vice versa
KEY_3   EQU   0
KEY_6   EQU   1
KEY_9   EQU   2
KEY_H   EQU   3

KEY_2   EQU   4
KEY_5   EQU   5
KEY_8   EQU   6
KEY_0   EQU   7

KEY_1   EQU   8
KEY_4   EQU   9
KEY_7   EQU   10
KEY_S   EQU   11

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
              MOV   PC, LR
;---------------------
