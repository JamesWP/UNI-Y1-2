// MU0 processor sample #4  Sept. 2012

`define FETCH   0				// State enumeration
`define EXECUTE 1

`define LDA 4'h0				// Function enumeration
`define STA 4'h1
`define ADD 4'h2
`define SUB 4'h3
`define JMP 4'h4
`define JGE 4'h5
`define JNE 4'h6
`define STP 4'h7

module MU0 (input  wire        clk,		// System clock
            input  wire        rst,		// Master reset
            input  wire [15:0] data_in,		// Data read from memory
            output wire [15:0] data_out,	// Data to write to memory
            output wire [11:0] address,		// Memory address
            output wire        memory_read,	// Memory read enable
            output wire        memory_write,	// Memory write enable
            // Additional exposed signals for observability
            output wire        fetch,		// In the fetch state?
            output reg  [15:0] acc,		// The accumulator
            output reg  [11:0] pc,		// The program counter
            output wire  [1:0] flags		// The N and Z flags
            );

// Declare internal registers
reg [15:0] ir;					// Instruction register
reg        state;				// FSM state

// Create debug (observability) signals
assign fetch = (state == `FETCH);
assign flags[0] = ~|acc;
assign flags[1] = acc[15];

////////////////////////////////////////////////////////////////////////////////

wire  [3:0] func;				// Generate aliases for
wire [11:0] operand;				//  instruction fields

assign func    = ir[15:12];			// ("function" is a reserved word)
assign operand = ir[11:0];

////////////////////////////////////////////////////////////////////////////////
// Derive memory control signals from 'logic equations'

wire read;					// Operand read required?
assign read = (func == `LDA) || (func == `ADD) || (func == `SUB);

assign memory_read  = ((state == `FETCH)   || read);
assign memory_write = ((state == `EXECUTE) && (func == `STA));

assign address      = (state == `FETCH) ? pc : operand;
		// Multiplexer implemented usign 'query' operator

assign data_out     = acc;

////////////////////////////////////////////////////////////////////////////////
// Combinatorial block to decide what next state should be

reg        next_state;
reg [11:0] next_pc;
reg [15:0] next_acc;
reg [15:0] next_ir;

always @ (state, pc, acc, data_in, func, operand)
  begin
  if (state == `FETCH)
    begin
    next_pc    = pc + 1;
    next_acc   = acc;
    next_ir    = data_in;
    next_state = `EXECUTE;
    end
  else
    begin

    case (func)
      `JMP:     next_pc = operand;
      `JGE:     next_pc = (acc[15] == 0) ? operand : pc;
      `JNE:     next_pc =    (|acc != 0) ? operand : pc;
       default: next_pc = pc;
     endcase

    case (func)
      `LDA:     next_acc = data_in;
      `ADD:     next_acc = acc + data_in;
      `SBU:     next_acc = acc - data_in;
       default: next_acc = acc;
     endcase

    next_ir = ir;				// Shouldn't change here

    case (func)
      `STP:     next_state = `EXECUTE;		// i.e. halt
       default: next_state = `FETCH;		// Go back and fetch again
    endcase

    end
  end

////////////////////////////////////////////////////////////////////////////////
// State holding elements
// Some people prefer this style

always @ (posedge clk or posedge rst)
  if (rst)					// Reset is active
    begin
    state <= `FETCH;				// Start with instruction fetch
    pc    <= 12'h000;				// PC starts at 000
    acc   <= 16'hxxxx;				// acc starts undefined
    ir    <= 16'hxxxx;				//  as does IR
    end
  else
    begin
    state <= next_state;
    pc    <= next_pc;
    acc   <= next_acc;
    ir    <= next_ir;
    end

////////////////////////////////////////////////////////////////////////////////

endmodule

////////////////////////////////////////////////////////////////////////////////
