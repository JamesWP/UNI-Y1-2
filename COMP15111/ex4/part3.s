; COMP15111 exercise 4 - Template file
; J.D. Garside (from an original by P.J. Jinks)
; September 2012

print_char	equ	0		; Define names to aid readability
stop		equ	2
print_str	equ	3
print_no	equ	4

cLF		equ	10		; Line-feed character


		ADR	SP, _stack	; set SP pointing to the end of our stack
		B	main

		DEFS	100		; this chunk of memory is for the stack
_stack					; This label is 'just after' the stack space

; public class AgeHistory2 {

wasborn		DEFB	"This person was born on ",0
was		DEFB	"This person was ",0
on		DEFB	" on ",0
is		DEFB	"This person is ",0
today		DEFB	" today!",0
willbe		DEFB	"This person will be ",0
		ALIGN

pDay		DEFW	23		; static int pDay = 23; //or whatever is todays date
pMonth		DEFW	11		; static int pMonth = 11; //or whatever is this month
pYear		DEFW	2005		; static int pYear = 2005; //or whatever is this year

printthedate
;printthedate (day,month,year) R4=year,R1=month, R6=day
			MOV	R0, R6
			SVC	print_no
			MOV	R0, #'/'
			SVC	print_char
			MOV	R0, R1
			SVC	print_no
			MOV	R0, #'/'
			SVC	print_char
			MOV	R0, R4
			SVC	print_no
			MOV	R0, #cLF
			SVC	print_char
			MOV PC, LR ; return

; private static void printAgeHistory(int bDay, int bMonth, int bYear){

; parameters
;  R0 = bDay (on entry, moved to R6 to allow SVC to output via R0)
;  R1 = bMonth
;  R2 = bYear
; local variables (callee-saved registers)
;  R4 = year
;  R5 = age
;  R6 = bDay - originally R0

printAgeHistory	PUSH 	{R4-R6,LR}; callee saves three registers

; int year = bYear + 1;
		ADD	R4, R2, #1
; int age = 1;
		MOV	R5, #1

; System.out.println("This person was born on "
;			+ bDay + "/" + bMonth + "/" + bYear);
		ADRL	R0, wasborn
		SVC	print_str


;printthedate(day,month,year)
		PUSH {r4}
		MOV r4, r2
		BL  printthedate
		POP  {r4}
;r6 day, r1 month, r2 year

; this code does: while (year < pYear) {
loop1	LDR	R0, pYear
		CMP	R4, R0
		BHS	end1		; Years are unsigned
; for part 4, should be changed to:
; while (year < pYear)
;	|| year == pYear && bMonth < pMonth
;	|| year == pYear && bMonth == pMonth && bDay < pDay) {

; System.out.println("This person was " + age
;			+ " on " + bDay + "/" + bMonth + "/" + year);
		ADRL	R0, was
		SVC	print_str
		MOV	R0, R5
		SVC	print_no
		ADRL	R0, on
		SVC	print_str

;printthedate(day,month,year) 
		;PUSH {r4,r6} ; swaps the vals in 4,6
		;MOV r4,r6
		;LDR r6, [SP]
		BL  printthedate
		;POP {r4,r6}
;r4 day, r1 month, r6 year


		; year++;
		ADD	R4, R4, #1
		; age++;
		ADD	R5, R5, #1
		; }
		B	loop1

end1
; this code does: if (bMonth == pMonth)
; for part 4, should be changed to:
; if (bMonth == pMonth && bDay == pDay)
		LDR	R0, pMonth
		CMP	R1, R0
		BNE	else1

; System.out.println("This person is "+age+" today!");
		ADRL	R0, is
		SVC	print_str
		MOV	R0, R5
		SVC	print_no
		ADRL	R0, today
		SVC	print_str
		MOV	R0, #cLF
		SVC	print_char

; else
		B	end2
else1
; System.out.println("This person will be " + age
;			+ " on " + bDay + "/" + bMonth + "/" + year);
		ADRL	R0, willbe
		SVC	print_str
		MOV	R0, R5
		SVC	print_no
		ADRL	R0, on
		SVC	print_str

;printthedate(day,month,year)
		PUSH {r4,r6} ; swaps the vals in 4,6
		MOV r4,r6
		LDR r6, [SP, #8]
		BL  printthedate
		POP {r4,r6}
; r6 day,r1 month, r4 year

; }// end of printAgeHistory
end2		
		POP	{R4-R6,LR}		; callee saved registers
		MOV	PC, LR

another		DEFB	"Another person",10,0
		ALIGN

; public static void main(String [] args) {
main
	LDR	R4, =&12345678		; Test value - not part of Java compilation
	MOV	R5, R4			; See later if these registers corrupted
	MOV	R6, R4

; printAgeHistory(pDay, pMonth, 2000);

		PUSH{R1-R2,R6}
		MOV	R2, #2000
		LDR	R1, pMonth
		LDR	R6, pDay
		BL	printAgeHistory

; System.out.println("Another person");
		ADRL	R0, another
		SVC	print_str

; printAgeHistory(13, 11, 2000);
		MOV	R2, #2000
		MOV	R1, #11
		MOV	R6, #13    
		BL	printAgeHistory
		POP{R1-R2,R6}

	; Now check to see if register values intact (Not part of Java)
	LDR	R0, =&12345678		; Test value
	CMP	R4, R0			; Did you preserve these registers?
	CMPEQ	R5, R0			;
	CMPEQ	R6, R0			;

	ADRLNE	R0, whoops1		; Oh dear!
	SVCNE	print_str		;

	ADRL	R0, _stack		; Have you balanced pushes & pops?
	CMP	SP, R0			;

	ADRLNE	R0, whoops2		; Oh no!!
	SVCNE	print_str		; End of test code

; }// end of main
		SVC	stop

; }// end of class


whoops1		DEFB	"\n** BUT YOU CORRUPTED REGISTERS!  **\n", 0
whoops2		DEFB	"\n** BUT YOUR STACK DIDN'T BALANCE!  **\n", 0
