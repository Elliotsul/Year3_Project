package data_minining_algorithms;
import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

/*

CSV library above is used to handle csv files
You can use this code for dataset reading in all your Data Mining Java programs

This program reads the dataset in CVS format (comma separated values)
and displays it line by line. Each value from dataset has a row and attribute.
For instance row2: attr3=No means that for patient 2 (given by row 2), the attribute 3
(which is SwollenGlands) has value No.

*/

public class example0 {

	private static final String datasetfile="data/Request_analysis.csv";
	                        // put your dataset file name here

	public static void main(String[] args) throws IOException {

		CSVReader reader = new CSVReader(new FileReader(datasetfile));
		String [][] data = new String[2500][8];
		String [] nextLine;
		                    // this prepares the reading a line from your dataset

//	    int i=0;
//		while ((nextLine = reader.readNext()) != null) {
//                            // this reads a new line from your dataset file
//                            // while there are unread lines;
//                            // for each line it does what follows
//
//			System.out.print("row"+ i+ ":");
//			for(int j=0;j<=6;j++) {
//				System.out.print(" attr"+j+"=" + nextLine[j]);
//			}
//				i++;   //this increses i by 1
//				System.out.println();
//		}
		int m =0;
		while ((nextLine = reader.readNext()) != null) { //while the line inputed is not empty do
			int k;
			for(k=0;k<=6;k++)
			    data[m][k]=nextLine[k];

			m++;    // this increses i with 1
		}
		
		
		for(int j = 0 ; j < 850; j++){
			for (int i =0; i < 7; i++){
				System.out.print(data[j][i]);
				System.out.print(", ");
			}
				System.out.println();
		}

	}
}