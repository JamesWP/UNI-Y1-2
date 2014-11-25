module Stump_Control_test ();


// signals for control sim
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

// instance of control
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

// init signals and reset system
initial
begin
	clk = 0;
	rst = 1;
  #100 rst=0;
  #90000 $stop;
end

// current 4msb of instruction
reg [3:0] instruction = 0;

// clock simulator
always #50 clk = !clk;

// change ir on fetch to new instruction for decode
always @(fetch, rst)
begin
  if (fetch & !rst)
  begin
    instruction = instruction + 1;
		ir = {instruction,12'b0000_0000_0000}; // compose new instruction from 
																					//  instruction type and 0's
  end
end

endmodule

