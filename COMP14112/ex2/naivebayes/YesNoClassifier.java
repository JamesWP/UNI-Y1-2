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

      //Classifier classifier = new Classifier(yesData.getMeanMfcc(),noData.getMeanMfcc(),0.5);
      Classifier classifier = new Classifier(yesData,noData);

      // Compute the probability of being in class one for the first yes example
      // using the 1st time-averaged MFCC as the feature

      int featureNumber = 0; // Using this MFCC component (0 is 1st component)

      int totalSamples = yesData.getNumberExamples();
      totalSamples += noData.getNumberExamples();

      int totalIncorect = 0;

      for (int i=0; i<yesData.getNumberExamples();i++)
        if (classifier.classify(yesData.getMeanMfcc(i))<0.5)
          totalIncorect++;
        
      for (int i=0; i<noData.getNumberExamples();i++)
        if (classifier.classify(noData.getMeanMfcc(i))>0.5)
          totalIncorect++;

      System.out.println("The percentage of incorrect classifications is "
        +Float.toString((float) totalIncorect / totalSamples * 100));
      System.out.println("with " + totalIncorect + " incorrect out of " + totalSamples);
    }
}
