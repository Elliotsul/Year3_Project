package data_minining_algorithms;

import java.io.IOException;

public class Analysis {

	
	public static void main(String[] args) throws Exception {
		int epoch = 0;
		
		Window win = new Window(5,41,"Year3_Project/Data/Request_analysis_monthly.csv",3,5,40);
		//win.print();
		NeuralNetwork nn = new NeuralNetwork(5,3,win);
		nn.NeuralNetworkGo();
		
		
		if (epoch < 5) {
		for(int i = 0; i < win.getWindowY();i++){
			nn.inputSetup();
			epoch++;
			}
		}
	}
}
