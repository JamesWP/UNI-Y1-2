
// Verilog stimulus file.
// Please do not create a module in this file.
/*

#VALUE      creates a delay of VALUE ns
a=VALUE;    sets the value of input 'a' to VALUE
$stop;      tells the simulator to stop

*/

initial
begin 
// Enter you stimulus below this line
clock = 0;
enable = 1;
reset = 0;

#100
reset = 1;
#50
reset = 0;

// wait for the clock to run for a while then pause then resume
#200
enable = 0;
#200
enable = 1;
#220


// test reset again
reset = 1;
#50
reset = 0;
#200


// Please make sure your stimulus is above this line 
$stop;
end 

always
begin
  #25
  clock = !clock;
end
