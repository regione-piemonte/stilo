/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.OpzioniTimbroDocBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class DocumentBean {
	
	private String idUd;
	private String idDoc;
	private String uriFile;
	private MimeTypeFirmaBean infoFile;
	private Boolean remoteUri;
	private Boolean isChanged;
	private String nomeFileOrig;
	private String nomeFileTif;
	private String uriFileTif;
	private String mimetypeVerPreFirma;
	private String nomeFileVerPreFirma;
	private String uriFileVerPreFirma;
	private String flgConvertibilePdfVerPreFirma;
	private String improntaVerPreFirma;
	private MimeTypeFirmaBean infoFileVerPreFirma;
	private Boolean flgSostituisciVerPrec;
	private String nroUltimaVersione;
	private String nomeFile;
	private Boolean flgTimbratoFilePostReg;
	private Boolean flgTimbraFilePostReg;
	private OpzioniTimbroDocBean opzioniTimbro;
	private String nroVersione;
		
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public String getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}
	public String getUriFile() {
		return uriFile;
	}
	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}
	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}
	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}
	public Boolean getRemoteUri() {
		return remoteUri;
	}
	public void setRemoteUri(Boolean remoteUri) {
		this.remoteUri = remoteUri;
	}
	public Boolean getIsChanged() {
		return isChanged;
	}
	public void setIsChanged(Boolean isChanged) {
		this.isChanged = isChanged;
	}
	public String getNomeFileOrig() {
		return nomeFileOrig;
	}
	public void setNomeFileOrig(String nomeFileOrig) {
		this.nomeFileOrig = nomeFileOrig;
	}
	public String getNomeFileTif() {
		return nomeFileTif;
	}
	public void setNomeFileTif(String nomeFileTif) {
		this.nomeFileTif = nomeFileTif;
	}
	public String getUriFileTif() {
		return uriFileTif;
	}
	public void setUriFileTif(String uriFileTif) {
		this.uriFileTif = uriFileTif;
	}
	public String getMimetypeVerPreFirma() {
		return mimetypeVerPreFirma;
	}
	public void setMimetypeVerPreFirma(String mimetypeVerPreFirma) {
		this.mimetypeVerPreFirma = mimetypeVerPreFirma;
	}
	public String getNomeFileVerPreFirma() {
		return nomeFileVerPreFirma;
	}
	public void setNomeFileVerPreFirma(String nomeFileVerPreFirma) {
		this.nomeFileVerPreFirma = nomeFileVerPreFirma;
	}
	public String getUriFileVerPreFirma() {
		return uriFileVerPreFirma;
	}
	public void setUriFileVerPreFirma(String uriFileVerPreFirma) {
		this.uriFileVerPreFirma = uriFileVerPreFirma;
	}
	public String getFlgConvertibilePdfVerPreFirma() {
		return flgConvertibilePdfVerPreFirma;
	}
	public void setFlgConvertibilePdfVerPreFirma(String flgConvertibilePdfVerPreFirma) {
		this.flgConvertibilePdfVerPreFirma = flgConvertibilePdfVerPreFirma;
	}
	public String getImprontaVerPreFirma() {
		return improntaVerPreFirma;
	}
	public void setImprontaVerPreFirma(String improntaVerPreFirma) {
		this.improntaVerPreFirma = improntaVerPreFirma;
	}
	public MimeTypeFirmaBean getInfoFileVerPreFirma() {
		return infoFileVerPreFirma;
	}
	public void setInfoFileVerPreFirma(MimeTypeFirmaBean infoFileVerPreFirma) {
		this.infoFileVerPreFirma = infoFileVerPreFirma;
	}
	public Boolean getFlgSostituisciVerPrec() {
		return flgSostituisciVerPrec;
	}
	public void setFlgSostituisciVerPrec(Boolean flgSostituisciVerPrec) {
		this.flgSostituisciVerPrec = flgSostituisciVerPrec;
	}
	public String getNroUltimaVersione() {
		return nroUltimaVersione;
	}
	public void setNroUltimaVersione(String nroUltimaVersione) {
		this.nroUltimaVersione = nroUltimaVersione;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public Boolean getFlgTimbratoFilePostReg() {
		return flgTimbratoFilePostReg;
	}
	public void setFlgTimbratoFilePostReg(Boolean flgTimbratoFilePostReg) {
		this.flgTimbratoFilePostReg = flgTimbratoFilePostReg;
	}
	public Boolean getFlgTimbraFilePostReg() {
		return flgTimbraFilePostReg;
	}
	public void setFlgTimbraFilePostReg(Boolean flgTimbraFilePostReg) {
		this.flgTimbraFilePostReg = flgTimbraFilePostReg;
	}
	public OpzioniTimbroDocBean getOpzioniTimbro() {
		return opzioniTimbro;
	}
	public void setOpzioniTimbro(OpzioniTimbroDocBean opzioniTimbro) {
		this.opzioniTimbro = opzioniTimbro;
	}
	public String getNroVersione() {
		return nroVersione;
	}	
	public void setNroVersione(String nroVersione) {
		this.nroVersione = nroVersione;
	}

}


