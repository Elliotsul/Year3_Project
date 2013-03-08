package data_minining_algorithms;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class Analysis2 {

	static String [] meanError = new String [2];
	static double [] meanAvgs = new double[2];
	
	public static void main(String[] args) throws Exception {
		
		//file name strings
		String weightValues = new String ("Year3_Project/Data/weightValue.csv");
		String biasValues = new String ("Year3_Project/Data/biasValue.csv");
		String evalAvgs = new String ("Year3_Project/Data/meanErrors.csv");
		
		//read avgs from file
		readEvaluations(evalAvgs);
		
		
		WindowAdvanced train = new WindowAdvanced(6,27,"Year3_Project/Data/Request_analysis_monthly_train.csv",5,7,26);
		WindowAdvanced test = new WindowAdvanced(6,14,"Year3_Project/Data/Request_analysis_monthly_test.csv",5,7,15);
		
		NeuralNetwork nn = new NeuralNetwork(6,3,train);
		NeuralNetwork nn2 = new NeuralNetwork(nn.getInputLength(),nn.getHiddenlength(),test,weightValues,biasValues);
		
		
		
		if ((nn2.rmse() < meanAvgs[0]) && (nn2.mse() < meanAvgs[1])) {
			
			nn.storeWeights(weightValues);
			nn.storeBias(biasValues);
			storeEvaluations(evalAvgs,nn.rmse(),nn.mse());
			
		}
	}
	

	//Store RMSE and MSE to a file
	public static void storeEvaluations(String filename,double rmse, double mse) throws IOException {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		
		
		meanError[0] = String.valueOf(rmse);
		meanError[1] = String.valueOf(mse);
		
		for (int m = 0; m < meanError.length; m++){
		    writer.write(meanError[m]);
		    writer.write(',');
		    //System.out.println(temp[m]);
		    }
		    writer.close();
	}
	
	
	//Read RMSE and MSE from a file
	public static void readEvaluations(String evaluations) throws Exception, IOException {
		
		
		String [] temp = new String [2];
		CSVReader reader = new CSVReader(new FileReader(evaluations));
		
		while ((temp = (reader.readNext())) != null) {
		
		for(int k=0;k < meanAvgs.length;k++){
			meanAvgs[k]=Double.parseDouble(temp[k]);
		
			}
		}
	}

}
