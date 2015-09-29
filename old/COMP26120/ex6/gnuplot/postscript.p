# Gnuplot script file producing postscript from current plot"
# Postscript file is called   my-plot.ps

set size 1.0, 0.6
set terminal postscript portrait enhanced mono dashed lw 1 "Helvetica" 14 
set output "my-plot.ps"
replot
set terminal x11
set size 1,1
