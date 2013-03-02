package data_minining_algorithms;

import java.io.IOException;



public class NeuralNetwork {

	private double [] netInputs;
	private double [] hiddenLayer;
	private double [] bias;
	private double [] error;
	public double [] result;
	public double output;
	private double iterations;
	private double learningRate;
	public double Min;
	public double Max;
	double [] weights;
	private int trackWeights;
	Window data;
	private int trackRow;
	private int trackEval;
	public double [] eval;
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
		this.trackWeights = 0;
		this.trackRow = 0;
		this.trackEval = 0;
		this.data = data;
		this.eval = new double[data.getWindowY() - 1];
		this.result = new double[data.getWindowY() -1];
		this.epochs = false;
		
		
	}
	
	public void NeuralNetworkGo() {
		
		
		weightSetup(weights); // setup the weights
		biasSetup(bias); // set the node bias'
		normalise(); // normalise the data

		
		/*ORDER of Calculations backpropagation
		 * 
		 * feed data through the network.
		 * calculate the error of the output
		 * update the weights between the hiddenLayer and Output
		 * Update the hiddenLayer nodes
		 * update the weights between the input and the hidden layer
		 */
	}
	
	
	
	private void normalise() {
		
		
		for(int j = 0 ; j < data.getWindowY(); j++){
			for (int i = 0; i < data.getWindowX(); i++){
				
				data.set(j, i,(minMax(data.get(j,i),findMin(i),findMax(i))));
				
				if(data.get(j, i) == -0.0) {
					data.set(j, i, 0.0);	
				}
			}
		}
	}
	
	public void inputSetup(){
		
		if(trackRow == data.getWindowY() - 1 && epochs == true) {
			output = 0;
			iterations++;
			learningRate = 1/iterations;
			trackWeights = 0;
			trackRow = 0;
			trackEval = 0;			
		}
		
		for(int i = 0; i < data.getWindowX() - 1;i++) {
			netInputs[i] = data.get(trackRow,i);
		}
		
		feedForward();
	}

	
	private static double logisticFunction(double x) {
		x = 1/(1 + Math.pow(Math.E,-x));
		return x;
	}
	
	private void feedForward() {
		
		double temp = 0.0;
	
		
		//iterate over the hiddenlayer
		for(int j = 0; j < hiddenLayer.length;j++) {
			for (int i = 0; i < netInputs.length;i++){ // for each input
				temp = temp + (netInputs[i]*weights[trackWeights]);// calculate the sum output of each input
				trackWeights++; // track the weight 	
			}
			
			//System.out.println();
			//System.out.println(temp);
			hiddenLayer[j] = logisticFunction(temp + bias[j]); // update the value of the hiddenlayer
			temp = 0; //return temp to zero
		}
		
		//System.out.println(trackWeights);
		output();
		
	}
	
	private void output() {
		
		double temp = 0;
		
		for(int h = 0; h < hiddenLayer.length; h++) {
			temp = temp + hiddenLayer[h]*weights[trackWeights];
			trackWeights++;
		}
		
		output  = logisticFunction(temp + bias[bias.length - 1]);
		result[trackEval] = output;
		temp = 0;
		//System.out.println(output);

		//System.out.println(trackWeights);
		outputError();
	}
	
	private void outputError() {
		
		//Measures the error of the Output Node
		error[error.length - 1] = output * (output - data.get(trackRow,data.getWindowX() - 1)) * (1 - output);
		eval();
		
		hiddenLayerToOutputWeights();
	
	
	}
	
	private void hiddenLayerToOutputWeights() {
		trackWeights--;
		int node = 0;
		for (int h = hiddenLayer.length - 1; h <= 0; h--) {
			weights[trackWeights] = (weights[trackWeights] + learningRate) * error[error.length-1] * hiddenLayer[node];
			node++;
			trackWeights--;
		}
		
		//System.out.println(trackWeights);
		hiddenLayerError();
	}
	
	
	private void hiddenLayerError() {
		
		
		
		//iterate back through the network. or in this case over the hidden layer and and update the error
		for(int i = hiddenLayer.length-1; i >= 0; i--) {
		error[i] = error[error.length - 1] * weights[trackWeights] * (1 - hiddenLayer[i]) * hiddenLayer[i];
		trackWeights--;
		}		
		
		//System.out.println(trackWeights);
		inputLayerToHiddenWeights();
	}
	
	private void inputLayerToHiddenWeights() {
		
		int nodeTo = hiddenLayer.length - 1;
		int from = netInputs.length - 1;
				
		//to update the weights from input to hiddenlayer
		for (int j = netInputs.length; j > 0; j--){
			for(int m = hiddenLayer.length; m > 0; m--) {
				weights[trackWeights] = weights[trackWeights] + (error[nodeTo] * netInputs[from]);
				nodeTo--;
				trackWeights--;
			}
			from--;
			nodeTo = hiddenLayer.length - 1;
			//System.out.println(track);
		}
		biasUpdate();
		
		//System.out.println(track);
		 }
	
	
	
		
	private void biasUpdate() {
		//bias updating
		for (int h = bias.length - 1; h >= 0; h--) {
			bias[h] = (bias[h] + learningRate) * error[h];
		}
		
		trackRow++;
		reset();

	}
	
	private void eval() {
		
		eval[trackEval] = error[error.length-1];
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
	
	private void reset() {
		
		output = 0;
		iterations++;
		learningRate = 1/iterations;
		trackWeights = 0;
		
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
	


	public double findMin(int column){
		
		double min = data.get(0,column);
		for (int i = 1; i < data.getWindowY(); i++) {
			if(data.get(i,column) < min) {
				min = data.get(i,column);
			}
		}
		return min;
	}
	
	public double findMax(int column){
		  
		double max = data.get(0,column);
		for (int i = 1; i < data.getWindowY(); i++) {
			if(data.get(i,column) > max) {
				max = data.get(i,column);
			}
		}
		return max;
	}
	
	
	public double minMax(double orig, double max, double min) {
		
		double newValue = (orig - min) / (max -min);

		return newValue;
	}
	
	public double invertMinMax(double orig, double max, double min) {
		
		double newValue = (max - min) * orig + min;
		
		return newValue;
	}
	

	public void hiddenLayerPrint(){
		
		for(int i =0; i < hiddenLayer.length; i++){
			
			System.out.println(hiddenLayer[i]);
		}
	}
		
	public void errorPrint(){
			
		for(int i = 0; i < error.length; i++){
				
			System.out.println(error[i]);				
		}
	}
	
	public void weightPrint(){
		
		for(int i = 0; i < weights.length; i++){
				
			System.out.println(weights[i]);				
		}
	}
	
	
	
	public static void main(String[] args) throws IOException {
		
		//Test code when NN is class based
		Window win = new Window(7,845,"Year3_Project/Data/Request_analysis_daily.csv",4,7,845);
		NeuralNetwork nn = new NeuralNetwork(6,3,win);
		
		
		
		nn.NeuralNetworkGo();
		
		for (int i = 0; i < 2; i++) {
		nn.inputSetup();
		System.out.println();
		nn.weightPrint();
		System.out.println();
		nn.netInputPrint();
		
		}
		//System.out.println(nn.rms());
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
