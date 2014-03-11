package naivebayes;
import java.util.*;
import javagently.*;

/**
 * Plotting normal densities fitted to the time-averaged 1st MFCC component for each class of data
 */

public class PlotFittedNormal {

    public static void main(String[] args)
    {
	
	// Read in the MFCC data for each class
	
	String mfccDataDirectory = "data/yesno/mfcc/"; 
	Data yesData = new Data (mfccDataDirectory+"yes");
	Data noData = new Data (mfccDataDirectory+"no");

	// Put the features for the 1st MFCC into an array for each class and sort

	double[] yesArray = yesData.getMeanMfcc()[0];
	Arrays.sort(yesArray);
	double[] noArray = noData.getMeanMfcc()[0];
	Arrays.sort(noArray);

        // Fit a normal distribution to MFCC 1 for each data set

	Normal yesNormal = new Normal (yesArray);
	Normal noNormal = new Normal (noArray);

	// Set up plot

	int xleft = (int) Math.floor(Math.min(yesArray[0],noArray[0]));
        int xright = (int) Math.ceil(Math.max(yesArray[yesArray.length-1],noArray[noArray.length-1]));
	Graph normalgraph = new Graph("Normal densities fitted to time-averaged MFCC component 1 for each class", "MFCC 1", "");
	normalgraph.setLimits(xleft,xright,0,0.2);
	normalgraph.setColor(2);

	// Plot normal density fitted to the yes data
	
	for (double x=xleft; x<=xright; x+=0.1) {
	    normalgraph.add(x,yesNormal.density(x));
	}
	normalgraph.showGraph();
	normalgraph.setTitle("yes");

	// Plot normal density fitted to the no data

	normalgraph.nextGraph();
	normalgraph.setColor(1);
	for (double x=xleft; x<=xright; x+=0.1) {
	    normalgraph.add(x,noNormal.density(x));
	}
	normalgraph.showGraph();
	normalgraph.setTitle("no"); 
    }
}
