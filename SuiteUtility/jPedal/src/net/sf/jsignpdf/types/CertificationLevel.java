/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * 
 * The Original Code is 'JSignPdf, a free application for PDF signing'.
 * 
 * The Initial Developer of the Original Code is Josef Cacek.
 * Portions created by Josef Cacek are Copyright (C) Josef Cacek. All Rights Reserved.
 * 
 * Contributor(s): Josef Cacek.
 * 
 * Alternatively, the contents of this file may be used under the terms
 * of the GNU Lesser General Public License, version 2.1 (the  "LGPL License"), in which case the
 * provisions of LGPL License are applicable instead of those
 * above. If you wish to allow use of your version of this file only
 * under the terms of the LGPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the LGPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the LGPL License.
 */
package net.sf.jsignpdf.types;

import org.jpedal.utils.Messages;

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
