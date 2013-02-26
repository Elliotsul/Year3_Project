package data_minining_algorithms;

import java.util.ArrayList;
import java.io.*;

import au.com.bytecode.opencsv.CSVReader;
import weka.core.*;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Attribute;
import weka.classifiers.*;
import weka.core.converters.CSVLoader;
import weka.classifiers.trees.J48;
import weka.filters.unsupervised.instance.*;
import weka.filters.unsupervised.attribute.*;


public class CopyOfLibraAnalysis {
	
	public String datasetfile;

	
	
	public static void main(String[] args) throws Exception {
		
		if (args.length != 2) {
			System.exit(1);
		}
		
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(args[0]));
		Instances inst = loader.getDataSet();
		
		ArffSaver save = new ArffSaver();
		save.setInstances(inst);
		save.setFile(new File(args[1]));
		save.setDestination(new File(args[1]));
		save.writeBatch();
	
		
	}

}
