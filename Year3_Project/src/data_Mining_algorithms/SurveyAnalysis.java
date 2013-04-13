package data_Mining_algorithms;

import java.util.ArrayList;
import java.io.*;

import weka.associations.Apriori;
import weka.associations.AssociationRule;
import weka.associations.AssociationRules;
import weka.associations.AssociationRulesProducer;
import weka.associations.FilteredAssociationRules;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.Tag;
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


public class SurveyAnalysis {

	
	
	public static void main(String[] args) throws Exception {
		
		
		//String [] fileArray = new String[2];
		//fileArray[0] = "Year3_Project/Data/LibraCheckDataSet.csv";
		//fileArray[1] = "Year3_Project/Data/LibraCheckDataSet.Arff";
		//CSV2Arff convert = new CSV2Arff();
		//convert.main(fileArray);
		
		
		
		String fileName = "Year3_Project/Data/SurveyReplies.Arff";
		String output = "Year3_Project/Data/AprioriResults.txt";
		
		BufferedWriter bf = new BufferedWriter(new FileWriter(output));
		
		ArffLoader loader = new ArffLoader();
		loader.setSource(new File(fileName));
		Instances data = loader.getDataSet();
		
		String [] NumericToNominal = new String[2];
		NumericToNominal[0] = "-R"; //range
		NumericToNominal[1] = "1-17"; //attribute numbers to convert
		
		String [] removedAttributes = new String[2];
		removedAttributes[0] = "-R"; //range
		removedAttributes[1] = "1,6,7"; //attribute numbers to remove
		
		
		String [] confidence = new String[2];
		confidence[0] = "- T";
		confidence[1] = "2";
		
		Remove remove = new Remove(); //remove object
		remove.setOptions(removedAttributes); //set remove object options
		remove.setInputFormat(data);   
		Instances filterData = Filter.useFilter(data, remove);
	
		NumericToNominal numToNom = new NumericToNominal(); //convert object
		numToNom.setOptions(NumericToNominal); // set convert object options
		numToNom.setInputFormat(filterData);         //inform filter about dataset **AFTER** setting options
		filterData = Filter.useFilter(filterData, numToNom); //apply filter
		
		System.out.println(filterData.numAttributes());
		
		Apriori ap = new Apriori();
		ap.setOptions(confidence);
		ap.setLowerBoundMinSupport(0.4);
		ap.setMinMetric(0.9);
		ap.setNumRules(1000000);
		ap.setUpperBoundMinSupport(1.0);
		ap.buildAssociations(filterData);
		System.out.println(ap.toString() + "\n");
		bf.append(ap.toString() + "\n");
	}
}
