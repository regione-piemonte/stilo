package it.eng.utility.pdfproducer;

import java.nio.charset.Charset;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.Serializer;
import org.htmlcleaner.TagNode;

public class HTMLCleanerHtmlCleaner implements HTMLCleaner {

	private HtmlCleaner htmlCleaner;
	private Serializer serializer;

	public HTMLCleanerHtmlCleaner(HtmlCleaner htmlCleaner, Serializer serializer) {
		this.htmlCleaner = htmlCleaner;
		this.serializer = serializer;
	}

	public HTMLCleanerHtmlCleaner() {
		this(null, null);
	}

	public void setHtmlCleaner(HtmlCleaner htmlCleaner) {
		this.htmlCleaner = htmlCleaner;
	}

	public void setSerializer(Serializer serializer) {
		this.serializer = serializer;
	}

	@Override
	public String convertToString(String htmlContent, Charset charset) {
		final TagNode rootTagNode = htmlCleaner.clean(htmlContent);
		final String str = serializer.getAsString(rootTagNode, charset.name(), false);
		return str;
	}

	@Override
	public String convertToString(String htmlContent) {
		final TagNode rootTagNode = htmlCleaner.clean(htmlContent);
		final String str = serializer.getAsString(rootTagNode, false);
		return str;
	}

}
