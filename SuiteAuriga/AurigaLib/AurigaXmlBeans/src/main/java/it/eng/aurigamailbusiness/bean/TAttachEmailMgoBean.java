/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

@XmlRootElement
public class TAttachEmailMgoBean extends AbstractBean implements Serializable {

	

	

	@Override
	public String toString() {
		return "TAttachEmailMgoBean [dimensione=" + dimensione + ", displayFilename=" + displayFilename
				+ ", flgFirmato=" + flgFirmato + ", flgFirmaValida=" + flgFirmaValida + ", idAttachEmail="
				+ idAttachEmail + ", idUteIns=" + idUteIns + ", idUteUltimoAgg=" + idUteUltimoAgg + ", mimetype="
				+ mimetype + ", nomeOriginale=" + nomeOriginale + ", nroAllegRegProt=" + nroAllegRegProt
				+ ", nroAllegSchedaDoc=" + nroAllegSchedaDoc + ", tsIns=" + tsIns + ", tsUltimoAgg=" + tsUltimoAgg
				+ ", idEmail=" + idEmail + ", idSchedaDocEmail=" + idSchedaDocEmail + ", impronta=" + impronta
				+ ", algoritmoImpronta=" + algoritmoImpronta + ", encodingImpronta=" + encodingImpronta
				+ ", idRegProtEmail=" + idRegProtEmail + ", nroProgr=" + nroProgr + ", nomeOriginaleCtx="
				+ nomeOriginaleCtx + ", flgPdfEditabile=" + flgPdfEditabile + ", flgPdfConCommenti=" + flgPdfConCommenti
				+ ", segnatura=" + segnatura + ", uriAttach=" + uriAttach + ", uriFileXOcr=" + uriFileXOcr + "]";
	}

	private static final long serialVersionUID = -7341833444762147789L;

	private long dimensione;

	private String displayFilename;

	private boolean flgFirmato;

	private boolean flgFirmaValida;

	private String idAttachEmail;

	private String idUteIns;

	private String idUteUltimoAgg;

	private String mimetype;

	private String nomeOriginale;

	private Byte nroAllegRegProt;

	private Byte nroAllegSchedaDoc;

	private Date tsIns;

	private Date tsUltimoAgg;

	private String idEmail;

	private String idSchedaDocEmail;

	private String impronta;

	private String algoritmoImpronta;

	private String encodingImpronta;

	private String idRegProtEmail;

	private BigDecimal nroProgr;

	private String nomeOriginaleCtx;
	
	private boolean flgPdfEditabile;
	
	private boolean flgPdfConCommenti;
	
	private String segnatura;
	
	private String uriAttach;
	
	private String uriFileXOcr;
	
	

	public String getUriFileXOcr() {
		return uriFileXOcr;
	}

	public void setUriFileXOcr(String uriFileXOcr) {
		this.uriFileXOcr = uriFileXOcr;
	}

	public long getDimensione() {
		return dimensione;
	}

	public void setDimensione(long dimensione) {
		this.dimensione = dimensione;
	}

	public String getDisplayFilename() {
		return displayFilename;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	public boolean getFlgFirmato() {
		return flgFirmato;
	}

	public void setFlgFirmato(boolean flgFirmato) {
		this.flgFirmato = flgFirmato;
	}

	public boolean isFlgFirmaValida() {
		return flgFirmaValida;
	}

	public void setFlgFirmaValida(boolean flgFirmaValida) {
		this.flgFirmaValida = flgFirmaValida;
	}

	public String getIdAttachEmail() {
		return idAttachEmail;
	}

	public void setIdAttachEmail(String idAttachEmail) {
		this.idAttachEmail = idAttachEmail;
	}

	public String getIdUteIns() {
		return idUteIns;
	}

	public void setIdUteIns(String idUteIns) {
		this.idUteIns = idUteIns;
	}

	public String getIdUteUltimoAgg() {
		return idUteUltimoAgg;
	}

	public void setIdUteUltimoAgg(String idUteUltimoAgg) {
		this.idUteUltimoAgg = idUteUltimoAgg;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getNomeOriginale() {
		return nomeOriginale;
	}

	public void setNomeOriginale(String nomeOriginale) {
		this.nomeOriginale = nomeOriginale;
	}

	public Byte getNroAllegRegProt() {
		return nroAllegRegProt;
	}

	public void setNroAllegRegProt(Byte nroAllegRegProt) {
		this.nroAllegRegProt = nroAllegRegProt;
	}

	public Byte getNroAllegSchedaDoc() {
		return nroAllegSchedaDoc;
	}

	public void setNroAllegSchedaDoc(Byte nroAllegSchedaDoc) {
		this.nroAllegSchedaDoc = nroAllegSchedaDoc;
	}

	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	public Date getTsUltimoAgg() {
		return tsUltimoAgg;
	}

	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
	}

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public String getIdSchedaDocEmail() {
		return idSchedaDocEmail;
	}

	public void setIdSchedaDocEmail(String idSchedaDocEmail) {
		this.idSchedaDocEmail = idSchedaDocEmail;
	}

	public String getImpronta() {
		return impronta;
	}

	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}

	public String getAlgoritmoImpronta() {
		return algoritmoImpronta;
	}

	public void setAlgoritmoImpronta(String algoritmoImpronta) {
		this.algoritmoImpronta = algoritmoImpronta;
	}

	public String getEncodingImpronta() {
		return encodingImpronta;
	}

	public void setEncodingImpronta(String encodingImpronta) {
		this.encodingImpronta = encodingImpronta;
	}

	public String getIdRegProtEmail() {
		return idRegProtEmail;
	}

	public void setIdRegProtEmail(String idRegProtEmail) {
		this.idRegProtEmail = idRegProtEmail;
	}

	public String getNomeOriginaleCtx() {
		return nomeOriginaleCtx;
	}

	public void setNomeOriginaleCtx(String nomeOriginaleCtx) {
		this.nomeOriginaleCtx = nomeOriginaleCtx;
	}

	public BigDecimal getNroProgr() {
		return nroProgr;
	}

	public void setNroProgr(BigDecimal nroProgr) {
		this.nroProgr = nroProgr;
	}
	
	public boolean isFlgPdfEditabile() {
		return flgPdfEditabile;
	}
	
	public void setFlgPdfEditabile(boolean flgPdfEditabile) {
		this.flgPdfEditabile = flgPdfEditabile;
	}
	
	public boolean isFlgPdfConCommenti() {
		return flgPdfConCommenti;
	}

	public void setFlgPdfConCommenti(boolean flgPdfConCommenti) {
		this.flgPdfConCommenti = flgPdfConCommenti;
	}

	public String getSegnatura() {
		return segnatura;
	}

	public void setSegnatura(String segnatura) {
		this.segnatura = segnatura;
	}

	public String getUriAttach() {
		return uriAttach;
	}

	public void setUriAttach(String uriAttach) {
		this.uriAttach = uriAttach;
	}

	
	
	
}