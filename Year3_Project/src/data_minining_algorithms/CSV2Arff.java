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
			      loader.setSource(new File("Year3_Project/Data/Libra_check_analysis_pruned.csv"));
			      Instances data = loader.getDataSet();
			  
			      // save ARFF
			      ArffSaver saver = new ArffSaver();
			      saver.setInstances(data);
			      saver.setFile(new File("Year3_Project/Data/Libra_check_analysis_new"));
			      saver.setDestination(new File("Year3_Project/Data/Libra_check_analysis_new"));
			      saver.writeBatch();
			    }

}
