/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

/**
 * 
 * @author dbe4235
 *
 */

public class CronologiaOperazioniBean {
	
	/**
	 * 1: Timestamp dell'operazione (nel formato dato dal parametro di config. FMT_STD_TIMESTAMP)
	 */
	private Date tsOperazione;
	/**
	 * 2: Progressivo operazione per ordinamento cronologico
	 */
	private String progressivoOperazioneXOrd;
	/**
	 * 3: Id. dell'utente che ha effettuato l'operazione
	 */
	private String idUtenteOperazione;
	/**
	 * 4: Decodifica dell'utente che ha effettuato l'operazione
	 */
	private String descrizioneUtenteOperazione;
	/**
	 * 5: Id. dell'utente a nome di cui stava lavorando chi ha effettuato l'operazione
	 */
	private String idUtenteOperazioneDelega;
	/**
	 * 6: Decodifica dell'utente a nome di cui stava lavorando chi ha effettuato l'operazione
	 */
	private String descrizioneUtenteOperazioneDelega;
	/**
	 * 7: Codice del tipo di operazione
	 */
	private String codTipoOperazione;
	/**
	 * 8: Decodifica del tipo di operazione
	 */
	private String descrizioneTipoOperazione;
	/**
	 * 9: Descrizione/dettagli dell'operazione
	 */
	private String descrizioneDettagliOperazione;
	/**
	 * 10:Esito dell'operazione (valori: successo; fallita)
	 */
	private String esitoOperazione;
	/**
	 * 11:Messaggio di errore (nel caso di operazione fallita)
	 */
	private String messaggioErroreOperazione;
	
	public Date getTsOperazione() {
		return tsOperazione;
	}
	public String getProgressivoOperazioneXOrd() {
		return progressivoOperazioneXOrd;
	}
	public String getIdUtenteOperazione() {
		return idUtenteOperazione;
	}
	public String getDescrizioneUtenteOperazione() {
		return descrizioneUtenteOperazione;
	}
	public String getIdUtenteOperazioneDelega() {
		return idUtenteOperazioneDelega;
	}
	public String getDescrizioneUtenteOperazioneDelega() {
		return descrizioneUtenteOperazioneDelega;
	}
	public String getCodTipoOperazione() {
		return codTipoOperazione;
	}
	public String getDescrizioneTipoOperazione() {
		return descrizioneTipoOperazione;
	}
	public String getDescrizioneDettagliOperazione() {
		return descrizioneDettagliOperazione;
	}
	public String getEsitoOperazione() {
		return esitoOperazione;
	}
	public String getMessaggioErroreOperazione() {
		return messaggioErroreOperazione;
	}
	public void setTsOperazione(Date tsOperazione) {
		this.tsOperazione = tsOperazione;
	}
	public void setProgressivoOperazioneXOrd(String progressivoOperazioneXOrd) {
		this.progressivoOperazioneXOrd = progressivoOperazioneXOrd;
	}
	public void setIdUtenteOperazione(String idUtenteOperazione) {
		this.idUtenteOperazione = idUtenteOperazione;
	}
	public void setDescrizioneUtenteOperazione(String descrizioneUtenteOperazione) {
		this.descrizioneUtenteOperazione = descrizioneUtenteOperazione;
	}
	public void setIdUtenteOperazioneDelega(String idUtenteOperazioneDelega) {
		this.idUtenteOperazioneDelega = idUtenteOperazioneDelega;
	}
	public void setDescrizioneUtenteOperazioneDelega(String descrizioneUtenteOperazioneDelega) {
		this.descrizioneUtenteOperazioneDelega = descrizioneUtenteOperazioneDelega;
	}
	public void setCodTipoOperazione(String codTipoOperazione) {
		this.codTipoOperazione = codTipoOperazione;
	}
	public void setDescrizioneTipoOperazione(String descrizioneTipoOperazione) {
		this.descrizioneTipoOperazione = descrizioneTipoOperazione;
	}
	public void setDescrizioneDettagliOperazione(String descrizioneDettagliOperazione) {
		this.descrizioneDettagliOperazione = descrizioneDettagliOperazione;
	}
	public void setEsitoOperazione(String esitoOperazione) {
		this.esitoOperazione = esitoOperazione;
	}
	public void setMessaggioErroreOperazione(String messaggioErroreOperazione) {
		this.messaggioErroreOperazione = messaggioErroreOperazione;
	}
}