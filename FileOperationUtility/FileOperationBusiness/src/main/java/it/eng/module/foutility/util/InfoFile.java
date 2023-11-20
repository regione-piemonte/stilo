/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

public class InfoFile {

	private File file;
	private String nome;
	private String path;
	private String mimeType;
	private String error;
	private boolean directory;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	public boolean isDirectory() {
		return directory;
	}
	public void setDirectory(boolean directory) {
		this.directory = directory;
	}
	@Override
	public String toString() {
		return "InfoFile [file=" + file + ", nome=" + nome + ", path=" + path
				+ ", mimeType=" + mimeType + ", error=" + error
				+ ", directory=" + directory + "]";
	}
	
	
	
	
}
