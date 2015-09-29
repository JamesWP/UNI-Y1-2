// Name:   stump_debugger
// Author: Jonathan Heathcote
// Date:   27/8/12
//
// A simple interface for debugging a STUMP. Provides a clock and memory
// interface and optionally allows certain registers to be broken-out (as
// read-only values).


// CPU type constants
`define CPU_TYPE_STUMP 8'h03
`define CPU_TYPE_MU0   8'h04

// Register availability (CPU sub-type) constants
`define CPU_SUB_TYPE_REG_AND_MEM 8'h00
`define CPU_SUB_TYPE_MEMORY_ONLY 8'h01

module stump_debugger // External interface to the uC running the 'Ackie' host program
                      (input  wire [15:0] data_in        // Data written to the debugger
                      ,output wire [15:0] data_out       // Data to read from the debugger
                      ,input  wire  [6:1] addr           // The address requested
                      ,input  wire        ncs            // Chip select
                      ,input  wire        nwe            // Write-enable (active low)
                      ,input  wire        nre            // Read-enable (active low)
                      // Connections to the device under test (e.g. a STUMP or MU0)
                      ,output wire        dut_clk        // Clock generated for the DUT
                      ,output wire        dut_reset      // DUT Reset signal
                      ,input  wire        dut_fetch      // Fetch signal out of DUT
                      ,input  wire [15:0] dut_addr       // Memory requested by the DUT
                      ,output wire [15:0] dut_data_in    // Data read by the DUT
                      ,input  wire [15:0] dut_data_out   // Data written by the DUT
                      ,input  wire        dut_ren        // DUT read-enable signal
                      ,input  wire        dut_wen        // DUT write-enable signal
                      // Broken-out connections to the STUMP's internal
                      // registers
                      ,input  wire [15:0] dut_reg_bank   // An additional register-bank input
                      ,output wire [ 2:0] dut_reg_sel    // Register-bank select for that input
                      ,input  wire [ 3:0] dut_cc         // Flag register
                      ,input  wire        regs_connected // Are the above register signals connected?
                      );

// The CPU sub-type depends on whether the registers are connected
wire [7:0] cpu_sub_type = regs_connected ? `CPU_SUB_TYPE_REG_AND_MEM
                                         : `CPU_SUB_TYPE_MEMORY_ONLY;

// Scan-path signals
wire scan_clk;
wire scan_en;
wire scan_in;
wire scan_out;

// An instance of the debugger
debugger dbg(.data_in (data_in)
            ,.data_out(data_out)
            ,.addr    (addr)
            ,.ncs     (ncs)
            ,.nwe     (nwe)
            ,.nre     (nre)
            // STUMP Interface signals
            ,.dut_clk     (dut_clk)
            ,.dut_reset   (dut_reset)
            ,.dut_fetch   (dut_fetch)
            ,.dut_addr    (dut_addr)
            ,.dut_data_in (dut_data_in)
            ,.dut_data_out(dut_data_out)
            ,.dut_ren     (dut_ren)
            ,.dut_wen     (dut_wen)
            // Scan-path
            ,.dut_scan_clk(scan_clk)
            ,.dut_scan_en (scan_en)
            ,.dut_scan_in (scan_in)
            ,.dut_scan_out(scan_out)
            // The CPU info magic-number
            ,.dut_cpu_type   (`CPU_TYPE_STUMP)
            ,.dut_cpu_subtype(cpu_sub_type)
            );

// A register scanner
stump_scanner scn(.reg_bank(dut_reg_bank)
                 ,.reg_sel (dut_reg_sel)
                 ,.cc      (dut_cc)
                 
                 ,.scan_clk(scan_clk)
                 ,.scan_en (scan_en)
                 ,.scan_out(scan_out)
                 // No scan-in!
                 );


endmodule
