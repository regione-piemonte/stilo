/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.annotation.Attachment;

@XmlRootElement
public class AggiungiDocumentoInBean implements Serializable {
	
	private static final long serialVersionUID = -3381467601554883309L;

	@Attachment
	private File file;
	
	private String displayFilename;
	private BigDecimal idDocumento;

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	public String getDisplayFilename() {
		return displayFilename;
	}

	public void setIdDocumento(BigDecimal idDocumento) {
		this.idDocumento = idDocumento;
	}

	public BigDecimal getIdDocumento() {
		return idDocumento;
	}
	

}
