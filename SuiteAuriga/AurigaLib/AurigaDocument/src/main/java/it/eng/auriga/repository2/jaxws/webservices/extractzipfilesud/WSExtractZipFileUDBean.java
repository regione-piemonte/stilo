/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */

package it.eng.auriga.repository2.jaxws.webservices.extractzipfilesud;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSExtractZipFileUDBean implements Serializable {

	private String xml;
	private List<ExtractZipFileUDBean> documentlist;
	private List<File> extractedFileList;

	private Map<String, File> extractedFileMap;

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public List<ExtractZipFileUDBean> getDocumentlist() {
		return documentlist;
	}

	public void setDocumentlist(List<ExtractZipFileUDBean> documentlist) {
		this.documentlist = documentlist;
	}

	public List<File> getExtractedFileList() {
		return extractedFileList;
	}

	public void setExtractedFileList(List<File> extractedFileList) {
		this.extractedFileList = extractedFileList;
	}

	public Map<String, File> getExtractedFileMap() {
		return extractedFileMap;
	}

	public void setExtractedFileMap(Map<String, File> extractedFileMap) {
		this.extractedFileMap = extractedFileMap;
	}

}
