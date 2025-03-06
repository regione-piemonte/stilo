package it.eng.utility.pdfproducer;

import java.util.regex.Pattern;

public class PDFProducerUtils {

	// The exception to this rule is that CDATA sections may contain any character, including ones not in the above range.
	private static final Pattern xmlInvalidChars = Pattern.compile("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\x{10000}-\\x{10FFFF}]");

	public static String sanitizeXmlChars(String xml) {
		if (xml == null || ("".equals(xml)))
			return "";
		// ref : http://www.w3.org/TR/REC-xml/#charsets
		return xmlInvalidChars.matcher(xml).replaceAll("x");
	}

}
