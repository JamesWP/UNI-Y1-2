// MU0 processor sample #6  Sept. 2012

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
            output reg  [15:0] data_out,	// Data to write to memory
            output reg  [11:0] address,		// Memory address
            output reg         memory_read,	// Memory read enable
            output reg         memory_write,	// Memory write enable
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
assign flags = {acc[15], ~|acc};		// Concatenate bits

////////////////////////////////////////////////////////////////////////////////

wire  [3:0] func;				// Generate aliases for
wire [11:0] operand;				//  instruction fields

assign func    = ir[15:12];			// ("function" is a reserved word)
assign operand = ir[11:0];

////////////////////////////////////////////////////////////////////////////////
// Combinatorial block which determines memory control outputs according to
//  current state and instruction type.

always @ (state, pc, func, operand, acc)
  begin
   data_out = acc;				// Can always be from this source

  if (state == `FETCH)				// During fetch cycle
    begin
    address      = pc;				// Set address multiplexer to PC
    memory_read  = 1;				// Read memory
    memory_write = 0;				// Don't write
    end
  else
    begin					// Must be execution phase
    address = operand;				// (If used at all)
    case (func)
      `LDA, `ADD, `SUB: begin			// LDA, ADD, SUB
                        memory_read  = 1;	// Read
                        memory_write = 0;	// Don't write
                        end
      `STA:             begin			// STA
                        memory_read  = 0;	// Don't read
                        memory_write = 1;	// Write

      default:          begin			// Anything else
                        memory_read  = 0;	// Don't read
                        memory_write = 0;	// Don't write
                        end
    endcase
    end
  end

////////////////////////////////////////////////////////////////////////////////
// Sequential blocks dealing with each register individually


always @ (posedge clk or posedge rst)		// Control FSM
  if (rst)					// Reset is active
    state <= `FETCH;				// Start with instruction fetch
  else
    if ((state == `FETCH) || (func != `STP))	// Fetch state
      state <= ~state;
	// Exploit that there are only two states by toggling bit


always @ (posedge clk or posedge rst)		// PC actions
  if (rst)					// Reset is active
    pc <= 12'h000;				// PC starts at 000
  else
    if (state == `FETCH)			// Fetch state
      pc <= pc + 1;
    else					// Execute state
      case (func)
        `JMP: pc  <= operand;			// JMP
        `JGE: if (acc[15] == 0) pc <= operand;	// JGE
        `JNE: if    (|acc != 0) pc <= operand;	// JNE
        default: ;				// Do nothing
      endcase


always @ (posedge clk)
  if (state == `FETCH) ir <= data_in;		// IR action


always @ (posedge clk)
  if (state == `EXECUTE)			// acc actions
    case (func)
      `LDA: acc <= data_in;			// LDA
      `ADD: acc <= acc + data_in;		// ADD
      `SUB: acc <= acc - data_in;		// SUB
      default: ;				// Do nothing
    endcase


////////////////////////////////////////////////////////////////////////////////

endmodule

////////////////////////////////////////////////////////////////////////////////
