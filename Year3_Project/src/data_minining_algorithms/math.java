package data_minining_algorithms;

public class math {

	// This is a class i used to test my logistic function and also the of 
	// summing of the weights as input to to hidden Layer.
	
	public static void main(String[] args) {
		
		double a = 0.5326798345584598 * 1;
		double b = 0.2278753898665289 * 2;
		double c = 0.735971001870078 * 3;
		double d = 0.7379450204288822 * 1;
		double e =  0.5350980855799474 * 2;
		double f = 0.34283278569240716 * 3;
		
		double total = a+b+c+d+e+f;
		
		System.out.println(a+b+c+d+e+f);

		//6.0329831685677515
		
		System.out.println(logisticFunction(total));
	}

	
	public static double logisticFunction(double x) {
		x = 1/(1+Math.pow(Math.E,-x));
		return x;
	}
}
