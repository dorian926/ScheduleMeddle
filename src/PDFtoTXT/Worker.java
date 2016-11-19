package PDFtoTXT;

public class Worker {

	public static void main(String[] args) {
		Converter converter = new Converter();
		System.out.println("printing args[0]");
		converter.convert(args[0]);
	}

}
