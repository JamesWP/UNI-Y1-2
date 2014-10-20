// Name:   mu0_fpga_top
// Author: Jonathan Heathcote
// Date:   7/9/12
// Modified 11/9/12 by J Pepper
//
// Structural Verilog which connects a system up to the FPGA's pads.

module mu0_fpga_top (inout  wire [15:0] data_pads
                ,input  wire  [6:0] addr_pads
                ,input  wire        ncs_pad
                ,input  wire        nwe_pad
                ,input  wire        nre_pad);

wire [15:0] data_in;
wire [15:0] data_out;
wire  [6:0] addr;
wire        ncs;
wire        nwe;
wire        nre;

// The system being connected to the pads
system_mu0 system(.host_data_in (data_in)   // Data written to the debugger
                 ,.host_data_out(data_out)  // Data to read from the debugger
                 ,.host_addr    (addr[6:1]) // The address requested
                 ,.host_ncs     (ncs)       // Chip select
                 ,.host_nwe     (nwe)       // Write-enable (active low)
                 ,.host_nre     (nre)       // Read-enable (active low)
                 );

// Control signals
IBUF ncs_ibuf        (.I(ncs_pad),   .O(ncs));
IBUF nwe_ibuf        (.I(nwe_pad),   .O(nwe));
IBUF nre_ibuf        (.I(nre_pad),   .O(nre));
IBUF addr_ibuf [6:0] (.I(addr_pads), .O(addr));

// Data bus. This is bidirectional and is tristated (high-impedance) unless
// we're not reading and selected.
wire tristate = ncs | nre;
OBUFT data_obuft[15:0] (.O(data_pads), .I(data_out), .T(tristate));
IBUF  data_ibuf [15:0] (.I(data_pads), .O(data_in));

endmodule
