/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * 
 * @author DANCRIST
 *
 */

public class VerificaPdfBean {
	
	/**
	 * Attiva = true/false verifica
	 */
	private String attivaVerificaEditabili = "false";
	
	private String attivaVerificaCommenti = "false";
	
	private String attivaVerificaNumeroPagine = "false";
	
	private String attivaIntegrazioneServizioStaticizzazione = "false";

	public String getAttivaVerificaEditabili() {
		return attivaVerificaEditabili;
	}
	public void setAttivaVerificaEditabili(String attivaVerificaEditabili) {
		this.attivaVerificaEditabili = attivaVerificaEditabili;
	}
	public String getAttivaVerificaCommenti() {
		return attivaVerificaCommenti;
	}
	public void setAttivaVerificaCommenti(String attivaVerificaCommenti) {
		this.attivaVerificaCommenti = attivaVerificaCommenti;
	}
	public String getAttivaVerificaNumeroPagine() {
		return attivaVerificaNumeroPagine;
	}
	public void setAttivaVerificaNumeroPagine(String attivaVerificaNumeroPagine) {
		this.attivaVerificaNumeroPagine = attivaVerificaNumeroPagine;
	}
	public String getAttivaIntegrazioneServizioStaticizzazione() {
		return attivaIntegrazioneServizioStaticizzazione;
	}
	public void setAttivaIntegrazioneServizioStaticizzazione(String attivaIntegrazioneServizioStaticizzazione) {
		this.attivaIntegrazioneServizioStaticizzazione = attivaIntegrazioneServizioStaticizzazione;
	}
	
}