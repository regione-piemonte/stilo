/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.gd.lucenemanager.document.AbstractDocumentReader;

import java.io.File;
import java.util.List;

public class FileVO {

	String nome;
	String path;
	String mimeType;
	AbstractDocumentReader abstractreader;
	File file;
	List<FileVO> listaFileVO;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public AbstractDocumentReader getAbstractreader() {
		return abstractreader;
	}
	public void setAbstractreader(AbstractDocumentReader abstractreader) {
		this.abstractreader = abstractreader;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<FileVO> getListaFileVO() {
		return listaFileVO;
	}
	public void setListaFileVO(List<FileVO> listaFileVO) {
		this.listaFileVO = listaFileVO;
	}
	@Override
	public String toString() {
		return "FileVO [nome=" + nome + ", path=" + path + ", mimeType="
				+ mimeType + ", abstractreader=" + abstractreader + ", file="
				+ file + ", listaFileVO=" + listaFileVO + "]";
	}
	
	
	
}