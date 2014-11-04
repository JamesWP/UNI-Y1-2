# Gnuplot script file for plotting data in file "data.dat" on log log scale
#
set   autoscale                        # scale axes automatically
set xtic auto                          # set xtics automatically
set ytic auto                          # set ytics automatically
set title "Algorithm"
set xlabel "Size of database"
set ylabel "Runtime (sec)"
set logscale
plot    "data.dat" using 1:2 with linespoints 
unset logscale
