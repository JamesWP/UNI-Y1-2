// Register file for Stump microprocessor

module Stump_registers (input  wire        clk,         // System clock
                        input  wire        rst,         // Master reset
                        input  wire        write_en,    // Write enable
                        input  wire  [2:0] write_addr,  // dest
                        input  wire [15:0] write_data,  // Data in
                        input  wire  [2:0] read_addr_A, // src_A
                        output wire [15:0] read_data_A, // Operand A
                        input  wire  [2:0] read_addr_B, // src_B
                        output wire [15:0] read_data_B,	// Operand B
                        input  wire  [2:0] read_addr_C, // src_C
                        output wire [15:0] read_data_C);// Operand C

reg  [15:0] r [0:7];				// Main register file
wire [15:0] PC;					// Alias for r7 observability only see JSP

initial r[0] = 16'h0000;			// R0 is always zero
// Okay for simulation/FPGA - would not work on ASIC

assign PC = r[7];

// Read ports read registers but force 0000 for R0
assign read_data_A = (read_addr_A == 0) ? 16'h0000 : r[read_addr_A];
assign read_data_B = (read_addr_B == 0) ? 16'h0000 : r[read_addr_B];
assign read_data_C = (read_addr_C == 0) ? 16'h0000 : r[read_addr_C];
// The third port (C) is not used for Stump execution;
// Its function is to allow observability via Perentie on the FPGA.

// Register write is synchronised by the clock
always @ (posedge clk)
  if (rst) r[7] <= 16'h0000;			// If reset, PC := 0000
  else
    if (write_en && (write_addr != 0))		// else write if enabled
      r[write_addr] <= write_data;		// and not R0

// In practice there is no need to trap both reads and writes to 'R0'
// This is done here for illustrative purposes.


// Print out whenever PC changes for debugging purposes
always @ (r[7]) $display("%t PC := %x", $time, r[7]);	// Simulation only

endmodule
