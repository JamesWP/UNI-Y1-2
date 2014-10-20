
// Verilog stimulus file.
// Please do not create a module in this file.
/*
 
#VALUE      creates a delay of VALUE ns
a=VALUE;    sets the value of input 'a' to VALUE
$stop;      tells the simulator to stop

*/



initial
begin
Clk = 0;
Reset = 1;
#200   Reset = 0;

#20000 Reset = 1;


// Please make sure your stimulus is above this line 
$stop;
end 

always
begin
 #20 Clk = !Clk;
end
