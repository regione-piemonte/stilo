/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RebuildedFileSinadoc implements Serializable {

	private String uriFile;
	private File file;
	private FileInfoBean info;
	private BigDecimal idDocumento;
	private Boolean annullaLastVer = false;
	private Boolean updateVersion = false;
	private Integer posizione;

	public String getUriFile() {
		return uriFile;
	}

	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}

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

}