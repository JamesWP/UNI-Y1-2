package markov;
import java.util.*;
import java.math.*;
import javagently.*;

/**
 * Class demonstrating how to read in some MFCC and label file data and which plots the state labels corresponding to an MFCC data sequence.
 * There are 165 examples in total, 82 of yes and 83 of no. If you type a number 
 * between 1 and 165 you will see that one plotted, otherwise you will see the first one.
 **/

public class PlotLabels {

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

	String mfccDataDirectory = "data/yesno_uncut/mfcc/"; 
	String labelDirectory = "data/yesno_uncut/labels/";

	// We want to number the example by starting from 0 for each class

	DataWithLabels exampleData;
	if (exampleNumber<82)
	    exampleData = new DataWithLabels (mfccDataDirectory+"yes",labelDirectory+"yes");
	else{
	    exampleData = new DataWithLabels (mfccDataDirectory+"no",labelDirectory+"no");
	    exampleNumber -=82;
	    exampleClass="no";
	}
	
	// Plot the 1st MFCC 

	double[][] mfccSequence = exampleData.getMfcc(exampleNumber); // size[13][sequence length]
	int lengthMfccSequence = mfccSequence[0].length;

	// Set up plot parameters for another plot

	Graph mfccGraph = new Graph("MFCC 1 for example "+Integer.toString(exampleNumber+1)+" of "+exampleClass, "Time", " ");
	mfccGraph.setLimits(0,lengthMfccSequence/(double) Data.mfccRate,0,1);
	mfccGraph.setColor(2);
	mfccGraph.showGraph();

	for (int i=0; i<lengthMfccSequence; i++) {

	    double time = i/(double) Data.mfccRate; // Work out the time
	    mfccGraph.add(time,mfccSequence[0][i]); // Add the first MFCC component at this time    
	}
	mfccGraph.showGraph();

	// Plot the associated labels

	int[] labelSequence = exampleData.getLabels(exampleNumber); 
	int lengthLabelSequence = labelSequence.length;

	// Set up plot parameters for another plot to show the state labels

	Graph labelGraph = new Graph("State labels for example "+Integer.toString(exampleNumber+1)+" of "+exampleClass, "Time", " ");
	labelGraph.setLimits(0,lengthLabelSequence/(double) Data.mfccRate,0,2);
	labelGraph.setColor(2);
	labelGraph.showGraph();

	for (int i=0; i<lengthMfccSequence; i++) {

	    double time = i/(double) Data.mfccRate; // Work out the time
	    labelGraph.add(time,labelSequence[i]); // Add the label at this time    
	}
	labelGraph.showGraph();  
    }
}
