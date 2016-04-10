package org.edu.regression;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

/**
 * Multiple regression using a number of sample data. The regression parameters
 * / beta is calculated. Need more than 2 observations of at least 2 independent
 * parameters.
 * 
 * Reference:
 * http://commons.apache.org/proper/commons-math/userguide/stat.html#a1.
 * 5_Multiple_linear_regression
 * 
 * @author shivam.maharshi
 */
public class MultipleRegression {

	public static void execute(double[] y, double[][] x) {
		OLSMultipleLinearRegression mlr = new OLSMultipleLinearRegression();
		// mlr.setNoIntercept(true);
		mlr.newSampleData(y, x);
		// Regression parameters.
		double[] beta = mlr.estimateRegressionParameters();
		System.out.println("Estimated Regression Parameters: ");
		for (double b : beta)
			System.out.print(b + " ");
		double[] residual = mlr.estimateResiduals();
		System.out.println("\nEstimated Residual Values: ");
		for (double r : residual)
			System.out.print(r + " ");
		System.out.println("\nCalculated R-Squared value is: " + mlr.calculateRSquared());
		System.out.println("Estimated Regress and Variance: " + mlr.estimateRegressandVariance());
		System.out.println("Estimated Regression Error: " + mlr.estimateRegressionStandardError());
	}

	public static void main(String[] args) {
		double[] y = new double[] { 5.0, 2.0, 3.0 };
		double[][] x = new double[3][];
		x[0] = new double[] { 1.0, 1.0 };
		x[1] = new double[] { 1.0, 0 };
		x[2] = new double[] { 0, 1.0 };
		execute(y, x);
	}

}
