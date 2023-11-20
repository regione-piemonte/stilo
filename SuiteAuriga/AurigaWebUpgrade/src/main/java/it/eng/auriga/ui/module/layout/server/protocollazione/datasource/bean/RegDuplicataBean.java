/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.dao.beans.PreferenceBean;

import java.util.Date;
import java.util.HashMap;

public class RegDuplicataBean extends PreferenceBean {

	private String idUd;
	private String score;
	private String estremiRegistrazione;
	private Date tsRegistrazione;
	private String flgVersoRegistrazione;
	private String mittenti;
	private String destinatari;
	private String rifRegProvenienza;
	private String nroRegProvenienza;
	private Date dtRegProvenienza;
	private String oggetto;
	private String flgPrimario;
	private String uriFilePrimario;
	private String nomeFilePrimario;
	private String flgPrimarioAllegati;
	private String nroAllegatoFileCoincidenti;
	private String uriFileCoincidenti;
	private String nomiFileCoincidenti;	
	private String nroRaccomandata;
	private Date dtRaccomandata;
	private HashMap<String, String> comparators;
	
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getEstremiRegistrazione() {
		return estremiRegistrazione;
	}
	public void setEstremiRegistrazione(String estremiRegistrazione) {
		this.estremiRegistrazione = estremiRegistrazione;
	}
	public Date getTsRegistrazione() {
		return tsRegistrazione;
	}
	public void setTsRegistrazione(Date tsRegistrazione) {
		this.tsRegistrazione = tsRegistrazione;
	}	
	public String getFlgVersoRegistrazione() {
		return flgVersoRegistrazione;
	}
	public void setFlgVersoRegistrazione(String flgVersoRegistrazione) {
		this.flgVersoRegistrazione = flgVersoRegistrazione;
	}	
	public String getMittenti() {
		return mittenti;
	}
	public void setMittenti(String mittenti) {
		this.mittenti = mittenti;
	}
	public String getDestinatari() {
		return destinatari;
	}
	public void setDestinatari(String destinatari) {
		this.destinatari = destinatari;
	}
	public String getRifRegProvenienza() {
		return rifRegProvenienza;
	}
	public void setRifRegProvenienza(String rifRegProvenienza) {
		this.rifRegProvenienza = rifRegProvenienza;
	}
	public String getNroRegProvenienza() {
		return nroRegProvenienza;
	}
	public void setNroRegProvenienza(String nroRegProvenienza) {
		this.nroRegProvenienza = nroRegProvenienza;
	}
	public Date getDtRegProvenienza() {
		return dtRegProvenienza;
	}
	public void setDtRegProvenienza(Date dtRegProvenienza) {
		this.dtRegProvenienza = dtRegProvenienza;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getFlgPrimario() {
		return flgPrimario;
	}
	public void setFlgPrimario(String flgPrimario) {
		this.flgPrimario = flgPrimario;
	}
	public String getUriFilePrimario() {
		return uriFilePrimario;
	}
	public void setUriFilePrimario(String uriFilePrimario) {
		this.uriFilePrimario = uriFilePrimario;
	}
	public String getNomeFilePrimario() {
		return nomeFilePrimario;
	}
	public void setNomeFilePrimario(String nomeFilePrimario) {
		this.nomeFilePrimario = nomeFilePrimario;
	}
	public String getFlgPrimarioAllegati() {
		return flgPrimarioAllegati;
	}
	public void setFlgPrimarioAllegati(String flgPrimarioAllegati) {
		this.flgPrimarioAllegati = flgPrimarioAllegati;
	}
	public String getNroAllegatoFileCoincidenti() {
		return nroAllegatoFileCoincidenti;
	}
	public void setNroAllegatoFileCoincidenti(String nroAllegatoFileCoincidenti) {
		this.nroAllegatoFileCoincidenti = nroAllegatoFileCoincidenti;
	}
	public String getUriFileCoincidenti() {
		return uriFileCoincidenti;
	}
	public void setUriFileCoincidenti(String uriFileCoincidenti) {
		this.uriFileCoincidenti = uriFileCoincidenti;
	}
	public String getNomiFileCoincidenti() {
		return nomiFileCoincidenti;
	}
	public void setNomiFileCoincidenti(String nomiFileCoincidenti) {
		this.nomiFileCoincidenti = nomiFileCoincidenti;
	}
	public String getNroRaccomandata() {
		return nroRaccomandata;
	}
	public void setNroRaccomandata(String nroRaccomandata) {
		this.nroRaccomandata = nroRaccomandata;
	}
	public Date getDtRaccomandata() {
		return dtRaccomandata;
	}
	public void setDtRaccomandata(Date dtRaccomandata) {
		this.dtRaccomandata = dtRaccomandata;
	}
	public HashMap<String, String> getComparators() {
		return comparators;
	}
	public void setComparators(HashMap<String, String> comparators) {
		this.comparators = comparators;
	}
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}	
	
}
