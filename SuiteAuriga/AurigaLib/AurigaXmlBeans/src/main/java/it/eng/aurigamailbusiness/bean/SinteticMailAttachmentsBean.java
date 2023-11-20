/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.Serializable;

import it.eng.core.annotation.Attachment;

public class SinteticMailAttachmentsBean implements Serializable {

	@Override
	public String toString() {
		return "SinteticMailAttachmentsBean [filename=" + filename + "]";
	}

	private static final long serialVersionUID = -1399708054087045775L;
	private String filename;
	
	private byte[] file;

	@Attachment
	private File fileAttach;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}
	
	public File getFileAttach() {
		return fileAttach;
	}

	public void setFileAttach(File fileAttach) {
		this.fileAttach = fileAttach;
	}
}