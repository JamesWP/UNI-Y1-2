// Name:   system_stump
// Author: Jonathan Heathcote
// Date:   27/8/12
//
// Structural Verilog connecting a STUMP model to a host system.

module system_stump (input  wire [15:0] host_data_in  // Data written to the debugger
                    ,output wire [15:0] host_data_out // Data to read from the debugger
                    ,input  wire  [6:1] host_addr     // The address requested
                    ,input  wire        host_ncs      // Chip select
                    ,input  wire        host_nwe      // Write-enable (active low)
                    ,input  wire        host_nre      // Read-enable (active low)
                    );

// Stump control signals
wire        clk;      // The system clock
wire        reset;    // System Reset signal
wire        fetch;    // Fetch signal out of DUT
wire [15:0] addr;     // Memory requested by the DUT
wire [15:0] data_in;  // Data read by the DUT
wire [15:0] data_out; // Data written by the DUT
wire        ren;      // DUT read-enable signal
wire        wen;      // DUT write-enable signal

// Broken-out register signals
wire        regs_connected = 1'b1; // The registers are broken out
wire [15:0] reg_bank;              // Broken-out register bank value
wire [ 2:0] reg_sel;               // Broken-out register select
wire [ 3:0] cc;                    // Broken-out Flags


// The debug interface to the host computer
stump_debugger dbg(.data_in       (host_data_in)
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
                  ,.dut_ren       (ren)
                  ,.dut_wen       (wen)
                  ,.dut_reg_bank  (reg_bank)
                  ,.dut_reg_sel   (reg_sel)
                  ,.dut_cc        (cc)
                  ,.regs_connected(regs_connected)
                  );

// The STUMP CPU to debug.
Stump dut(.clk     (clk)
         ,.rst     (reset)
         ,.mem_ren (ren)
         ,.mem_wen (wen)
         ,.address (addr)
         ,.data_in (data_in)
         ,.data_out(data_out)
         // Broken-out registers
         ,.srcC(reg_sel)
         ,.regC(reg_bank)
         ,.cc   (cc)
	 ,.fetch(fetch)
         );

endmodule
