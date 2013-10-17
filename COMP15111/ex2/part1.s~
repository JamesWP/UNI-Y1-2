; Hello Someone program - version 1

	B main

hello	DEFB	"Hello \0"
goodbye	DEFB	"and good-bye!\n\0"
	ALIGN

main	ADR	R0, hello	; System.out.print("Hello ");
	SVC 	3

	; while (R0 != 10) {// translate to ARM code
loop	CMP	R0,	#10
	BEQ	cont
	SVC	1		; input a character to R0
	SVC	0		; output the character in R0
	B	loop
	; }// translate to ARM code

cont	ADR	R0, goodbye 	; System.out.println("and good-bye!");
	SVC	3

	SVC  	2		; stop the program
