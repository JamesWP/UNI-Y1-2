// MU0 processor sample #3  Sept. 2012

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
// Sequential block which manages what state will be adopted at the end of the
//  current cycle.

always @ (posedge clk or posedge rst)
  if (rst)					// Reset is active
    begin
    pc    <= 12'h000;				// PC starts at 000
    state <= `FETCH;				// Start with instruction fetch
    end
  else
    case (state)

      `FETCH:					// Fetch state
        begin
        ir    <= data_in;
        pc    <= pc + 1;
        state <= `EXECUTE;			// Execute next
	end

      `EXECUTE:					// Execute state
        begin

        case (func)
	  `LDA: acc <= data_in;			// LDA
          `STA: ; // No internal state changes	// STA
          `ADD: acc <= acc + data_in;		// ADD
          `SUB: acc <= acc - data_in;		/  SUB
          `JMP: pc  <= operand;			// JMP
          `JGE: if (acc[15] == 0) pc <= operand;// JGE
          `JNE: if    (|acc != 0) pc <= operand;// JNE
						// Note reduction 'OR' operator
          default: ;				// Do nothing
        endcase

        if (func != `STP) state <= `FETCH;	// Don't halt, fetch again

        end

    endcase

////////////////////////////////////////////////////////////////////////////////

endmodule

////////////////////////////////////////////////////////////////////////////////
