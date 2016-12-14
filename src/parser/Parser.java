package parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Parser {
	public void parseFile(File input) {
		// This will reference one line at a time
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(input);

			HashSet<String> track = new HashSet<String>();

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			File file = new File("outFile.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			while ((line = bufferedReader.readLine()) != null) {
				track.add(normalizedSentence(line));
				// bw.append(normalizedSentence(line));
			}
			for (String element : track) {
				bw.write(element);
			}
			// Always close files.
			bufferedReader.close();
			bw.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + input + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + input + "'");

		}
	}

	public String normalizedSentence(String line) {
		String [][] mn = new CSVReader("externallib/dates.csv").collectDates();
		String[] mna = {"january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november","december"};
		String[] ma = { "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec" };
		Map<String, String> dic = new HashMap<String, String>();

		for (int i = 0; i < ma.length; i++) {
			if (i < 9) {
				dic.put(ma[i], "0" + Integer.toString(i + 1));
			} else {
				dic.put(ma[i], Integer.toString(i + 1));
			}
		}

		// System.

		ArrayList<String> monthsA = new ArrayList<String>(Arrays.asList(ma));
		ArrayList<String> monthsNA = new ArrayList<String>(Arrays.asList(mna));

		String[] lookFor = { "exams", "exam", "final", "quiz", "midterm" };

		String[] w = line.replaceAll("[^a-zA-Z0-9 ]", " ").toLowerCase().split("\\s+");
		for(int i = 0; i < monthsNA.size(); i++) {
			for(int j = 0; j < w.length; j++) {
				if(w[j].equals(monthsNA.get(i))) {
					w[j] = monthsA.get(i);
				}
			}
		}
		ArrayList<String> words = new ArrayList<String>(Arrays.asList(w));

		
		boolean does = false;
		String found = "";
		for(int i = 0; i < mn.length;i++){
			for(int j = 0; j < mn[i].length; j++) {
				if(words.contains(mn[i][j])){
					for(String key : lookFor) {
						if (words.contains(key)) {
							if (words.indexOf(key) != words.size() - 1) {
								if (words.get(words.indexOf(key) + 1).equals("paper")) {
									does = true;
									found = key + " paper";
									break;
								}
								if (words.get(words.indexOf(key) + 1).equals("review")) {
									does = true;
									found = key + " review";
									break;
								}
								if (isNumeric(words.get(words.indexOf(key) + 1))) {
									does = true;
									found = key + words.get(words.indexOf(key) + 1);
									break;
								}
							}
							does = true;
							found = key;
						}
					}
				}
			}
		}

		String retValue = "";
		boolean valueAcceptedThisLine = false;
		if (does) {
			for (int i = 0; i < words.size(); i++) {
				for (int j = 0; j < mn.length; j++) {
					String availFormats = "";
					for(int x = 0; x < mn[j].length; x++) {
						availFormats += mn[j][x];
					}
					if (availFormats.contains(words.get(i))) {
						String month = "";
						if(j < 10) {
							month = "0" + (j+1);
						} else {month = "" + (j+1);}
						if(!valueAcceptedThisLine){
							if(i != words.size()-1) {
								if (isNumeric(words.get(i + 1))) {
									String change = words.get(i + 1);
									if (Integer.parseInt(change) < 10) {
										change = "0" + change;
									}
									retValue += "(" + found + ", 2016-" + month + "-" + change + "T12:00:00-00:00)"
											+ '\n';
									valueAcceptedThisLine = true;
								} else if(i-1 != -1) {
									if(isNumeric(words.get(i - 1))) {
										String change = words.get(i - 1);
										if (Integer.parseInt(change) < 10) {
											change = "0" + change;
										}
										retValue += "(" + found + ", 2016-" + month + "-" + change + "T12:00:00-00:00)"
												+ '\n';
										valueAcceptedThisLine = true;
									}
								}
							}
						}
					}
				}
			}
		}
		return retValue;

	}

	public boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
