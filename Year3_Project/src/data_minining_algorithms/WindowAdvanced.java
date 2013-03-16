package data_minining_algorithms;


import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class WindowAdvanced extends WindowBasic {

	
	
	public WindowAdvanced(int dataX, int dataY, String datasetfile, int column,int windowX, int windowY) throws IOException {
		super(dataX, dataY, datasetfile, column, windowX, windowY);
		
	}
	
	public double [][] createWindow(){
		
		//From the matrix put the selected column into a 2d array
		int track = 0;
		for (int i = 1; i < dataY - 1; i ++) {
			temp[track] = Double.parseDouble(((data[i][column])));
			//System.out.print(temp[track]);
			//System.out.print(", ");	
			track++;
		}
		
		//Create the window from the 2D array above.
		int x=0;
		int y=0;
		int j=0;
		

		for(j = 0; j < temp.length-1; j++) {
		
			if((y < windowY) && (x < windowX)) {
			
			window[y][x] = temp[j];
			x++;
				if (x == windowX) {
					y++;
					x=0;
					j = j - (windowX-1);
				}
			}
		}
		return window;
	}
}


