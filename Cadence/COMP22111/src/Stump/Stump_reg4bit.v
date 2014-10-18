module Stump_reg4bit  (input wire CLK,
			input wire CE,
			input wire [3:0] D,
			output reg [3:0] Q
			);
			
always @(posedge CLK)
if (CE == 1)
  Q <= D;
  
endmodule
