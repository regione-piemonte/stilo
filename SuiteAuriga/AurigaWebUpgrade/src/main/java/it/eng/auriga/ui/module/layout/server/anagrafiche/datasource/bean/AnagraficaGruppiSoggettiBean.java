/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;
import java.util.Map;

public class AnagraficaGruppiSoggettiBean {

	private String idGruppo;			
	private String codiceRapido;	
	private String nome; 				
	private String flgSoggettiGruppo;
    private Date dtValiditaDa;
	private Date dtValiditaA;
	private String idUtenteIns;
	private String descUtenteIns;
	private Date dataIns;
	private String idUtenteUltMod;
	private String descUtenteUltMod;
	private Date dataUltMod;
    private String recProtetto;
    private String flgValido;    
    private List<SoggettoGruppoBean> listaSoggettiGruppo;
    private Boolean flgInibitaAss;
    
    private String rowid;
    
    // Attributi dinamici
 	private Map<String, Object> valori;
 	private Map<String, String> tipiValori;
 	
	public String getCodiceRapido() {
		return codiceRapido;
	}
	public void setCodiceRapido(String codiceRapido) {
		this.codiceRapido = codiceRapido;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(String idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public String getDescUtenteIns() {
		return descUtenteIns;
	}
	public void setDescUtenteIns(String descUtenteIns) {
		this.descUtenteIns = descUtenteIns;
	}
	public Date getDataIns() {
		return dataIns;
	}
	public void setDataIns(Date dataIns) {
		this.dataIns = dataIns;
	}
	public String getIdUtenteUltMod() {
		return idUtenteUltMod;
	}
	public void setIdUtenteUltMod(String idUtenteUltMod) {
		this.idUtenteUltMod = idUtenteUltMod;
	}
	public String getDescUtenteUltMod() {
		return descUtenteUltMod;
	}
	public void setDescUtenteUltMod(String descUtenteUltMod) {
		this.descUtenteUltMod = descUtenteUltMod;
	}
	public Date getDataUltMod() {
		return dataUltMod;
	}
	public void setDataUltMod(Date dataUltMod) {
		this.dataUltMod = dataUltMod;
	}
	public String getRecProtetto() {
		return recProtetto;
	}
	public void setRecProtetto(String recProtetto) {
		this.recProtetto = recProtetto;
	}
	public String getFlgValido() {
		return flgValido;
	}
	public void setFlgValido(String flgValido) {
		this.flgValido = flgValido;
	}
	public List<SoggettoGruppoBean> getListaSoggettiGruppo() {
		return listaSoggettiGruppo;
	}
	public void setListaSoggettiGruppo(List<SoggettoGruppoBean> listaSoggettiGruppo) {
		this.listaSoggettiGruppo = listaSoggettiGruppo;
	}
	public String getIdGruppo() {
		return idGruppo;
	}
	public void setIdGruppo(String idGruppo) {
		this.idGruppo = idGruppo;
	}
	public Date getDtValiditaDa() {
		return dtValiditaDa;
	}
	public void setDtValiditaDa(Date dtValiditaDa) {
		this.dtValiditaDa = dtValiditaDa;
	}
	public Date getDtValiditaA() {
		return dtValiditaA;
	}
	public void setDtValiditaA(Date dtValiditaA) {
		this.dtValiditaA = dtValiditaA;
	}
	public String getFlgSoggettiGruppo() {
		return flgSoggettiGruppo;
	}
	public void setFlgSoggettiGruppo(String flgSoggettiGruppo) {
		this.flgSoggettiGruppo = flgSoggettiGruppo;
	}
	public Boolean getFlgInibitaAss() {
		return flgInibitaAss;
	}
	public void setFlgInibitaAss(Boolean flgInibitaAss) {
		this.flgInibitaAss = flgInibitaAss;
	}
	public Map<String, Object> getValori() {
		return valori;
	}
	public void setValori(Map<String, Object> valori) {
		this.valori = valori;
	}
	public Map<String, String> getTipiValori() {
		return tipiValori;
	}
	public void setTipiValori(Map<String, String> tipiValori) {
		this.tipiValori = tipiValori;
	}
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}						
}