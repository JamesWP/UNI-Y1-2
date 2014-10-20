// Verilog stimulus file.
// Please do not create a module in this file.
/*

#VALUE      creates a delay of VALUE ns
a=VALUE;    sets the value of input 'a' to VALUE
$stop;      tells the simulator to stop

*/

initial
begin
#100
// Enter you stimulus below this line
// Using a gray code to make sure each bit has correct weight
// 34 test vectors required
a=0; b=0; cin =0;
#100 a=16'h0001;
#100 a=16'h0003;
#100 a=16'h0007;
#100 a=16'h000f;
#100 a=16'h001f;
// ADD YOUR TEST VECTORS HERE
#100 a=16'h003f;
#100 a=16'h007f;
#100 a=16'h00ff;
#100 a=16'h01ff;
#100 a=16'h03ff;
#100 a=16'h07ff;
#100 a=16'h0fff;
#100 a=16'h1fff;
#100 a=16'h3fff;
#100 a=16'h7fff;
#100 a=16'hffff;

#100 b=16'h0001;
#100 b=16'h0003;
#100 b=16'h0007;
#100 b=16'h000f;
#100 b=16'h001f;
#100 b=16'h003f;
#100 b=16'h007f;
#100 b=16'h00ff;
#100 b=16'h01ff;
#100 b=16'h03ff;
#100 b=16'h07ff;
#100 b=16'h0fff;
#100 b=16'h1fff;
#100 b=16'h3fff;
#100 b=16'h7fff;
#100 b=16'hffff;
#100 cin=1;

// ADD TESTS FOR FINDING CARRY DELAY HERE //
// 2 test vectors required
#100 a=16'hffff; b=16'hf0000; cin=0;
#100 a=16'hffff; b=16'hf0000; cin=1;


// delay for end of wave traces to be visible
#100


// Please make sure your stimulus is above this line
$stop;
end

