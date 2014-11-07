module Stump_reg16bit  (input wire CLK,
			input wire CE,
			input wire [15:0] D,
			output reg [15:0] Q
			);
			
always @(posedge CLK)
if (CE == 1)
  Q <= D;
  
endmodule
