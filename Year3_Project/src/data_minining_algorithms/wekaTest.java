package data_minining_algorithms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class wekaTest {

	
	public static void main(String[] args) throws IOException {
		
		double percentage = 66;
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File("Year3_Project/Data/Libra_check_analysis_pruned.csv"));
		Instances data = loader.getDataSet();
		//Instances data = DataSource.read("Year3_Project/Data/Libra_check_analysis_pruned.csv");
		int trainsize = (int) Math.round(data.numInstances() * percentage / 100);
		int testsize = data.numInstances() - trainsize;
		
		ArrayList<String> time = new ArrayList<String>();
		time.add("AM");
		time.add("PM"); 
		
		ArrayList<String> days = new ArrayList<String>();
		days.add("Monday");
		days.add("Tuesday"); 
		days.add("Wednesday"); 
		days.add("Thursday"); 
		days.add("Friday"); 
		days.add("Saturday"); 
		
		ArrayList<String> result = new ArrayList<String>();
		result.add("Adjournment");
		result.add("Bailconditions"); 
		result.add("NonRec"); 
		result.add("Warrant"); 
		result.add("Result"); 
		
		
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
		Attribute prevCheckTime = new Attribute("Previous Check Time");
		Attribute prevTimeZone = new Attribute("Previous Check AM/PM", time);
		Attribute bestPredDate = new Attribute("Best predicted Date", "dd-MM-yyyy");
		Attribute bestPredtimeZone = new Attribute("Best Predicted check AM/PM", time);
		Attribute dateDif2 = new Attribute("Date Difference Printing to last Check");
		
		ArrayList <Attribute> attributes = new ArrayList<Attribute>();
		attributes.add(primaryKey);
		attributes.add(caseArea);
		attributes.add(courtName);
		attributes.add(courtDate);
		attributes.add(dayOfHearing);
		attributes.add(datePrinted);
		attributes.add(dayPrinted);
		attributes.add(dateDif);
		attributes.add(resultType);
		attributes.add(prevCheckDate);
		attributes.add(prevCheckTime);
		attributes.add(prevTimeZone);
		attributes.add(bestPredDate);
		attributes.add(bestPredtimeZone);
		attributes.add(dateDif2);
		
		
		Instances dataset = new Instances ("test-dataset",attributes,0);
		Instances train = new Instances(data,0,trainsize);
		Instances test = new Instances(data,trainsize,testsize);
		
		for(int i = 0; i < dataset.numAttributes(); i ++)
			System.out.println(dataset.attributeStats(i));

	}

}
