//Verilog HDL for "MU0_lib", "mux_2to1_12bit" "functional"


module mux_2to1_12bit ( output reg [11:0] q,
			input [11:0] a,
			input [11:0] b,
			input sel );

always @ (sel,a,b)
begin
  case (sel)
   1:q = a;
   0:q = b;
   default:q=0;
  endcase
end

endmodule
