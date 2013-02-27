package data_minining_algorithms;


//Content of this class has been sourced from the WEKA Wiki website.

import java.io.*;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;



public class CSV2Arff {
	
	
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
