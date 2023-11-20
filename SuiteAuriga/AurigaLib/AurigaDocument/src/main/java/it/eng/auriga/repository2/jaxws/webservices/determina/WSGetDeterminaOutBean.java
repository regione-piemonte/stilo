/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.io.File;
import java.io.Serializable;
import java.util.List;


public class WSGetDeterminaOutBean implements Serializable {

	private String xml;
	private List<FileBean> listaFile;
	private List<File> listaFileExtracted;
	
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	
	public List<File> getListaFileExtracted() {
		return listaFileExtracted;
	}
	public void setListaFileExtracted(List<File> listaFileExtracted) {
		this.listaFileExtracted = listaFileExtracted;
	}
	public List<FileBean> getListaFile() {
		return listaFile;
	}
	public void setListaFile(List<FileBean> listaFile) {
		this.listaFile = listaFile;
	}
	
}
