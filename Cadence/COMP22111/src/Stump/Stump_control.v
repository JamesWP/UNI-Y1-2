// P W Nutter
//
// January 2013


`include "src/Stump/Stump_definitions.v"

/*----------------------------------------------------------------------------*/

module Stump_control (input  wire rst,
                      input  wire clk,
                      input  wire [3:0]  cc,            // current status of cc
                      input  wire [15:0] ir,            // current instruction
                            
                      output reg         fetch,
                      output reg         execute,    // current state
                      output reg         memory,
                      output reg         ext_op,              
                      output reg         reg_write,    // register write enable
                      output reg  [2:0]  dest,            // destination register for writeback              
                      output reg  [2:0]  srcA,            // Source register for operand A
                      output reg  [2:0]  srcB,            // Source register for operand B
                      output reg  [1:0]  shift_op,
                      output reg         opB_mux_sel,   // operandB mux select
                      output reg  [2:0]  alu_func,      // alu function derived from ir
                      output reg         cc_en,         // cc register enable
                      output reg         mem_ren,    // Memory read enable              
                      output reg         mem_wen    // Memory write enable
                      );

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
/* Declarations of signals and buses                       */

reg [1:0] state;

// is load or store
wire instr;
assign instr = ir[15:13];

// Stump FSM
always @ (posedge clk, posedge rst)
begin
  if (!rst)
  begin
    case (state)
      `FETCH   : state <= `EXECUTE;
      `EXECUTE : state <= (instr==`LDST)?`MEMORY:`FETCH;
      `MEMORY  : state <= `FETCH;
      default  : state <= `FETCH;
    endcase
  end else begin
    state <= `FETCH;
  end
end


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
// Control decoder
/*
// stcc from instruction
wire stcc;
assign = ir[12];

// condition code
wire cond;
assign = ir[11:8];

assign fetch = state==`FETCH;
assign execute = state==`EXECUTE;
assign memory = state==`MEMORY;

always @ (*)
begin
  case (state)
    `FETCH  : begin
      reg_write = 1'b1;
      ext_op = 1'bX;
      dest = 3'b111;
      srcA = 3'b111;
      srcB = 3'bXXX;
      shift_op = 2'bXX;
    end;
    `EXECUTE: begin
      reg_write = (instr == `BCC) 
                   ? Testbranch(cond,cc) 
                   : (instr != `LDST);
      ext_op = stcc & (instr==`BCC);
      dest = ir[10:8];// from instruction
      srcA = (instr!=`BCC)
              ?ir[7:5]
              :3'bXXX;
      srcB = ((instr!=`BCC) && !stcc)
              ?ir[4:2]
              :3'bXXX;
      shift_op = ((instr!='BCC) && !stcc)
                  ?ir[1:0]
                  :2'bXX; 
    end;
    `MEMORY : begin
      reg_write = !stcc;
      ext_op = '1bX;
      dest = (stcc)
              ? 3'bXXX
              :ir[10:8];
      srcA = (stcc)
              ?ir[10:8]
              : 3'bXXX;
      srcB = 3'bXXX;
      shift_op = 2'bXX;
    end;
    default : begin
      reg_write = 1'bX;
      ext_op = 1'bX;
      dest = 3'bXXX;
      srcA = 3'bXXX;
      srcB = 3'bXXX;
      shift_op = 2'bXX;
    end
  endcase
end
*/
/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
/* Condition evaluation - an example 'function'                               */

function Testbranch;         // Returns '1' if branch taken, '0' otherwise
input [3:0] condition;        // Condition bits from instruction
input [3:0] CC;            // Current condition code register
reg N, Z, V, C;

begin
  {N,Z,V,C} = CC;        // Break condition code register into flags
  case (condition)
     0 : Testbranch =   1;    // Always (true)
     1 : Testbranch =   0;    // Never (false)
     2 : Testbranch = ~(C|Z);
     3 : Testbranch =   C|Z;
     4 : Testbranch =  ~C;
     5 : Testbranch =   C;
     6 : Testbranch =  ~Z;
     7 : Testbranch =   Z;
     8 : Testbranch =  ~V;
     9 : Testbranch =   V;
    10 : Testbranch =  ~N;
    11 : Testbranch =   N;
    12 : Testbranch =   V~^N;
    13 : Testbranch =   V^N;
    14 : Testbranch = ~((V^N)|Z) ;
    15 : Testbranch =  ((V^N)|Z) ;
  endcase
end
endfunction

/*----------------------------------------------------------------------------*/

endmodule

/*============================================================================*/
