package it.eng.client.applet.operation.jsignpdf;

import com.itextpdf.text.pdf.PdfWriter;
 
/**
 * Enum of hash algorithms supported in PDF signatures.
 */

public enum HashAlgorithm {
	SHA1("SHA1", PdfWriter.VERSION_1_3),
	SHA256("SHA256", PdfWriter.VERSION_1_6),
	SHA384("SHA384", PdfWriter.VERSION_1_7),
	SHA512("SHA512", PdfWriter.VERSION_1_7),
	RIPEMD160("RIPEMD160", PdfWriter.VERSION_1_7);

	private final char pdfVersion;
	private final String algorithmName;

	private HashAlgorithm(final String aName, char aVersion) {
		algorithmName = aName;
		pdfVersion = aVersion;
	}

	/**
	 * Gets algorithm name.
	 * 
	 * @return
	 */
	public String getAlgorithmName() {
		return algorithmName;
	}

	/**
	 * Gets minimal PDF version supporting the algorithm.
	 * 
	 * @return
	 */
	public char getPdfVersion() {
		return pdfVersion;
	}
	
	public static HashAlgorithm fromValue(String v) {
        for (HashAlgorithm h: HashAlgorithm.values()) {
            if (h.algorithmName.equals(v)) {
                return h;
            }
        }
        throw new IllegalArgumentException(v);
    }
}