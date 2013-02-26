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
			        System.out.println("\nUsage: CSV2Arff <input.csv> <output.arff>\n");
			        System.exit(1);
			      }
			      
			      // load CSV
			      CSVLoader loader = new CSVLoader();
			      loader.setSource(new File(args[0]));
			      Instances data = loader.getDataSet();
			  
			      // save ARFF
			      ArffSaver saver = new ArffSaver();
			      saver.setInstances(data);
			      saver.setFile(new File(args[1]));
			      saver.setDestination(new File(args[1]));
			      saver.writeBatch();
			    }

}
