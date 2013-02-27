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
		
		
		String [] fileName = new String[2];
		fileName[0] = "Year3_Project/Data/Libra_check_analysis_pruned.csv";
		fileName[1] = "Year3_Project/Data/Libra_check_analysis_NEW.arff";
		CSV2Arff convert = new CSV2Arff();
		convert.main(fileName);
		
		
		double percentage = 66;
		ArffLoader loader = new ArffLoader();
		loader.setSource(new File("Year3_Project/Data/Libra_check_analysis_NEW"));
		Instances data = loader.getDataSet();
		//Instances data = DataSource.read("Year3_Project/Data/Libra_check_analysis_pruned.csv");
		int trainsize = (int) Math.round(data.numInstances() * percentage / 100);
		int testsize = data.numInstances() - trainsize;
		
		for(int i = 0; i < data.numAttributes(); i ++)
			System.out.println(data.attributeStats(i));
		
	}
		
}
