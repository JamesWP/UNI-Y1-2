# Gnuplot script file for plotting data with errorbars. Data must consist
#   of three columns, where the third column is the size of the errorbar.
#   Data is in file "dataerror.dat"
# 
set   autoscale                        # scale axes automatically
set xtic auto                          # set xtics automatically
set ytic auto                          # set ytics automatically
set title "Algorithm"
set xlabel "Size of database"
set ylabel "Runtime (sec)"
plot "dataerror.dat" with errorlines 
