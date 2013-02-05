package data_minining_algorithms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class dataSetup {
	
	private static String[][] data;
	private static String newRecord;
	private static int x;
	private static int y;
	int numberOfRecords;
	static BufferedReader reader;
	BufferedWriter writer;
	
	
	dataSetup(int x, int y, String z) throws IOException {
		this.data = new String[y][x];
		reader = new BufferedReader(new FileReader(z));
		inputData(z);
	}
	
	public static void inputData(String y) throws IOException{
		
		int j,i=0;
		while ((newRecord = reader.readLine()) != null) { //while the line inputed is not empty do
			for(j=0;j<=x;j++)
			    data[i][j]=newRecord;

			i++;    // this increses i with 1
		}
		
	}

	
}
