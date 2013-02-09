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
	private boolean first;
	private static String [] nextRecord;
	
	
	
	Window(int dataX, int dataY, String datasetfile,int column, int windowX, int windowY) throws IOException{
		

		this.dataX = dataX;
		this.dataY = dataY;
		this.windowX = windowX;
		this.windowY = windowY;
		data = new String [dataY][dataX];
		
		
		this.column = column;
		first = true;

		this.datasetfile = datasetfile;
		nextRecord = new String[dataX];
		temp = new int [dataY];
		window = new int [windowY][windowX];
		readData();
		createWindow();
	
	}
	
	public void readData() throws IOException{
		CSVReader reader = new CSVReader(new FileReader(datasetfile));
		int m =0;
		while ((nextRecord = (reader.readNext())) != null) {
		
		for(int k=0;k<dataX;k++){
			data[m][k]=nextRecord[k];
		}
			m++; 
		}
	}
	
	
	public void createWindow(){
		
		//put the column of the dataset into an array to assist creating the window
		int track = 0;
		for (int i = 1; i < dataY-1; i ++) {
			temp[track] = Integer.parseInt(data[i][column]);
			//System.out.print(temp[track]);
			//System.out.print(", ");	
			track++;
			}

		
		for(int j = 0; j < temp.length; j++) {

			int x=0;
			int y=0;
			if (first){ //on the first iteration enter the data straight across the matrix
				this.window[0][x] = temp[0];
				first = false;
				x++;
				} else { //on the following iterations populate the matrix leaving the first input
					this.window[y][x] = temp[j];
					x++;
				}
			if (x == windowX) { // when at the end of the window return to the 2nd space
					x = 1;
					y++;
					this.window[y][0] = temp[j]; //make the first input the last from the previous row
					}
				}
		
	}
	
	 public void print(){	
		
		for(int j = 0 ; j < windowY; j++){
			for (int i = 0; i < windowX; i++){
				System.out.print(this.window[j][i]);
				System.out.print(", ");
			}
			System.out.println();
		}
	}
	 
	 public static void main(String [] args) throws IOException{
		Window test = new Window(7,845,"Year3_Project/Data/Request_analysis.csv",3,5,215);
		test.print();
		
		

		
		}
}


