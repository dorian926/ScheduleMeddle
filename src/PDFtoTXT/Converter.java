package PDFtoTXT;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;


public class Converter {
	public boolean convert(String input) {
		File fp = new File(input);
		String s = null;
		
		if(input.contains(".doc")) { //word handling
			try {
				HWPFDocument wordDoc = new HWPFDocument(new FileInputStream(fp)); //load word doc from input
				WordExtractor extractor = new WordExtractor(wordDoc); //extractor takes formatted text from doc
				s = extractor.getText(); // add text to s
				wordDoc.close(); //close document, done with it
				extractor.close();//close extractor, done with it
				Files.write(Paths.get("syllabus.txt"), s.getBytes(), StandardOpenOption.CREATE); //write the stripped text to a new file called syllabus.txt
				return true;
			} catch (IOException e) {
				printException(e);
			}
		} else if(input.contains(".pdf")) { //pdf handling
			try {
				PDFTextStripper stripper = new PDFTextStripper(); //create a new stripper from pdfbox api to strip text from pdf
				PDDocument pdfDoc = PDDocument.load(fp); //take the input of the file and have it loaded as PDF Document, pdfbox api
				s = stripper.getText(pdfDoc); //strip the text from the document. One whole line including newline symbols, does '\t'?
				pdfDoc.close(); //close document done with it
				Files.write(Paths.get("syllabus.txt"), s.getBytes(), StandardOpenOption.CREATE); //write the stripped text to a new file called syllabus.txt
				return true;
			} catch (IOException e) {
				printException(e);
			}
		}
		return false;
	}

public void printException(Exception e) {
	System.out.println("Failed to retrieve file");
	System.out.println(e.getMessage());
}
}