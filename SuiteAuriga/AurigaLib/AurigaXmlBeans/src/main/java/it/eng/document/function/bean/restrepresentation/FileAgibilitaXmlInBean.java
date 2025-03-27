/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean.restrepresentation;

import java.math.BigDecimal;
import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.function.bean.Flag;

/**
 * @author Antonio Peluso
 */

public class FileAgibilitaXmlInBean {
	
	@NumeroColonna(numero = "1")
	private String uri;
	
	@NumeroColonna(numero = "2")
	private String nome;
	
	@NumeroColonna(numero = "3")
	private BigDecimal dimensione;
	
	@NumeroColonna(numero = "4")
	private String impronta;
	
	@NumeroColonna(numero = "5")
	private String algoritmo;
	
	@NumeroColonna(numero = "6")
	private String encoding;
	
	@NumeroColonna(numero = "7")
	private String mimetype;
	
	@NumeroColonna(numero = "8")
	private Integer flgFirmato;
	
	//Firmatari del file (se più di uno separati da ;)
	@NumeroColonna(numero = "9")
	private String firmatari;	
	
//	10) Indicazione del tipo di firma (CAdES o PAdES)
	@NumeroColonna(numero = "10")
	private String tipoFirma;
	
//	11) Info di verifica della firma 
	@NumeroColonna(numero = "11")
	private String infoVerificaFirma;

//	12) Data e ora delle marca se presente marca temporale valida (nel formato DD/MM/RRR HH24:MI:SS)
	@NumeroColonna(numero = "12")
	private Date dataOraMarca;

//	13) Tipo di marca temporale se presente
	@NumeroColonna(numero = "13")
	private String tipoMarca;

//	14) Informazioni di verifica della marca temporale se presente
	@NumeroColonna(numero = "14")
	private String infoVerificaMarca;

//	15) Data e ora della firma digitale della busta crittografica più esterna, se presente (nel formato DD/MM/RRR HH24:MI:SS)
	@NumeroColonna(numero = "15")
	@TipoData(tipo = Tipo.DATA)
	private Date dataFirmaBustaCrittografica;
	
//	18) Flag di firma non valida alla data (valori 1/0/NULL) (la firma della busta crittografica più esterna)
	@NumeroColonna(numero = "18")
	private Flag flgFirmaCrittograficaNonValida;
	
//	19) Flag di manca temporale non valida alla data (valori 1/0/NULL)
	@NumeroColonna(numero = "19")
	private Flag flgMarcaTemporaleNonValida;

//	20) Data emissione certificato firmatario (se più di uno separati da “;” )
	@NumeroColonna(numero = "20")
	private String dataOraEmissioneCertificatoFirma;

//	21) Data scadenza certificato firmatario (se più di uno separati da “;” )
	@NumeroColonna(numero = "21")
	private String dataOraScadenzaCertificatoFirma;

//	22) Indica se firma Qualifica (=Q) o Avanzata (=A) (se più di uno separate da “;” )
	@NumeroColonna(numero = "22")
	private String tipoFirmaQA;

//	23) Cod. fiscali dei firmatari (se più di uno separati da “;” )
	@NumeroColonna(numero = "23")
	private String cfFirmatario;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getDimensione() {
		return dimensione;
	}

	public void setDimensione(BigDecimal dimensione) {
		this.dimensione = dimensione;
	}

	public String getImpronta() {
		return impronta;
	}

	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public Integer getFlgFirmato() {
		return flgFirmato;
	}

	public void setFlgFirmato(Integer flgFirmato) {
		this.flgFirmato = flgFirmato;
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

	public Date getDataFirmaBustaCrittografica() {
		return dataFirmaBustaCrittografica;
	}

	public void setDataFirmaBustaCrittografica(Date dataFirmaBustaCrittografica) {
		this.dataFirmaBustaCrittografica = dataFirmaBustaCrittografica;
	}

	public Flag getFlgFirmaCrittograficaNonValida() {
		return flgFirmaCrittograficaNonValida;
	}

	public void setFlgFirmaCrittograficaNonValida(Flag flgFirmaCrittograficaNonValida) {
		this.flgFirmaCrittograficaNonValida = flgFirmaCrittograficaNonValida;
	}

	public Flag getFlgMarcaTemporaleNonValida() {
		return flgMarcaTemporaleNonValida;
	}

	public void setFlgMarcaTemporaleNonValida(Flag flgMarcaTemporaleNonValida) {
		this.flgMarcaTemporaleNonValida = flgMarcaTemporaleNonValida;
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

}
