package data_minining_algorithms;

import java.util.ArrayList;
import java.io.*;
import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Attribute;
import weka.classifiers.*;
import weka.core.converters.CSVLoader;
import weka.classifiers.trees.J48;
import weka.filters.unsupervised.instance.*;
import weka.filters.unsupervised.attribute.*;


public class CopyOfLibraAnalysis {

	
	
	public static void main(String[] args) throws Exception {
		
		String [] test = new String[2];
		test[0] = "Year3_Project/Data/Test:Unused Datsets/Libra_check_analysis_pruned.csv";
		test[1] = "Year3_Project/Data/Libra_check_analysis_NEW.arff";
		CSV2Arff convert = new CSV2Arff();
		convert.main(test);
	}

}
