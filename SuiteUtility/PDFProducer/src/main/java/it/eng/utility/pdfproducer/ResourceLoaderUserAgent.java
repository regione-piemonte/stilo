package it.eng.utility.pdfproducer;

import java.io.InputStream;

import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextUserAgent;

public class ResourceLoaderUserAgent extends ITextUserAgent {

	public ResourceLoaderUserAgent(ITextOutputDevice outputDevice) {
		super(outputDevice);
	}

	@Override
	protected InputStream resolveAndOpenStream(String uri) {
		final InputStream is = super.resolveAndOpenStream(uri);
		return is;
	}

}
