package data_minining_algorithms;




public class Analysis {

	
	public static void main(String[] args) throws Exception {
		String weightValues = new String ("Year3_Project/Data/weightValue.csv");
		String biasValues = new String ("Year3_Project/Data/biasValue.csv");
		int epoch = 3;
		int rate = 0;
		double [] result = new double[5];
		
		
		
		
		//Window win = new Window(7,845,"Year3_Project/Data/Request_analysis_daily.csv",4,6,842);
		Window win = new Window(6,41,"Year3_Project/Data/Request_analysis_monthly.csv",5,5,38);
		NeuralNetwork nn = new NeuralNetwork(4,3,win);
	
		nn.data.print();
		//nn.NeuralNetworkGo();
		//System.out.println(nn.Min);
		//System.out.println(nn.Max);
		
		nn.epochs = true;
		//nn.weightPrint();
		
		for(int j = 0; j < 500; j++){
		for(int i = 0 ; i < nn.data.getWindowY(); i ++){
		nn.inputSetup();
		
			}
		}
		
		//nn.evalPrint();
		//nn.reverseNormalisation();
		//nn.resultPrint();
		//nn.reverseNormalisation();
		//System.out.println();
		//System.out.println();
		//nn.resultPrint();
		
		System.out.println(nn.rmse());
		
		
		
		//for(int J = 0; J < 300; J++) {
			//for(int i = 0; i < win.getWindowY() - 1; i ++) {
	
				//nn.inputSetup();
				//nn.evalPrint();
				//for(int i = 0; i < nn.eval.length; i++) {
					
				
					//Look at RMS Error;
				//}
				//nn.weightPrint();
				
			//}
			
			
			//nn.evalPrint();
			//nn.errorPrint();
			//nn.weightPrint();
		//}
		
	//	nn.storeWeights(weightValues);
	//	nn.storeBias(biasValues);
	//	NeuralNetwork nn2 = new NeuralNetwork(4,3,win,weightValues,biasValues);
		
	//	System.out.println();
	//	nn2.NeuralNetworkGo();
	//	nn2.weightPrint();
		
	}
}