/* Template ALU for Stump processor.                                          */

`include "src/Stump/Stump_definitions.v"
	// 'include' definitions of function codes etc.
	// e.g. can use "`ADD" instead of "'h0" to aid readability
	// Substitute your own definitions if you prefer.

/*----------------------------------------------------------------------------*/

module Stump_ALU (input  wire [15:0] operand_A,		// First operand
                  input  wire [15:0] operand_B,		// Second operand
		  input  wire  [2:0] func,		// Function specifier
		  input  wire        c_in,		// Carry input
		  input  wire        csh,               // Carry from shifter
		  output reg  [15:0] result,		// ALU output
		  output wire  [3:0] flags_out);	// Flags {N, Z, V, C}

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/

/* result */

reg c_out;
reg over; // overflow bit
reg [15:0] operand_Bs;
reg c_ins;

// assign the value of result and c_out depending on the alu func
always@ (func, operand_A, operand_B, c_in)
begin
  operand_Bs = ~operand_B;
  c_ins = ~c_in;
  case (func)
    3'b000: /*add*/ alu_add(operand_A,  operand_B,  1'b0, result, c_out,over);
    3'b001: /*adc*/ alu_add(operand_A,  operand_B,  c_in, result, c_out,over);
    3'b010: /*sub*/ alu_add(operand_A, operand_Bs,  1'b1, result, c_out,over);
    3'b011: /*sbc*/ alu_add(operand_A, operand_Bs, c_ins, result, c_out,over);
    3'b100: /*and*/ result = operand_A & operand_B;
    3'b101: /*or */ result = operand_A | operand_B;
    3'b110: /*LDS*/ alu_add(operand_A,  operand_B,  1'b0, result, c_out,over);
    3'b111: /*Bcc*/ alu_add(operand_A,  operand_B,  1'b0, result, c_out,over);
    default:
    begin 
      result = 16'hXXXX;
      c_out = 1'bX;
      over = 1'bX; 
    end
  endcase
end

/* alu_add: performs addition of op1 and op2 with c_in
outputs the sum and the value of c15,overflow to be used to calculate overflow
*/
task alu_add;
input [15:0] op1;
input [15:0] op2;
input [15:0] c_in;
output reg [15:0] out;
output reg c15;
output reg overflow;

begin
  {c15, out} = op1 + op2 + c_in;
  overflow = (op1[15] & op2[15] & ~out[15])
             | (~op1[15] &  ~op2[15] & out[15]);
end
endtask


/* flags_out: assigns the flags */ 

//N
assign flags_out[3] = result[15];

//Z
assign flags_out[2] = (result == 0) ? 1'b1 : 1'b0;  

//V
assign flags_out[1] = ~func[2] & over;

//C
assign flags_out[0] = (func[2] & csh)
                      | (~func[2] & func[1] ^ c_out);


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/

endmodule

/*----------------------------------------------------------------------------*/
