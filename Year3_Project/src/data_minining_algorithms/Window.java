package data_minining_algorithms;


import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class Window {

	private static String [][] data; // input dataset
	private int [][] window; // output
	private int dataX; //number of columns in dataset
	private int dataY; // number of records within the dataset
	private int windowX; // number of columns in window output
	private int windowY; // number of records within the window
	private int column; // column number used from dataset
	private String datasetfile;
	
	//housekeeping variables
	private int [] temp;
	private static String [] nextRecord;
	
	
	
	Window(int dataX, int dataY, String datasetfile,int column, int windowX, int windowY) throws IOException{
		

		this.dataX = dataX;
		this.dataY = dataY;
		this.datasetfile = datasetfile;
		this.column = column;
		this.windowX = windowX;
		this.windowY = windowY;
		
		data = new String [dataY][dataX];
		nextRecord = new String[dataX];
		temp = new int [dataY];
		window = new int [windowY][windowX];
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
	
	
	public int [][] createWindow(){
		
		//From the matrix select put the selected column into a 2d array
		int track = 0;
		for (int i = 1; i < dataY-1; i ++) {
			temp[track] = Integer.parseInt(data[i][column]);
			//System.out.print(temp[track]);
			//System.out.print(", ");	
			track++;
		}
		
		
		//Create the window from the 2D array above.
		int x=0;
		int y=0;
		for(int j = 0; j < temp.length; j++) {
		
			window[y][x] = temp[j];
			x++;
				if (x == 5) {
					y++;
					x=0;
					j-=4;
				}
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
	 
	 public static void main(String [] args) throws IOException{
		Window test = new Window(7,845,"Year3_Project/Data/Request_analysis.csv",3,5,842);
		test.print();
		
		}
}


