package data_minining_algorithms;

import java.io.IOException;

public class Analysis2 {

	
	public static void main(String[] args) throws IOException {
		
		String weightValues = new String ("Year3_Project/Data/weightValue.csv");
		String biasValues = new String ("Year3_Project/Data/biasValue.csv");
		
		WindowAdvanced train = new WindowAdvanced(6,27,"Year3_Project/Data/Request_analysis_monthly_train.csv",5,7,26);
		WindowAdvanced test = new WindowAdvanced(6,14,"Year3_Project/Data/Request_analysis_monthly_test.csv",5,7,15);
		
		
	}

}
