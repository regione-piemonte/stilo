/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.webservices.extractmulti;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSExtractMultiBean implements Serializable {

	private String xml;
	private List<ExtractBean> documentlist;	
	private List<File> extractedFileList;
	
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public List<ExtractBean> getDocumentlist() {
		return documentlist;
	}
	public void setDocumentlist(List<ExtractBean> documentlist) {
		this.documentlist = documentlist;
	}
	public List<File> getExtractedFileList() {
		return extractedFileList;
	}
	public void setExtractedFileList(List<File> extractedFileList) {
		this.extractedFileList = extractedFileList;
	}
	
	

	}
