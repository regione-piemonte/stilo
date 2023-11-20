/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSGetDatiPubblicazioneAttoBean implements Serializable {

	private String xml;
	private List<RelatePubblBean> documentlist;	
	private List<File> extractedFileList;
	
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public List<RelatePubblBean> getDocumentlist() {
		return documentlist;
	}
	public void setDocumentlist(List<RelatePubblBean> documentlist) {
		this.documentlist = documentlist;
	}
	public List<File> getExtractedFileList() {
		return extractedFileList;
	}
	public void setExtractedFileList(List<File> extractedFileList) {
		this.extractedFileList = extractedFileList;
	}
	
	

	}
