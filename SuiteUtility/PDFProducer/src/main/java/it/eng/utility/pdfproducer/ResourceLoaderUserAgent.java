/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
