package data_Mining_algorithms;
import java.io.IOException;

/* This class extends WindowBasic and creates a matrix differently
 * 
 */

public class WindowAdvanced extends WindowBasic {

	protected int column;
	
	
	public WindowAdvanced(int dataX, int dataY, String datasetfile, int column,int windowX, int windowY) 
							throws IOException {
		super(dataX, dataY, datasetfile, windowX, windowY);
		this.column = column;
		readData();
		createWindow();
		
	}
	
	//takes a single row from the data set and generates a window
	//first adds the values to a temp array
	//passes values into window in the correct format
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
		
		//iterate over the temp array
		for(j = 0; j < temp.length-1; j++) {
			
			// if y and x are less than thresholds
			if((y < windowY) && (x < windowX)) {
			
			//add values to window at temp j	
			window[y][x] = temp[j];
			x++;
				
				if (x == windowX) { //window column test
					y++; //next row in the window
					x=0; // position of window
					j = j - (windowX-1); //data position
				}
			}
		}
		return window;
	}
}


