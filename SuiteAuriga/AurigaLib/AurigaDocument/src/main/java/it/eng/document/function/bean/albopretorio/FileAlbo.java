/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

/**
 * @author Antonio Peluso
 */

@XmlRootElement(name = "fileAlbo")
@XmlAccessorType(XmlAccessType.FIELD)
public class FileAlbo implements Serializable{

	private static final long serialVersionUID = -3412059749324296363L;

	private String nomeFile;
	private String uriFile;
	private String flgPrimario;
	private String displayFilename;
	private String dimensione;
	private String flgFirmato;
	private String flgConvertibilePdf;
	private String mimetype;
	private String firmatari;	
	private String idDoc;
	private String idUd;
	private String impronta;
	private String algoritmoImpronta;
	private String encodingImpronta;

	public String getNomeFile() {
		return nomeFile;
	}

	public String getUriFile() {
		return uriFile;
	}	

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}

	public String getFlgPrimario() {
		return flgPrimario;
	}

	public String getDisplayFilename() {
		return displayFilename;
	}

	public String getDimensione() {
		return dimensione;
	}

	public String getFlgFirmato() {
		return flgFirmato;
	}

	public String getFlgConvertibilePdf() {
		return flgConvertibilePdf;
	}

	public String getMimetype() {
		return mimetype;
	}

	public String getFirmatari() {
		return firmatari;
	}

	public void setFlgPrimario(String string) {
		this.flgPrimario = string;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	public void setDimensione(String dimensione) {
		this.dimensione = dimensione;
	}

	public void setFlgFirmato(String flgFirmato) {
		this.flgFirmato = flgFirmato;
	}

	public void setFlgConvertibilePdf(String flgConvertibilePdf) {
		this.flgConvertibilePdf = flgConvertibilePdf;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public void setFirmatari(String firmatari) {
		this.firmatari = firmatari;
	}

	public String getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
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

}

