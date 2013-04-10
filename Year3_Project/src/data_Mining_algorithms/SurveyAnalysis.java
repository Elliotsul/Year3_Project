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
		NumericToNominal[1] = "14-18"; //attribute numbers to convert
		
		String [] removedAttributes = new String[2];
		removedAttributes[0] = "-R"; //range
		removedAttributes[1] = "1"; //attribute numbers to remove
		
		Remove remove = new Remove(); //remove object
		remove.setOptions(removedAttributes); //set remove object options
		remove.setInputFormat(data);   
		Instances filterData = Filter.useFilter(data, remove);
	
		NumericToNominal numToNom = new NumericToNominal(); //convert object
		numToNom.setOptions(NumericToNominal); //set convert object options
		numToNom.setInputFormat(filterData);         //inform filter about dataset **AFTER** setting options
		filterData = Filter.useFilter(filterData, numToNom);   //apply filter
		

		//for(int i = 0; i < filterData.numAttributes(); i++) {
		//	System.out.println(filterData.attributeStats(i));
		//		}
		
		int trainsize = (int) Math.round(filterData.numInstances() * trainPercentage / 100);
		int testsize = filterData.numInstances() - trainsize;
		
		
		Instances train = new Instances(filterData, 0, trainsize);
		Instances test = new Instances(filterData,trainsize, testsize);


		
		Tag[] tags = new Tag[1];
		tags[0] = new Tag(1,"-d");
		
		Apriori ap = new Apriori();
		ap.setLowerBoundMinSupport(0.8);
		ap.setMinMetric(0.8);
		ap.setNumRules(150);
		ap.setUpperBoundMinSupport(1.0);
		ap.setMetricType(new SelectedTag(0,tags));
		System.out.println(ap.getMetricType());
		ap.buildAssociations(filterData);
		ap.getAssociationRules();
		
		
	}
}
