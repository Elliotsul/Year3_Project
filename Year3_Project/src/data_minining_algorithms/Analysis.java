package data_minining_algorithms;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class Analysis {
	
	//This class is used to create models of a Neural Network that will accuratly forcast workload.
	//This class generates models that uses the entire data set to learn and train on.
	//It tests to see if any improvements have been made by model by comparing the RMSE and MSE of the best
	//generated model so far.

	static String [] meanError = new String [4];
	static double [] meanAvgs = new double[4];
	static int nnInput = 0;
	static int nnHidden = 0;
	
	public static void main(String[] args) throws Exception {
		
		//file name strings
		String weightValues = new String ("Year3_Project/Data/weightValue.csv");
		String biasValues = new String ("Year3_Project/Data/biasValue.csv");
		String evalAvgs = new String ("Year3_Project/Data/meanErrors.csv");
		
		
		//read avgs from file
		readEvaluations(evalAvgs);
	
		
		
		//create a window and neural network
		WindowAdvanced win = new WindowAdvanced(6,41,"Year3_Project/Data/Request_analysis_monthly.csv",5,7,33);
		//NeuralNetwork nn = new NeuralNetwork(6,4,win,weightValues,biasValues);
		NeuralNetwork nn = new NeuralNetwork(6,4,win);
		//nn.isTest();

		
		//NeuralNetwork nn = new NeuralNetwork(6,2,win);
		
		win.print();
		
		nnInput = nn.getInputLength();
		nnHidden = nn.getHiddenlength();
			
		nn.NeuralNetworkGo();
		
		if(nn.test == true) {
		
		for(int i = 0; i < nn.data.getWindowY() - 1; i ++) {
			nn.inputSetup();
			}
		
		nn.reverseNormalisation();
		System.out.println();
		nn.resultPrint();
		System.out.println();
		System.out.println(nn.rmse());
		System.out.println(nn.mse());
		
		} else {
		
		if(nn.test == false){
		nn.epochs = true;
		
		for(int j = 0; j < 10; j++){
		for(int i = 0 ; i < nn.data.getWindowY() - 1; i ++){
		nn.inputSetup();
		//nn.resultPrint();
		//System.out.println();
				}	
			}
		}
		

		//nn.reverseNormalisation();
		//nn.resultPrint();
		
		
		System.out.println(nn.rmse());
		System.out.println(nn.mse());
		
		if ((nn.rmse() < meanAvgs[0]) && (nn.mse() < meanAvgs[1]) && (nn.test == false)) {
	
		nn.storeWeights(weightValues);
		nn.storeBias(biasValues);
		storeEvaluations(evalAvgs,nn.rmse(),nn.mse(),nnInput,nnHidden);
		
			}
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
