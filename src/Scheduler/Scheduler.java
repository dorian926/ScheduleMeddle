package Scheduler;

import java.io.File;

import PDFtoTXT.Converter;
import parser.Parser;

public class Scheduler {

	public static void main(String[] args) {
		Converter converter = new Converter();
		Parser parser = new Parser();
		
		File file = converter.convert(args[0]);
		parser.parseFile(file);
	}

}
