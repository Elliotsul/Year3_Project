package data_minining_algorithms;



public class NeuralNetwork {

	double [] netInputs;
	double [] hiddenLayer;
	double [] bias;
	double [] error;
	double output;
	int iterations;
	double learningRate;
	double [] weights;
	int track;
	
	
	NeuralNetwork(int inputs,int hidden) {
		
		this.netInputs = new double [inputs];
		this.hiddenLayer = new double [hidden];
		this.bias = new double [hiddenLayer.length+1];
		this.error = new double [bias.length];
		this.output = 0;
		this.iterations = 1;
		this.learningRate = 1/iterations;
		this.weights = new double [netInputs.length * hiddenLayer.length + hiddenLayer.length];
		this.track = 0;
		NeuralNetworkSetup();
	}
	
	private void NeuralNetworkSetup() {
	
		inputSetup();
		weightSetup(weights);
		biasSetup(bias);
	}
	
	private static double logisticFunction(double x) {
			x = 1/(1+Math.pow(Math.E,-x));
			return x;
	}

	
	public void backProp() {
		int errorTo = 0;
		int outputFrom = 0;
		int trackWeight = 0;
		
		//to update the weights from input to hiddenlayer
		for(int m = 0 ; m < hiddenLayer.length; m++) {
			for (int j = 0; j < netInputs.length; j++) {
				weights[j] = (weights[j] + learningRate) * error[errorTo] * netInputs[outputFrom];
				outputFrom++;
				trackWeight++;
			}
			outputFrom = 0;
			errorTo++;
		}
		
		//System.out.println(trackWeight);
		//System.out.println(errorTo);
		
		//to update the weights from hiddenlayer to output
		int node = 0;
		for (int h = 0; h < hiddenLayer.length; h++) {
			weights[trackWeight] = (weights[trackWeight] + learningRate) * error[error.length-1] * hiddenLayer[node];
			node++;
			trackWeight++;
		}
		
		//System.out.println(trackWeight);
		
		//bias updating
		for (int h = bias.length - 1; h >= 0; h--) {
			bias[h] = (bias[h] + learningRate) * error[h];
		}
	}
	
	public void feedForward() {
		
		double temp = 0.0;
		
		//iterate over the hiddenlayer
		for(int j = 0; j < hiddenLayer.length;j++) {
			for (int i = 0; i < netInputs.length;i++){ // for each input
				temp = temp + (netInputs[i]*weights[track]);// calculate the sum output of each input
				track++; // track the weight 	
			}
			
			//System.out.println();
			//System.out.println(temp);
			hiddenLayer[j] = logisticFunction(temp + bias[j]); // update the value of the hiddenlayer
			temp = 0; //return temp to zero
		} 
		output();
		track = 0;
	}
	
	private void output() {
		double temp = 0;
		for(int h = 0; h < hiddenLayer.length; h++) {
			temp = temp + hiddenLayer[h]*weights[track];
		}
		output  = logisticFunction(temp + bias[bias.length - 1]);
		temp = 0;
		error();
	}
	
	private void error() {
		
		//Measures the error of the Output
		//THIS NEEDS LOOKING AT
		error[error.length-1] = output * (1 -output) * (1 - output) * ( 1 - output);
		
		//tracks the weight.
		int trackWeights = weights.length - error.length-1;
		
		//iterate back through the network. or in this case over the hidden layer and and update the error
		for(int i = hiddenLayer.length-1; i >= 0; i--) {
		error[i] = hiddenLayer[i] * (1 - hiddenLayer[i]) * error[error.length-1] * weights[trackWeights];
		trackWeights++;
		}		
	}
			
	
	private void biasSetup(double [] bias){
		
		double min = -1.00;
		double max = 1.00;
		
		for(int i =0; i < bias.length; i++) {
			bias[i] = min + Math.random()*(max-min);
			//System.out.println(weights[i]);
		}
	}	
	
	private void weightSetup(double [] weights){
		
		double min = -1.00;
		double max = 1.00;
		
		for(int i =0; i < weights.length; i++) {
			weights[i] = min + Math.random()*(max-min);
			//System.out.println(weights[i]);
		}
	}
	
	private void netInputPrint(){
		
		for(int i =0; i < netInputs.length; i++){
			
			System.out.println(netInputs[i]);
			
		}
		
	}
	
	public void inputSetup(){
		
		netInputs[0] = 1;
		netInputs[1] = 2;
		netInputs[2] = 3;
		netInputs[3] = 1;
		netInputs[4] = 2;
		netInputs[5] = 3;
		
	}
	
	
	public void hiddenLayerPrint(){
		
		for(int i =0; i < hiddenLayer.length; i++){
			
			System.out.println(hiddenLayer[i]);
			
		}
	}
		
	public void errorPrint(){
			
			for(int i =0; i < error.length; i++){
				
				System.out.println(error[i]);				
	}
		
}
	
	public static void main(String[] args) {
		
		//Test code when NN is class based
		NeuralNetwork nn = new NeuralNetwork(6,3);
		nn.NeuralNetworkSetup();
		nn.feedForward();
		nn.backProp();
	
		
		// Test code when neural network was static
		/*inputSetup();
		weightSetup(weights);
		biasSetup(bias);
		feedForward();
		error();
		backProp();
		*/
	}
	
	
	
	

}
