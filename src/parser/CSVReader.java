package parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
	String csvFile;
	String line;
	BufferedReader br;
	
	public CSVReader(String file) {
		csvFile = file;
		line = "";
		br = null;
		loadCSV(file);
	}
	
	public void loadCSV(String file) {
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found. File: " + file);
		}
	}
	
	public String[][] collectDates() {
		String[][] dates = new String[12][3];
		try {
			int month = 0;
			while((line = br.readLine()) != null) {
				String[] format = line.split(",");
				for(int i = 0; i < 3; i++) {
					dates[month][i]=format[i];
				}
				month++;
			}
		} catch(IOException e) {
			System.out.println("Error reading line:");
			e.printStackTrace();
		}
		return dates;
	}
}
