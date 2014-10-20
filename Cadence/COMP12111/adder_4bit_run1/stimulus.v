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

// Check all fulladders are connected to something, S=0 cout=0 not Xs
a=0; b=0; cin =0;
//Check A[0] S[0], S=0001 cout=0
#100 a='b0001; b='b0000; cin=0;
//Check B[0] S[1] & 1st part of carry chain, S=0010 cout=0
#100 a='b0001; b='b0001; cin=0;
//Check cin,  S=0011 cout=0
#100 a='b0001; b='b0001; cin=1;
//Check A[1] S[2] & 2nd part of carry chain, S=0101 cout=0
#100 a='b0011; b='b0001; cin=1;
//Check B[1], S=0111 cout=0
#100 a='b0011; b='b0011; cin=1;
//Check A[2] S[3] & 3rd part of carry chain, S=1011 cout=0
//ADD 4 MORE TESTS TO COMPLETE FUNCTIONAL TESTS //

// sum should be 1010
#100 a='b0111; b='b0011; cin=0; //add code on this line before semicolon

// sum shoud be 1000
#100 a='b0100; b='b0100; cin=0; //add code on this line before semicolon

//  should be 1111
#100 a='b1111; b='b0000; cin=0; //add code on this line before semicolon

//  should be 0000 cout 1
#100 a='b1111; b='b0000; cin=1; //add code on this line before semicolon

// ADD TESTS FOR FINDING CARRY DELAY HERE //
#100 a='b1111; b='b0000; cin=0;
#100 cin=1;
// delay for end of wave traces to be visible
#100


// Please make sure your stimulus is above this line
$stop;
end
