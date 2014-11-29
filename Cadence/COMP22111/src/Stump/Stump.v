// Incomplete Stump processor

`include "src/Stump/Stump_definitions.v"

/*----------------------------------------------------------------------------*/

module Stump (input  wire        clk,    // System clock
              input  wire        rst,    // Master reset
              input  wire [15:0] data_in,  // Data from memory
              output wire [15:0] data_out,  // Data to memory
              output wire [15:0] address,  // Address
              output wire        mem_wen,  // Memory write enable
              output wire        mem_ren,  // Memory read enable
              // Extra signals for observability
              output wire        fetch,    // CPU in the fetch state
              input  wire [ 2:0] srcC,    // Extra register port select
              output wire [15:0] regC,    // Extra register port data
              output wire [ 3:0] cc);    // Flags

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
/* Declarations of signals and buses                                          */

wire reg_write;
wire [2:0]  dest;
wire [15:0] reg_data;
wire [2:0]  srcA, srcB;
wire [15:0] regA, regB;

wire        memory;
wire [15:0] ir;
wire        ext_op;
wire [15:0] immed;
wire        opB_mux_sel; 
wire [15:0] src_2;
wire [15:0] operand_A, operand_B;
wire        csh;
wire        cc_en;
wire [1:0]  shift_op;
wire [3:0]  alu_flags;
wire [2:0]  alu_func;
wire [15:0] alu_out;
wire        execute;
wire [15:0] addr_reg;

assign data_out = regA;

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
// Instantiate the registers 
Stump_registers registers (.clk(clk),
                           .rst(rst),
                           .write_en(write_reg),
                           .write_addr(dest),
                           .write_data(reg_data),
                           .read_addr_A(srcA), 
                           .read_data_A(regA),
                           .read_addr_B(srcB), 
                           .read_data_B(regB),
                           .read_addr_C(srcC),     // Debug port address - Perentie
                           .read_data_C(regC));    // Debug port data    - Perentie

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/

Stump_mux16bit reg_data_mux (.D0(alu_out),
                             .D1(data_in),
                             .S(memory),
                             .Q(reg_data));

Stump_reg16bit reg_ir (.CLK(clk),
                       .CE(fetch),
                       .D(data_in),
                       .Q(ir));


Stump_sign_extender immed_sign_extend (.ext_op(ext_op),
                                       .D(ir[7:0]),
                                       .Q(immed));

Stump_mux16bit immed_reg_mux (.D0(regB),
                              .D1(immed),
                              .S(opB_mux_sel),
                              .Q(src_2));

Stump_mux16bit incr_mux (.D0(src_2),
                         .D1(16'd1),
                         .S(fetch),
                         .Q(operand_B));

Stump_shift shifter (.operand_A(regA),
                     .c_in(cc[0]),
                     .shift_op(shift_op),
                     .c_out(csh),
                     .shift_out(operand_A));

Stump_ALU alu (.operand_A(operand_A),  // First operand
               .operand_B(operand_B),  // Second operand
               .func(alu_func),        // Function specifier
               .c_in(cc[0]),           // Carry input
               .csh(csh),              // Carry from shifter
               .result(alu_out),       // ALU output
               .flags_out(alu_flags)); // Flags {N, Z, V, C}


Stump_reg4bit reg_cc (.CLK(clk),
                       .CE(cc_en),
                       .D(alu_flags),
                       .Q(cc));

Stump_reg16bit reg_address (.CLK(clk),
                            .CE(execute),
                            .D(alu_out),
                            .Q(addr_reg));


Stump_mux16bit mem_addr_mux (.D0(regA),
                             .D1(addr_reg),
                             .S(memory),
                             .Q(address));

/* - - - - - - - - -*/
// Instantiate the control
Stump_control control (.rst(rst),
                       .clk(clk),
                       .cc(cc),                  // current status of cc
                       .ir(ir),                  // current instruction
                       .fetch(fetch),
                       .execute(execute),        // current state
                       .memory(memory),
                       .ext_op(ext_op),          
                       .reg_write(write_reg),    // register write enable
                       .dest(dest),              // destination register for writeback          
                       .srcA(srcA),              // Source register for operand A
                       .srcB(srcB),              // Source register for operand B
                       .shift_op(shift_op),
                       .opB_mux_sel(opB_mux_sel),// operandB mux select
                       .alu_func(alu_func),      // alu function derived from ir
                       .cc_en(cc_en),            // cc register enable
                       .mem_ren(mem_ren),        // Memory read enable          
                       .mem_wen(mem_wen));       // Memory write enable

                      

endmodule

/*============================================================================*/
