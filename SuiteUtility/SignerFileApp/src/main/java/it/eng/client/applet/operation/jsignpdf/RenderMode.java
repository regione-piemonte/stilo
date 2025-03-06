package it.eng.client.applet.operation.jsignpdf;

import it.eng.client.applet.i18N.Messages;

import com.itextpdf.text.pdf.PdfSignatureAppearance;

/**
 * Enum for visible sign rendering configuration
 * 
 */
public enum RenderMode {

	DESCRIPTION_ONLY("solo testo", PdfSignatureAppearance.RenderingMode.DESCRIPTION),
	GRAPHIC("solo immagine", PdfSignatureAppearance.RenderingMode.GRAPHIC),
	GRAPHIC_AND_DESCRIPTION("testo e immagine", PdfSignatureAppearance.RenderingMode.GRAPHIC_AND_DESCRIPTION),
	SIGNAME_AND_DESCRIPTION("testo e nome firmatario", PdfSignatureAppearance.RenderingMode.NAME_AND_DESCRIPTION);

	private String msgKey;
	private PdfSignatureAppearance.RenderingMode render;

	RenderMode(final String aMsgKey, final PdfSignatureAppearance.RenderingMode aLevel) {
		msgKey = aMsgKey;
		render = aLevel;
	}

	/**
	 * Returns internationalized description of a right.
	 */
	@Override
	public String toString() {
		return Messages.getMessage(msgKey);
	}

	/**
	 * Returns Visible Signature Render flag.
	 * 
	 * @return integer flag
	 * @see PdfSignatureAppearance#setRender(int)
	 */
	public PdfSignatureAppearance.RenderingMode getRender() {
		return render;
	}

	public String getRenderKey() {
		return msgKey;
	}

	public static RenderMode fromValue(String v) {
		for (RenderMode r: RenderMode.values()) {
			if (r.getRenderKey().equals(v)) {
				return r;
			}
		}
		throw new IllegalArgumentException(v);
	}

 
}