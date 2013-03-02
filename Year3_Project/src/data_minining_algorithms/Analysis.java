package data_minining_algorithms;

import java.io.IOException;

public class Analysis {

	
	public static void main(String[] args) throws Exception {
		int epoch = 3;
		int rate = 0;
		double [] result = new double[5];
		
		
		
		//Window win = new Window(7,845,"Year3_Project/Data/Request_analysis_daily.csv",4,6,842);
		Window win = new Window(6,41,"Year3_Project/Data/Request_analysis_monthly.csv",5,5,38);
		NeuralNetwork nn = new NeuralNetwork(4,3,win);
	
		
		nn.NeuralNetworkGo();
		
		nn.inputSetup();
		
		//nn.netInputPrint();
		
		
		nn.data.print();
		
		//nn.errorPrint();
		
		//nn.evalPrint();
		//System.out.println(nn.rms());
		
		//nn.errorPrint();
		//System.out.println();
		//nn.evalPrint();
		//System.out.println();
		//System.out.println(nn.rms());
	
	}
}