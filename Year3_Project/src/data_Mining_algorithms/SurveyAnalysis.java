package data_Mining_algorithms;

import java.util.ArrayList;
import java.io.*;

import weka.associations.Apriori;
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
		
		double trainPercentage = 70 ;
		ArffLoader loader = new ArffLoader();
		loader.setSource(new File(fileName));
		Instances data = loader.getDataSet();
		
		String [] NumericToNominal = new String[2];
		
	
		NumericToNominal[0] = "-R"; //range
		NumericToNominal[1] = "14,15,16,17"; //attribute numbers to remove
		
	


		NumericToNominal numToNom = new NumericToNominal(); //remove object
		numToNom.setOptions(NumericToNominal); //set remove object options
		numToNom.setInputFormat(data);                          // inform filter about dataset **AFTER** setting options
		Instances filterData = Filter.useFilter(data, numToNom);   // apply filter
		
		System.out.println(filterData.numAttributes());
	
		int trainsize = (int) Math.round(filterData.numInstances() * trainPercentage / 100);
		int testsize = filterData.numInstances() - trainsize;
		
		//System.out.println(trainsize);
		//System.out.println(testsize);
		//System.out.println(trainsize+testsize);
		
		Instances train = new Instances(filterData, 0, trainsize);
		Instances test = new Instances(filterData,trainsize, testsize);

		
		Apriori ap = new Apriori();
		ap.setLowerBoundMinSupport(0.8);
		ap.setMinMetric(0.8);
		ap.setNumRules(150);
		ap.setUpperBoundMinSupport(1.0);
		
		ap.buildAssociations(filterData);
		ap.getAssociationRules();
		
		
	}
}
