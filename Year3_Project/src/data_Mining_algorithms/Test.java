package data_Mining_algorithms;

import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class Test {
	
	String data;
	String [] a;
	String [][]b;
	CSVReader reader;
	int x;
	int y;
	int z;
	
	
	
	Test(int x, int y, int z,String blah) {
		
		this.data = blah;
		this.x =x;
		this.y =y;
		this.z =z;
		b = new String[x][y];
		a = new String[y];
		
		
	}
	
	public void matrixSetup() throws IOException{
		CSVReader reader = new CSVReader(new FileReader(data));
		int m =0;
		while ((a = reader.readNext()) != null) {
		
		for(int k=0;k<y;k++){
			b[m][k]=a[k];
		}
			m++; 
		}
	}
	
	public static void main(String[] args) throws IOException {
		Test test = new Test(845,7,7,"Year3_Project/Data/Request_analysis.csv");
		test.matrixSetup();
		
	}

}
