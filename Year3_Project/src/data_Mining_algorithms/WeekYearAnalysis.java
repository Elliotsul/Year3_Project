package data_Mining_algorithms;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class WeekYearAnalysis {


	/*  This class uses a split data set to which to train the a network and then test it against the rest.
	 * There is a 66% and 34% split being used.
	 * The code will test as many epochs as the user wishes, in any increment that is preferred
	 * This class will generate a dataset that will show the RMSE and MSE values for each number of epochs 
	 * run, ensuring that the same starting weight and bias values are used after each test.
	 * 
	 * week and Year workload analysis class
	 */

	static String [] meanError = new String [9];
	static int nnInput = 0;
	static int nnHidden = 0;
	static BufferedWriter writer;

	public static void main(String[] args) throws Exception {
		//file name strings
		String weightValues = new String ("Year3_Project/Data/weightTemp.csv"); // temp weight values
		String biasValues = new String ("Year3_Project/Data/biasTemp.csv"); //temp bias values
		String bestWeights = new String ("Year3_Project/Data/bestWeights.csv"); //record of optimum weights
		String bestBias = new String ("Year3_Project/Data/bestBias.csv"); //record of optimum bias'
		String evalAvgs = new String ("Year3_Project/Data/meanErrorsSplitSet.csv"); // evaluation data set
		String randomWeights = new String ("Year3_Project/Data/randomWeights.csv"); //initial weights values
		String randomBias = new String ("Year3_Project/Data/randomBias.csv"); //initial bias value

		double rmse = 50;
		double mse = 50;
		double rmseToStore;
		double mseToStore;
		int epochs = 0;
		int epochMax = 10000;
		int epochInc = 500;

		writer = new BufferedWriter(new FileWriter(evalAvgs));

		setupEval();

		//create two windows		
		WindowBasic train = new WindowBasic(6,43,"Year3_Project/Data/Weekly_Requests_Week&Year_Train.csv",5,42);
		WindowBasic test = new WindowBasic(6,23,"Year3_Project/Data/Weekly_Requests_Week&YearTest.csv",5,22);

		//create two neural networks. one of each type
		NeuralNetwork nn = new NeuralNetwork(6,5,train);
		NeuralNetworkTest nn2 = new NeuralNetworkTest(nn.getInputLength(),nn.getHiddenlength(),test,weightValues,biasValues);

		//nn.data.print();
		//System.out.println();
		//nn2.data.print();


		//Store the Random values on the first test, to re-use them for future tests
		nn.NeuralNetworkGo();
		nn.generateRandomSetup();
		nn.storeBias(randomBias);
		nn.storeWeights(randomWeights);

		//Setup test network
		nn2.NeuralNetworkGo();

		nn.epochs = true;
		nn2.epochs = true;

		// stopping criteria for testing
		while(epochs <= epochMax) {
			
			//train network read values in
			nn.readWeights(randomWeights);
			nn.readBias(randomBias);
			
			//train for the number of epochs depicted above
			for(int j = 0; j <= epochs; j++) {
				nn.runEpoch();
			}
			
			//store the weight and bias values
			nn.storeWeights(weightValues);
			nn.storeBias(biasValues);
			
			//apply the values to test neural network
			nn2.readWeights(weightValues);
			nn2.readBias(biasValues);

			//run one epoch with model values
			nn2.runEpoch();

			//measure the rmse and mse and check for improvements
			if((nn2.rmse() < rmse) && (nn2.mse() < mse)) {
				
				//store the neural network values if mse and rmse is better
				nn2.storeBias(bestBias);
				nn2.storeWeights(bestWeights);
			}

			rmseToStore = nn2.rmse();
			mseToStore = nn2.mse();

			nn2.reverseNormalisation();
			
			//generate csv of evaluation figures to assist with graphs
			storeEvaluations(evalAvgs,nn.rmse(),nn.mse(),rmseToStore,mseToStore,nn.getInputLength(),nn.getHiddenlength(),epochs,nn2.rmse(),nn2.mse());
			//increase the amount of epochs
			epochs = epochs + epochInc;
			//clear evaluation figures to ensure they are empty
			nn.emptyEval();
			nn2.emptyEval();	
		}

		writer.close();
		System.out.println("Finished");
	}

	//Store RMSE and MSE to a file
	public static void storeEvaluations(String filename,double rmse, double mse,double rmseTest,double mseTest, int nnInput,int nnHidden,int epochs,double normTestRmse,double normTestMse) throws IOException {

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