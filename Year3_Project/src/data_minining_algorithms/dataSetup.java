package data_minining_algorithms;

import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.*;

//The following code was written to develop the correct code
//that i wished to use in my window class.

public class dataSetup {
	
	static String datasetfile = "Year3_Project/Data/Request_analysis.csv";
	static CSVReader reader;
	static String [][] data = new String[845][7];
	static String [][] london = new String[215][5];
	static String [] temp = new String[845];
	static String [] nextRecord = new String[7];
	static boolean first = true;
	static int x = 0;
	static int y = 0;
	static int width = 4;
	static int height = 845;

	public static void main(String[] args) throws IOException {
			reader = new CSVReader(new FileReader(datasetfile));
			matrixSetup();
			londonSetup();
			printDataset();
			
		}


	static void printDataset(){	
		System.out.println();
		for(int j = 0 ; j < 215; j++){
			for (int i = 0; i < 5; i++){
				System.out.print(london[j][i]);
				System.out.print(", ");
			}
			System.out.println();
		}
	}
	
	public static void matrixSetup() throws IOException{
		int m =0;
		while ((nextRecord = reader.readNext()) != null) {
		
		for(int k=0;k<7;k++){
			data[m][k]=nextRecord[k];
		}
			m++; 
		}
	}
	
	
	public static void londonSetup() {
		
		//extract from dataset and place in temp array
		int track = 0;
		for (int i = 1; i < 845; i ++) {
			temp[track] = data[i][3];
			System.out.print(temp[track]);
			System.out.print(", ");	
			track++;
			}
		
		for(int j = 0; j < temp.length; j++) {
			
			if (first){ //on the first iteration
				london[0][x] = temp[0];
				first = false;
				x++;
				} else {
					london[y][x] = temp[j];
					x++;
				}
			if (x == 5) {
					x = 1;
					y++;
					london[y][0] = temp[j];
					}
				}
			}
		}	
	
		



