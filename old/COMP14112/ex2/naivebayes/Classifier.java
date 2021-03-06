package naivebayes;

/** A two class naive Bayes classifier. This class stores the prior probability
 * of each class, p(C1) and p(C2), and the conditional probabilities p(x|C1) and p(x|C2) which are modelled as 
 * normal densities for each feature vector component. The constructor method fits the conditional probabilities 
 * by setting the mean and variance of these equal to the empirical mean and variance of data from each
 * class. 
 */

public class Classifier {

    private double priorClass1; // p(C1) - prior probability for Class 1
    private double priorClass2; // p(C2) - prior probability for Class 2
    private Normal[] pxGivenClass1; // p(x|C1) for each feature dimension
    private Normal[] pxGivenClass2; // p(x|C2) for each feature dimension
    private int d; // Dimension of feature vector 

    /**
     * This constructor method fits the parameters of two normal densities
     * and stores the priors for each class
     */


    /**
      * this constructor creats a classifier from two data sets and constructs the probabaility on the number of eachh
      *
      */
    public Classifier (Data feature1,Data feature2) {
      double priorClass1 = (double) feature1.getNumberExamples()/feature2.getNumberExamples()+feature1.getNumberExamples();
      init(feature1.getMeanMfcc(),feature2.getMeanMfcc(),priorClass1);
    }

    // this constructor creates a classifier from the given data with the given previous belief
    public Classifier (double[][] featureClass1,double[][] featureClass2, double pC1)
    {
      init(featureClass1,featureClass2,pC1);
    }
    
    // performes the standard init
    private void init(double[][] featureClass1, double[][] featureClass2, double pC1) {
      priorClass1 = pC1;
      priorClass2 = 1.0 - pC1;  // The prior probabilities for each class must sum to one
      d = featureClass1.length;

      // Fit a normal density for each feature dimension

      pxGivenClass1 = new Normal[d];
      pxGivenClass2 = new Normal[d];
      for (int i=0;i<d;i++){
          pxGivenClass1[i] = new Normal(featureClass1[i]);
          pxGivenClass2[i] = new Normal(featureClass2[i]);
      }
    }
    /**
     * This method returns the probability of being in class 1 using only data from one feature
     * which is in featureVector[featureNo]
     */

    public double classify (double[] featureVector, int featureNo) {
      // Numerator of Bayes' theorem as given in the Lecture notes

      double numerator = pxGivenClass1[featureNo].density(featureVector[featureNo])*priorClass1;

      // Denominator of Bayes' theorem 

      double denominator = numerator + pxGivenClass2[featureNo].density(featureVector[featureNo])*priorClass2;

      return numerator/denominator;
    }

    
    /**
     * This method should be modified in order to return the probability of being in class 1 using all the
     * components of the feature vector
     */

    public double classify (double[] featureVector) {

      double numerator = 1;
      for (int i = 0;i < d; i++)
        numerator *= pxGivenClass1[i].density(featureVector[i]);
      numerator *= priorClass1;
     
      double denominator = 1;
      for (int i = 0;i < d; i++)
        denominator *= pxGivenClass2[i].density(featureVector[i]);
      denominator *= priorClass2;
      denominator+=numerator;
      return numerator/denominator;
    }
} 
