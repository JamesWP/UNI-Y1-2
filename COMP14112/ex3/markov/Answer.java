package markov;
import java.util.*;
import java.math.*;
import java.lang.reflect.*;

/**
 * Class with main method for presenting the results of the lab. At the moment this just reads in the sequence data 
 * and the corresponding state labels.
 */

public class Answer {

    public static void main(String[] args)
    {
	String mfccDataDirectory = "data/yesno_uncut/mfcc/"; 
	String labelDirectory = "data/yesno_uncut/labels/";

	// Read in the MFCC data and state labels from each class

	DataWithLabels dataClass1 = new DataWithLabels (mfccDataDirectory+"yes",labelDirectory+"yes");
	DataWithLabels dataClass2 = new DataWithLabels (mfccDataDirectory+"no",labelDirectory+"no");

	// Task 1

	// Task 2

	// Task 3

	// Task 4
    }
}
