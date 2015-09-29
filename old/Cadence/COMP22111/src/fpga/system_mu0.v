// Name:   system_mu0
// Author: Jonathan Heathcote
// Date:   27/8/12
// Modified JDG 20/9/12
// //
// Structural Verilog connecting a MU0 model to a host system.

module system_mu0 (input  wire [15:0] host_data_in  // Data written to the debugger
                  ,output wire [15:0] host_data_out // Data to read from the debugger
                  ,input  wire  [6:1] host_addr     // The address requested
                  ,input  wire        host_ncs      // Chip select
                  ,input  wire        host_nwe      // Write-enable (active low)
                  ,input  wire        host_nre      // Read-enable (active low)
                  );

// mu0 control signals
wire        clk;      // The system clock
wire        reset;    // System Reset signal
wire        fetch;    // Fetch signal out of DUT
wire [11:0] addr;     // Memory requested by the DUT
wire [15:0] data_in;  // Data read by the DUT
wire [15:0] data_out; // Data written by the DUT
wire        wen;      // DUT write-enable signal

// Broken-out register signals
wire        regs_connected = 1'b1; // The registers are broken out
wire [15:0] acc;                   // Broken-out register bank value
wire [11:0] pc;                    // Broken-out register select
wire [ 1:0] flags;                 // Broken-out Flags


// The debug interface to the host computer
mu0_debugger dbg(.data_in       (host_data_in)
                ,.data_out      (host_data_out)
                ,.addr          (host_addr)
                ,.ncs           (host_ncs)
                ,.nwe           (host_nwe)
                ,.nre           (host_nre)
                ,.dut_clk       (clk)
                ,.dut_reset     (reset)
                ,.dut_fetch     (fetch)
                ,.dut_addr      (addr)
                ,.dut_data_in   (data_in)
                ,.dut_data_out  (data_out)
                ,.dut_ren       (~wen) // Read whenever not writing
                ,.dut_wen       (wen)
                ,.dut_acc       (acc)
                ,.dut_pc        (pc)
                ,.dut_flags     (flags)
                ,.regs_connected(regs_connected)
                );

// The MU0 CPU to debug.
MU0 dut(.clk         (clk)
       ,.rst         (reset)
       ,.memory_write(wen)
       ,.memory_read ()		// Read can always occur
       ,.address     (addr)
       ,.data_in     (data_in)
       ,.data_out    (data_out)
       // Broken-out registers
       ,.acc  (acc)
       ,.pc   (pc)
       ,.flags(flags)
       ,.fetch(fetch)
       );

endmodule
