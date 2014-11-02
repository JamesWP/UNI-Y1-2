
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


bcd=0;
#50
bcd=1;
#50
bcd=2;
#50
bcd=3;
#50
bcd=4;
#50
bcd=5;
#50
bcd=6;
#50
bcd=7;
#50
bcd=8;
#50
bcd=9;
#50
bcd=4'ha;
#50
bcd=4'hb;
#50
bcd=4'hc;
#50
bcd=4'hd;
#50
bcd=4'he;
#50
bcd=4'hf;
#50


// Please make sure your stimulus is above this line 
$stop;
end 