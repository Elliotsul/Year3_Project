package data_minining_algorithms;




public class Analysis {

	
	public static void main(String[] args) throws Exception {
		String nnValues = new String ("Year3_Project/Data/nnValue.csv");
		int epoch = 3;
		int rate = 0;
		double [] result = new double[5];
		
		
		
		
		//Window win = new Window(7,845,"Year3_Project/Data/Request_analysis_daily.csv",4,6,842);
		Window win = new Window(6,41,"Year3_Project/Data/Request_analysis_monthly.csv",5,5,38);
		NeuralNetwork nn = new NeuralNetwork(4,3,win);
	
		
		nn.NeuralNetworkGo();
		nn.epochs = true;
		
		//nn.weightPrint();
		
		for(int J = 0; J < 300; J++) {
			for(int i = 0; i < win.getWindowY() - 1; i ++) {
	
				nn.inputSetup();
			}
			
			//System.out.println();
			//nn.evalPrint();
			//nn.errorPrint();
			//nn.weightPrint();
		}
		
		nn.storeWeights(nnValues);
	}
}