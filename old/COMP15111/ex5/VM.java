public class VM {

    static int [] code= new int[100];
    static final int
	iadd=0, imul=1, ifgt=2, if_icmplt=3, bipush=4,
	go_to=5, iload=6, istore=7, iprint=8, stop=9;

    static void initcode () {
	//example 1
	//int a = 1;
	code[0]= bipush; code[1]= 1;
	code[2]= istore; code[3]= 1;
	//jump to test for while
	code[4]= go_to; code[5]= 12; //12+4=16
	//print a
	code[6]= iload; code[7]= 1;
	code[8]= iprint;
	//a = a * 2;
	code[9]= iload; code[10]= 1;
	code[11]= bipush; code[12]= 2;
	code[13]= imul;
	code[14]= istore; code[15]= 1;
	//while (a * 2 > 0)
	code[16]= iload; code[17]= 1;
	code[18]= bipush; code[19]= 2;
	code[20]= imul;
	code[21]= ifgt; code[22]= -15; //21-15=6
	//end
	//print a
	code[23]= iload; code[24]= 1;
	code[25]= iprint;
	code[26]= stop;

	//example 2
	//int vol = 200;
	code[30]= bipush; code[31]= 200;
	code[32]= istore; code[33]= 1;
	//int side= 1;
	code[34]= bipush; code[35]= 1;
	code[36]= istore; code[37]= 2;
       //jump to test for while
	code[38]= go_to; code[39]= 12; //38+12 =50
	//System.out.println(" "+side);
	code[40]= iload; code[41]= 2;
	code[42]= iprint;
	//side = side + 1;
	code[43]= iload; code[44]= 2;
	code[45]= bipush; code[46]= 1;
	code[47]= iadd;
	code[48]= istore; code[49]= 2;
	//while (side * side * side < vol)
	code[50]= iload; code[51]= 2;
	code[52]= iload; code[53]= 2;
	code[54]= imul;
	code[55]= iload; code[56]= 2;
	code[57]= imul;
	code[58]= iload; code[59]= 1;
	code[60]= if_icmplt; code[61]= -20; //60-20 = 40
	//}
	code[62]= stop;
    }

    public static void main (String argv[]) {
	boolean done= false;
	boolean trace= false;
	int vmPC= 0;
	int [] stack= new int[10];
	int vmSP= 10;
	int [] vars= new int[10];
	initcode();

	if (argv.length>0 && "2".compareTo(argv[0])==0) {
	    vmPC= 30;
	    System.out.println("Warning: part 2 not fully implemented!");
	}
	if (argv.length>1 && "trace".compareTo(argv[1])==0)
	    trace= true;

	while (!done) {
	    if (trace)
		System.out.println("vmPC=" + vmPC
				   + " code=" + code[vmPC]
				   + " vmSP=" + vmSP);
	    switch (code[vmPC++]) {
	    case go_to: vmPC= code[vmPC++] + vmPC - 2;
		break;
	    case ifgt:
		{int target= code[vmPC++] + vmPC - 2;
		if (stack[vmSP++]>0)
		    vmPC= target;
		}
		break;
	    case iload: stack[--vmSP]= vars[code[vmPC++]];
		break;
	    case istore: vars[code[vmPC++]]= stack[vmSP++];
		break;
	    case bipush: stack[--vmSP]= code[vmPC++];
		break;
	    case imul:
		{int a= stack[vmSP++];
		int b= stack[vmSP++];
		stack[--vmSP]= a*b;
		}
		break;
	    case iprint: System.out.println(" " + stack[vmSP++]);
		break;
	    case stop: done=true;
		break;
	    default: System.out.println("not implemented:" + code[vmPC-1]);
		done= true;
		break;
	    }
	}
    }

}
