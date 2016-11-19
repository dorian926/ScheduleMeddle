package PDFtoTXT;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Converter {

	public boolean convert(String input) {
		try {
			PDFTextStripper stripper = new PDFTextStripper(); //create a new stripper from pdfbox api to strip text from pdf
			PDDocument document = PDDocument.load(new File(input)); //take the input of the file and have it loaded as PDF Document, pdfbox api
			System.out.println("Howdy, I picked up the File!"); // nice lil confirmation
			String s = stripper.getText(document); //strip the text from the document. One whole line including newline symbols, does '\t'?
			Files.write(Paths.get("syllabus.txt"), s.getBytes(), StandardOpenOption.CREATE); //write the stripped text to a new file called syllabus.txt
			return true;
		} catch (IOException e) {
			System.out.println("failed to get input"); //lol you failed son
			e.printStackTrace();
		}
		return false;
	}
}
