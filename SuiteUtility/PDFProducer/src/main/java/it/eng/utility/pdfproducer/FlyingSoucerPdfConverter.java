package it.eng.utility.pdfproducer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.XMLResource;
import org.xml.sax.InputSource;

import com.steadystate.css.dom.CSSStyleSheetImpl;
import com.steadystate.css.parser.CSSOMParser;

public class FlyingSoucerPdfConverter implements PdfConverter {

	private HTMLCleaner htmlCleaner;
	private Path baseDir;
	private ITextRenderer renderer;
	public static final String CSS_FILE_NAME = "style.css";

	public FlyingSoucerPdfConverter(HTMLCleaner htmlCleaner, ITextRenderer renderer, Path baseDir) {
		this.htmlCleaner = htmlCleaner;
		this.renderer = renderer;
		this.baseDir = baseDir;
	}

	public FlyingSoucerPdfConverter(HTMLCleaner htmlCleaner, ITextRenderer renderer) {
		this(htmlCleaner, renderer, null);
	}

	public FlyingSoucerPdfConverter() {
		this(null, null);
	}

	public void setRenderer(ITextRenderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public Path getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(Path baseDir) {
		this.baseDir = baseDir;
	}

	public void setHtmlCleaner(HTMLCleaner htmlCleaner) {
		this.htmlCleaner = htmlCleaner;
	}

	// ===============================================================================================================================

	@Override
	public void generatePdf(String htmlContent, File pdf, boolean skipCleaningup) throws Exception {
		generatePdf(htmlContent, null, pdf, skipCleaningup);
	}

	@Override
	public void generatePdf(String htmlContent, Charset charset, File pdf, boolean skipCleaningup) throws Exception {
		generatePdf(htmlContent, charset, pdf, getBaseUrl(), skipCleaningup);
	}

	@Override
	public void generatePdf(String htmlContent, File pdf, URL baseURL, boolean skipCleaningup) throws Exception {
		generatePdf(htmlContent, null, pdf, baseURL, skipCleaningup);
	}

	@Override
	public void generatePdf(String htmlContent, Charset charset, File pdf, URL baseURL, boolean skipCleaningup) throws Exception {
		final Document doc = createDocument(htmlContent, charset, skipCleaningup);
		OutputStream os = null;
		try {
			os = new FileOutputStream(pdf);
			final String baseUrl = getBaseURL(baseURL);
			final ITextRenderer renderer = new ITextRenderer();
			final ResourceLoaderUserAgent callback = new ResourceLoaderUserAgent(renderer.getOutputDevice());
			callback.setSharedContext(renderer.getSharedContext());
			renderer.getSharedContext().setUserAgentCallback(callback);
			renderer.setDocument(doc, baseUrl); // _sharedContext.setBaseURL(url);
			renderer.layout();
			renderer.createPDF(os);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}// generatePdf

	// ===============================================================================================================================
	@Override
	public void generatePdfMode2(String htmlContent, File pdf, boolean skipCleaningup) throws Exception {
		generatePdfMode2(htmlContent, null, pdf, skipCleaningup);
	}

	@Override
	public void generatePdfMode2(String htmlContent, Charset charset, File pdf, boolean skipCleaningup) throws Exception {
		generatePdfMode2(htmlContent, charset, pdf, getBaseUrl(), skipCleaningup);
	}

	@Override
	public void generatePdfMode2(String htmlContent, File pdf, URL baseURL, boolean skipCleaningup) throws Exception {
		generatePdfMode2(htmlContent, null, pdf, baseURL, skipCleaningup);
	}

	@Override
	public void generatePdfMode2(String htmlContent, Charset charset, File pdf, URL baseURL, boolean skipCleaningup) throws Exception {
		if (renderer == null) {
			generatePdf(htmlContent, charset, pdf, baseURL, skipCleaningup);
		}
		final Document doc = createDocument(htmlContent, charset, skipCleaningup);
		OutputStream os = null;
		try {
			os = new FileOutputStream(pdf);
			final String baseUrl = getBaseURL(baseURL);
			synchronized (renderer) {
				final ResourceLoaderUserAgent callback = new ResourceLoaderUserAgent(renderer.getOutputDevice());
				callback.setSharedContext(renderer.getSharedContext());
				renderer.getSharedContext().setUserAgentCallback(callback);
				renderer.setDocument(doc, baseUrl); // _sharedContext.setBaseURL(url);
				renderer.layout();
				renderer.createPDF(os);
			}
		} catch (Exception e) {
			renderer = new ITextRenderer();
			throw e;
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}// generatePdf

	private Document createDocument(String htmlContent, Charset charset, boolean skipCleaningup) throws IOException {
		String xHtml = htmlContent;
		if (!skipCleaningup && htmlCleaner != null) {
			if (charset != null) {
				xHtml = htmlCleaner.convertToString(htmlContent, charset);
			} else {
				xHtml = htmlCleaner.convertToString(htmlContent);
			}
		}

		xHtml = PDFProducerUtils.sanitizeXmlChars(xHtml);
		// ITextFontResolver resolver = renderer.getFontResolver();
		// resolver.addFont("C:\\WINNT\\Fonts\\ARIALUNI.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		final InputSource is = new InputSource(new BufferedReader(new StringReader(xHtml)));
		final Document doc = XMLResource.load(is).getDocument();
		final NodeList headList = doc.getElementsByTagName("head");
		Node head = headList.item(0);
		if (head == null) {
			head = doc.createElement("head");
			doc.appendChild(head);
		}
		// <link rel="stylesheet" type="text/css" media="all" href="style.css"/>
		head.appendChild(createLink(doc));

		final NodeList styleList = doc.getElementsByTagName("style");
		final Node style = styleList.item(0);
		if (style != null && style.getTextContent() != null) {
			style.setTextContent(cssCleanup(style.getTextContent()));
		}
		return doc;
	}

	private String getBaseURL(URL baseURL) throws MalformedURLException {
		String baseUrl;
		URL url = baseURL;
		if (url == null) {
			url = new URL("file", "", "/");
			baseUrl = url.toString();
		} else {
			baseUrl = url.toString();
			baseUrl = baseUrl.replace('\\', '/');
			if (!baseUrl.endsWith("/")) {
				baseUrl += "/";
			}
		}
		return baseUrl;
	}

	private URL getBaseUrl() throws MalformedURLException {
		return this.getBaseUrl(getBaseDir());
	}

	private URL getBaseUrl(Path path) throws MalformedURLException {
		if (path == null) {
			return new URL("file", "", "/");
		}
		return path.toUri().toURL();

	}

	private String cssCleanup(final String content) throws IOException {
		String css = content;
		final org.w3c.css.sac.InputSource source = new org.w3c.css.sac.InputSource(new BufferedReader(new StringReader(content)));
		final CSSOMParser parser = new CSSOMParser();
		final CSSErrorHandler errorHandler = new CSSErrorHandler();
		parser.setErrorHandler(errorHandler);
		final CSSStyleSheet sheet = parser.parseStyleSheet(source, null, null);
		if (errorHandler.getResult() != null) {
			final CSSRuleList rules = sheet.getCssRules();
			int n = -1;
			for (int i = 0; i < rules.getLength(); i++) {
				final CSSRule rule = rules.item(i);
				// System.out.println("(" + i + ")=>" + rule.getCssText());
				if (rule.getCssText().contains(errorHandler.getResult())) {
					n = i;
					break;
				}
			}
			if (n > -1) {
				sheet.deleteRule(n);
			}
		}
		if (sheet instanceof CSSStyleSheetImpl) {
			css = ((CSSStyleSheetImpl) sheet).getCssText();
			// System.out.println("css: " + css);
		}
		return css;
	}// cssCleanup

	private Element createLink(final Document doc) {
		final Element link = doc.createElement("link");
		link.setAttribute("rel", "stylesheet");
		link.setAttribute("type", "text/css");
		link.setAttribute("media", "all");
		link.setAttribute("href", CSS_FILE_NAME);
		return link;
	}

}