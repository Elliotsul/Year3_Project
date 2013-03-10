package data_minining_algorithms;


import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;




public class NeuralNetwork {

	private double [] netInputs;
	private double [] hiddenLayer;
	private double [] bias;
	private double [] error;
	public double [] result;
	public double [] eval;
	public double output;
	private double iterations;
	private double learningRate;
	public double Min;
	public double Max;
	double [] weights;
	private int trackWeights;
	WindowAdvanced data;
	private int trackRow;
	private int trackEval;
	
	public boolean epochs;
	public boolean test;
	public int numberEpochs;
	public String weightData;
	public String biasData;
	
	//Contructors
	
	NeuralNetwork(int inputs,int hidden, WindowAdvanced data) {
		
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
		this.eval = new double[data.getWindowY()];
		this.result = new double[data.getWindowY()];
		this.epochs = false;
		this.numberEpochs = 0;
		this.test = false;
		this.Min = findMin(data.getWindowX()-1);
		this.Max = findMax(data.getWindowX()-1);
		
		
		
	}
	
	NeuralNetwork(int inputs,int hidden, WindowAdvanced data,String weightData, String biasData) {
		
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
		this.eval = new double[data.getWindowY()-1];
		this.result = new double[data.getWindowY()-1];
		this.epochs = false;
		this.numberEpochs = 0;
		this.test = true;
		this.weightData = weightData;
		this.biasData = biasData;
		this.Min = findMin(data.getWindowX()-1);
		this.Max = findMax(data.getWindowX()-1);

	}
	
	//Setup Neural Network
	
	public void NeuralNetworkGo() throws Exception {
		
		if(test == false) {
		weightSetup(weights); // setup the weights
		biasSetup(bias); // set the node bias'
		normalise(); // normalise the data
		} else {
				readWeights(weightData);
				readBias(biasData);
				normalise();
		}
	}
		
		/*ORDER of Calculations backpropagation
		 * 
		 * feed data through the network.
		 * calculate the error of the output
		 * update the weights between the hiddenLayer and Output
		 * Update the hiddenLayer nodes
		 * update the weights between the input and the hidden layer
		 */
	
	
	//Normalise data
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
	
	
	//Setup the Neural Network input nodes
	
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

	
	//inputNodes to hiddenLayer 
	
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
			hiddenLayer[j] = logisticFunction((temp + bias[j])); // update the value of the hiddenlayer
			temp = 0.0; //return temp to zero
		}
		
		//System.out.println(trackWeights);
		//hiddenLayerPrint();
		output();
		
	}
	
	private void output() {
		
		double temp = 0;
		
		for(int h = 0; h < hiddenLayer.length; h++) {
			temp = temp + hiddenLayer[h]*weights[trackWeights];
			trackWeights++;
		}
		//System.out.println(output);
		output  = logisticFunction(temp + bias[bias.length - 1]);
		result[trackEval] = output;
		temp = 0;
		//System.out.println(result[trackEval]);
		//System.out.println(trackWeights);
		outputError();
	}
	
	private void outputError() {
		
		//Calculates the error of the Output Node
		error[error.length - 1] = output * (output - data.get(trackRow,data.getWindowX()-1)) * (1 - output);
		
		//Stores that error for evaluation -- choose one!
		//storeForEvaluation(error[error.length-1]);
		storeForEvaluation((data.get(trackRow, data.getWindowX()-1)) - output);
		
		//If testing the neural network, skip updating methods
		if(test == false){
		hiddenLayerToOutputWeights();
		} else {
			resetTest();
		}
	}
	
	// Update the weights from the Hidden Layer to the output Layer
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
	
	
	//update the error from the hiddenlayer
	private void hiddenLayerError() {
		
		//iterate back through the network. or in this case over the hidden layer and and update the error
		for(int i = hiddenLayer.length-1; i >= 0; i--) {
		error[i] = error[error.length - 1] * weights[trackWeights] * (1 - hiddenLayer[i]) * hiddenLayer[i];
		trackWeights--;
		}		
		
		//System.out.println(trackWeights);
		inputLayerToHiddenWeights();
	}
	
	//Update the weights between the input layer to the Hidden Layer
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
		}
		biasUpdate();
	}

	
	//Update the bias
	private void biasUpdate() {
		//bias updating
		for (int h = bias.length - 1; h >= 0; h--) {
			bias[h] = (bias[h] + learningRate) * error[h];
		}
		trackRow++;
		reset();
	}
	
	
	//Operation Methods//
	private static double logisticFunction(double x) {
		x = 1/(1 + Math.pow(Math.E,-x));
		return x;
	}
	
	private void storeForEvaluation(double error) {
		
		eval[trackEval] = error;
		trackEval++;
		
	}
	
	public void reverseNormalisation() {
		for (int i = 0; i < result.length; i++) {
			result[i] = invertMinMax(result[i],Max, Min);
		}	
	}
	
	public double mse() {
		
		double rms = 0;
		
		for(int i = 0; i < eval.length; i++) {
			rms = rms + (Math.pow(eval[i],2));
		}
		rms = (rms/eval.length);	
		return rms;
	}
	
	public double rmse() {
		
		double rms = 0;
		
		for(int i = 0; i < eval.length; i++) {
			rms = rms + (Math.pow(eval[i],2));
		}
	
		rms = Math.sqrt(rms/eval.length);
		return rms;
	}
	
	public void emptyResult(){
		for(int i = 0; i < result.length;i++) {
			result[i] = 0.0;
		}
	}
	
	private void biasSetup(double [] bias){
		
		double min = -1.00;
		double max = 1.00;
		
		for(int i =0; i < bias.length; i++) {
			bias[i] = min + Math.random()*(max-min);
		}
	}
	
	private void reset() {
		output = 0;
		emptyHiddenLayer();
		iterations++;
		learningRate = 1/iterations;
		trackWeights = 0;
	}
	
	private void resetTest() {
		output = 0;
		emptyHiddenLayer();
		iterations++;
		learningRate = 1/iterations;
		trackWeights = 0;
		trackRow++;
	}
	
	private void weightSetup(double [] weights){
		
		double min = -1.00;
		double max = 1.00;
		
		for(int i =0; i < weights.length; i++) {
			weights[i] = min + Math.random()*(max-min);
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
	
	public void resultPrint(){
		for(int i =0; i < result.length; i++){
			System.out.println(result[i]);
		}		
	}
	
	public double findMin(int column) {
		
		double [] temp = new double[data.getWindowY()];
		double bubble;
		
		for(int i =0; i < data.getWindowY(); i++) {
			temp[i] = data.get(i, column);
		}
		
		for(int m = 0; m < temp.length ;m++){
			for(int j =1; j < (temp.length - m);j++) {
				if (temp[j-1] > temp[j]){
					bubble = temp[j-1];
					temp[j-1] = temp[j];
					temp[j] = bubble;
				}
			}
		}
		
		int k =0;
		
		while(temp[k] == 0.0) {
			k++;
		}
		
		double min = temp[k];		
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
	
	
	public int getInputLength(){
		return netInputs.length;
	}
	
	public int getHiddenlength() {
		return hiddenLayer.length;
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
	
	public void isTest() {
		this.test = true;
	
	}

	//Store Weights to a CSV file
	public void storeWeights(String filename) throws IOException {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		
		String [] temp = new String [netInputs.length * hiddenLayer.length + hiddenLayer.length];
		for(int i = 0; i < weights.length; i++) {
			temp[i] = String.valueOf(weights[i]);
		}
		
		    for (int m = 0; m < weights.length; m++){
		    writer.write(temp[m]);
		    writer.write(',');
		    //System.out.println(temp[m]);
		    }
		    writer.close();
	}
		
	//Read Weights to a CSV file
	public void readWeights(String file) throws Exception, IOException {
		
		String [] temp = new String [15];
		CSVReader reader = new CSVReader(new FileReader(file));
		
		while ((temp = (reader.readNext())) != null) {	
		
			for(int k=0;k<weights.length;k++){
				weights[k] = Double.parseDouble((temp[k]));
			}
		}
	}
	
	//Write bias values to a CSV file
	public void storeBias(String filename) throws IOException {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		
		String [] temp = new String [bias.length];
		for(int i = 0; i < bias.length; i++) {
			temp[i] = String.valueOf(bias[i]);
		}
		
		    for (int m = 0; m < bias.length; m++){
		    writer.write(temp[m]);
		    writer.write(',');
		    //System.out.println(temp[m]);
		    }
		    writer.close();
	}
	
	//Read Bias values from a CSV file
	public void readBias(String biasData) throws Exception, IOException {
		String [] temp = new String [bias.length];
		CSVReader reader = new CSVReader(new FileReader(biasData));
		
		while ((temp = (reader.readNext())) != null) {
		
		for(int k=0;k<bias.length;k++){
			bias[k]=Double.parseDouble(temp[k]);
			
			}
		}
	}
	
	private void emptyHiddenLayer() {
		for (int i = 0; i < hiddenLayer.length; i++) {
			hiddenLayer[i] = 0.0;
		}
	}
}
