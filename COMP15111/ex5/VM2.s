	ADR	SP, _stack	; set SP pointing to the end of our stack
	B	main
	DEFS	1000	; this chunk of memory is for the stack
_stack
			;public class VM {
			;static final int
iadd	EQU	0	;iadd=0, imul=1, ifgt=2, if_icmplt=3, bipush=4,
imul	EQU	1
ifgt	EQU	2
if_icmplt EQU	3
bipush	EQU	4
go_to	EQU	5	;go_to=5, iload=6, istore=7, iprint=8, stop=9;
iload	EQU	6
istore	EQU	7
iprint	EQU	8
stop	EQU	9

;the following defines "code" and initialises it
code			;static int [] code= new int[100];
			;static void initcode () {
			;//example1
			;//int a = 1;
  DEFW	bipush, 1	;code[0]= bipush; code[1]= 1;
  DEFW	istore, 1	;code[2]= istore; code[3]= 1;
			;//jump to test for while
  DEFW	go_to, 12	;code[4]= go_to; code[5]= 12; //12+4=16
			;//print a
  DEFW	iload, 1	;code[6]= iload; code[7]= 1;
  DEFW	iprint		;code[8]= iprint;
			;//a = a * 2;
  DEFW	iload, 1	;code[9]= iload; code[10]= 1;
  DEFW	bipush, 2	;code[11]= bipush; code[12]= 2;
  DEFW	imul		;code[13]= imul;
  DEFW	istore, 1	;code[14]= istore; code[15]= 1;
			;//while (a * 2 > 0)
  DEFW	iload, 1	;code[16]= iload; code[17]= 1;
  DEFW	bipush, 2	;code[18]= bipush; code[19]= 2;
  DEFW	imul		;code[20]= imul;
  DEFW	ifgt, -15	;code[21]= ifgt; code[22]= -15; //21-15=6
			;//end
			;//print a
  DEFW	iload, 1	;code[23]= iload; code[24]= 1;
  DEFW	iprint		;code[25]= iprint;
  DEFW	stop		;code[26]= stop;
  DEFW  0, 0, 0 	; [27] [28] [29]
			;//example2
			;//int vol = 200;
  DEFW	bipush, 200	;code[30]= bipush; code[31]= 200;
  DEFW	istore, 1	;code[32]= istore; code[33]= 1;
			;//int side= 1;
  DEFW	bipush, 1	;code[34]= bipush; code[35]= 1;
  DEFW	istore, 2	;code[36]= istore; code[37]= 2;
			;//jump to test for while
  DEFW	go_to, 12	;code[38]= go_to; code[39]= 12; //38+12 =50
			;//System.out.println(" "+side);
  DEFW	iload, 2	;code[40]= iload; code[41]= 2;
  DEFW	iprint		;code[42]= iprint;
			;//side = side + 1;
  DEFW	iload, 2	;code[43]= iload; code[44]= 2;
  DEFW	bipush, 1	;code[45]= bipush; code[46]= 1;
  DEFW	iadd		;code[47]= iadd;
  DEFW	istore, 2	;code[48]= istore; code[49]= 2;
			;//while (side * side * side < vol)
  DEFW	iload, 2	;code[50]= iload; code[51]= 2;
  DEFW	iload, 2	;code[52]= iload; code[53]= 2;
  DEFW	imul		;code[54]= imul;
  DEFW	iload, 2	;code[55]= iload; code[56]= 2;
  DEFW	imul		;code[57]= imul;
  DEFW	iload, 1	;code[58]= iload; code[59]= 1;
  DEFW	if_icmplt, -20	;code[60]= if_icmplt; code[61]= -20; //60-20 = 40
			;//}
  DEFW	stop		;code[62]= stop;
			;}

_trace1	DEFB	"trace: vmPC=",0
	ALIGN
_trace2	DEFB	" code=",0
	ALIGN
_trace3	DEFB	" vmSP=",0
	ALIGN
_notimpl DEFB	"not implemented:",0
	ALIGN

stack	DEFS 40		;int [] stack= new int[10];
vars	DEFS 40		;int [] vars= new int[10];

vmPC	RN	R2	;use R2 for vmPC
vmSP	RN	R3	;use R3 for vmSP
done	RN	R4	;use R4 for done

getCode	; R0 = code[vmPC]
	STMFD SP!, {R8,R9}
	MOV   R9, vmPC
	MOV   R8, #4
	MUL   R9, R9, R8
	ADR   R8, code
	ADD   R9, R9, R8
	LDR   R0, [R9]
	LDMFD SP!, {R8,R9}
	MOV   PC, LR

popR0	; R0 = stack[vmSP++]
	STMFD SP!, {R8,R9}
	MOV   R9, vmSP
	ADD   vmSP, vmSP, #1
	MOV   R8, #4
	MUL   R9, R9, R8
	ADR   R8, stack
	ADD   R9, R9, R8
	LDR   R0, [R9]
	LDMFD SP!, {R8,R9}
	MOV   PC, LR

pushR0	; stack[--vmSP] = R0
	STMFD SP!, {R8,R9}
	SUB   vmSP, vmSP, #1
	MOV   R9, vmSP
	MOV   R8, #4
	MUL   R9, R9, R8
	ADR   R8, stack
	ADD   R9, R9, R8
	STR   R0, [R9]
	LDMFD SP!, {R8,R9}
	MOV   PC, LR

getVars	;R0=vars[R0]
	STMFD SP!, {R8,R9}
	MOV  R8, #4
	MUL  R9, R0, R8
	ADR  R8, vars
	ADD  R9, R9, R8
	LDR  R0, [R9]
	LDMFD SP!, {R8,R9}
	MOV  PC, LR

putVars	;vars[R1]=R0
	STMFD SP!, {R8,R9}
	MOV  R8, #4
	MUL  R9, R1, R8
	ADR  R8, vars
	ADD  R9, R9, R8
	STR  R0, [R9]
	LDMFD SP!, {R8,R9}
	MOV  PC, LR

main	;public static void main (String argv[]) {
	MOV done ,#0 ;boolean done= false;
	MOV vmPC ,#30  ;,#0 ;int vmPC= 0;	// or vmPC= 30; for part 2 !
	MOV vmSP ,#10;int vmSP= 10;
	;nothing needed for: initcode1();
	;while (!done) {

while
	CMP done, #1
  BEQ end
  ;this code does System.out.println("trace: vmPC=" + vmPC
	;  + " code=" + code[vmPC] + " vmSP=" + vmSP);
	ADR R0, _trace1
	SVC 3
	MOV R0, vmPC
	SVC 4
	ADR R0, _trace2
	SVC 3
	BL  getCode
	SVC 4
	ADR R0, _trace3
	SVC 3
	MOV R0, vmSP
	SVC 4
	MOV R0, #10
	SVC 0

	;switch (code[vmPC++]) {
  BL   getCode ; result in R0
  ADD  vmPC , vmPC , #1 ; increment pc ++
  ADRL R1 ,switch
  LDR  PC, [R1,R0,LSL #2] ; jump to code
switch	DEFW	default	;case_iadd
	DEFW	case_imul
	DEFW	case_ifgt
	DEFW	default ;case_if_icmplt
	DEFW	case_bipush
	DEFW	case_go_to
	DEFW	case_iload
	DEFW	case_istore
	DEFW	case_iprint
	DEFW	case_stop

case_go_to	;case go_to: vmPC= code[vmPC++] + vmPC - 2;
		;break;
    BL getCode
    ADD vmPC, vmPC, #1
    ADD R0, R0, vmPC
    SUB R0, R0, #2
    MOV vmPC, R0
    B while

case_ifgt	;case ifgt:
		;{int target= code[vmPC++] + vmPC - 2;
		;if (stack[vmSP++]>0)
		;vmPC= target;
		;}
		;break;
    BL getCode
    ADD vmPC, vmPC, #1
    ADD R0, R0, vmPC
    SUB R0, R0, #2
    MOV R1, R0
    BL popR0
    CMP R0,#0
    MOVGT vmPC, R1 
    B while

case_iload	;case iload: stack[--vmSP]= vars[code[vmPC++]];
		BL getCode
    ADD vmPC, vmPC, #1
    BL getVars
    BL pushR0
    B while
    ;break;

case_istore	;case istore: vars[code[vmPC++]]= stack[vmSP++];
		;break;
    BL getCode
    ADD vmPC, vmPC, #1
    MOV R1, R0
    BL popR0
    BL putVars;vars[R1]=R0
    B while


case_bipush	;case bipush: stack[--vmSP]= code[vmPC++];
    BL getCode ; code[vmPC++]
    ADD  vmPC , vmPC , #1
    BL pushR0  ;
    B  while

case_imul	;case imul:
		;{int a= stack[vmSP++];
		;int b= stack[vmSP++];
		;stack[--vmSP]= a*b;
		;}
		;break;
    BL popR0
    MOV R1,R0
    BL popR0
    MUL R0,R0,R1
    BL pushR0
    B while

case_iprint	;case iprint:
		;the following ARM code does:
		;System.out.println(" " + stack[vmSP++]);
		BL  popR0
		SVC 4
		MOV R0, #10
		SVC 0
    B while
		;break;

case_stop	;case stop: done=true;
    MOV done , #1
    B while
		;break;

default		;default:
		;the following ARM code does:
		;System.out.println("not implemented:" + code[vmPC-1]);
		ADR R0, _notimpl
		SVC 3
		SUB vmPC, vmPC, #1
		BL  getCode
		SVC 4
		MOV R0, #10
		SVC 0

    MOV done, #1
    B while
		;done= true;
		;break;

		;}//end of switch
		;}//end of while
end
	SVC 2	;}//end of main
		;}//end of class
