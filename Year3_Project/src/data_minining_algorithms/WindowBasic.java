package data_minining_algorithms;

import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class WindowBasic {

	protected static String [][] data; // input dataset
	protected double [][] window; // output
	protected int dataX; //number of columns in dataset
	protected int dataY; // number of records within the dataset
	protected int windowX; // number of columns in window output
	protected int windowY; // number of records within the window
	protected int column; // column number used from dataset
	protected String datasetfile;
	
	//housekeeping variables
	protected double [] temp;
	protected static String [] nextRecord;
	
	public WindowBasic(int dataX, int dataY, String datasetfile,int column, int windowX, int windowY) throws IOException{
		

		this.dataX = dataX;
		this.dataY = dataY;
		this.datasetfile = datasetfile;
		this.column = column;
		this.windowX = windowX;
		this.windowY = windowY;
		
		data = new String [dataY][dataX];
		nextRecord = new String[dataX];
		temp = new double [dataY];
		window = new double [windowY][windowX];
		readData();
		createWindow();
	
	}
	

	public void readData() throws IOException{
		//Read the CSV data into a Matrix
		CSVReader reader = new CSVReader(new FileReader(datasetfile));
		int m =0;
		while ((nextRecord = (reader.readNext())) != null) {
		
		for(int k=0;k<dataX;k++){
			data[m][k]=nextRecord[k];
		}
			m++; 
		}
	}
	
	
	public double [][] createWindow(){
		
		int x = 0;
		int y = 1;
		
		for(int i = 0; i < dataY; i++){
			for(int k = 0; k < dataX; k++) {
		
				window[y][x] = Double.parseDouble(data[y][x]);
				x++;
			}
			y++;
		}
		return window;
	}
	
	 public void print(){	
		
		for(int j = 0 ; j < windowY; j++){
			for (int i = 0; i < windowX; i++){
				System.out.print(window[j][i]);
				System.out.print(", ");
			}
			System.out.println();
		}
	}
	 
	 public double get(int row, int column) {
		 
		 return window[row][column];
	 }
	 
	 public void set(int row , int column, double input) {
		 
		 window[row][column] = input ;
	 }
	 
	 public int getWindowX() {
		 
		 return windowX;
	 }
	 
	 public int getWindowY() {
		 
		 return windowY;
	 }

}
