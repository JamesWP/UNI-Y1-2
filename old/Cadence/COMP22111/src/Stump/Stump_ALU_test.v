/* Tests for Stump ALU.  Developed for COMP22111 labs.      JDG  Nov 2012     */
/*                                                                            */
/* This runs through a set of test vectors, with both input carry states for  */
/* all six ALU functions generating all possible combinations of output flags.*/

`include "src/Stump/Stump_definitions.v"

/*----------------------------------------------------------------------------*/

module Stump_ALU_test ();                 // Declare the test module: no I/O

reg  [15:0] operand_A;                    // Declare the variables at this level
reg  [15:0] operand_B;                    // 'reg' for values test assigns to
reg   [2:0] func;                         // (i.e. inputs to device)
reg         c_in;
reg         csh;
wire [15:0] result;                       // 'wire' for device's output values
wire  [3:0] flags_out;

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/

Stump_ALU ALU (.operand_A(operand_A),     // Instantiate a 'Stump_ALU'
               .operand_B(operand_B),     // called 'ALU'
	       .func(func),               // and connect its buses
	       .c_in(c_in),               // to signals of the same name
               .csh(csh),
	       .result(result),
	       .flags_out(flags_out));

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/

integer file_handle;                      // The place to write output
integer tst, k;                           // Some internal variables

reg [15:0] A_in [0:8];                    // Small RAMs to hold test values
reg [15:0] B_in [0:8];

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/

initial                                   // Run the following once
begin

// Setup test data here rather than reading from files: easier for small sets
A_in[0] = 16'h4000; B_in[0] = 16'h3FFF;   // Data for test #0
A_in[1] = 16'h5000; B_in[1] = 16'h5000;   // etc.
A_in[2] = 16'h4000; B_in[2] = 16'hBFFF;
A_in[3] = 16'h4000; B_in[3] = 16'hD000;
A_in[4] = 16'hC000; B_in[4] = 16'h4000;
A_in[5] = 16'hD000; B_in[5] = 16'h2FFF;
A_in[6] = 16'hC000; B_in[6] = 16'h9000;
A_in[7] = 16'hC000; B_in[7] = 16'hBFFF;
A_in[8] = 16'h8000; B_in[8] = 16'h8000;

file_handle = $fopen("ALU_test_out.txt"); // Open a message output file
$fdisplay(file_handle, "Outcome from Stump ALU tests\n"); // Output title

for (func = 0; func < 6; func = func + 1) // Iterate over six function types
  begin
  case (func)
    0: $fdisplay(file_handle, "Testing ADD function");
    1: $fdisplay(file_handle, "Testing ADC function");
    2: $fdisplay(file_handle, "Testing SUB function");
    3: $fdisplay(file_handle, "Testing SBC function");
    4: $fdisplay(file_handle, "Testing AND function");
    5: $fdisplay(file_handle, "Testing OR  function");
    default: $fdisplay(file_handle, "Unknown function");
  endcase
  for (tst = 0; tst < 9; tst = tst + 1)   // Iterate over test data
    begin
    operand_A = A_in[tst];                // Set input buses
    operand_B = B_in[tst];
    for (k = 0; k < 2; k = k + 1)         // Iterate over (both) carry states
      begin
       if(func < 4)
        c_in = k;                         // Carry adopts LSB
       else
        csh = k;
      #100                                // Pause so viewable as waveform
      display_state(result, flags_out);   // Write results to output file
      end
    end

  $fdisplay(file_handle, "");             // Blank line after each test block
  end

#100;                                     // Extra pause before finishing

$fclose(file_handle);                     // Close output file

$stop;                                    // Tell simulator to stop
end 

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
/* Example of a 'task' (a bit like a 'method')                                */

task display_state;                       // Task declaration and name
input [15:0] result;                      // Inputs in the appropriate order
input  [3:0] flags;                       // (Outputs (many) are also possible)

case (flags)
  'h0: $fdisplay(file_handle, "Output state: %x  ....", result);
  'h1: $fdisplay(file_handle, "Output state: %x  ...C", result);
  'h2: $fdisplay(file_handle, "Output state: %x  ..V.", result);
  'h3: $fdisplay(file_handle, "Output state: %x  ..VC", result);
  'h4: $fdisplay(file_handle, "Output state: %x  .Z..", result);
  'h5: $fdisplay(file_handle, "Output state: %x  .Z.C", result);
  'h6: $fdisplay(file_handle, "Output state: %x  .ZV.", result);
  'h7: $fdisplay(file_handle, "Output state: %x  .ZVC", result);
  'h8: $fdisplay(file_handle, "Output state: %x  N...", result);
  'h9: $fdisplay(file_handle, "Output state: %x  N..C", result);
  'hA: $fdisplay(file_handle, "Output state: %x  N.V.", result);
  'hB: $fdisplay(file_handle, "Output state: %x  N.VC", result);
  'hC: $fdisplay(file_handle, "Output state: %x  NZ..", result);
  'hD: $fdisplay(file_handle, "Output state: %x  NZ.C", result);
  'hE: $fdisplay(file_handle, "Output state: %x  NZV.", result);
  'hF: $fdisplay(file_handle, "Output state: %x  NZVC", result);
  default:
       $fdisplay(file_handle, "Output state: %x  ????", result);
endcase

endtask

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/

endmodule

/*----------------------------------------------------------------------------*/
