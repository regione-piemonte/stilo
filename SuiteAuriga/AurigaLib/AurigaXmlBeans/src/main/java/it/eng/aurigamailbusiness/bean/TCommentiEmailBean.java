/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

@XmlRootElement
public class TCommentiEmailBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7341833444762147789L;

	private String commento;

	@Override
	public String toString() {
		return "TCommentiEmailBean [idCommentoEmail=" + idCommentoEmail + "]";
	}

	private String tag;
	private String uriFile;
	private BigDecimal nroProgr;
	private String displayFilename;
	private BigDecimal dimensioneFile;
	private boolean flgFileFirmato;
	private String mimetypeFile;
	private String improntaFile;
	private String algoritmoImpronta;
	private String encodingImpronta;
	private boolean flgAnn;
	private boolean flgPubblico;
	private String idCommentoEmail;
	private String idUteIns;
	private String idUteUltimoAgg;
	private Date tsCommento;
	private Date tsIns;
	private Date tsUltimoAgg;
	private String displayFilenameCtx;

	public String getCommento() {
		return commento;
	}

	public void setCommento(String commento) {
		this.commento = commento;
	}

	public boolean getFlgAnn() {
		return flgAnn;
	}

	public void setFlgAnn(boolean flgAnn) {
		this.flgAnn = flgAnn;
	}

	public boolean getFlgPubblico() {
		return flgPubblico;
	}

	public void setFlgPubblico(boolean flgPubblico) {
		this.flgPubblico = flgPubblico;
	}

	public String getIdCommentoEmail() {
		return idCommentoEmail;
	}

	public void setIdCommentoEmail(String idCommentoEmail) {
		this.idCommentoEmail = idCommentoEmail;
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

	public Date getTsCommento() {
		return tsCommento;
	}

	public void setTsCommento(Date tsCommento) {
		this.tsCommento = tsCommento;
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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getUriFile() {
		return uriFile;
	}

	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}

	public BigDecimal getNroProgr() {
		return nroProgr;
	}

	public void setNroProgr(BigDecimal nroProgr) {
		this.nroProgr = nroProgr;
	}

	public String getDisplayFilename() {
		return displayFilename;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	public BigDecimal getDimensioneFile() {
		return dimensioneFile;
	}

	public void setDimensioneFile(BigDecimal dimensioneFile) {
		this.dimensioneFile = dimensioneFile;
	}

	public boolean isFlgFileFirmato() {
		return flgFileFirmato;
	}

	public void setFlgFileFirmato(boolean flgFileFirmato) {
		this.flgFileFirmato = flgFileFirmato;
	}

	public String getMimetypeFile() {
		return mimetypeFile;
	}

	public void setMimetypeFile(String mimetypeFile) {
		this.mimetypeFile = mimetypeFile;
	}

	public String getImprontaFile() {
		return improntaFile;
	}

	public void setImprontaFile(String improntaFile) {
		this.improntaFile = improntaFile;
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

	public String getDisplayFilenameCtx() {
		return displayFilenameCtx;
	}

	public void setDisplayFilenameCtx(String displayFilenameCtx) {
		this.displayFilenameCtx = displayFilenameCtx;
	}

}