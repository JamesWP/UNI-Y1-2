//Verilog HDL for "COMP12111", "counter09" "functional"


module counter09 ( output reg [3:0] digits,
		   output reg carry,
		   input clock,
		   input reset,
		   input enable );

reg	[3:0] next_state;
reg	[3:0] current_state;

initial
begin
  current_state = 0;
end

always @ (current_state)
begin
  case (current_state)
    0:	next_state = 1;
    1:	next_state = 2;
    2:	next_state = 3;
    3:	next_state = 4;
    4:	next_state = 5;
    5:	next_state = 6;
    6:	next_state = 7;
    7:	next_state = 8;
    8:	next_state = 9;
    9:	next_state = 0;
    default : next_state = 0;
  endcase
end

always @ (posedge clock, posedge reset)
begin
  if (reset)
    current_state <= 0;
  else	
    if(enable)
      current_state <= next_state;
end


always @ (current_state)
begin
  digits = current_state;
  if (current_state == 9)
    carry = 1;
  else
    carry = 0;
end

endmodule
