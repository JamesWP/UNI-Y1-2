//Verilog HDL for "COMP12111", "sevensegmentdecoder" "functional"


module sevensegmentdecoder (input [3:0] bcd,
			    output reg [7:0] segments);

always @ (bcd)
  case (bcd)
    0:	segments = 8'b0011_1111;
    1:	segments = 8'b0000_0110;
    2:	segments = 8'b0101_1011;
    3:	segments = 8'b0100_1111;
    4:	segments = 8'b0110_0110;
    5:	segments = 8'b0110_1101;
    6:	segments = 8'b0111_1101;
    7:	segments = 8'b0000_0111;
    8:	segments = 8'b0111_1111;
    9:	segments = 8'b0110_1111;
    4'ha:segments = 8'b0111_0111;
    4'hb:segments = 8'b0111_1100;
    4'hc:segments = 8'b0011_1001;
    4'hd:segments = 8'b0101_1110;
    4'he:segments = 8'b0111_1001;
    4'hf:segments = 8'b0111_0001;

    default: segments = 8'b0000_0000;
  endcase
endmodule
