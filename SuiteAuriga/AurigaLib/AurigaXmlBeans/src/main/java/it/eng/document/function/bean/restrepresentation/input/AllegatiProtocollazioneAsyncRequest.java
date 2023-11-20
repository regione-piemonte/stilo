/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "allegatiProtocollazioneAsyncRequest")
public class AllegatiProtocollazioneAsyncRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String fileName;
	private String hashFile;
	
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getHashFile() {
		return hashFile;
	}
	
	public void setHashFile(String hashFile) {
		this.hashFile = hashFile;
	}
	
}
