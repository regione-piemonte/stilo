/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.annotation.Attachment;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FileAllegatoSinadocBean implements Serializable{

	private static final long serialVersionUID = 4214358898578136186L;

	@Attachment
	private String uriFile;
	private File file;
	private BigDecimal idDocumento;
	private Boolean isNewOrChanged;
	private FileInfoBean info;
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public void setInfo(FileInfoBean info) {
		this.info = info;
	}
	public FileInfoBean getInfo() {
		return info;
	}
	public BigDecimal getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(BigDecimal idDocumento) {
		this.idDocumento = idDocumento;
	}
	public Boolean getIsNewOrChanged() {
		return isNewOrChanged;
	}
	public void setIsNewOrChanged(Boolean isNewOrChanged) {
		this.isNewOrChanged = isNewOrChanged;
	}
	public String getUriFile() {
		return uriFile;
	}
	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}
		
}
