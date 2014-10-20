// Verilog HDL for "COMP10242", "enc_8_3" "functional"

module enc_8_3 (input i7,
		input i6,
		input i5,
		input i4,
		input i3,
		input i2,
		input i1,
		input i0,
		output reg [2:0] q);


always @ (i7, i6, i5, i4, i3, i2, i1, i0)
  if      (i7) q = 7;
  else if (i6) q = 6;
  else if (i5) q = 5;
  else if (i4) q = 4;
  else if (i3) q = 3;
  else if (i2) q = 2;
  else if (i1) q = 1;
  else if (i0) q = 0;


endmodule
