package data_Mining_algorithms;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import au.com.bytecode.opencsv.CSVReader;

public class Analysis {

	//file name strings
	String weightValues;
	String biasValues;
	String bestWeights;
	String bestBias;
	String evalAvgs;
	String randomWeights;
	String randomBias;
	double rmse = 50;
	double mse = 50;
	double rmseToStore;
	double mseToStore;
	int epochs;
	int epochMax;
	int epochInc;

	static Writer writer;

	WindowBasic train;
	WindowBasic test;

	NeuralNetwork nn;
	NeuralNetworkTest nn2;

	Analysis (int epochs, int epochMax, int epochInc) {

		this.weightValues = new String ("Year3_Project/Data/weightTemp.csv");
		this.biasValues = new String ("Year3_Project/Data/biasTemp.csv");
		this.bestWeights = new String ("Year3_Project/Data/bestWeights.csv");
		this.bestBias = new String ("Year3_Project/Data/bestBias.csv");
		this.evalAvgs = new String ("Year3_Project/Data/meanErrorsSplitSet.csv");
		this.randomWeights = new String ("Year3_Project/Data/randomWeights.csv");
		this.randomBias = new String ("Year3_Project/Data/randomBias.csv");
		this.rmse = 50;
		this.mse = 50;
		this.rmseToStore = 0;
		this.mseToStore = 0;
		this.epochs = epochs;
		this.epochMax = epochMax;
		this.epochInc = epochInc;
		
		try {
			writer = new BufferedWriter(new FileWriter(evalAvgs));
			train = new WindowBasic(6,43,"Year3_Project/Data/Weekly_Requests_Week&Year_Train.csv",5,42);
			test = new WindowBasic(6,23,"Year3_Project/Data/Weekly_Requests_Week&YearTest.csv",5,22);
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		nn = new NeuralNetwork(4,3,train);
		nn2 = new NeuralNetworkTest(nn.getInputLength(),nn.getHiddenlength(),test,weightValues,biasValues);

	
	}
	
	public void runAnalysis() throws IOException, Exception {
		
		nn.NeuralNetworkGo();
	
		
		nn.generateRandomSetup();
		nn.storeBias(randomBias);
		nn.storeWeights(randomWeights);

		//Setup test network
		nn2.NeuralNetworkGo();
		
		nn.epochs = true;
		nn2.epochs = true;

		
		while(epochs <= epochMax) {
			
			nn.readWeights(randomWeights);
			nn.readBias(randomBias);
		
			for(int j = 0; j <= epochs; j++) {
				nn.runEpoch();
			}
		
			nn.storeWeights(weightValues);
			nn.storeBias(biasValues);
			
			nn2.readWeights(weightValues);
			nn2.readBias(biasValues);
				
			nn2.runEpoch();
			
			if((nn2.rmse() < rmse) && (nn2.mse() < mse)) {
				
				nn2.storeBias(bestBias);
				nn2.storeWeights(bestWeights);
			}
			
			rmseToStore = nn2.rmse();
			mseToStore = nn2.mse();
			
			nn2.reverseNormalisation();
		
			storeEvaluations(evalAvgs,nn.rmse(),nn.mse(),rmseToStore,mseToStore,nn.getInputLength(),nn.getHiddenlength(),epochs,nn2.rmse(),nn2.mse());
			epochs = epochs + epochInc;
			nn.emptyEval();
			nn2.emptyEval();	
		 }
		
		writer.close();
		System.out.println("Finished");

	}

	public static void storeEvaluations(String filename,double rmse, double mse,double rmseTest,
			double mseTest, int nnInput,int nnHidden,int epochs,double normTestRmse,
			double normTestMse) throws IOException {

		String [] meanError = new String [9];

		meanError[0] = String.valueOf(rmse);
		meanError[1] = String.valueOf(mse);
		meanError[2] = String.valueOf(rmseTest);
		meanError[3] = String.valueOf(mseTest);
		meanError[4] = String.valueOf(nnInput);
		meanError[5] = String.valueOf(nnHidden);
		meanError[6] = String.valueOf(epochs);
		meanError[7] = String.valueOf(normTestRmse);
		meanError[8] = String.valueOf(normTestMse);


		for (int m = 0; m < meanError.length; m++){
			writer.write(meanError[m]);
			writer.write(',');
			//System.out.println(temp[m]);
		}
		writer.write("\n");
	}

	public static void setupEval() throws IOException {
		writer.write("Training RMSE");
		writer.write(',');
		writer.write("Training MSE");
		writer.write(',');
		writer.write("Test RMSE");
		writer.write(',');
		writer.write("Test MSE");
		writer.write(',');
		writer.write("Number of Inputs Node");
		writer.write(',');
		writer.write("Number of Hidden Node");
		writer.write(',');
		writer.write("Number of Epochs");
		writer.write(',');
		writer.write("Norm Test RMSE");
		writer.write(',');
		writer.write("Norm Test MSE");
		writer.write("\n");
	}


}
