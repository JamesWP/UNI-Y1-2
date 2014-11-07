/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
/* MU0 processor test harness: provides a simple, preloaded synchronous       */
/* memory model, a clock and a reset pulse.                                   */
/* J Garside  Sept. 2012                                                      */

`define PERIOD      100			// Clock period
`define MAX_CYCLES  200			// Cycles from reset to halting

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/

module MU0_test();

  reg         clk, reset;		// Global control signals
  wire [11:0] address;			// Memory address
  reg  [15:0] data_in;			// Data read from memory
  wire [15:0] data_out;			// Data to write to memory
  wire        memory_read;		// Command to read memory
  wire        memory_write;		// Command to write memory

  reg  [15:0] memory [0:1023];		// Memory model (undersized)

  wire        fetch;			// Used to track execution for debugging

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
/* Instantiate the device under test                                          */

//<instance> <name> (<connections>);
MU0 processor(.clk (clk),		// Connect ports to local buses
              .rst (reset),
          .address (address),
          .data_in (data_in),
         .data_out (data_out),
      .memory_read (memory_read),
     .memory_write (memory_write),
	    .fetch (fetch),
	      .acc (),
	       .pc (),
	    .flags ());

// Test does not need other signals {fetch, acc, pc, flags}.
// These are brought out for observability in-circuit - 
// the simulator can observe them in place.
// Unconnected ports may be omitted - although *inputs* to DUT should be driven.

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
/* Generate clock                                                             */

initial clk = 1;			// Clock initialisation
always #(`PERIOD/2) clk = ~clk;		// Clock switching

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
/* Reset and run                                                              */

initial					// This block resets the processor
  begin					// and, later, stops the simulation
  reset = 0;				// First, leave things undefined
  #`PERIOD;				// Wait for one clock cycle
  reset = 1;				// Reset to define state
  #`PERIOD;				// Wait for one clock cycle
  reset = 0;				// Release reset to run
  #(`MAX_CYCLES * `PERIOD);		// Run for predetermined time
  $stop;				// Halt simulation
  end

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
/* Simple memory model                                                        */

initial $readmemh("$COMP22111/MU0_src/gcd.hex", memory);// Choose one file
//initial $readmemh("$COMP22111/MU0_src/total.hex", memory);
					// Memory initialisation

always @ (address, memory[address])	// Simple asynchronous memory read
  #20 data_in = memory[address];	// With cosmetic delay added

always @ (posedge clk)			// Make memory writes synchronous
  if (memory_write) memory[address] = data_out;

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
/* Example of how printout may be used to trace execution                     */
/* This gives a trace of fetched instructions in ncsim.log                    */

always @ (posedge clk)
  if (fetch) $display("Fetching op. code %x from %x at time %t", data_in,
                       address, $time);

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/

endmodule

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
