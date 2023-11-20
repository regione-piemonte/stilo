/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.annotation.Attachment;

@XmlRootElement
public class FilePrimarioBean implements Serializable{

	private static final long serialVersionUID = 4214358898578136185L;

	@Attachment
	private File file;
	
	private String percorsoFilePrimario;
	private String nomeFilePrimario;
	
	private Boolean isNewOrChanged;
	private BigDecimal idDocumento;
	private FileInfoBean info;
	private Boolean flgSostituisciVerPrec;
	
	@Attachment
	private File fileOmissis;
	
	private Boolean isNewOrChangedOmissis;
	private BigDecimal idDocumentoOmissis;
	private FileInfoBean infoOmissis;
	private Boolean flgSostituisciVerPrecOmissis;
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public String getPercorsoFilePrimario() {
		return percorsoFilePrimario;
	}

	public void setPercorsoFilePrimario(String percorsoFilePrimario) {
		this.percorsoFilePrimario = percorsoFilePrimario;
	}

	public String getNomeFilePrimario() {
		return nomeFilePrimario;
	}

	public void setNomeFilePrimario(String nomeFilePrimario) {
		this.nomeFilePrimario = nomeFilePrimario;
	}

	public Boolean getIsNewOrChanged() {
		return isNewOrChanged;
	}
	
	public void setIsNewOrChanged(Boolean isNewOrChanged) {
		this.isNewOrChanged = isNewOrChanged;
	}
	
	public BigDecimal getIdDocumento() {
		return idDocumento;
	}
	
	public void setIdDocumento(BigDecimal idDocumento) {
		this.idDocumento = idDocumento;
	}
	
	public FileInfoBean getInfo() {
		return info;
	}
	
	public void setInfo(FileInfoBean info) {
		this.info = info;
	}
	
	public Boolean getFlgSostituisciVerPrec() {
		return flgSostituisciVerPrec;
	}
	
	public void setFlgSostituisciVerPrec(Boolean flgSostituisciVerPrec) {
		this.flgSostituisciVerPrec = flgSostituisciVerPrec;
	}

	public File getFileOmissis() {
		return fileOmissis;
	}

	public void setFileOmissis(File fileOmissis) {
		this.fileOmissis = fileOmissis;
	}
	
	public Boolean getIsNewOrChangedOmissis() {
		return isNewOrChangedOmissis;
	}

	public void setIsNewOrChangedOmissis(Boolean isNewOrChangedOmissis) {
		this.isNewOrChangedOmissis = isNewOrChangedOmissis;
	}

	public BigDecimal getIdDocumentoOmissis() {
		return idDocumentoOmissis;
	}

	public void setIdDocumentoOmissis(BigDecimal idDocumentoOmissis) {
		this.idDocumentoOmissis = idDocumentoOmissis;
	}

	public FileInfoBean getInfoOmissis() {
		return infoOmissis;
	}

	public void setInfoOmissis(FileInfoBean infoOmissis) {
		this.infoOmissis = infoOmissis;
	}

	public Boolean getFlgSostituisciVerPrecOmissis() {
		return flgSostituisciVerPrecOmissis;
	}

	public void setFlgSostituisciVerPrecOmissis(Boolean flgSostituisciVerPrecOmissis) {
		this.flgSostituisciVerPrecOmissis = flgSostituisciVerPrecOmissis;
	}
		
}
