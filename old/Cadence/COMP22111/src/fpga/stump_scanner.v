// Name:   stump_scanner
// Author: Jonathan Heathcote
// Date:   14/8/12
//
// A device which exposes its various inputs to a read-only scan-path style
// interface compatible with the debugger

module stump_scanner (input  wire [15:0] reg_bank      // Register-bank input
                     ,output reg  [ 2:0] reg_sel       // Register-bank selection
                     ,input  wire [ 3:0] cc            // Flag register
                     
                     ,input  wire             scan_clk // Scan-path clock
                     ,input  wire             scan_en  // Scan-mode enable
                     ,output wire             scan_out // Scan-path bit out
                     );

// A counter which stores the bit number currently being read
reg [7:0] scan_bit;


// The scan-path register operating off the scan path clock.
always @ (posedge scan_clk)
	if (!scan_en)
		// When clocked without the scan enable, reset the counter
		scan_bit <= 0;
	else
		// When clocked and enabled, count up and wrap-around on the last bit
		if (scan_bit < (((16*8) + 4) - 1))
			scan_bit <= scan_bit + 1;
		else
			scan_bit <= 0;


// The current regiser value being scanned out
reg [15:0] cur_value;
// The bit index within that value to scan out
reg  [3:0] cur_bit;

// Select the register/bit to be output
always @ (*)
	begin
		reg_sel = 3'hX;
		
		if (scan_bit < (16*8))
			begin
				// The registers R0-R7
				reg_sel   = scan_bit >> 4; // scan_bit / 16
				cur_value = reg_bank;
				cur_bit   = scan_bit[3:0]; // scan_bit % 16
			end
		else if (scan_bit < ((16*8) + 4))
			begin
				// The flags
				cur_value = cc;
				cur_bit   = {2'h0, scan_bit[1:0]}; // scan_bit % 4
			end
	end

assign scan_out = cur_value[cur_bit];


endmodule



