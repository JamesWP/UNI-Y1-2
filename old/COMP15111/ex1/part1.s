	LDR	R0, tom
	LDR	R1, jill
	LDR	R2, jack
	LDR	R3, one
	LDR	R4, zero
loop	ADD	R0, R0, R1
	SUB	R2, R2, R3
	CMP	R2, R4
	BNE	loop
	SVC	2
jack	DEFW	3
jill	DEFW	4
tom	DEFW	0
one	DEFW	1
zero	DEFW	0
