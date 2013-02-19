package data_minining_algorithms;

public class NeuralNetwork {

	static double [] netInputs = new double [6];
	static double [] hiddenLayer = new double [3];
	static double [] bias = new double [4];
	static double [] error = new double [4];
	static double output = 0;
	static int iterations = 1;
	static double learningRate = 1/iterations;
	static double [] weights = new double [21];
	static int track = 0;
	
	public static void main(String[] args) {
	
		
		
		weightSetup(weights);
		biasSetup(bias);
		feedForward();
		error();

		
	}
	
	
	public static double logisticFunction(double x) {
		
		
			x = 1/(1+Math.pow(Math.E,-x));
			return x;
	}
	
	public static void SigmoidFunction() {
		
		
	}
	
	public static void feedForward() {
		
		double temp = 0.0;
		
		for(int j = 0; j < hiddenLayer.length;j++) {
			for (int i = 0; i < netInputs.length;i++){
				temp = temp + (netInputs[i]*weights[track]);
				track++;
			}
			hiddenLayer[j] = logisticFunction(temp + bias[j]);
			temp = 0;
		} 
		output();
		track = 0;
	}
	
	public static void output() {
		double temp = 0;
		for(int h = 0; h < hiddenLayer.length; h++) {
			temp = temp + hiddenLayer[h]*weights[track];
		}
		
		output  = logisticFunction(temp + bias[bias.length - 1]);
		temp = 0;
	}
	
	public static void error() {
		error[3] = output * (1 -output) * (1 - output) * ( 1 - output);
		
		error[0] = hiddenLayer[0] * (1 - hiddenLayer[0]) * error[3] * weights[18];
		error[1] = hiddenLayer[1] * (1 - hiddenLayer[1]) * error[3] * weights[19];
		error[2] = hiddenLayer[2] * (1 - hiddenLayer[2]) * error[3] * weights[20];
				
	}
	
	
	public static void biasSetup(double [] bias){
		
		double min = -1.00;
		double max = 1.00;
		
		for(int i =0; i < bias.length; i++) {
			bias[i] = min + Math.random()*(max-min);
			//System.out.println(weights[i]);
		}
	}	
	
	public static void weightSetup(double [] weights){
		
		double min = -1.00;
		double max = 1.00;
		
		for(int i =0; i < weights.length; i++) {
			weights[i] = min + Math.random()*(max-min);
			//System.out.println(weights[i]);
		}
	}
	
	
	

}
