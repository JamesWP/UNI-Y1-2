// Name:   mu0_scanner
// Author: Jonathan Heathcote
// Date:   14/8/12
//
// A device which exposes its various inputs to a read-only scan-path style
// interface compatible with the debugger

module mu0_scanner(input  wire [15:0] acc   // Accumulator
                  ,input  wire [11:0] pc    // Program counter
                  ,input  wire [ 1:0] flags // Flag register
                  
                  ,input  wire        scan_clk // Scan-path clock
                  ,input  wire        scan_en  // Scan-mode enable
                  ,output wire        scan_out // Scan-path bit out
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
		if (scan_bit < (16 + 12 + 2 - 1))
			scan_bit <= scan_bit + 1;
		else
			scan_bit <= 0;


// The current value being scanned out
reg [15:0] cur_value;
// The bit index within that value
reg  [3:0] cur_bit;

// Select the register/bit to be output
always @ (*)
	begin
		if (scan_bit < 16)
			begin
				// Accumulator
				cur_value = acc;
				cur_bit   = scan_bit;
			end
		else if (scan_bit < (16+12))
			begin
				// Program Counter
				cur_value = pc;
				cur_bit   = scan_bit-16;
			end
		else if (scan_bit < (16 + 12 + 2))
			begin
				// Flags
				cur_value = flags;
				cur_bit   = scan_bit - 16 - 12;
			end
	end


assign scan_out = cur_value[cur_bit];


endmodule






