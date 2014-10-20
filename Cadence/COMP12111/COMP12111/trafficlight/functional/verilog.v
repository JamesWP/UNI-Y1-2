//Verilog HDL for "COMP12111", "trafficlight" "functional"
//
//COMP12111 - Exercise 4- Traffic Light Controller
//
//Version 1. Sept 2012. P W Nutter
//
//This is the verilog module for the traffic light controller
//
//The aim of this exercise is complete the finite state machine using the
//state transition diagram given in the notes. 
//
//DO NOT change the interface to this design or it may not be marked completely
//when submitted.
//
//Make sure you document your code and marks may be awarded/lost for the 
//quality of the comments given.

module trafficlight ( output reg reset_count,		//resets the counter when high
		      output reg enable_count,		//enables the counter when high
		      output reg [5:0] lightseq,	//the 6-bit value to determine the light sequence on the LEDs
		      input clock,			//clock is the clock that drives the fsm
		      input reset,			//reset signal for the fsm/controller
		      input [3:0] count);		//count is the value from the counter

		      //lightseq[5] -> left red light
		      //lightseq[4] -> left amber light
		      //lightseq[3] -> left green light
		      //lightseq[2] -> right red light
		      //lightseq[1] -> right amber light
		      //lightseq[0] -> right green light
	
reg	[3:0] current_state;		//internal variable for storing the 
					//current state value
reg	[3:0] next_state;		//internal variable for storing the 
					//next state value

initial
begin //James is a peach!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!                                                                          
	next_state = 0;
end
//Use `define to define the bit patterns for the light sequence to make code
//easier to read. See output assignment block for an example of usage.

`define R_R		6'b100100	//left light red, right light red
`define R_RA		6'b100110	//left light red, right light red/amber
`define	R_G		6'b100001	//left light red, right light green
`define R_A		6'b100010	//left light red, right light amber
`define RA_R		6'b110100	//left light red/amber, right light red
`define G_R		6'b001100	//left light green, right light red
`define A_R		6'b010100	//left light amber, right light red


always @ (current_state, count)		//next state is determined by current 
begin					//state and current count
case(current_state)
	4'b0000:	next_state = 1;
	4'b0001:	if(count == 4'd1)	next_state = 2;
			else		next_state = 1;		
	4'b0010:	next_state = 3;
	4'b0011:	next_state = count == 4'd1 ? 4 : 3;
	4'b0100:	next_state = 5;	
	4'b0101:	next_state = count == 4'd8 ? 6 : 5;	
	4'b0110:	next_state = 7;	
	4'b0111:	next_state = count == 4'd2 ? 8 : 7;
	4'b1000:	next_state = 9;
	4'b1001:	next_state = count == 4'd1 ? 10 : 9;
	4'b1010:	next_state = 11;
	4'b1011:	next_state = count == 4'd1 ? 12 : 11;	
	4'b1100:	next_state = 13;	
	4'b1101:	next_state = count == 4'd8 ? 14 : 13;	
	4'b1110:	next_state = 15;	
	4'b1111:	next_state = count == 4'd2 ? 0 : 15;	
endcase
end



always @ (posedge clock, posedge reset)
	if(reset == 1)			//if reset is high - move to state 0 
	   current_state <= 0;
	else
	   current_state <= next_state;



always @ (current_state)		//output signals depend on current state
begin
case(current_state)
	4'b0000:	begin
			   lightseq = `R_R;
			   reset_count = 1;
			   enable_count = 0;
			end
	4'b0001:	begin
			   lightseq = `R_R;
			   reset_count = 0;
			   enable_count = 1;
			end
	4'b0010:	begin lightseq = `R_RA; reset_count = 1; enable_count = 0; end
	4'b0011:	begin lightseq = `R_RA; reset_count = 0; enable_count = 1; end
	4'b0100:	begin lightseq = `R_G;  reset_count = 1; enable_count = 0; end
	4'b0101:	begin lightseq = `R_G;  reset_count = 0; enable_count = 1; end
	4'b0110:	begin lightseq = `R_A;  reset_count = 1; enable_count = 0; end
	4'b0111:	begin lightseq = `R_A;  reset_count = 0; enable_count = 1; end
	4'b1000:	begin lightseq = `R_R;  reset_count = 1; enable_count = 0; end
	4'b1001:	begin lightseq = `R_R;  reset_count = 0; enable_count = 1; end
	4'b1010:	begin lightseq = `RA_R; reset_count = 1; enable_count = 0; end
	4'b1011:	begin lightseq = `RA_R; reset_count = 0; enable_count = 1; end
	4'b1100:	begin lightseq = `G_R;  reset_count = 1; enable_count = 0; end
	4'b1101:	begin lightseq = `G_R;  reset_count = 0; enable_count = 1; end
	4'b1110:	begin lightseq = `A_R;  reset_count = 1; enable_count = 0; end
	4'b1111:	begin lightseq = `A_R;  reset_count = 0; enable_count = 1; end
endcase
end

endmodule

