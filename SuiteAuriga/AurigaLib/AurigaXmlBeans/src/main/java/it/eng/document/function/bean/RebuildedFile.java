/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.annotation.Attachment;

@XmlRootElement
public class RebuildedFile implements Serializable {

	@Attachment
	private File file;
	
	private FileInfoBean info;
	private BigDecimal idFolder;
	private BigDecimal idUd;
	private BigDecimal idDocumento;
	private Boolean annullaLastVer = false;
	private Boolean updateVersion = false;
	private Integer posizione;
	private String idTask;
	
	

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public FileInfoBean getInfo() {
		return info;
	}

	public void setInfo(FileInfoBean info) {
		this.info = info;
	}
	
	public BigDecimal getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(BigDecimal idFolder) {
		this.idFolder = idFolder;
	}

	public BigDecimal getIdUd() {
		return idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}

	public BigDecimal getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(BigDecimal idDocumento) {
		this.idDocumento = idDocumento;
	}

	public Boolean getAnnullaLastVer() {
		return annullaLastVer;
	}

	public void setAnnullaLastVer(Boolean annullaLastVer) {
		this.annullaLastVer = annullaLastVer;
	}

	public Boolean getUpdateVersion() {
		return updateVersion;
	}

	public void setUpdateVersion(Boolean updateVersion) {
		this.updateVersion = updateVersion;
	}

	public Integer getPosizione() {
		return posizione;
	}

	public void setPosizione(Integer posizione) {
		this.posizione = posizione;
	}

	public String getIdTask() {
		return idTask;
	}

	public void setIdTask(String idTask) {
		this.idTask = idTask;
	}


}