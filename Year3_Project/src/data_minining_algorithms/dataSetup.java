package data_minining_algorithms;

import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.*;

public class dataSetup {
	
	static String datasetfile = "Year3_Project/Data/Request_analysis.csv";
	static CSVReader reader;
	static String [][] data = new String[845][7];
	static String [][] london = new String[845][4];
	static String [][] county = new String[845][4];
	static String [][] crown = new String[845][4];
	static String [] nextRecord = new String[7];

		public static void main(String[] args) throws IOException {
			reader = new CSVReader(new FileReader(datasetfile));
			matrixSetup();
			printDataset();
			
		}

      

		
	static void printDataset(){	
		for(int j = 0 ; j < 844; j++){
			for (int i =0; i < 7; i++){
				System.out.print(data[j][i]);
				System.out.print(", ");
			}
			System.out.println();
		}
	}
	
	public static void matrixSetup() throws IOException{
		int m =0;
		while ((nextRecord = reader.readNext()) != null) {
		int k;
		for(k=0;k<7;k++){
			data[m][k]=nextRecord[k];
		}
			m++; 
		}
	}
	
	public static void londonSetup() {
		
		for (int i = 0; i < 845; i++){
				data[i][3] = london[0][0];
				
			}
		}
	}	



