package data_minining_algorithms;

import java.util.ArrayList;
import java.io.*;
import weka.core.*;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Attribute;
import weka.classifiers.*;
import weka.core.converters.CSVLoader;
import weka.classifiers.trees.J48;
import weka.filters.unsupervised.instance.*;
import weka.filters.unsupervised.attribute.*;


public class LibraAnalysis {

	
	
	public static void main(String[] args) throws Exception {
		
		
		String [] fileArray = new String[2];
		fileArray[0] = "Year3_Project/Data/LibraCheckDataSet.csv";
		fileArray[1] = "Year3_Project/Data/LibraCheckDataSet.Arff";
		CSV2Arff convert = new CSV2Arff();
		convert.main(fileArray);
		
		String fileName = "Year3_Project/Data/LibraCheckDataSet.Arff";
		
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
	
		//System.out.println(train.numInstances());
		//System.out.println(test.numInstances());
		//System.out.println(test.numInstances() + train.numInstances());
		
		
		//for(int i = 0; i < train.numAttributes(); i ++)
		//	System.out.println(train.attributeStats(i));
		
		
		
	}
		
	

	
}
