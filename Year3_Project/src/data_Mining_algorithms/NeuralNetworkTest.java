package data_Mining_algorithms;

import java.io.IOException;

/* NeuralNetworkTest extends the standard class except no
 * back propagation is coded
 * 
 */

public class NeuralNetworkTest extends NeuralNetwork {

	public String weightData;
	public String biasData;
	
	//constructor one takes an input of strucutre, a window and weight and bias values
	NeuralNetworkTest(int inputs,int hidden, WindowBasic data,String weightData, String biasData) {
		super(inputs, hidden,data);
		this.netInputs = new double [inputs];
		this.hiddenLayer = new double [hidden];
		this.bias = new double [hiddenLayer.length+1];
		this.error = new double [bias.length];
		this.output = 0;
		this.iterations = 1;
		this.learningRate = 1/iterations;
		this.weights = new double [netInputs.length * hiddenLayer.length + hiddenLayer.length];
		this.trackWeights = 0;
		this.setTrackRow(0);
		this.trackEval = 0;
		this.data = data;
		this.eval = new double[data.getWindowY()-1];
		this.epochs = false;
		this.weightData = weightData;
		this.biasData = biasData;
		this.Min = findMin(data.getWindowX()-1);
		this.Max = findMax(data.getWindowX()-1);
	}
	
	//Constructor two takes as  input the structure and a window
	NeuralNetworkTest(int inputs,int hidden, WindowBasic data) {
		super(inputs, hidden,data);
		this.netInputs = new double [inputs];
		this.hiddenLayer = new double [hidden];
		this.bias = new double [hiddenLayer.length+1];
		this.error = new double [bias.length];
		this.output = 0;
		this.iterations = 1;
		this.learningRate = 1/iterations;
		this.weights = new double [netInputs.length * hiddenLayer.length + hiddenLayer.length];
		this.trackWeights = 0;
		this.setTrackRow(0);
		this.trackEval = 0;
		this.data = data;
		this.eval = new double[data.getWindowY()-1];
		this.epochs = false;
		this.Min = findMin(data.getWindowX()-1);
		this.Max = findMax(data.getWindowX()-1);
		this.weightData = "null";
		this.biasData = "null";
	}
	
	public void NeuralNetworkGo() throws IOException, Exception {
		
		//readWeights(weightData);
		//readBias(biasData);
		normalise();
	}
	
	//Override this method to stop back propagation
	protected void outputError() {
		
		//Calculates the error of the Output Node
		error[error.length - 1] = output * (output - data.get(getTrackRow(),data.getWindowX()-1)) * (1 - output);
		//Stores that error for evaluation -- choose one!
		storeForEvaluation((data.get(getTrackRow(), data.getWindowX()-1)) - output);
		reset();
	}
	
	
	protected void reset() {
		output = 0;
		emptyHiddenLayer();
		iterations++;
		learningRate = 1/iterations;
		trackWeights = 0;
		trackRow++;
	}

}
