// Verilog HDL for "COMP12111", "memory" "functional"

module memory_1 (input Clk,
		 input [11:0] address,
		 input [15:0] write_data,
		 output reg [15:0] read_data,
		 input WEn);

reg    [15:0] mem [12'h000:12'hFFF];

initial
$readmemh("/opt/info/courses/COMP12111/MU0_examples/MU0_test.mem", mem);

always @ (negedge Clk)
  if (WEn) mem[address] <= write_data;
  else     read_data    <= mem[address];

endmodule
