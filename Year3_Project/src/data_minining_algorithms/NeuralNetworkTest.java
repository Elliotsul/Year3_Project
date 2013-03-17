package data_minining_algorithms;

import java.io.IOException;

public class NeuralNetworkTest extends NeuralNetwork {

	public String weightData;
	public String biasData;


	NeuralNetworkTest(int inputs,int hidden, WindowAdvanced data,String weightData, String biasData) {
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
		this.result = new double[data.getWindowY()-1];
		this.epochs = false;
		this.weightData = weightData;
		this.biasData = biasData;
		this.Min = findMin(data.getWindowX()-1);
		this.Max = findMax(data.getWindowX()-1);
	}
	
	public void NeuralNetworkGo() throws IOException, Exception {
		
		readWeights(weightData);
		readBias(biasData);
		normalise();
	}
	
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
