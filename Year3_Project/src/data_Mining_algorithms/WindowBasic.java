package data_Mining_algorithms;

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
	protected String datasetfile;
	
	//housekeeping variables
	protected double [] temp;
	protected static String [] nextRecord;
	
	public WindowBasic(int dataX, int dataY, String datasetfile,
				int windowX, int windowY) throws IOException{

		this.dataX = dataX; //number of columns in dataset
		this.dataY = dataY; //number of rows in dataset
		this.datasetfile = datasetfile; //filename 
		this.windowX = windowX; //window column size
		this.windowY = windowY; // window row size
		data = new String [dataY][dataX]; // create matrix
		nextRecord = new String[dataX]; // string for the next record
		temp = new double [dataY]; 
		window = new double [windowY][windowX];//window size defined
		readData();
		createWindow();
	
	}
	

	//read data from the CSV into the matrix
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
		
		//transfer from the data matrix and pass into the "window"
		//matrix ignoring the first row as it is column headings
		int x = 0;
		int y = 1;
		
		for(int i = 0; i < dataY - 1; i++){
			for(int k = 0; k < dataX - 1; k++) {
				window[i][k] = Double.parseDouble(data[y][x]);
				x++;
			}
			y++;
			x = 0;
		}
		return window;
	}
	
	// Operation methods
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

	 public static void main(String [] args) throws Exception {
		 
			WindowBasic test = new WindowBasic (3,4,"Year3_Project/Data/Test.csv",3,3);
			test.print();

	 }
}
