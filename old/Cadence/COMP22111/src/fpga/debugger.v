// Name:   debugger
// Author: Jonathan Heathcote
// Date:   2/8/12
//
// A debugger for controlling and debugging a 16-bit CPU implementation running
// on the FPGA. Provides an addressable interface for use by a controling uC
// which can control clock generation, memory-simulation and scan-path
// operation.

// Scan-path control addresses
`define REG_SCAN_CONTROL   7'h00
`define REG_SCAN_IN        7'h02
`define REG_SCAN_OUT       7'h04
// DUT control addresses
`define REG_DUT_CONTROL  7'h06
`define REG_DUT_FETCH    7'h08
`define REG_DUT_ADDR     7'h0A
`define REG_DUT_RWEN     7'h0C
`define REG_DUT_DATA_OUT 7'h0E
`define REG_DUT_DATA_IN  7'h10

// Komodo CPU-type Identifiers for the attached device
`define REG_CPU_TYPE    7'h20
`define REG_CPU_SUBTYPE 7'h22

module debugger // External interface to the uC running the 'Ackie' host program
                (input  wire [15:0] data_in        // Data written to the debugger
                ,output reg  [15:0] data_out       // Data to read from the debugger
                ,input  wire  [6:1] addr           // The address requested
                ,input  wire        ncs            // Chip select
                ,input  wire        nwe            // Write-enable (active low)
                ,input  wire        nre            // Read-enable (active low)
                // Connections to the device under test (e.g. a STUMP or MU0)
                ,output wire        dut_clk         // Clock generated for the DUT
                ,output wire        dut_scan_clk    // Scan-path clock generated for the DUT
                ,output reg         dut_reset       // DUT Reset signal
                ,output reg         dut_scan_en     // Scan-mode enable generated for the DUT
                ,output reg         dut_scan_in     // Scan-path into DUT
                ,input  wire        dut_scan_out    // Scan-path out of DUT
                ,input  wire        dut_fetch       // Fetch signal out of DUT
                ,input  wire [15:0] dut_addr        // Memory requested by the DUT
                ,output reg  [15:0] dut_data_in     // Data read by the DUT
                ,input  wire [15:0] dut_data_out    // Data written by the DUT
                ,input  wire        dut_ren         // DUT read-enable signal
                ,input  wire        dut_wen         // DUT write-enable signal
                ,input  wire  [7:0] dut_cpu_type    // Komodo CPU type constant
                ,input  wire  [7:0] dut_cpu_subtype // Komodo CPU sub-type bottom-byte
                );

// The current state of the system and scan clocks. These are buffered onto
// clock nets which are then exposed..
reg dut_clk_unbuffered;
reg dut_scan_clk_unbuffered;

// Xilinx Clock Buffers. These sends the clock signals onto special clock nets
// in the FPGA to allow propper clock distribution.
BUFG BUFG_clk (.O(dut_clk)            // Wire on the clock net
              ,.I(dut_clk_unbuffered) // Clock buffer input
              );
BUFG BUFG_scan_clk(.O(dut_scan_clk)            // Wire on the clock net
                  ,.I(dut_scan_clk_unbuffered) // Clock buffer input
                  );

// Debugger interface
always @ (*)
	begin
		data_out = 16'hXXXX;
		
		if (!ncs)
			// Convert the address into an ARM-style byte-address
			case ({addr, 1'b0})
				// Control the scan clk and scan enable (bits 0 and 1). All other bits
				// are ignored.
				`REG_SCAN_CONTROL:
					if      (!nre) data_out = {dut_scan_en, dut_scan_clk_unbuffered};
					else if (!nwe) {dut_scan_en, dut_scan_clk_unbuffered} = data_in[1:0];
				
				// The bit that will be scanned into the stump on clock (bit 0). All
				// other bits are ignored.
				`REG_SCAN_IN:
					if      (!nre) data_out = dut_scan_in;
					else if (!nwe) dut_scan_in = data_in[0];
				
				// The bit that is being scanned out of the stump (bit 0). All other
				// bits are 0.
				`REG_SCAN_OUT:
					if (!nre) data_out = dut_scan_out;
				
				// Control the clk and reset lines for the stump in bits 0 and
				// 1 respectively. All other bits are invalid and ignored.
				`REG_DUT_CONTROL:
					if      (!nre) data_out = {dut_reset, dut_clk_unbuffered};
					else if (!nwe) {dut_reset, dut_clk_unbuffered} = data_in[1:0];
				
				// Signal asserted while the STUMP is in the FETCH state, i.e.
				// immediately after it has done a complete step. This can be checked to
				// allow stepping instruction-by-instruction. Read-only.
				`REG_DUT_FETCH:
					if (!nre) data_out = dut_fetch;
				
				// Memory address produced by the STUMP. Read-only.
				`REG_DUT_ADDR:
					if (!nre) data_out = dut_addr;
				
				// Read/Write-enable signals (active high) from the STUMP in bit 0 and
				// 1 respectively.  All other bits are zero meaning a read of zero from
				// this register means no operation is required. Read-only.
				`REG_DUT_RWEN:
					if (!nre) data_out = {14'h0, dut_wen, dut_ren};
				
				// Data to be written to memory when dut_wen is high. Read-only.
				`REG_DUT_DATA_OUT:
					if (!nre) data_out = dut_data_out;
				
				// Data returning from memory to the dut_ren
				// being high.
				`REG_DUT_DATA_IN:
					if      (!nre) data_out = dut_data_in;
					else if (!nwe) dut_data_in = data_in;
				
				// CPU-type returned. Read-only.
				`REG_CPU_TYPE:
					if (!nre) data_out = dut_cpu_type;
				
				// CPU-subtype returned. Read-only.
				`REG_CPU_SUBTYPE:
					if (!nre) data_out = {8'h00, dut_cpu_subtype};
				
				default:
					if (!nre) // Default read value
						data_out = 16'hXXXX;
			endcase
	end


endmodule
