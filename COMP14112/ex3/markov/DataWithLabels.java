package markov;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;

/** 
 *
 * This class extends Data to also store state labels associated with the data. The state labels are numberred 
 * from zero in order of appearance in the sequence. Silence before and after the yes/no states are modelled
 * as different states, since this forces the transitions to go from start->sil->yes/no->sil->end.
 *
 */

public class DataWithLabels extends Data {
    
    private ArrayList <int[]> labelList; // List containing sequence of states associated with each data sequence
    
    /** 
     * Constructor when the MFCC files and Labelling provided
     */

    public DataWithLabels (String pathMfcc, String pathLabels) {
	super(pathMfcc);
	labelList = new ArrayList<int[]>();
	try {
	    File fl = new File(pathLabels);    // Directory containing data labels
	    File[] fileArray = fl.listFiles(); // Array of file objects for all the files in the directory specified by pathLabels

	    // Loop through all examples in the directory specified by pathLabels
	
	    int state;
	    int j;
	    int endTime=0;
	    for(int i=0;i<fileArray.length;i++){
		int exampleLength = Array.getLength(this.getMfcc().get(i)[0]);
		int[] labelSequence = new int[exampleLength]; // Sequence of labels for all times for one example
		FileReader fr = new FileReader(fileArray[i]);
		BufferedReader br = new BufferedReader(fr);
		state=0;
		j=0;
		while((line = br.readLine()) != null) { 
		    String[] theline = line.split(" ");
		    endTime = (int) (Double.parseDouble(theline[1])*(double) mfccRate); // End time for each consecutive state
		    while(j<endTime & j<exampleLength){
			labelSequence[j]=state;
			j++;
		    }
		    state++;
		}
		// If there is a discrepancy with the length pad label sequence with the final state
		if(endTime<exampleLength){
		    while(j<exampleLength){
			labelSequence[j]=state-1;
			j++;
		    }
		}
		labelList.add(labelSequence);
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
     * Method to return state label sequence for one example
     */

    public int[] getLabels (int exampleNumber){
	return labelList.get(exampleNumber);
    }

    /**
     * Method to return state label sequence for all examples
     */

    public ArrayList <int[]> getLabels (){
	return labelList;
    }
    
}



    
