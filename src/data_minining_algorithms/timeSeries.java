package data_minining_algorithms;


import java.io.FileReader;
import java.io.IOException;
import au.com.bytecode.opencsv.*;

public class timeSeries {
	
	public int numberOfRecords;
	static String [] nextRecord;
	static String [][] data = new String[2500][8];
	final static String dataset = "Year3_Project/Data/Request_analysis.csv";
	
	static CSVReader reader;
	
	public static void main(String [] args) throws IOException {
		
		//setupData();
		//print();
		
		CSVReader reader = new CSVReader(new FileReader(dataset));
		String [][] data = new String[2500][8];
		String [] nextLine;

		int m =0;
		while ((nextLine = reader.readNext()) != null) { //while the line inputed is not empty do
			int k;
			for(k = 0 ;k <= 6; k++)
			    data[m][k]=nextLine[k];

			m++;    // this increses i with 1
		}
		
		
		for(int j = 0 ; j < 850; j++){
			for (int i =0; i <= 6; i++){
				System.out.print(data[j][i]);
				System.out.print(", ");
			}
				System.out.println();
		}

	}
	
	
	
	
	

	public static void setupData() throws IOException {
		
		reader = new CSVReader(new FileReader(dataset));
	
		int m = 0;
		int k;
		while ((nextRecord = reader.readNext()) != null) { //while the line inputed is not empty do
			for(k=0;k<=6;k++)
			    data[m][k]=nextRecord[k];
			System.out.println(data[m][k]);
				m++;    // this increses i with 1
		}
			
	}
	
	
	public static void print() {
		for(int j = 0 ; j < 850; j++){
			for (int i =0; i < 8; i++){
				System.out.print(data[j][i]);
				System.out.print(", ");
			}
				System.out.println();
		}
	}
	
	
	

	
	
}
