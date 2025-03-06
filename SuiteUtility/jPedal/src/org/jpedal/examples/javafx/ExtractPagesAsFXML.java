package org.jpedal.examples.javafx;

/**
 * A wrapper class for FXML in the interests of simplicity to
 * be able to extract as FXML without having to set the flag.
 *
 */
public class ExtractPagesAsFXML extends ExtractPagesAsJavaFX {
	
	static {
		System.setProperty("org.jpedal.pdf2javafx.outputAsFXML", "true");
	}

	public static void main(String[] args) {
		ExtractPagesAsJavaFX.main(args);
	}
	
	public ExtractPagesAsFXML() {
		super();
	}
	
	public ExtractPagesAsFXML(String[] args) {
		super(args);
	}

}
