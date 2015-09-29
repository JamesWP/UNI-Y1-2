; Hello Someone program - version 1

	B main

hello	DEFB	"Hello \0"
goodbye	DEFB	"and good-bye!\n\0"
	ALIGN

main	ADR	R0, hello	; System.out.print("Hello ");
	SVC 	3

	; while (R0 != 10) {// translate to ARM code
loop	CMP	R0,	#10
	SVCNE	1		; input a character to R0
	SVCNE	0		; output the character in R0
	
	ADREQ	R0, goodbye 	; System.out.println("and good-bye!");
	SVCEQ	3

	SVCEQ  	2		; stop the program
	B	loop