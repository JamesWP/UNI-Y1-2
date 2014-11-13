# Gnuplot script file for plotting data in file "data.dat" and fitting
# to function of form f(x) = a*x**b
# 
# This file is called   fitscript
set   autoscale                        # scale axes automatically
set xtic auto                          # set xtics automatically
set ytic auto                          # set ytics automatically
set title "Algorithm"
set xlabel "Size of database"
set ylabel "Runtime (sec)"
set key on
set logscale
f(x)=a*x*log(x) # change this to match your function
fit f(x) "data.dat" using 1:2 via a
plot    "data.dat" using 1:2 title 'Experimental results' with linespoints, \
   f(x) title "f(N) = a*x*log(x)"
