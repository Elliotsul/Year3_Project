package data_minining_algorithms;

import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.*;

public class dataSetup {
	
	static String datasetfile = "Year3_Project/Data/Request_analysis.csv";

		public static void main(String[] args) throws IOException {

		CSVReader reader = new CSVReader(new FileReader(datasetfile));
		String [][] data = new String[845][7];
		String [] nextRecord = new String[7];
       
		int m =0;
		while ((nextRecord = reader.readNext()) != null) {
			int k;
			for(k=0;k<7;k++){
				data[m][k]=nextRecord[k];
		}
			m++; 
		}


		for(int j = 0 ; j < m; j++){
			for (int i =0; i < 7; i++){
				System.out.print(data[j][i]);
				System.out.print(", ");
			}
			System.out.println();
		}

	}

}
