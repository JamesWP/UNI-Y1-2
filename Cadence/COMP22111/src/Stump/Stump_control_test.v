module Stump_Control_test ();

reg rst;
reg clk;
reg [3:0]  cc;
reg [15:0] ir;
wire memory;
wire fetch;
wire execute;
wire ext_op;
wire reg_write;
wire [2:0] dest;
wire [2:0] srcA;
wire [2:0] srcB;
wire [1:0]  shift_op;
wire opB_mux_sel;
wire [2:0] alu_func;
wire cc_en;
wire mem_ren;
wire mem_wen;

Stump_control control (.rst(rst),
                       .clk(clk),
                       .cc(cc),
                       .ir(ir),
                       .memory(memory),
                       .fetch(fetch),
                       .execute(execute),
                       .ext_op(ext_op),
                       .reg_write(reg_write),
                       .dest(dest),
                       .srcA(srcA),
                       .srcB(srcB),
                       .shift_op(shift_op),
                       .opB_mux_sel(opB_mux_sel),
                       .alu_func(alu_func),
                       .cc_en(cc_en),
                       .mem_ren(mem_ren),
                       .mem_wen(mem_wen));
initial
begin
	clk = 0;
	rst = 1;
  #40 rst=0;
end

integer instruction = 0;

always @ (*)
begin
	#10 clk = !clk;
  if (fetch)
  begin
    instruction = instruction + 1;
		if (instruction==1) ir = 16'b0000_0000_0000_0000;// calling a sample
		if (instruction==2) ir = 16'b0010_0000_0000_0000;// of instructions
		if (instruction==3) ir = 16'b0100_0000_0000_0000;// with stcc and without
		if (instruction==4) ir = 16'b1100_0000_0000_0000;// load
		if (instruction==5) ir = 16'b0001_0000_0000_0000;
		if (instruction==6) ir = 16'b0011_0000_0000_0000;
		if (instruction==7) ir = 16'b0101_0000_0000_0000;
		if (instruction==8) ir = 16'b1101_0000_0000_0000;// store
		if (instruction==7) ir = 16'b1110_0000_0000_0000; 
		if (instruction==8) ir = 16'b1111_0000_0000_0000;
		if (instruction==9)$stop;
  end
end

endmodule

