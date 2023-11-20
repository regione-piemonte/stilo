/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

/**
 * 
 * @author DANIELE CRISTIANO
 *
 */

public class VocabolarioBean {
	
	private String  valore;
	private String  valoreOld;
	private String  valoreCkeditor;
	private String  codiceValore;
	private Date    dtInizioValidita;
	private Date    dtFineValidita;
	private String  significatoValore;
	private String  dictionaryEntry;
	private String  dictionaryEntryVincolo;
	private String  valueGenVincolo;
	private Integer flgValiditaValore;
	private Integer flgLocked;
	private Integer flgValueReferenced;
	private Integer flgCodValReferenced;
	private Integer flgCodObbligatorio;
	private String  vocabolarioDi;
	private String  flgVisibSottoUo;
	private String  flgGestSottoUo;
	private String  flgAbilModifica;
	private String  flgAbilEliminazione;
	
	private String idUoAssociata;
	private String numeroLivelli;
	private String descrizioneUo;
	private Boolean flgVisibileDaSottoUo;
	private Boolean flgModificabileDaSottoUo;
	private String  flgValoriCkeditor;
	
	
	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
	public String getCodiceValore() {
		return codiceValore;
	}
	public void setCodiceValore(String codiceValore) {
		this.codiceValore = codiceValore;
	}
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	public String getSignificatoValore() {
		return significatoValore;
	}
	public void setSignificatoValore(String significatoValore) {
		this.significatoValore = significatoValore;
	}	
	public String getDictionaryEntry() {
		return dictionaryEntry;
	}
	public void setDictionaryEntry(String dictionaryEntry) {
		this.dictionaryEntry = dictionaryEntry;
	}
	public String getDictionaryEntryVincolo() {
		return dictionaryEntryVincolo;
	}
	public void setDictionaryEntryVincolo(String dictionaryEntryVincolo) {
		this.dictionaryEntryVincolo = dictionaryEntryVincolo;
	}
	public String getValueGenVincolo() {
		return valueGenVincolo;
	}
	public void setValueGenVincolo(String valueGenVincolo) {
		this.valueGenVincolo = valueGenVincolo;
	}
	public Integer getFlgValiditaValore() {
		return flgValiditaValore;
	}
	public void setFlgValiditaValore(Integer flgValiditaValore) {
		this.flgValiditaValore = flgValiditaValore;
	}
	public Integer getFlgLocked() {
		return flgLocked;
	}
	public void setFlgLocked(Integer flgLocked) {
		this.flgLocked = flgLocked;
	}
	public Integer getFlgValueReferenced() {
		return flgValueReferenced;
	}
	public void setFlgValueReferenced(Integer flgValueReferenced) {
		this.flgValueReferenced = flgValueReferenced;
	}
	public Integer getFlgCodValReferenced() {
		return flgCodValReferenced;
	}
	public void setFlgCodValReferenced(Integer flgCodValReferenced) {
		this.flgCodValReferenced = flgCodValReferenced;
	}
	public Integer getFlgCodObbligatorio() {
		return flgCodObbligatorio;
	}
	public void setFlgCodObbligatorio(Integer flgCodObbligatorio) {
		this.flgCodObbligatorio = flgCodObbligatorio;
	}
	public String getValoreOld() {
		return valoreOld;
	}
	public void setValoreOld(String valoreOld) {
		this.valoreOld = valoreOld;
	}
	public String getVocabolarioDi() {
		return vocabolarioDi;
	}
	public void setVocabolarioDi(String vocabolarioDi) {
		this.vocabolarioDi = vocabolarioDi;
	}
	public String getFlgVisibSottoUo() {
		return flgVisibSottoUo;
	}
	public void setFlgVisibSottoUo(String flgVisibSottoUo) {
		this.flgVisibSottoUo = flgVisibSottoUo;
	}
	public String getFlgGestSottoUo() {
		return flgGestSottoUo;
	}
	public void setFlgGestSottoUo(String flgGestSottoUo) {
		this.flgGestSottoUo = flgGestSottoUo;
	}
	/**
	 * @return the flgAbilModifica
	 */
	public String getFlgAbilModifica() {
		return flgAbilModifica;
	}
	/**
	 * @param flgAbilModifica the flgAbilModifica to set
	 */
	public void setFlgAbilModifica(String flgAbilModifica) {
		this.flgAbilModifica = flgAbilModifica;
	}
	/**
	 * @return the flgAbilEliminazione
	 */
	public String getFlgAbilEliminazione() {
		return flgAbilEliminazione;
	}
	/**
	 * @param flgAbilEliminazione the flgAbilEliminazione to set
	 */
	public void setFlgAbilEliminazione(String flgAbilEliminazione) {
		this.flgAbilEliminazione = flgAbilEliminazione;
	}
	public String getIdUoAssociata() {
		return idUoAssociata;
	}
	public void setIdUoAssociata(String idUoAssociata) {
		this.idUoAssociata = idUoAssociata;
	}
	public String getNumeroLivelli() {
		return numeroLivelli;
	}
	public void setNumeroLivelli(String numeroLivelli) {
		this.numeroLivelli = numeroLivelli;
	}
	public String getDescrizioneUo() {
		return descrizioneUo;
	}
	public void setDescrizioneUo(String descrizioneUo) {
		this.descrizioneUo = descrizioneUo;
	}
	public Boolean getFlgVisibileDaSottoUo() {
		return flgVisibileDaSottoUo;
	}
	public void setFlgVisibileDaSottoUo(Boolean flgVisibileDaSottoUo) {
		this.flgVisibileDaSottoUo = flgVisibileDaSottoUo;
	}
	public Boolean getFlgModificabileDaSottoUo() {
		return flgModificabileDaSottoUo;
	}
	public void setFlgModificabileDaSottoUo(Boolean flgModificabileDaSottoUo) {
		this.flgModificabileDaSottoUo = flgModificabileDaSottoUo;
	}
	public String getFlgValoriCkeditor() {
		return flgValoriCkeditor;
	}
	public void setFlgValoriCkeditor(String flgValoriCkeditor) {
		this.flgValoriCkeditor = flgValoriCkeditor;
	}
	public String getValoreCkeditor() {
		return valoreCkeditor;
	}
	public void setValoreCkeditor(String valoreCkeditor) {
		this.valoreCkeditor = valoreCkeditor;
	}
	
}
