package data_minining_algorithms;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class Analysis2 {
	
	/* This class uses a split data set to which to train the a network and then test it against the rest.
	 * There is a 66% and 34% split being used.
	 * 
	 */

	static String [] meanError = new String [4];
	static double [] meanAvgs = new double[4];
	static int nnInput = 0;
	static int nnHidden = 0;
	
	public static void main(String[] args) throws Exception {
		
		//file name strings
		String weightValues = new String ("Year3_Project/Data/weightValueSplitSet.csv");
		String biasValues = new String ("Year3_Project/Data/biasValueSplitSet.csv");
		String evalAvgs = new String ("Year3_Project/Data/meanErrorsSplitSet.csv");
		
		//read avgs from file
		readEvaluations(evalAvgs);
		
		
		WindowAdvanced train = new WindowAdvanced(6,27,"Year3_Project/Data/Request_analysis_monthly_train.csv",5,7,19);
		WindowAdvanced test = new WindowAdvanced(6,14,"Year3_Project/Data/Request_analysis_monthly_test.csv",5,7,6);
		
		NeuralNetwork nn = new NeuralNetwork(6,5,train);
		nn.epochs = true;
		//train.print();
		//System.out.println();
		test.print();
		
		nn.NeuralNetworkGo();
		
		
		for(int j = 0; j < 50; j++) {
			
			
			for (int i = 0; i < nn.data.getWindowY()-1; i ++) {
				nn.inputSetup();
			}
		}
		
		nn.storeWeights(weightValues);
		nn.storeBias(biasValues);
		
		NeuralNetwork nn2 = new NeuralNetwork(nn.getInputLength(),nn.getHiddenlength(),test,weightValues,biasValues);
		nnInput = nn2.getInputLength();
		nnHidden = nn2.getHiddenlength();
		nn2.isTest();
		nn2.NeuralNetworkGo();
		
		
			for(int k =0; k < nn2.data.getWindowY()-1; k++) {
				nn2.inputSetup();	
			}
		
		
		System.out.println(nn2.rmse());
		System.out.println(nn2.mse());
		
		nn2.reverseNormalisation();
		nn2.resultPrint();
		
		if ((nn2.rmse() < meanAvgs[0]) && (nn2.mse() < meanAvgs[1])) {
			
			nn.storeWeights(weightValues);
			nn.storeBias(biasValues);
			storeEvaluations(evalAvgs,nn.rmse(),nn.mse(),nnInput,nnHidden);

			}
		}
	
		

		//Store RMSE and MSE to a file
		public static void storeEvaluations(String filename,double rmse, double mse,int nnInput,int nnHidden) throws IOException {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		
		
		meanError[0] = String.valueOf(rmse);
		meanError[1] = String.valueOf(mse);
		meanError[2] = String.valueOf(nnInput);
		meanError[3] = String.valueOf(nnHidden);
		
		for (int m = 0; m < meanError.length; m++){
		    writer.write(meanError[m]);
		    writer.write(',');
		    //System.out.println(temp[m]);
		    }
		    writer.close();
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
