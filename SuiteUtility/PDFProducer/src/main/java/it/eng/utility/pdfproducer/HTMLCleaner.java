package it.eng.utility.pdfproducer;

import java.nio.charset.Charset;

public interface HTMLCleaner {

	String convertToString(String htmlContent, Charset charset);

	String convertToString(String htmlContent);

}
