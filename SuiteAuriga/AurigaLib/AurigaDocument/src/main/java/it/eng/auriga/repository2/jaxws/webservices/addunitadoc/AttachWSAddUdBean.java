/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.webservices.addunitadoc;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.function.bean.Flag;

@XmlRootElement
public class AttachWSAddUdBean implements Serializable{

	private static final long serialVersionUID = 5611310655086324291L;

	//-- 1: DisplayName del file (eventualmente rinominato per rendere coerente l'estensione con il formato riconosciuto)
	@NumeroColonna(numero = "1")
	private String displayFilename;
			
	//-- 2: URI del file salvato su archivio definitivo in notazione storageUtil		
	@NumeroColonna(numero = "2")
	private String uri;
	
	//-- 3: Nro di attachment con cui il file appare nella request secondo schema NEWUD.xsd
	//-     Nel caso di unico attachment zip che viene "spacchettato" come accade per il il SUE 
	//      di ImpresaInUnGiorno il n.ro attach sarà sempre 1 per tutti i file dell'unico attachment zip
	@NumeroColonna(numero = "3")
	private String numeroAttach;
	
	//-- 4: Nome della tipologia documentale dell'allegato
	@NumeroColonna(numero = "4")
	private String nomeTipologia;
	
	//-- 5: Descrizione dell'allegato
	@NumeroColonna(numero = "5")
	private String descrizione;
	
	//-- 6: dimensione
	@NumeroColonna(numero = "6")	
	private BigDecimal dimensione;

	//-- 7: impronta
	@NumeroColonna(numero = "7")	
	private String impronta;
	
	//-- 8: algoritmo calcolo impronta
	@NumeroColonna(numero = "8")
	private String algoritmo  ;
	
	//-- 9: encoding di calcolo impronta: colonna
	@NumeroColonna(numero = "9")
	private String encodingImpronta;
	
	//-- 10: Flg file firmato (valori 1/0)
	@NumeroColonna(numero = "10")
	private String flgFirmato;
	
	//-- 11: Mimetype
	@NumeroColonna(numero = "11")
	private String mimetype;
	
	//-- 12: Firmatari
	@NumeroColonna(numero = "12")
	private String firmatari;
	
	//-- 13: Indicazione del tipo di firma (CAdES o PAdES)
	@NumeroColonna(numero = "13")
	private String tipoFirma;
	
	//-- 14: Info di verifica della firma
	@NumeroColonna(numero = "14")
	private String infoVerificaFirma;
	
	//-- 15: Data e ora delle marca se presente marca temporale valida (nel formato DD/MM/RRR HH24:MI:SS)
	@NumeroColonna(numero = "15")
	@TipoData(tipo = Tipo.DATA)
	private Date dataOraMarca;
	
	//-- 16: Tipo di marca temporale se presente
	@NumeroColonna(numero = "16")
	private String tipoMarca;
	
	//-- 17: Informazioni di verifica della marca temporale se presente
	@NumeroColonna(numero = "17")
	private String infoVerificaMarca;
	
	//-- 21: ID dell'allegato nel sistema di provenienza (UUID dell'allegato nel json)
	@NumeroColonna(numero = "21")
	private String idSistemaProvenienza;
	
	//-- 22: Data e ora della firma digitale della busta crittografica piu' esterna, se presente (nel formato DD/MM/RRR HH24:MI:SS)
	@NumeroColonna(numero = "22")
	@TipoData(tipo = Tipo.DATA)
	private Date dataFirmaBustaCrittografica;
	
	//-- 23: Flag di firma non valida alla data (valori 1/0/NULL) (la firma della busta crittografica piu' esterna)
	@NumeroColonna(numero = "23")
	private Flag flgFirmaCrittograficaNonValida;
	
	//-- 24: Flag di manca temporale non valida alla data (valori 1/0/NULL)
	@NumeroColonna(numero = "24")
	private Flag flgMarcaTemporaleNonValida;
	
	//-- 25: Data emissione certificato firmatario (se piu' di uno separati da ";")
	@NumeroColonna(numero = "25")
	private String dataOraEmissioneCertificatoFirma;
	
	//-- 26: Data scadenza certificato firmatario (se piu' di uno separati da ";")
	@NumeroColonna(numero = "26")
	private String dataOraScadenzaCertificatoFirma;
	
	//-- 27: Indica se firma Qualifica (=Q) o Avanzata (=A) (se piu' di uno separati da ";")
	@NumeroColonna(numero = "27")
	private String tipoFirmaQA;
	
	//-- 28: Cod. fiscali dei firmatari (se piu' di uno separati da ";")
	@NumeroColonna(numero = "28")
	private String cfFirmatario;
	
	@NumeroColonna(numero = "29")
	private String improntaPreFirmaDaFileOp;
	
	@NumeroColonna(numero = "30")
	private String flgFilePrimario;
	
	// Puntamento al file
	private File file;

	public String getDisplayFilename() {
		return displayFilename;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getNumeroAttach() {
		return numeroAttach;
	}

	public void setNumeroAttach(String numeroAttach) {
		this.numeroAttach = numeroAttach;
	}

	public String getImpronta() {
		return impronta;
	}

	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}

	public BigDecimal getDimensione() {
		return dimensione;
	}

	public void setDimensione(BigDecimal dimensione) {
		this.dimensione = dimensione;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

	public String getEncodingImpronta() {
		return encodingImpronta;
	}

	public void setEncodingImpronta(String encodingImpronta) {
		this.encodingImpronta = encodingImpronta;
	}

	public String getFlgFirmato() {
		return flgFirmato;
	}

	public void setFlgFirmato(String flgFirmato) {
		this.flgFirmato = flgFirmato;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getFirmatari() {
		return firmatari;
	}

	public void setFirmatari(String firmatari) {
		this.firmatari = firmatari;
	}

	public String getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public String getInfoVerificaFirma() {
		return infoVerificaFirma;
	}

	public void setInfoVerificaFirma(String infoVerificaFirma) {
		this.infoVerificaFirma = infoVerificaFirma;
	}

	public Date getDataOraMarca() {
		return dataOraMarca;
	}

	public void setDataOraMarca(Date dataOraMarca) {
		this.dataOraMarca = dataOraMarca;
	}

	public String getTipoMarca() {
		return tipoMarca;
	}

	public void setTipoMarca(String tipoMarca) {
		this.tipoMarca = tipoMarca;
	}

	public String getInfoVerificaMarca() {
		return infoVerificaMarca;
	}

	public void setInfoVerificaMarca(String infoVerificaMarca) {
		this.infoVerificaMarca = infoVerificaMarca;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNomeTipologia() {
		return nomeTipologia;
	}

	public void setNomeTipologia(String nomeTipologia) {
		this.nomeTipologia = nomeTipologia;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getIdSistemaProvenienza() {
		return idSistemaProvenienza;
	}

	public void setIdSistemaProvenienza(String idSistemaProvenienza) {
		this.idSistemaProvenienza = idSistemaProvenienza;
	}

	public Flag getFlgMarcaTemporaleNonValida() {
		return flgMarcaTemporaleNonValida;
	}

	public void setFlgMarcaTemporaleNonValida(Flag flgMarcaTemporaleNonValida) {
		this.flgMarcaTemporaleNonValida = flgMarcaTemporaleNonValida;
	}

	public Flag getFlgFirmaCrittograficaNonValida() {
		return flgFirmaCrittograficaNonValida;
	}

	public void setFlgFirmaCrittograficaNonValida(Flag flgFirmaCrittograficaNonValida) {
		this.flgFirmaCrittograficaNonValida = flgFirmaCrittograficaNonValida;
	}

	public Date getDataFirmaBustaCrittografica() {
		return dataFirmaBustaCrittografica;
	}

	public void setDataFirmaBustaCrittografica(Date dataFirmaBustaCrittografica) {
		this.dataFirmaBustaCrittografica = dataFirmaBustaCrittografica;
	}

	public String getDataOraEmissioneCertificatoFirma() {
		return dataOraEmissioneCertificatoFirma;
	}

	public void setDataOraEmissioneCertificatoFirma(String dataOraEmissioneCertificatoFirma) {
		this.dataOraEmissioneCertificatoFirma = dataOraEmissioneCertificatoFirma;
	}

	public String getDataOraScadenzaCertificatoFirma() {
		return dataOraScadenzaCertificatoFirma;
	}

	public void setDataOraScadenzaCertificatoFirma(String dataOraScadenzaCertificatoFirma) {
		this.dataOraScadenzaCertificatoFirma = dataOraScadenzaCertificatoFirma;
	}

	public String getTipoFirmaQA() {
		return tipoFirmaQA;
	}

	public void setTipoFirmaQA(String tipoFirmaQA) {
		this.tipoFirmaQA = tipoFirmaQA;
	}

	public String getCfFirmatario() {
		return cfFirmatario;
	}

	public void setCfFirmatario(String cfFirmatario) {
		this.cfFirmatario = cfFirmatario;
	}

	public String getImprontaPreFirmaDaFileOp() {
		return improntaPreFirmaDaFileOp;
	}

	public void setImprontaPreFirmaDaFileOp(String improntaPreFirmaDaFileOp) {
		this.improntaPreFirmaDaFileOp = improntaPreFirmaDaFileOp;
	}

	public String getFlgFilePrimario() {
		return flgFilePrimario;
	}

	public void setFlgFilePrimario(String flgFilePrimario) {
		this.flgFilePrimario = flgFilePrimario;
	}   
}
