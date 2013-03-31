package data_Mining_algorithms;

import java.util.ArrayList;
import java.io.*;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Attribute;
import weka.classifiers.*;
import weka.core.converters.CSVLoader;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.LMT;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.*;
import weka.filters.unsupervised.attribute.*;
import weka.gui.treevisualizer.PlaceNode2; 
import weka.gui.treevisualizer.TreeVisualizer;
import java.awt.BorderLayout;
import javax.swing.JFrame;


public class LibraAnalysis {

	
	
	public static void main(String[] args) throws Exception {
		
		
		//String [] fileArray = new String[2];
		//fileArray[0] = "Year3_Project/Data/LibraCheckDataSet.csv";
		//fileArray[1] = "Year3_Project/Data/LibraCheckDataSet.Arff";
		//CSV2Arff convert = new CSV2Arff();
		//convert.main(fileArray);
		
		String fileName = "Year3_Project/Data/LibraCheckDataSet2.Arff";
		
		double trainPercentage = 70 ;
		ArffLoader loader = new ArffLoader();
		loader.setSource(new File(fileName));
		Instances data = loader.getDataSet();
		
		String [] removedAttributes = new String[2];
		removedAttributes[0] = "-R"; //range
		removedAttributes[1] = "1,3,4,7,8,9"; //attribute numbers to remove


		Remove remove = new Remove(); //remove object
		remove.setOptions(removedAttributes); //set remove object options
		remove.setInputFormat(data);                          // inform filter about dataset **AFTER** setting options
		Instances filterData = Filter.useFilter(data, remove);   // apply filter
		
		System.out.println(filterData.numAttributes());
	
		int trainsize = (int) Math.round(filterData.numInstances() * trainPercentage / 100);
		int testsize = filterData.numInstances() - trainsize;
		
		//System.out.println(trainsize);
		//System.out.println(testsize);
		//System.out.println(trainsize+testsize);
		
		Instances train = new Instances(filterData, 0, trainsize);
		Instances test = new Instances(filterData,trainsize, testsize);
		
		train.setClassIndex(9);
		test.setClassIndex(9);

		J48 tree = new J48();
		tree.setUnpruned(true);
		tree.setMinNumObj(200);
	
	
		
		FilteredClassifier fc = new FilteredClassifier();
		fc.setFilter(remove);
		fc.setClassifier(tree);
		fc.buildClassifier(train);
		
		Evaluation eval = new Evaluation(train);
		eval.evaluateModel(fc,test);
		
		//System.out.println(test.numInstances());
		//System.out.println(train.numInstances());

		System.out.println(eval.toSummaryString("\nResults\n\n", false));
		//.confusionMatrix();
		System.out.println(eval.toMatrixString());
		
		TreeVisualizer tv = new TreeVisualizer(null, fc.graph(), new PlaceNode2());
		
				JFrame jf = new JFrame("Weka Classifier Tree Visualizer: J48"); 
				jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
				jf.setSize(1200, 800);
				jf.getContentPane().setLayout(new BorderLayout());
				jf.getContentPane().add(tv, BorderLayout.CENTER);
				
				jf.setVisible(true);
				
				// adjust tree
				tv.fitToScreen();
	
				
	
		
	/*	for (int i = 0; i < test.numInstances(); i++) {
			   double pred = fc.classifyInstance(test.instance(i));
			   System.out.print("ID: " + test.instance(i).value(0));
			   System.out.print(", actual: " + test.classAttribute().value((int) test.instance(i).classValue()));
			   System.out.println(", predicted: " + test.classAttribute().value((int) pred));
		}*/
	}
}
