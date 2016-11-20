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
import java.util.Map;

public class Parser {
	public static void main(String[] arg) {
		// Text File I Deal with
		String fileName = "/Users/king/Desktop/testFile.txt";

		// This will reference one line at a time
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			File file = new File("/Users/king/Desktop/outFile.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			while ((line = bufferedReader.readLine()) != null) {
				// Files.write(file.toPath(),
				// normalizedSentence(line).getBytes(),
				// StandardOpenOption.APPEND);
				bw.append(normalizedSentence(line));
				// System.out.println("kl" + normalizedSentence(line) + "end");

			}
			// bw.write("final lol duplicate");
			// Always close files.
			bufferedReader.close();
			bw.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");

		}
	}

	public static String normalizedSentence(String line) {
		String[] ma = { "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec" };
		Map<String, String> dic = new HashMap<String, String>();

		for (int i = 0; i < ma.length; i++) {
			dic.put(ma[i], Integer.toString(i + 1));
		}

		ArrayList<String> monthsA = new ArrayList<String>(Arrays.asList(ma));

		String[] lookFor = { "exams", "exam", "final", "quiz", "midterm" };

		String[] w = line.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().split("\\s+");
		ArrayList<String> words = new ArrayList<String>(Arrays.asList(w));

		boolean does = false;
		String found = "";

		for (String month : monthsA) {
			if (words.contains(month)) {
				for (String key : lookFor) {
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
						}
						does = true;
						found = key;
					}
				}
			}
		}

		String retValue = "";
		boolean found2 = true;

		if (does) {
			for (int i = 0; i < words.size(); i++) {
				for (String month : monthsA) {
					if (words.get(i).contains(month)) {
						if (isNumeric(words.get(i + 1))) {
							retValue += "(" + found + ", 2016-" + dic.get(month) + "-" + words.get(i + 1)
									+ "T12:00:00-00:00)" + '\n';
							System.out.println(retValue);
						}
					}
				}
			}
		}
		// System.out.println(retValue);
		return retValue;

	}

	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
