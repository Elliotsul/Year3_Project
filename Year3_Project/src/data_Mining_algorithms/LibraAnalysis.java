package data_Mining_algorithms;


import java.io.*;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.classifiers.*;
import weka.classifiers.trees.J48;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.*;
import weka.gui.treevisualizer.PlaceNode2; 
import weka.gui.treevisualizer.TreeVisualizer;
import java.awt.BorderLayout;
import javax.swing.JFrame;


/* Class written to develop decision tree models
 * via the WEKA API
*/

public class LibraAnalysis {


	public static void main(String[] args) throws Exception {
		
		// Code testing
		
		//String [] fileArray = new String[2];
		//fileArray[0] = "Year3_Project/Data/LibraCheckDataSet.csv";
		//fileArray[1] = "Year3_Project/Data/LibraCheckDataSet.Arff";
		//CSV2Arff convert = new CSV2Arff();
		//convert.main(fileArray);
		
		
		//Libra dataset location
		String fileName = "Year3_Project/Data/LibraAnalysisProcessed.Arff";
		
		
		double trainPercentage = 66 ;
		ArffLoader loader = new ArffLoader();//instantiate loader
		loader.setSource(new File(fileName));//set the source
		Instances data = loader.getDataSet();//create a set of instances called data
		
		String [] removedAttributes = new String[2];
		removedAttributes[0] = "-R"; //range
		removedAttributes[1] = "1,4,6,10,11,14,16"; //attribute numbers to remove
		

		Remove remove = new Remove(); //remove object
		remove.setOptions(removedAttributes); //set remove object options
		remove.setInputFormat(data);// inform filter about dataset **AFTER** setting options
		Instances filterData = Filter.useFilter(data, remove);// apply filter
		
		//dataset size math
		int trainsize = (int) Math.round(filterData.numInstances() * trainPercentage / 100);
		int testsize = filterData.numInstances() - trainsize;
		
		//System.out.println(trainsize);
		//System.out.println(testsize);
		//System.out.println(trainsize+testsize);
		
		//Split the data set into two
		Instances train = new Instances(filterData, 0, trainsize);
		Instances test = new Instances(filterData,trainsize, testsize);
		
		//System.out.println(filterData.numInstances());
		//System.out.println(train.numInstances());
		//System.out.println(test.numInstances());
		
		//set class attribute
		train.setClassIndex(8); 
		test.setClassIndex(8);
		
		//for(int i = 0; i < test.numAttributes(); i++) {
		//	System.out.println(filterData.attributeStats(i));
		//}
		
		
		//Set the tree options
		float confidence = 0.4f;
		
		J48 tree = new J48();
		tree.setBinarySplits(false);
		tree.setCollapseTree(true);
		tree.setConfidenceFactor(confidence);
		tree.setDebug(false);
		tree.setMinNumObj(20);
		tree.setNumFolds(8);
		tree.setReducedErrorPruning(false);
		tree.setSaveInstanceData(false);
		tree.setSeed(1);
		tree.setSubtreeRaising(true);
		tree.setUnpruned(false);
		tree.setUseLaplace(false);
		tree.setUseMDLcorrection(true);
		
		//build tree
		tree.buildClassifier(train);
	
		//System.out.println(test.numAttributes());
		
		//evaluate the model
		Evaluation eval = new Evaluation(train);
	
		eval.evaluateModel(tree,test);
		
		//print the results of the evaluation
		System.out.println(eval.toSummaryString("\nResults\n\n", false));
		System.out.println(eval.toMatrixString());

		
		//visualise the tree using swing class
		TreeVisualizer tvis = new TreeVisualizer(null, tree.graph(), new PlaceNode2());
				//create a jframe
				JFrame jf = new JFrame("Tree J48"); //title
				jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
				jf.setSize(1600, 800); // size
				jf.getContentPane().setLayout(new BorderLayout());
				jf.getContentPane().add(tvis, BorderLayout.CENTER); //add tree to centre
				jf.setVisible(true); // set as visible
				tvis.fitToScreen(); // fit to screen
	}
}
