// Stump shift unit

module Stump_shift (input  wire [15:0] operand_A,
                    input  wire        c_in,
                    input  wire [1:0]  shift_op,
                    output reg  [15:0] shift_out,
                    output reg         c_out);


//If you wish to implement your own shifter add your code where indicated and
//remove the "//" from the comment block tokens "/*" and "*/"


//Add your code below this line		    


always@ (shift_op,operand_A,c_in)
begin
  case(shift_op)
    2'b00: /* - */ {c_out, shift_out} = {c_in, operand_A};
    2'b01: /*ASR*/ {c_out, shift_out} = {operand_A[15], operand_A[15], operand_A[15:1]};
    2'b10: /*ROR*/ {c_out, shift_out} = {operand_A[0 ], operand_A[0 ], operand_A[15:1]};
    2'b11: /*RRC*/ {c_out, shift_out} = {operand_A[15], c_in         , operand_A[15:1]};
    default: {c_out, shift_out} = 17'bXXXXXXXXXXXXXXXX;
  endcase
end


//Add your code above this line
 
/*

wire [1:0] shift_op_inv;
wire [15:0] shift_out_t0;
wire [15:0] shift_out_t1;
wire [15:0] shift_out_t2;
wire [15:0] shift_out_t3;
wire [15:0] shift_out_i;
wire        c_out_t0;
wire        c_out_t1;
wire        c_out_t2;
wire        c_out_t3;
wire        c_out_i;

not n1[1:0]  (shift_op_inv, shift_op);
and a1[15:0] (shift_out_t0, operand_A, shift_op_inv[1], shift_op_inv[0]);
or  o1       (c_out_t0, shift_op[1], shift_op[0]);

and a2       (shift_out_t1[15], operand_A[15], shift_op_inv[1], shift_op[0]);
and a3[14:0] (shift_out_t1[14:0], operand_A[15:1], shift_op_inv[1], shift_op[0]);
and a4       (c_out_t1, operand_A[0], shift_op_inv[1], shift_op[0]);


and a5       (shift_out_t2[15], operand_A[0], shift_op[1], shift_op_inv[0]);
and a6[14:0] (shift_out_t2[14:0], operand_A[15:1], shift_op[1], shift_op_inv[0]);
and a7       (c_out_t2, operand_A[0], shift_op[1], shift_op_inv[0]);


and a8       (shift_out_t3[15], c_in, shift_op[1], shift_op[0]);
and a9[14:0] (shift_out_t3[14:0], operand_A[15:1], shift_op[1], shift_op[0]);
and a10      (c_out_t3, operand_A[0], shift_op[1], shift_op[0]);

or  o2[15:0] (shift_out_i, shift_out_t0, shift_out_t1, shift_out_t2, shift_out_t3);
or  o3       (c_out_i, c_out_t0, c_out_t1, c_out_t2, c_out_t3);


always @(shift_out_i) shift_out = shift_out_i;
always @(c_out_i)     c_out = c_out_i;

*/


endmodule
