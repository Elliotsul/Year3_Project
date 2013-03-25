package data_minining_algorithms;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class WeeklyAnalysis {
	
	/* This class uses a split data set to which to train the a network and then test it against the rest.
	 * There is a 66% and 34% split being used.
	 */

	static String [] meanError = new String [9];
	static double [] meanAvgs = new double[4];
	static int nnInput = 0;
	static int nnHidden = 0;
	static BufferedWriter writer;
	
	public static void main(String[] args) throws Exception {
		
		//file name strings
		String weightValues = new String ("Year3_Project/Data/weightTemp.csv");
		String biasValues = new String ("Year3_Project/Data/biasTemp.csv");
		String bestWeights = new String ("Year3_Project/Data/bestWeights.csv");
		String bestBias = new String ("Year3_Project/Data/bestBias.csv");
		String evalAvgs = new String ("Year3_Project/Data/meanErrorsSplitSet.csv");
		String randomWeights = new String ("Year3_Project/Data/randomWeights.csv");
		String randomBias = new String ("Year3_Project/Data/randomBias.csv");
		double rmse = 50;
		double mse = 50;
		double rmseToStore;
		double mseToStore;
		
		writer = new BufferedWriter(new FileWriter(evalAvgs));
		setupEval();
		int epochs = 0;
		
		//read avgs from file
		readEvaluations(evalAvgs);
		
		WindowAdvanced train = new WindowAdvanced(5,113,"Year3_Project/Data/Request_analysis_weekly_train.csv",4,7,105);
		WindowAdvanced test = new WindowAdvanced(5,59,"Year3_Project/Data/Request_analysis_weekly_test.csv",4,7,51);
	
		NeuralNetwork nn = new NeuralNetwork(6,3,train);
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
		
		
		nnInput = nn2.getInputLength();
		nnHidden = nn2.getHiddenlength();
		
		while(epochs <= 30000) {
			
			nn.readWeights(randomWeights);
			nn.readBias(randomBias);
		
			for(int j = 0; j <= epochs; j++) {
				nn.runEpoch();
			}
		
			nn.storeWeights(weightValues);
			nn.storeBias(biasValues);
			
			//nn2 = new NeuralNetwork(6,4,test,weightValues,biasValues);
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
		
			//nn2.evalPrint();
			//System.out.println();
		
			storeEvaluations(evalAvgs,nn.rmse(),nn.mse(),rmseToStore,mseToStore,nnInput,nnHidden,epochs,nn2.rmse(),nn2.mse());
			epochs = epochs + 500;
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
	
		//Read RMSE and MSE from a file
	public static void readEvaluations(String evaluations) throws Exception, IOException {
	
		String [] temp = new String [4];
		CSVReader reader = new CSVReader(new FileReader(evaluations));
			
		while ((temp = (reader.readNext())) != null) {
			
			for(int k=0;k < 2;k++){
				meanAvgs[k]=Double.parseDouble(temp[k]);
			
			}
			
			nnInput = Integer.parseInt(temp[2]);
			nnHidden = Integer.parseInt(temp[3]);
		}
	}
}
