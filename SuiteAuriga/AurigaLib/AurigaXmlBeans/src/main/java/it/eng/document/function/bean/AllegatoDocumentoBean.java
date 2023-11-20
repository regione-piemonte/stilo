/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AllegatoDocumentoBean implements Serializable{

	private static final long serialVersionUID = -8152167827197759943L;

	private String descrizione = "allegato elettronico";
	private Integer docType;
	private GenericFile infoFile;
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDocType(Integer docType) {
		this.docType = docType;
	}
	public Integer getDocType() {
		return this.docType;
	}
	public void setInfoFile(GenericFile infoFile) {
		this.infoFile = infoFile;
	}
	public GenericFile getInfoFile() {
		return infoFile;
	}
}
