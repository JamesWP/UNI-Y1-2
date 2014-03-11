package naivebayes;

/** 
 * Class representing a univariate Normal distribution
 */

public class Normal {

    public double mean;
    public double variance;

    /** 
     * Constructor method to create a normal object given the mean and variance
     */

    public Normal (double m,double v) {
	mean = m;
	variance = v;
    }

    /** 
     * Constructor method to create a normal object by fitting the mean and variance to
     * the empirical mean and variance of some data held in an array
     */

     public Normal (double[] data) {
	 mean = 0.0;
	 variance = 0.0;

	 for (int i=0;i<data.length;i++) {
	     mean += data[i];
	 }
	 mean = mean/data.length;

	 for (int i=0;i<data.length;i++) {
	     variance += Math.pow(data[i]-mean,2);
	 }
	 variance = variance/data.length;
     }

    /** 
     * Method to evaluate the density function p(x) at a specified point x
     */

    public double density (double x) {
	return Math.exp(-Math.pow(x-mean,2)/(2*variance))/Math.sqrt(2*Math.PI*variance);
    }
}
    
