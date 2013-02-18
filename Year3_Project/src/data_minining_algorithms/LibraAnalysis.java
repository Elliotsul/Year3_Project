package data_minining_algorithms;

import java.util.ArrayList;

import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;


public class LibraAnalysis {

	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
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

		Instances data = DataSource.read("Year3_Project/Data/Request_analysis_daily.csv");
		
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
	}

}
