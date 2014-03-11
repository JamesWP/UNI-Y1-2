package naivebayes;
import java.util.*;
import javagently.*;

/**
 * Using a naive Bayes classifier to distinguish utterances of the word yes from the word no
 */

public class YesNoClassifier {

    public static void main(String[] args)
    {
	// Read in MFCC data 
	
	String mfccDataDirectory = "data/yesno/mfcc/"; 
	Data yesData = new Data (mfccDataDirectory+"yes");
	Data noData = new Data (mfccDataDirectory+"no");

	// Build a naive Bayes classifier

	Classifier classifier = new Classifier(yesData.getMeanMfcc(),noData.getMeanMfcc(),0.5);

	// Compute the probability of being in class one for the first yes example
	// using the 1st time-averaged MFCC as the feature

	int featureNumber = 0; // Using this MFCC component (0 is 1st component)
	int exampleNumber = 0; // Classifying this example

	double answer = classifier.classify(yesData.getMeanMfcc(exampleNumber),featureNumber);
		
	System.out.println("The probability of this example being a yes is "+Float.toString((float) answer));
    }
}
