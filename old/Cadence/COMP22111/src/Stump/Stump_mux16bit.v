module Stump_mux16bit  (input wire [15:0] D0,
			input wire [15:0] D1,
			input wire        S,
			output reg [15:0] Q
			);
			
always @ (S, D0, D1)
 if(S == 1)
  Q = D1;
 else
  Q = D0;
  
endmodule
