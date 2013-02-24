package data_minining_algorithms;

import java.io.IOException;



public class NeuralNetwork {

	private double [] netInputs;
	private double [] hiddenLayer;
	private double [] bias;
	private double [] error;
	private double output;
	private int iterations;
	private double learningRate;
	private double [] weights;
	private int track;
	Window data;
	private int trackRow;
	private int trackEval;
	private double [] eval;
	public boolean epochs;
	
	
	NeuralNetwork(int inputs,int hidden, Window data) {
		
		this.netInputs = new double [inputs];
		this.hiddenLayer = new double [hidden];
		this.bias = new double [hiddenLayer.length+1];
		this.error = new double [bias.length];
		this.output = 0;
		this.iterations = 1;
		this.learningRate = 1/iterations;
		this.weights = new double [netInputs.length * hiddenLayer.length + hiddenLayer.length];
		this.track = 0;
		this.trackRow = 0;
		this.trackEval = 0;
		this.data = data;
		this.eval = new double[data.getWindowY()];
		this.epochs = false;
		
	}
	
	public void NeuralNetworkGo() {
		
		
		weightSetup(weights); // setup the weights
		biasSetup(bias); // set the node bias'
		normalise(); // normalise the data

	}
	
	
	
	private void normalise() {
		
		for(int j = 0 ; j < data.getWindowY(); j++){
			for (int i = 0; i < data.getWindowX(); i++){
				
				data.set(j, i,(minMax(data.get(j,i),findMin(i),findMax(i))));
		
			}
		}
	}
	
	public void inputSetup(){
		
		if(trackRow == data.getWindowY() - 1 && epochs == true) {
			output = 0;
			iterations++;
			learningRate = 1/iterations;
			track = 0;
			trackRow = 0;
			trackEval = 0;			
		}
		
		for(int i = 0; i < data.getWindowX() - 1;i++) {
			netInputs[i] = data.get(trackRow,i);
		}
		feedForward();
	}

	
	private static double logisticFunction(double x) {
		x = 1/(1+Math.pow(Math.E,-x));
		return x;
	}
	
	private void feedForward() {
		
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
		error[error.length-1] = Math.abs(output - (data.get(trackRow,data.getWindowX() - 1)));
		eval();
		
		//tracks the weight.
		int trackWeights = weights.length - error.length-1;
		
		//iterate back through the network. or in this case over the hidden layer and and update the error
		for(int i = hiddenLayer.length-1; i >= 0; i--) {
		error[i] = hiddenLayer[i] * (1 - hiddenLayer[i]) * error[error.length-1] * weights[trackWeights];
		trackWeights++;
		}		
		
		trackRow++;
		backProp();
	}
	
	private void backProp() {
		
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
	
	private void eval() {
		
		eval[trackEval] = Math.sqrt(error[error.length-1]);
		//System.out.println(error[error.length-1]);
		//System.out.println(eval[trackEval]);
		trackEval++;
		
	}
	
	public double rms() {
		
		double rms = 0;
		
		for(int i = 0; i < eval.length; i++) {
			rms = rms + eval[i];
		}
		
		
		rms = Math.sqrt(rms/eval.length);
		
		return rms;

	}
	
	public void emptyEval(){
		for(int i = 0; i < eval.length;i++) {
			eval[i] = 0.0;
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
	
	public void netInputPrint(){
		
		for(int i =0; i < netInputs.length; i++){
			
			System.out.println(netInputs[i]);

		}		
	}
	
	public void evalPrint(){
		
		for(int i =0; i < eval.length; i++){
			
			System.out.println(eval[i]);

		}		
	}
	
	
	
	
	
	private double findMin(int column){
		
		double min = data.get(0,column);
		for (int i = 1; i < data.getWindowY(); i++) {
			if(data.get(i,column) < min) {
				min = data.get(i,column);
			}
		}
		return min;
	}
	
	private double findMax(int column){
		  
		double max = data.get(0,column);
		for (int i = 1; i < data.getWindowY(); i++) {
			if(data.get(i,column) > max) {
				max = data.get(i,column);
			}
		}
		return max;
	}
	
	
	private double minMax(double orig, double max, double min) {
		
		double newValue = (orig - min) / (max -min);

		return newValue;
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
	
	public static void main(String[] args) throws IOException {
		
		//Test code when NN is class based
		Window win = new Window(7,845,"Year3_Project/Data/Request_analysis_daily.csv",4,5,842);
		NeuralNetwork nn = new NeuralNetwork(4,3,win);
		nn.NeuralNetworkGo();
		nn.inputSetup();
		
		nn.rms();
		//nn.evalPrint();
		//nn.errorPrint();
			
		
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
