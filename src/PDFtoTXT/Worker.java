package PDFtoTXT;

public class Worker {

	public static void main(String[] args) {
		Converter converter = new Converter();
		converter.convert(args[0]);
	}

}
