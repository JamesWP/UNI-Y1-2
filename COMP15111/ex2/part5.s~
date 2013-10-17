; Age History

	B  main

born	DEFB "you were born in \0"
were	DEFB "you were \0"
in	DEFB " in \0"
are	DEFB "you are \0"
this	DEFB " this year\n\0"
	ALIGN

present DEFW	2005	; int present = 2005
birth	DEFW	1959	; int birth = 1959
year	DEFW	0	; int year
age	DEFW	1	; int age = 1
	ALIGN
main
	LDR R4, present
	LDR R5, birth
	LDR R6, year
	LDR R7, age

	; this code does System.out.println("you were born in " + birth);
	ADR R0, born
	SVC 3
	;LDR R0, birth ; make sure this will work!
	MOV R0, R5
	SVC 4
	MOV R0, #10
	SVC 0

	;LDR R0, birth; year = birth + 1
	ADD R6, R5, #1
	;STR R0,	year

loop	;LDR R0, year
	;LDR R1,	present
	CMP R6, R4; while (year != present) {
	BEQ end

	; this code does System.out.println("you were " + age + " in " + year);
	ADR R0, were
	SVC 3
	;LDR R0, age ; make sure this will work!
	MOV R0, R7
	SVC 4
	ADR R0, in
	SVC 3
	;LDR R0, year ; make sure this will work!
	MOV R0, R6
	SVC 4
	MOV R0, #10
	SVC 0

	;LDR R0, year;   year = year + 1;
	ADD R6, R6, #1
	;STR R0, year

	;LDR R0, age;   age = age + 1;
	ADD R7, R7, #1
	;STR R0, age
	
	B loop
	; }

	; this code does System.out.println("you are " + age + "this year");
end	ADR R0, are
	SVC 3
	;LDR R0, age ; make sure this will work!
	MOV R0, R7
	SVC 4
	ADR R0, this
	SVC 3

	SVC 2 ; stop
