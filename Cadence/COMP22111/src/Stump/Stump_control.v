// P W Nutter
//
// January 2013


`include "src/Stump/Stump_definitions.v"

/*----------------------------------------------------------------------------*/

module Stump_control (input  wire rst,
                      input  wire clk,
                      input  wire [3:0]  cc,            // current status of cc
                      input  wire [15:0] ir,            // current instruction
                            
                      output wire        fetch,
                      output wire        execute,    // current state
                      output wire        memory,
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

// instruction type
wire [2:0] instr;
assign instr = ir[15:13];

// Stump FSM decides next state from curent and instruction type
always @ (posedge clk)
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

// assigns the state monitors // used in simulation
assign fetch = state==`FETCH;
assign execute = state==`EXECUTE;
assign memory = state==`MEMORY;

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
// Control decoder

// stcc from instruction
wire stcc;
assign stcc = ir[11];

// instruction type
wire intype;
assign intype = ir[12];

// condition code
wire [3:0] cond;
assign cond = ir[11:8];


always @ (*)
begin
  case (state)
    `FETCH  : begin
      reg_write = 1'b1;
      ext_op = 1'bX;
      dest = 3'b111;
      srcA = 3'b111;
      srcB = 3'bXXX;
      shift_op = 2'b00;
			opB_mux_sel = 1'bX;
			alu_func = `ADD;
			cc_en = 1'b0;
			mem_ren = 1'b1;
			mem_wen = 1'b0;
    end
    `EXECUTE: begin
      reg_write = (instr == `BCC) 
                   ? Testbranch(cond,cc) // if branch use Testbranch 
                   : (instr != `LDST); // otherwise store if alu func
      ext_op = (intype)
                ? (instr==`BCC)
                : 1'bX;
      dest = (instr!=`BCC)
              ? ir[10:8]
              : 3'b111;// from instruction
      srcA = (instr!=`BCC)
              ?ir[7:5]
              :3'b111;
      srcB = ((instr!=`BCC) && !intype)
              ?ir[4:2]
              :3'bXXX;
      shift_op = ((instr!=`BCC) & !intype)
                  ?ir[1:0]
                  :2'b00; 
      opB_mux_sel = intype;
			alu_func = ir[15:13];
			cc_en = (instr!=`BCC && instr!=`LDST)
               ? stcc
               : 1'b0;
			mem_ren = 1'b0;
			mem_wen = 1'b0;
    end
    `MEMORY : begin
      reg_write = !stcc;
      ext_op = 1'bX;
      dest = (stcc)
              ? 3'bXXX
              :ir[10:8];
      srcA = (stcc)
              ?ir[10:8]
              : 3'bXXX;
      srcB = 3'bXXX;
      shift_op = 2'bXX;
			opB_mux_sel = 1'bX;
			alu_func = 3'bXXX;
			cc_en = 1'b0;
			mem_ren = !stcc;
			mem_wen = stcc;
    end
    default : begin
      reg_write = 1'bX;
      ext_op = 1'bX;
      dest = 3'bXXX;
      srcA = 3'bXXX;
      srcB = 3'bXXX;
      shift_op = 2'bXX;
			opB_mux_sel = 1'bX;
			alu_func = 3'bXXX;
			cc_en = 1'bX;
			mem_ren = 1'bX;
			mem_wen = 1'bX;
    end
  endcase
end

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
