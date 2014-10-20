
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

  a=0;
  b=0;
  #20
  b=1;
  #20
  b=0;
  a=1;
  #20
  b=1;
  #20
  


// Please make sure your stimulus is above this line 
$stop;
end 
