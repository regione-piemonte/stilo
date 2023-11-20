/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author Cristiano Daniele
 *
 */

public class AurigaAutoCompletamentoBean {

	/**
	 * Valore di input
	 */
	private String destinatario;

	/**
	 * Descrizione voce della rubrica e-mail + eventuale indirizzo mail
	 */
	@NumeroColonna(numero = "1")
	private String descVoceRubrica;
	
	/**
	 * Indirizzo mail
	 */
	@NumeroColonna(numero = "2")
	private String indirizzoEmail;

	/**
	 * valori (C/O/NULL) Indicazione se indirizzo Certificato (=C) o Ordinario (=O)
	 */
	@NumeroColonna(numero = "3")
	private String tipoIndirizzo;

	/**
	 * Id. voce della rubrica e-mail corrisondente
	 */
	@NumeroColonna(numero = "4")
	private String idVoceRubrica;

	/**
	 * Flag 1/0: se 1 è una mailing list, se 0 un singolo indirizzo
	 * Se 0 allora ho colonna 2 altrimenti colonna 1
	 */
	@NumeroColonna(numero = "5")
	private Integer flgMailingList;
	
	public String getDestinatario() {
		return destinatario;
	}
	
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	
	public String getDescVoceRubrica() {
		return descVoceRubrica;
	}

	public void setDescVoceRubrica(String descVoceRubrica) {
		this.descVoceRubrica = descVoceRubrica;
	}
	
	public String getIndirizzoEmail() {
		return indirizzoEmail;
	}
	
	public void setIndirizzoEmail(String indirizzoEmail) {
		this.indirizzoEmail = indirizzoEmail;
	}
	
	public String getTipoIndirizzo() {
		return tipoIndirizzo;
	}
	
	public void setTipoIndirizzo(String tipoIndirizzo) {
		this.tipoIndirizzo = tipoIndirizzo;
	}

	public String getIdVoceRubrica() {
		return idVoceRubrica;
	}

	public void setIdVoceRubrica(String idVoceRubrica) {
		this.idVoceRubrica = idVoceRubrica;
	}
	
	public Integer getFlgMailingList() {
		return flgMailingList;
	}
	
	public void setFlgMailingList(Integer flgMailingList) {
		this.flgMailingList = flgMailingList;
	}
}
