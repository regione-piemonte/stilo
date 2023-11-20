/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.Date;

public class CronologiaOperazioniEffettuateDettEmailBean extends AbstractBean implements Serializable{
	
	private String idUserLavoroIn;
	private String idEmailIn;
	private String flgIncludiOpFalliteIn;
	private String progressivoAzione;
	private Date tsOperazione;
	private String tsOperazioneXOrd;
	private String idUtenteOperazione;
	private String decodificaIdUtenteOperazione;
	private String idUtenteOperazioneSecondario;
	private String decodificaIdUtenteOperazioneSecondario;
	private String tipoOperazione;
	private String decodificaTipoOperazione;
	private String dettaglioOperazione;
	private String esitoOperazione;
	private String msgErrore;
	
	public String getIdUserLavoroIn() {
		return idUserLavoroIn;
	}
	public String getIdEmailIn() {
		return idEmailIn;
	}
	public String getFlgIncludiOpFalliteIn() {
		return flgIncludiOpFalliteIn;
	}
	public Date getTsOperazione() {
		return tsOperazione;
	}
	public String getTsOperazioneXOrd() {
		return tsOperazioneXOrd;
	}
	public String getIdUtenteOperazione() {
		return idUtenteOperazione;
	}
	public String getDecodificaIdUtenteOperazione() {
		return decodificaIdUtenteOperazione;
	}
	public String getIdUtenteOperazioneSecondario() {
		return idUtenteOperazioneSecondario;
	}
	public String getDecodificaIdUtenteOperazioneSecondario() {
		return decodificaIdUtenteOperazioneSecondario;
	}
	public String getTipoOperazione() {
		return tipoOperazione;
	}
	public String getDecodificaTipoOperazione() {
		return decodificaTipoOperazione;
	}
	public String getDettaglioOperazione() {
		return dettaglioOperazione;
	}
	public String getEsitoOperazione() {
		return esitoOperazione;
	}
	public String getMsgErrore() {
		return msgErrore;
	}
	public void setIdUserLavoroIn(String idUserLavoroIn) {
		this.idUserLavoroIn = idUserLavoroIn;
	}
	public void setIdEmailIn(String idEmailIn) {
		this.idEmailIn = idEmailIn;
	}
	public void setFlgIncludiOpFalliteIn(String flgIncludiOpFalliteIn) {
		this.flgIncludiOpFalliteIn = flgIncludiOpFalliteIn;
	}
	public void setTsOperazione(Date tsOperazione) {
		this.tsOperazione = tsOperazione;
	}
	public void setTsOperazioneXOrd(String tsOperazioneXOrd) {
		this.tsOperazioneXOrd = tsOperazioneXOrd;
	}
	public void setIdUtenteOperazione(String idUtenteOperazione) {
		this.idUtenteOperazione = idUtenteOperazione;
	}
	public void setDecodificaIdUtenteOperazione(String decodificaIdUtenteOperazione) {
		this.decodificaIdUtenteOperazione = decodificaIdUtenteOperazione;
	}
	public void setIdUtenteOperazioneSecondario(String idUtenteOperazioneSecondario) {
		this.idUtenteOperazioneSecondario = idUtenteOperazioneSecondario;
	}
	public void setDecodificaIdUtenteOperazioneSecondario(
			String decodificaIdUtenteOperazioneSecondario) {
		this.decodificaIdUtenteOperazioneSecondario = decodificaIdUtenteOperazioneSecondario;
	}
	public void setTipoOperazione(String tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}
	public void setDecodificaTipoOperazione(String decodificaTipoOperazione) {
		this.decodificaTipoOperazione = decodificaTipoOperazione;
	}
	public void setDettaglioOperazione(String dettaglioOperazione) {
		this.dettaglioOperazione = dettaglioOperazione;
	}
	public void setEsitoOperazione(String esitoOperazione) {
		this.esitoOperazione = esitoOperazione;
	}
	public void setMsgErrore(String msgErrore) {
		this.msgErrore = msgErrore;
	}
	public String getProgressivoAzione() {
		return progressivoAzione;
	}
	public void setProgressivoAzione(String progressivoAzione) {
		this.progressivoAzione = progressivoAzione;
	}
	
	
}
