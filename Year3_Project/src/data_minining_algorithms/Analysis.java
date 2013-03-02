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
		nn.epochs = true;
		nn.weightPrint();
		for(int J = 0; J < 300; J++) {
			for(int i = 0; i < win.getWindowY() - 1; i ++) {
	
				nn.inputSetup();
			}
			System.out.println(J);
			//nn.evalPrint();
			nn.errorPrint();
			//nn.weightPrint();
		}
	
		
	
		
		//System.out.println(nn.result[0]);
		
		
		//	for(int i = 0 ; i < nn.result.length; i++) {
				//nn.result[i] = nn.invertMinMax(nn.result[i],nn.findMin(nn.data.getWindowX() - 1),nn.findMax(nn.data.getWindowX() - 1));
		//		System.out.println(nn.result[0]);
		//	}
		
	
		
		//System.out.println(nn.rms());
		
		//nn.evalPrint();
		
		//System.out.println(nn.result[0]);
		//System.out.println(nn.eval[0]);
		
		//nn.netInputPrint();
		
		
		//nn.data.print();
		
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