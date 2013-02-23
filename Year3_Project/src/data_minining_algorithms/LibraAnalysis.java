package data_minining_algorithms;

import java.util.ArrayList;

import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.*;
import weka.classifiers.trees.J48;
import weka.filters.unsupervised.instance.*;


public class LibraAnalysis {

	
	
	public static void main(String[] args) throws Exception {
	
		double percentage = 66;
		Instances data = DataSource.read("Year3_Project/Data/Libra_check_analysis_pruned.csv");
		int trainsize = (int) Math.round(data.numInstances() * percentage / 100);
		int testsize = data.numInstances() - trainsize;
		Instances train = new Instances(data,0,trainsize);
		Instances test = new Instances(data,trainsize,testsize);
	
		if (data.classIndex() == -1)
			data.setClassIndex(0);
		
		//String [] options = new String[1];
		//options[0] = "-P";
		//J48 tree = new J48();
		//tree.setOptions(options);
		//tree.buildClassifier(train);
		
		
		
		
		/*
		ArrayList<String> time = new ArrayList<String>();
		time.add("AM");
		time.add("PM"); 
		
		ArrayList<String> days = new ArrayList<String>();
		time.add("Monday");
		time.add("Tuesday"); 
		time.add("Wednesday"); 
		time.add("Thursday"); 
		time.add("Friday"); 
		time.add("Saturday"); 
		
		ArrayList<String> result = new ArrayList<String>();
		time.add("Adjournment");
		time.add("Bail conditions"); 
		time.add("Non Rec"); 
		time.add("Warrant"); 
		time.add("Result"); 
		
		
		ArrayList<String> area = new ArrayList<String>();
		area.add("H");
		area.add("B"); 
		area.add("L"); 

		
		
		Attribute primaryKey = new Attribute("Primary_Key");
		Attribute caseArea =  new Attribute("Area", area);
		Attribute courtName = new Attribute("Court Name", (ArrayList<String>) null);
		Attribute courtDate = new Attribute("courtDate", "dd-MM-yyyy");
		Attribute dayOfHearing = new Attribute("Day of Hearing", days);
		Attribute datePrinted= new Attribute("Date Printed", "dd-MM-yyyy");
		Attribute dayPrinted = new Attribute("Day Printed", days);
		Attribute dateDif = new Attribute("Date Difference Hearing to Result");
		Attribute resultType = new Attribute("Result Type", result);
		Attribute prevCheckDate = new Attribute("Date of Previous Check", "dd-MM-yyyy");
		Attribute prevCheckTime = new Attribute("Previous Check Time", (ArrayList<String>) null);
		Attribute prevTimeZone = new Attribute("Previous Check AM/PM", time);
		Attribute bestPredDate = new Attribute("Best predicted Date", "dd-MM-yyyy");
		Attribute bestPredtimeZone = new Attribute("Best Predicted check AM/PM", time);
		Attribute dateDif2 = new Attribute("Date Difference Printing to last Check");
		*/
	}

}
