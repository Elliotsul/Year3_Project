package data_minining_algorithms;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class Analysis {

	static String [] meanError = new String [2];
	static double [] meanAvgs = new double[2];
	
	public static void main(String[] args) throws Exception {
		
		String weightValues = new String ("Year3_Project/Data/weightValue.csv");
		String biasValues = new String ("Year3_Project/Data/biasValue.csv");
		String evalAvgs = new String ("Year3_Project/Data/meanErrors.csv");
		
		readEvaluations(evalAvgs);
		
		WindowAdvanced win = new WindowAdvanced(6,41,"Year3_Project/Data/Request_analysis_monthly.csv",5,7,33);
		
		NeuralNetwork nn = new NeuralNetwork(6,3,win);
		
	
		nn.NeuralNetworkGo();
		nn.epochs = true;
		
		for(int j = 0; j < 300; j++){
		for(int i = 0 ; i < nn.data.getWindowY(); i ++){
		nn.inputSetup();
			}
		}
		
		if ((nn.rmse() < meanAvgs[0]) && (nn.mse() < meanAvgs[1])) {
	
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
		
		CSVReader reader = new CSVReader(new FileReader(evaluations));
		
		while ((meanError = (reader.readNext())) != null) {
		
		for(int k=0;k<meanError.length;k++){
			meanAvgs[k]=Double.parseDouble(meanError[k]);
			}
		}
	}

}
