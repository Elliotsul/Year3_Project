package data_minining_algorithms;

import java.io.IOException;

public interface Evaluations {

	
	public void storeEvaluations(String filename,double rmse, double mse,double rmseTest,
				double mseTest, int nnInput,int nnHidden,int epochs) throws IOException;
	
	public void setupEval() throws IOException;
	
	public void readEvaluations(String evaluations) throws Exception, IOException;
}
