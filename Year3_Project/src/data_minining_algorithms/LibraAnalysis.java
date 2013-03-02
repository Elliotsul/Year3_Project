package data_minining_algorithms;

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
import weka.filters.Filter;
import weka.filters.unsupervised.instance.*;
import weka.filters.unsupervised.attribute.*;


public class LibraAnalysis {

	
	
	public static void main(String[] args) throws Exception {
		
		
		//String [] fileArray = new String[2];
		//fileArray[0] = "Year3_Project/Data/LibraCheckDataSet.csv";
		//fileArray[1] = "Year3_Project/Data/LibraCheckDataSet.Arff";
		//CSV2Arff convert = new CSV2Arff();
		//convert.main(fileArray);
		
		String fileName = "Year3_Project/Data/WEKATEST.Arff";
		
		double percentage = 66;
		ArffLoader loader = new ArffLoader();
		loader.setSource(new File(fileName));
		Instances data = loader.getDataSet();
	
		int trainsize = (int) Math.round(data.numInstances() * percentage / 100);
		int testsize = data.numInstances() - trainsize;
		
		//System.out.println(trainsize);
		//System.out.println(testsize);
		//System.out.println(trainsize+testsize);
		
		Instances train = new Instances(data, 0, trainsize);
		Instances test = new Instances(data,trainsize, testsize);
		
		String [] options = new String[1];
		options[0] = "-R";
		
		StringToNominal filter = new StringToNominal();
		filter.setInputFormat(train);
		
		
		int track = 0;
		
		int [] removedAttributes = new int[6];
		removedAttributes[0] = 1;
		removedAttributes[1] = 4;
		removedAttributes[2] = 6;
		removedAttributes[3] = 10;
		removedAttributes[4] = 12;
		removedAttributes[5] = 14;
	
		
		
		
		Remove remove = new Remove(); 
		remove.setAttributeIndicesArray(removedAttributes);

		train.setClassIndex(9);
		
		
		
		J48 tree = new J48();
		tree.setUnpruned(true);
		
		FilteredClassifier fc = new FilteredClassifier();
		fc.setFilter(remove);
		fc.setClassifier(tree);
		fc.buildClassifier(train);
		
		
		
		
		
		
		
	}
		
	

	
}
