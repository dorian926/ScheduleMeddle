package PDFtoTXT;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


public class Converter {
	public File convert(String input) {
		File fp = new File(input);
		File output = null;
		BufferedWriter bw = null;
		try {
			output = File.createTempFile("output", ".txt");
			bw = new BufferedWriter(new FileWriter(output));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String s = null;
		
		if(input.contains(".docx")) { //word handling
			try {
				XWPFDocument wordXDoc = new XWPFDocument(new FileInputStream(fp));
				XWPFWordExtractor extractor = new XWPFWordExtractor(wordXDoc); //extractor takes formatted text from doc
				s = extractor.getText(); // add text to s
				wordXDoc.close(); //close document, done with it
				extractor.close();//close extractor, done with it
				String[] periodSplit = s.split("\\.");
				for(String line : periodSplit) {
					line.replace("\n", " ");
					line.replace("\t", " ");
					line += "\n";
					bw.write(line);
				}
				bw.close();
				//Files.write(Paths.get("syllabus.txt"), s.getBytes(), StandardOpenOption.CREATE); //write the stripped text to a new file called syllabus.txt
				return output;
			} catch (IOException e) {
				printException(e);
			}
		} else if(input.contains(".doc")) {
			try {
				HWPFDocument wordDoc = new HWPFDocument(new FileInputStream(fp)); //load word doc from input
				WordExtractor extractor = new WordExtractor(wordDoc); //extractor takes formatted text from doc
				s = extractor.getText(); // add text to s
				wordDoc.close(); //close document, done with it
				extractor.close();//close extractor, done with it
				String[] periodSplit = s.split("\\.");
				for(String line : periodSplit) {
					line.replace("\n", " ");
					line.replace("\t", " ");
					line += "\n";
					bw.write(line);
				}
				bw.close();
				//Files.write(Paths.get("syllabus.txt"), s.getBytes(), StandardOpenOption.CREATE); //write the stripped text to a new file called syllabus.txt
				return output;
			} catch (IOException e) {
				printException(e);
			}
		} else if(input.contains(".pdf")) { //pdf handling
			try {
				PDFTextStripper stripper = new PDFTextStripper(); //create a new stripper from pdfbox api to strip text from pdf
				PDDocument pdfDoc = PDDocument.load(fp); //take the input of the file and have it loaded as PDF Document, pdfbox api
				s = stripper.getText(pdfDoc); //strip the text from the document. One whole line including newline symbols, does '\t'?
				pdfDoc.close(); //close document done with it
				String[] periodSplit = s.split("\\.");
				for(String line : periodSplit) {
					line.replace("\n", " ");
					line.replace("\t", " ");
					line += "\n";
					bw.write(line);
				}
				bw.close();
				//Files.write(Paths.get("syllabus.txt"), s.getBytes(), StandardOpenOption.CREATE); //write the stripped text to a new file called syllabus.txt
				return output;
			} catch (IOException e) {
				printException(e);
			}
		}
		return null;
	}

public void printException(Exception e) {
	System.out.println("Failed to retrieve file");
	System.out.println(e.getMessage());
}
}