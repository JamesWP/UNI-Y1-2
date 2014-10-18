// Incomplete Stump processor

`include "src/Stump/Stump_definitions.v"

/*----------------------------------------------------------------------------*/

module Stump (input  wire        clk,		// System clock
              input  wire        rst,		// Master reset
              input  wire [15:0] data_in,	// Data from memory
              output wire [15:0] data_out,	// Data to memory
              output wire [15:0] address,	// Address
              output wire        mem_wen,	// Memory write enable
              output wire        mem_ren,	// Memory read enable
              // Extra signals for observability
              output wire        fetch,		// CPU in the fetch state
              input  wire [ 2:0] srcC,		// Extra register port select
              output wire [15:0] regC,		// Extra register port data
              output wire [ 3:0] cc);		// Flags

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
/* Declarations of signals and buses                                          */

wire        reg_write;
wire [2:0]  dest;
wire [15:0] reg_data;
wire [2:0]  srcA, srcB;
wire [15:0] regA, regB;


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
// Instantiate the registers 
Stump_registers registers (.clk(clk),
                           .rst(rst),
                           .write_en(reg_write),
                           .write_addr(dest),
                           .write_data(reg_data),
                           .read_addr_A(srcA), 
                           .read_data_A(regA),
                           .read_addr_B(srcB), 
                           .read_data_B(regB),
                           .read_addr_C(srcC), 		// Debug port address - Perentie
                           .read_data_C(regC));		// Debug port data    - Perentie

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/


endmodule

/*============================================================================*/
