package markov;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;

/** 
 * This class reads in and stores data from a directory containing 
 * a number of Mel frequency cepstrum coefficient (MFCC) vector sequences.
 *
 */

public class Data {

    /**
     * Sampling rate for MFCC vectors in samples per second
     */

    static public int mfccRate = 100;  

    /**
     * Number of MFCCs used
     */

    static public int noMfcc = 13;

    private ArrayList <double[][]> mfccList; // List containing a sequence of MFCC vectors for each example, each one size [noMfcc][lengthSequence]
    private int noExamples; // Number of examples in this data set
    String line = "";
    
    /** 
     * Constructor when the path to the MFCC files is provided
     */

    public Data (String path) {
	mfccList = new ArrayList<double[][]>();
	try {
	    File fl = new File(path); // Directory containing MFCC files
	    File[] fileArray = fl.listFiles(); // Array of file objects for all the files in the directory specified by path
	    noExamples = fileArray.length; 

	    // Loop through all the examples in the directory specified by path

	    for(int i=0;i<noExamples;i++) {
		ArrayList <double[]> timeSequence = new ArrayList<double[]>(); // List of MFCC vectors for all times for one example
		FileReader fr = new FileReader(fileArray[i]);
		BufferedReader br = new BufferedReader(fr);

		// Read the MFCC vector for each sampled time point in this example and add it to a list

		while((line = br.readLine()) != null) { 
		    String[] theline = line.split(" ");
		    double[] sample = new double[noMfcc]; // The MFCC vector for one time in one example
		    for (int j=0;j<noMfcc;j++) {
			sample[j] = Double.parseDouble(theline[j]);
		    }
		    timeSequence.add(sample); // Add the sampled MFCC vector to the list for this example
		}

		// Put the example into a double array - each sequence can be of different length so we have to 
		// specify the size of the array for each example

		double[][] timeArray = new double[noMfcc][timeSequence.size()];
		for(int j=0;j<timeSequence.size();j++)
		    for(int k=0;k<noMfcc;k++)
			timeArray[k][j]=timeSequence.get(j)[k];

		// Add this example to mfccList
	      
		mfccList.add(timeArray); 
	    }
	}
	catch(FileNotFoundException fN) {
	    fN.printStackTrace();
	}
	catch(IOException e){
	    System.out.println(e);
	}
    }

    /**
     * Method to get the number of examples in the data set
     */

    public int getNumberExamples (){
	return noExamples;
    }

    /**
     * Method to get the MFCC vectors for one example of size[noMfcc][sequence length]
     */

    public double[][] getMfcc (int exampleNumber){
	return mfccList.get(exampleNumber);
    }

    /**
     * Method to get a list containing all of the MFCC vector sequences, each of size[noMfcc][sequence length]
     */

    public ArrayList <double[][]> getMfcc (){
	return mfccList;
    }
}



    
