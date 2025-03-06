package it.eng.client.applet.operation.jsignpdf;

import it.eng.client.applet.i18N.Messages;

import com.itextpdf.text.pdf.PdfSignatureAppearance;

/**
 * Enum of possible certification levels used to Sign PDF.
 *  */
public enum CertificationLevel {

  NOT_CERTIFIED("Nessuna Certificazione", PdfSignatureAppearance.NOT_CERTIFIED),
  CERTIFIED_NO_CHANGES_ALLOWED("Modifiche non consentite", PdfSignatureAppearance.CERTIFIED_NO_CHANGES_ALLOWED),
  CERTIFIED_FORM_FILLING("Compilazione dei campi modulo non consentita", PdfSignatureAppearance.CERTIFIED_FORM_FILLING),
  CERTIFIED_FORM_FILLING_AND_ANNOTATIONS("Compilazione dei campi modulo e annotazioni consentita", PdfSignatureAppearance.CERTIFIED_FORM_FILLING_AND_ANNOTATIONS);

  private String msgKey;
  private int level;

  CertificationLevel(final String aMsgKey, final int aLevel) {
    msgKey = aMsgKey;
    level = aLevel;
  }

  /**
   * Returns internationalized description of a level.
   */
  public String toString() {
    return Messages.getMessage(msgKey);
  }

  /**
   * Returns Level as defined in iText.
   * 
   * @return
   * @see PdfSignatureAppearance#setCertificationLevel(int)
   */
  public int getLevel() {
    return level;
  }
  
  public String getKey() {
	    return msgKey;
	  }

  /**
   * Returns {@link CertificationLevel} instance for given code. If the code is
   * not found, {@link CertificationLevel#NOT_CERTIFIED} is returned.
   * 
   * @param certLevelCode
   *          level code
   * @return not-null CertificationLevel instance
   */
  public static CertificationLevel findCertificationLevel(int certLevelCode) {
    for (CertificationLevel certLevel : values()) {
      if (certLevelCode == certLevel.getLevel()) {
        return certLevel;
      }
    }
    return NOT_CERTIFIED;
  }
  
  public static CertificationLevel findCertificationCode(String certLevelKey) {
	    for (CertificationLevel certLevel : values()) {
	      if (certLevelKey == certLevel.getKey()) {
	        return certLevel;
	      }
	    }
	    return NOT_CERTIFIED;
	  }
}