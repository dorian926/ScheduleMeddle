package PDFtoTXT;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

public class Converter {
	File output;
	public Converter() {
		output = new File("Syllabus.txt");
	}
	
	public File convert(String input) {
		System.out.println(input);
		PDDocument document = null;
		try {
			document = PDDocument.load(new File(input));
			System.out.println("Howdy, I picked up the File!");
		} catch (IOException e) {
			System.out.println("failed to get input");
			e.printStackTrace();
		}
		PDFRenderer pdfRenderer = new PDFRenderer(document);
//		PDFTextStripper stripper = new PDFTextStripper();
		
		return null;
	}
}
