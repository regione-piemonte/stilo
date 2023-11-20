/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

public class FileUnitoBean {
	
	private String nomeFileUnito;
	private File file;
	
	public String getNomeFileUnito() {
		return nomeFileUnito;
	}
	public File getFile() {
		return file;
	}
	public void setNomeFileUnito(String nomeFileUnito) {
		this.nomeFileUnito = nomeFileUnito;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
	
}
