module Stump_SHIFT_test ();                 // Declare the test module: no I/O

reg  [15:0] operand_A;                    // Declare the variables at this level
reg         c_in;
reg  [1:0]  shift_op;

wire  [15:0] shift_out;
wire         c_out;

integer file_handle;
integer test;
integer val;
/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/

Stump_shift SHIFT (
	.operand_A(operand_A),
  .c_in(c_in),
	.shift_op(shift_op),              
	.c_out(c_out),
	.shift_out(shift_out)
);


initial
begin

file_handle = $fopen("SHIFT_test_out.txt");
$fdisplay(file_handle, "Outcome from Stump SHIFT tests\n");

for (test = 0; test<4; test = test + 1)
begin
	case(test)
		0: 
			begin
				$fdisplay(file_handle, "Testing SHIFT\n");
				shift_op = 2'b00;
			end
		1:
			begin
				$fdisplay(file_handle, "Testing ASR\n");
				shift_op = 2'b01;
			end
		2:
			begin
				$fdisplay(file_handle, "Testing ROR\n");
				shift_op = 2'b10;
			end
		3:
			begin
				$fdisplay(file_handle, "Testing RRC\n");
				shift_op = 2'b11;
			end
	endcase
	for (val = 0; val<10; val = val + 1)
	begin
		if (val<5)
			c_in = 0;
		else
			c_in = 1;
		case(val)
      0: operand_A = 16'h0000;
			1: operand_A = 16'b1010101010101010;
			2: operand_A = 16'hFFFF;
			3: operand_A = 16'h0F0F;
			4: operand_A = 16'hABCD;
			5: operand_A = 16'h0000;  
			6: operand_A = 16'b1010101010101010;
			7: operand_A = 16'hFFFF;
			8: operand_A = 16'h0F0F;
			9: operand_A = 16'hABCD;
		endcase
	  #100
	  display_state(shift_out,c_out); 
	end
end

end 

task display_state;
input [15:0] result;
input 			 c_out;

if(c_out==1)
	$fdisplay(file_handle, "Output state %x c_out: 1", result);
else
	$fdisplay(file_handle, "Output state %x c_out: 0", result);

endtask


endmodule
