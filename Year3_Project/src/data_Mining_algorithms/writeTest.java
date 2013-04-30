package data_Mining_algorithms;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class writeTest {

	public static void main(String[] args) throws IOException {
		String filename = "Year3_Project/Data/test.csv";
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		String [] temp = new String [3];
		temp[0] = "number";
		temp[1] = "two";
		temp[2] = "three";
	
		    writer.append(temp[0]);
		    writer.append(',');
		    writer.close();
		    System.out.println(temp[0]);
	}
}