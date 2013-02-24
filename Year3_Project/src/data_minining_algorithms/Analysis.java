package data_minining_algorithms;

import java.io.IOException;

public class Analysis {

	
	public static void main(String[] args) throws Exception {
		int epoch = 3;
		int rate = 0;
		Window win = new Window(5,41,"Year3_Project/Data/Request_analysis_monthly.csv",4,5,38);
		//Window win = new Window(7,845,"Year3_Project/Data/Request_analysis_daily.csv",6,5,842);
		//win.print();
		NeuralNetwork nn = new NeuralNetwork(win.getWindowX() - 1,3,win);
		nn.NeuralNetworkGo();
		nn.epochs = true;
		//nn.inputSetup();
	
		for(int j = 0; j < epoch; j++){
		for (int i = 0; i < win.getWindowY();i++) {
			nn.inputSetup();
			rate++;
		}
		//nn.evalPrint();
		nn.rms();
		System.out.println(nn.rms());
		nn.emptyEval();
		System.out.println(rate);
		}
	}
}