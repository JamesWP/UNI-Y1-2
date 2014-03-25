package markov;
import java.util.*;
import javagently.*;

/**
 * Class demonstrating how to read in some MFCC and wav file data, and plot an example.
 * There are 165 examples in total, 82 of yes and 83 of no. If you type a number 
 * between 1 and 165 you will see that one plotted, otherwise you will see the first one.
 */

public class PlotSound {

    public static void main(String[] args)
    {
	int exampleNumber=0; // Default choice of example
	String exampleClass="yes"; // Default classification
	double lower_limit=0.0; // Plot lower limit;
	double upper_limit=0.0; // Plot upper limit

	// If integer provided in standard input, use this example instead (must be integer between 1 and 165)

	if (args.length==1){
	    try {exampleNumber=Integer.decode(args[0])-1;}
	    catch (NumberFormatException e) {
		System.out.println(e);
	    };
	}
	
	// Read in MFCC and associated wav file data - this reads in all the data from the associated
	// directories, not just the example being plotted.

	String mfccDataDirectory = "data/yesno_uncut/mfcc/"; 
	String wavDataDirectory = "data/yesno_uncut/wav/";

	// We want to number the example by starting from 0 for each class

	DataWithWav exampleData;
	if (exampleNumber<82)
	    exampleData = new DataWithWav (mfccDataDirectory+"yes",wavDataDirectory+"yes");
	else{
	    exampleData = new DataWithWav (mfccDataDirectory+"no",wavDataDirectory+"no");
	    exampleNumber -=82;
	    exampleClass="no";
	}

	// Get the sound amplitude for this example

	double[] ampSequence = exampleData.getSoundWave(exampleNumber); 
	int lengthAmpSequence = ampSequence.length; // No. of frames in this example

	// Get the y-axis range for plotting

	for (int i=0; i<lengthAmpSequence; i++) {
	    if (ampSequence[i]<lower_limit)
		lower_limit = ampSequence[i];
	    if (ampSequence[i]>upper_limit)
		upper_limit = ampSequence[i];
	}

	// Set up the plot parameters

	Graph ampGraph = new Graph("Sound wave amplitude for example "+Integer.toString(exampleNumber+1)+" of "+exampleClass, "Time", " ");
	ampGraph.setLimits(0,lengthAmpSequence/(double) DataWithWav.wavRate+0.01,lower_limit,upper_limit);
	ampGraph.setColor(2);

	// Get data for the 1st MFCC for the same example

	double[][] mfccSequence = exampleData.getMfcc(exampleNumber); // size[13][sequence length]
	int lengthMfccSequence = mfccSequence[0].length;

	// Set up plot parameters for MFCC plot

	Graph mfccGraph = new Graph("MFCC 1 for example "+Integer.toString(exampleNumber+1)+" of "+exampleClass, "Time", " ");
	mfccGraph.setLimits(0,lengthMfccSequence/(double) Data.mfccRate,0,1);
	mfccGraph.setColor(2);

	// Plot the sound amplitude

	for (int i=0; i<lengthAmpSequence; i++) {

	    // Plot only every 4th point to avoid plot having too many points

	    if(i%4==0){
		double time = i/(double) DataWithWav.wavRate; // Work out time 
		ampGraph.add(time,ampSequence[i]); // Add one sample to the plot
	    }
	}
	ampGraph.showGraph();

	for (int i=0; i<lengthMfccSequence; i++) {

	    double time = i/(double) Data.mfccRate; // Work out the time
	    mfccGraph.add(time,mfccSequence[0][i]); // Add the first MFCC component at this time    
	}
	mfccGraph.showGraph();  
    }
}
