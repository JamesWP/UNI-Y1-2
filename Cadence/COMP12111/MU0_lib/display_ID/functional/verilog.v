// Verilog HDL for "COMP10242", "display_ID" "functional"

module display_ID (input [2:0] sel,
		   output reg [7:0] LHS,
		   output reg [7:0] RHS);


always @ (sel)
  case (sel)
    0: begin LHS = 8'b0111_0111; RHS = 8'b0000_0000; end  // A
    1: begin LHS = 8'b0101_1110; RHS = 8'b0001_0000; end  // di
    2: begin LHS = 8'b0101_1110; RHS = 8'b0101_1100; end  // do
    3: begin LHS = 8'b0111_0011; RHS = 8'b0101_1000; end  // Pc
    4: begin LHS = 8'b0000_0110; RHS = 8'b0101_0000; end  // Ir
    5: begin LHS = 8'b0111_0111; RHS = 8'b0101_1000; end  // Ac
    6: begin LHS = 8'b0101_1110; RHS = 8'b0000_0000; end  // d
    7: begin LHS = 8'b0111_0111; RHS = 8'b1101_0011; end  // A?
    default: begin LHS = 8'hFF;  RHS = 8'hFF; end
  endcase

endmodule
