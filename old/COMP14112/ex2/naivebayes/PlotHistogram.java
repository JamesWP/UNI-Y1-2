package naivebayes;
import java.util.*;
import javagently.*;

/**
 * Plotting histogram of time-averaged 1st MFCC component for each class of data
 */

public class PlotHistogram {

    public static void main(String[] args)
    {
	
	// Read in MFCC data 
	
	String mfccDataDirectory = "data/yesno/mfcc/"; 
	Data yesData = new Data (mfccDataDirectory+"yes");
	Data noData = new Data (mfccDataDirectory+"no");

	// First sort the feature values for the time-averaged 1st MFCC into accending order for each class

	double[] yesArray = yesData.getMeanMfcc()[0];
	Arrays.sort(yesArray);
	double[] noArray = noData.getMeanMfcc()[0];
	Arrays.sort(noArray);

	// Set range of plot

	int xleft = (int) Math.floor(Math.min(yesArray[0],noArray[0]));
        int xright = (int) Math.ceil(Math.max(yesArray[yesArray.length-1],noArray[noArray.length-1]));

	// Set up the plot parameters

	Graph histogram = new Graph("Histogram of time-averaged MFCC component 1 for each class", "MFCC 1", " ");
	histogram.setLimits(xleft,xright,0,0.2);
	histogram.setColor(2);

	// Plot the yes data histogram, binning into unit width bins

	int indx=0;
	int counter=0;
	double px;
	for (int x=xleft; x<=xright; x++) {
	    px = (double) counter/yesArray.length;
	    histogram.add(x,px);
	    counter = 0;
	    while(yesArray[indx] <= x & indx < yesArray.length-1){
		counter++;
		indx++;
	    }
	    px = (double) counter/yesArray.length;
	    histogram.add(x,px);
	}
        histogram.showGraph();
	histogram.setTitle("yes");

	// Plot the no data histogram, binning into unit width bins

	histogram.nextGraph();
	histogram.setColor(1);

	indx=0;
	counter=0;
	for (int x=xleft; x<=xright; x++) {
	    px = (double) counter/noArray.length;
	    histogram.add(x,px);
	    counter = 0;
	    while(noArray[indx] <= x & indx < noArray.length-1){
		counter++;
		indx++;
	    }
	    px = (double) counter/noArray.length;
	    histogram.add(x,px);
	}
        histogram.showGraph();
	histogram.setTitle("no");
    }
}

