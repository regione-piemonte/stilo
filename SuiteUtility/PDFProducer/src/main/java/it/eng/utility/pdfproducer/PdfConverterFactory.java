package it.eng.utility.pdfproducer;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.Serializer;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class PdfConverterFactory {

	@Deprecated
	public static final PdfConverter create(ConverterType type, String baseDir, CleanerProperties props) {
		switch (type) {
		case FLYING_SOUCER_HTML_CLEANER:
			return createFlyingSoucerHtmlCleaner(baseDir, props);
		case FLYING_SOUCER_JTIDY:
			throw new UnsupportedOperationException("Casisitica da implementare: " + type);
		default:
			throw new UnsupportedOperationException("Casisitica da implementare: " + String.valueOf(type));
		}
	}

	public static final PdfConverter createFlyingSoucerHtmlCleaner() {
		return createFlyingSoucerHtmlCleaner(null, null, null);
	}

	public static final PdfConverter createFlyingSoucerHtmlCleaner(ITextRenderer renderer) {
		return createFlyingSoucerHtmlCleaner(null, null, renderer);
	}

	public static final PdfConverter createFlyingSoucerHtmlCleaner(String baseDir) {
		return createFlyingSoucerHtmlCleaner(baseDir, null, null);
	}

	public static final PdfConverter createFlyingSoucerHtmlCleaner(String baseDir, ITextRenderer renderer) {
		return createFlyingSoucerHtmlCleaner(baseDir, null, renderer);
	}

	public static final PdfConverter createFlyingSoucerHtmlCleaner(CleanerProperties props, ITextRenderer renderer) {
		return createFlyingSoucerHtmlCleaner(null, props, renderer);
	}

	public static final PdfConverter createFlyingSoucerHtmlCleaner(String baseDir, CleanerProperties props) {
		return createFlyingSoucerHtmlCleaner(baseDir, props, null);
	}

	public static final PdfConverter createFlyingSoucerHtmlCleaner(String baseDir, CleanerProperties props, ITextRenderer renderer) {
		if (props == null) {
			props = new CleanerProperties();
		}
		final HtmlCleaner htmlCleaner = new HtmlCleaner(props);
		final Serializer serializer = new PrettyXmlSerializer(props);
		final HTMLCleanerHtmlCleaner cleaner = new HTMLCleanerHtmlCleaner(htmlCleaner, serializer);
		final Path path = baseDir != null ? Paths.get(baseDir) : null;
		final FlyingSoucerPdfConverter pdfConverter = new FlyingSoucerPdfConverter(cleaner, renderer, path);
		return pdfConverter;
	}

}// PdfConverterFactory
