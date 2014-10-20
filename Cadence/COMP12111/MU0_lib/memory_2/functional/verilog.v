// Verilog HDL for "COMP10242", "memory_2" "functional"

module memory_2 (input Clk0,
		 input [11:0] address0,
		 input [15:0] write_data0,
		 output reg [15:0] read_data0,
		 input WEn0, 
                 input Clk1,
		 input [11:0] address1,
		 input [15:0] write_data1,
		 output reg [15:0] read_data1,
		 input WEn1);

reg    [15:0] mem [12'h000:12'hFFF];


initial
$readmemh("MU0_demo.mem", mem);

always @ (negedge Clk0)
  if (WEn0) mem[address0] <= write_data0;
  else      read_data0    <= mem[address0];

always @ (negedge Clk1)
  begin
  if (WEn1) mem[address1] <= write_data1;
  read_data1 <= mem[address1];
  end
   
endmodule
