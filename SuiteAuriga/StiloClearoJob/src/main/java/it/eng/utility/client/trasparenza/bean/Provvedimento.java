/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class Provvedimento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String anno;
	private String semestre;
	private String numProv;
	private String urlProvvedimento;
	private String aliasProvvedimento;
	private String dataProv;
	private String flagConcessione;
	private String oggetto;
	private String spesa;
	private String urlIndiceFascicolo;
	private String aliasIndiceFascicolo;
	
	public String getAnno() {
		return anno;
	}
	
	public void setAnno(String anno) {
		this.anno = anno;
	}
	
	public String getSemestre() {
		return semestre;
	}
	
	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}
	
	public String getNumProv() {
		return numProv;
	}
	
	public void setNumProv(String numProv) {
		this.numProv = numProv;
	}
	
	public String getUrlProvvedimento() {
		return urlProvvedimento;
	}
	
	public void setUrlProvvedimento(String urlProvvedimento) {
		this.urlProvvedimento = urlProvvedimento;
	}
	
	public String getAliasProvvedimento() {
		return aliasProvvedimento;
	}
	
	public void setAliasProvvedimento(String aliasProvvedimento) {
		this.aliasProvvedimento = aliasProvvedimento;
	}
	
	public String getDataProv() {
		return dataProv;
	}
	
	public void setDataProv(String dataProv) {
		this.dataProv = dataProv;
	}
	
	public String getFlagConcessione() {
		return flagConcessione;
	}
	
	public void setFlagConcessione(String flagConcessione) {
		this.flagConcessione = flagConcessione;
	}
	
	public String getOggetto() {
		return oggetto;
	}
	
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	
	public String getSpesa() {
		return spesa;
	}
	
	public void setSpesa(String spesa) {
		this.spesa = spesa;
	}
	
	public String getUrlIndiceFascicolo() {
		return urlIndiceFascicolo;
	}
	
	public void setUrlIndiceFascicolo(String urlIndiceFascicolo) {
		this.urlIndiceFascicolo = urlIndiceFascicolo;
	}
	
	public String getAliasIndiceFascicolo() {
		return aliasIndiceFascicolo;
	}
	
	public void setAliasIndiceFascicolo(String aliasIndiceFascicolo) {
		this.aliasIndiceFascicolo = aliasIndiceFascicolo;
	}
	
	
	@Override
	public String toString() {
		return "Provvedimento [anno=" + anno + ", semestre=" + semestre + ", numProv=" + numProv + ", urlProvvedimento="
				+ urlProvvedimento + ", aliasProvvedimento=" + aliasProvvedimento + ", dataProv=" + dataProv
				+ ", flagConcessione=" + flagConcessione + ", oggetto=" + oggetto + ", spesa=" + spesa
				+ ", urlIndiceFascicolo=" + urlIndiceFascicolo + ", aliasIndiceFascicolo=" + aliasIndiceFascicolo + "]";
	}
	
}
