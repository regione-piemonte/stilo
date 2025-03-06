package it.eng.utility.pdfproducer;

import java.nio.charset.Charset;

public class HTMLCleanerJTidy implements HTMLCleaner {

	@Override
	public String convertToString(String htmlContent, Charset charset) {
		// final Tidy tidy = new Tidy();
		// tidy.setInputEncoding(charset.name());
		// tidy.setOutputEncoding(charset.name());
		// tidy.setXHTML(true);
		// final ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes(charset.name()));
		// final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// tidy.parseDOM(inputStream, outputStream);
		// return outputStream.toString(charset.name());
		return null;
	}

	@Override
	public String convertToString(String htmlContent) {
		// TODO Auto-generated method stub
		return null;
	}

}
