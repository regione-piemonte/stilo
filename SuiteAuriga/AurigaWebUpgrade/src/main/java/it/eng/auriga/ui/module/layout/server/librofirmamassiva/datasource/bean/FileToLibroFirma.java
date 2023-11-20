/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

@XmlRootElement
public class FileToLibroFirma {

	@NumeroColonna(numero = "1")
	private String idDoc;
	@NumeroColonna(numero = "2")
	private String uriFile;
	@NumeroColonna(numero = "3")
	private String tipoFirma;
	@NumeroColonna(numero = "4")
	private String mimetype;
	@NumeroColonna(numero = "5")
	private String dimensione;
	@NumeroColonna(numero = "6")
	private String impronta;
	@NumeroColonna(numero = "7")
	private String algoritmoImpronta;
	@NumeroColonna(numero = "8")
	private String encodingImpronta;
	@NumeroColonna(numero = "9")
	private String firmatari;
	@NumeroColonna(numero = "10")
	@TipoData(tipo=Tipo.DATA_ESTESA)
	private Date dataOraEmissioneCertificatoFirma;
	@NumeroColonna(numero = "11")
	@TipoData(tipo=Tipo.DATA_ESTESA)
	private Date dataOraScadenzaCertificatoFirma;
	@NumeroColonna(numero = "12")
	private String tipoFirmaQA;
	
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
	public String getTipoFirma() {
		return tipoFirma;
	}
	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public String getDimensione() {
		return dimensione;
	}
	public void setDimensione(String dimensione) {
		this.dimensione = dimensione;
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
	public String getFirmatari() {
		return firmatari;
	}
	public void setFirmatari(String firmatari) {
		this.firmatari = firmatari;
	}
	public Date getDataOraEmissioneCertificatoFirma() {
		return dataOraEmissioneCertificatoFirma;
	}
	public void setDataOraEmissioneCertificatoFirma(Date dataOraEmissioneCertificatoFirma) {
		this.dataOraEmissioneCertificatoFirma = dataOraEmissioneCertificatoFirma;
	}
	public Date getDataOraScadenzaCertificatoFirma() {
		return dataOraScadenzaCertificatoFirma;
	}
	public void setDataOraScadenzaCertificatoFirma(Date dataOraScadenzaCertificatoFirma) {
		this.dataOraScadenzaCertificatoFirma = dataOraScadenzaCertificatoFirma;
	}
	public String getTipoFirmaQA() {
		return tipoFirmaQA;
	}
	public void setTipoFirmaQA(String tipoFirmaQA) {
		this.tipoFirmaQA = tipoFirmaQA;
	}
}
