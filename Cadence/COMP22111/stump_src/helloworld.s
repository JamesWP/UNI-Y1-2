	bal Main

counter defw 0 ;global counter variable
onehund defw 100; constant literal
asciioffset  defw 48 ; constant literal

hund    defw 0x082D
button  defw 0x0801
backli  defw 0x0802

Main:     
					add r2,r0,#1
					ld r1, [r0,#backli] ; load button pointer
					st r2, [r1]         ; load button state

MainLoop: add r6, PC, #1; save LR
					bal IncrCount ; call IncrCount
    
					ld r1, [r0,#counter]
          add r6, PC, #1; save LR
          bal GetDigs

          ld r1, [r0,#hund]
					ld r5, [r0,#asciioffset]
          add r2, r2, r5
					st r2, [r1, #0]
          add r3, r3, r5
          st r3, [r1, #1]
          add r4, r4, r5
          st r4, [r1, #2]

					ld r1, [r0,#button] ; load button pointer
					ld r1, [r1]         ; load button state
					subs r0,r1,r0
          add r6, PC, #1; save LR
					bne ZeroCount ; call zero if button

					bal MainLoop ; call IncrCount

					bal Stop;stop

Stop:     st r0, [r0, #-1]; STOP PLZ
					bal Stop        ; loop indef

;LR= R6
IncrCount:ld r1, [r0,#counter]   ;load count
					add r1, r1, #1   ;incr
					st r1, [r0,#counter]   ;store count+1
					add PC, r6, r0   ;return

ZeroCount:st r0, [r0,#counter]   ; load count
					add PC, r6, r0   ;return

;LR= IN  R6
;R1= IN  number to count
;R2= OUT number of 100's
;R3= OUT number of 10's
;R4= OUT number of 1's
GetDigs:
					add R2, R0, R0;R2 = 0
					add R3, R0, R0;R3 = 0
					add R4, R0, R0;R4 = 0
          ld R5, [r0,#onehund]; = 100
					l100:
          subs R0, R1, R5 ;if number < 100 ... goto l10
          ble l10           ; goto l10
          add R2, R2, #1    ;incr R2
          sub R1, R1, R5  ;decr R1 by 100
          bal l100; repeat
					l10:
          subs R0, R1, #10  ;if number <10 ... goto l1
          ble l1            ; goto l1
          add R3, R3, #1    ;incr R3
          sub R1, R1, #10   ;decr R1 by 10
          bal l10
          l1:
          subs R0, R1, #1   ;if number <1 ... done
          ble GetDigsReturn  ; return
          add R4, R4, #1    ;incr R4
          sub R1, R1, #1    ;decr R1 by 1
          bal l1
          GetDigsReturn: add PC, R6, R0; return

org 0x0821
  data 0x0048;H
  data 0x0065;e
  data 0x006C;l
  data 0x006C;l
  data 0x006F;o

  data 0x0010;_

  data 0x0057;W
  data 0x006F;o
  data 0x0072;r
  data 0x006C;l
  data 0x0064;d
 
  data 0x0010 
d100:
  data 0x0030 
d10:
  data 0x0030 
d1:
  data 0x0030 



org 0x0834
  data 0x004A;J
  data 0x0057;W
  data 0x0050;P

org 0x083c
  data 0x0032;2
  data 0x0030;0
  data 0x0031;1
  data 0x0034;4
