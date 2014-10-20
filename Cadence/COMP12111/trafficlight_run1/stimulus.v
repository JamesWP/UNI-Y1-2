
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

reset = 0;
clock = 0;
count = 0;
#10000
reset = 1;
#20
reset = 0;
#10000


// Please make sure your stimulus is above this line 
$stop;
end 

always
begin
  #1000
  count = count + 1;
end

always
begin
  #50
  clock = !clock;
end
