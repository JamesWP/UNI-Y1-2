//Verilog HDL for "MU0_lib", "mux_2to1_16bit" "functional"


module mux_2to1_16bit ( output reg [15:0] q,
			input [15:0] a,
			input [15:0] b,
			input sel);

always @ (sel,a,b)
begin
  case (sel)
   1:q = a;
   0:q = b;
   default:q=0;
  endcase
end

endmodule
