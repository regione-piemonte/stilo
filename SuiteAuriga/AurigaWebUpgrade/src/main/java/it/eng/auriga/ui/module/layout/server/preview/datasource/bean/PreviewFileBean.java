/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class PreviewFileBean {

	private String uri;
	private String nomeFile;
	private String mimetype;
	private boolean remote;
	private String recordType;
	private int width;
	private int height;
	private MimeTypeFirmaBean infoFile;
	
	// Flag che indicano quando il file è troppo grande per essere convertito o visualizzato come immagine
	private boolean fileDimensionExceedToPdfConvert;
	private boolean fileDimensionExceedToImagePreview;
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public boolean isRemote() {
		return remote;
	}
	public void setRemote(boolean remote) {
		this.remote = remote;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}
	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}
	public boolean isFileDimensionExceedToPdfConvert() {
		return fileDimensionExceedToPdfConvert;
	}
	public void setFileDimensionExceedToPdfConvert(boolean fileDimensionExceedToPdfConvert) {
		this.fileDimensionExceedToPdfConvert = fileDimensionExceedToPdfConvert;
	}
	public boolean isFileDimensionExceedToImagePreview() {
		return fileDimensionExceedToImagePreview;
	}
	public void setFileDimensionExceedToImagePreview(boolean fileDimensionExceedToImagePreview) {
		this.fileDimensionExceedToImagePreview = fileDimensionExceedToImagePreview;
	}
	
}
