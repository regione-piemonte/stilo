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
 * Enum for visible sign rendering configuration
 * 
 * @author Josef Cacek
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
