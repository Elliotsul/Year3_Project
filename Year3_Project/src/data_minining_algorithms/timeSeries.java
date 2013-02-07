package data_minining_algorithms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import au.com.bytecode.opencsv.CSVReader;

public class timeSeries {
	
	private static String[][] data;
	public int numberOfRecords;
	private static String [] nextRecord;
	private static int x;
	private static int y;
	
	static CSVReader reader;
	
	
	
	
	
	public static void inputData(String y) throws IOException{

		int m =0;
		while ((nextRecord = reader.readNext()) != null) { //while the line inputed is not empty do
			int k;
			for(k=0;k<=6;k++)
			    data[m][k]=nextRecord[k];

			m++;    
		}
		
	}
	
	public static void setupData(int x, int y, String z) throws IOException {
		data = new String[y][x];
		reader = new CSVReader(new FileReader(z));
		inputData(z);
	}
	
	public static void print() {
	
		for(int j =0 ; j < y; j++){
			for (int i =0; i < x; i++){
				System.out.print(data[j][i]);
			}
			System.out.println();
		}
	}
	
	
	public static void main(String [] args) throws IOException {
		
		setupData(8,900,"Year3_Project/data/Request_analysis.csv");
		print();
		
	}

	
	
}
