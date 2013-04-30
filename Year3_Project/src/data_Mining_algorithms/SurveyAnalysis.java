package data_Mining_algorithms;


import java.io.*;
import weka.associations.Apriori;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.*;

/* Class written to generate association rules
 * via the Apriori algorithm via the WEKA API
 * 
 */

public class SurveyAnalysis {

	
	
	public static void main(String[] args) throws Exception {
		
		
		//String [] fileArray = new String[2];
		//fileArray[0] = "Year3_Project/Data/LibraCheckDataSet.csv";
		//fileArray[1] = "Year3_Project/Data/LibraCheckDataSet.Arff";
		//CSV2Arff convert = new CSV2Arff();
		//convert.main(fileArray);
		
		
		//file names strings
		String fileName = "Year3_Project/Data/SurveyReplies.Arff";
		String output = "Year3_Project/Data/AprioriResults.txt";
		
		BufferedWriter bf = new BufferedWriter(new FileWriter(output));//a writer
		
		ArffLoader loader = new ArffLoader();//instantiate a loader
		loader.setSource(new File(fileName)); //set the data source
		Instances data = loader.getDataSet();// create a set of instances from data
		
		//Filters - 
		String [] NumericToNominal = new String[2];
		NumericToNominal[0] = "-R"; //range
		NumericToNominal[1] = "1-17"; //attribute numbers to convert
		
		String [] removedAttributes = new String[2];
		removedAttributes[0] = "-R"; //range
		removedAttributes[1] = "1,6,7"; //attribute numbers to remove
		
		//set the measurement for the algorithm to confidence
		String [] confidence = new String[2];
		confidence[0] = "- T";
		confidence[1] = "2";
		
		Remove remove = new Remove(); //remove object
		remove.setOptions(removedAttributes); //set remove object options
		remove.setInputFormat(data);   
		Instances filterData = Filter.useFilter(data, remove);
	
		NumericToNominal numToNom = new NumericToNominal(); //convert object
		numToNom.setOptions(NumericToNominal); // set convert object options
		numToNom.setInputFormat(filterData);         //inform filter about dataset after setting options
		filterData = Filter.useFilter(filterData, numToNom); //apply filter
		
		System.out.println(filterData.numAttributes());
		
		Apriori ap = new Apriori(); //create apriori object
		ap.setOptions(confidence); //set the options
		ap.setLowerBoundMinSupport(0.4); //minimum support threshold
		ap.setMinMetric(0.9); // confidence minimum threshold
		ap.setNumRules(1000000); //number of rules
		ap.setUpperBoundMinSupport(1.0); // maximum support threshold
		ap.buildAssociations(filterData); // build associator 
		System.out.println(ap.toString() + "\n");
		bf.append(ap.toString() + "\n"); //to string the output of the algorithm to a txt file
	}
}
