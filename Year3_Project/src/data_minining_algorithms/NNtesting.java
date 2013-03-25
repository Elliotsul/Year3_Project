package data_minining_algorithms;




public class NNtesting {

	
	public static void main(String[] args) throws Exception {
		String weightValues = new String ("Year3_Project/Data/weightValue.csv");
		String biasValues = new String ("Year3_Project/Data/biasValue.csv");
		int epoch = 3;
		int rate = 0;
		double [] result = new double[5];
		
		
		//Window win = new Window(7,845,"Year3_Project/Data/Request_analysis_daily.csv",4,6,842);
		WindowAdvanced win = new WindowAdvanced(6,41,"Year3_Project/Data/Request_analysis_monthly.csv",5,7,33);
		WindowAdvanced train = new WindowAdvanced(6,27,"Year3_Project/Data/Request_analysis_monthly_train.csv",5,7,26);
		WindowAdvanced test = new WindowAdvanced(6,14,"Year3_Project/Data/Request_analysis_monthly_test.csv",5,7,15);
		
		NeuralNetwork nn = new NeuralNetwork(6,3,train);
		
		
	
		//nn.data.print();
		nn.NeuralNetworkGo();
		//System.out.println(nn.Min);
		//System.out.println(nn.Max);
		
		nn.epochs = true;
		//nn.weightPrint();
		
		for(int j = 0; j < 300; j++){
		for(int i = 0 ; i < nn.data.getWindowY(); i ++){
		nn.inputSetup();
		
		//nn.netInputPrint();
		//System.out.println();
		//nn.hiddenLayerPrint();
		//System.out.println();
			}
		}
		
		nn.evalPrint();
		//nn.reverseNormalisation();
		System.out.println();
		//nn.resultPrint();
		//nn.reverseNormalisation();
		nn.storeWeights(weightValues);
		nn.storeBias(biasValues);
		//nn.resultPrint();
		System.out.println();
		System.out.println(nn.rmse());

		System.out.println(nn.mse());
		
		
		NeuralNetworkTest nn2 = new NeuralNetworkTest(nn.getInputLength(),nn.getHiddenlength(),test,weightValues,biasValues);
		
		
		System.out.println();

		nn2.NeuralNetworkGo();
		
		for(int j = 0; j < nn2.data.dataY-1; j++){
		nn2.inputSetup();
		}
		
		nn2.reverseNormalisation();
		nn2.evalPrint();
		
		
	}
}