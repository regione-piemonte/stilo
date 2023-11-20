/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBSalvaAllegatoIn implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long idAtto;

    private boolean isMainDocument;

    private java.lang.String fileName;

    private java.lang.String mimeType;

    private byte[] fileContent;

	public AlboAVBSalvaAllegatoIn() {	
	}

	public AlboAVBSalvaAllegatoIn(long idAtto, boolean isMainDocument, String fileName, String mimeType,
			byte[] fileContent) {
		this.idAtto = idAtto;
		this.isMainDocument = isMainDocument;
		this.fileName = fileName;
		this.mimeType = mimeType;
		this.fileContent = fileContent;
	}

	public long getIdAtto() {
		return idAtto;
	}

	public void setIdAtto(long idAtto) {
		this.idAtto = idAtto;
	}

	public boolean isMainDocument() {
		return isMainDocument;
	}

	public void setMainDocument(boolean isMainDocument) {
		this.isMainDocument = isMainDocument;
	}

	public java.lang.String getFileName() {
		return fileName;
	}

	public void setFileName(java.lang.String fileName) {
		this.fileName = fileName;
	}

	public java.lang.String getMimeType() {
		return mimeType;
	}

	public void setMimeType(java.lang.String mimeType) {
		this.mimeType = mimeType;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
}
