/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RelazioneVsFolderBean implements Serializable{

	private static final long serialVersionUID = 5611310655086324293L;
		
	@NumeroColonna(numero = "1")
	private String idFolder;
	
	@NumeroColonna(numero = "2")
	private String pathFolder;
	
	@NumeroColonna(numero = "3")
	private String annoFascicolo;
	
	@NumeroColonna(numero = "5")
	private String livelliClassificazione;
	
	@NumeroColonna(numero = "5")
	private String riferimentoFascicolo;
	
	@NumeroColonna(numero = "6")
	private String valoreFisso;
	
	@NumeroColonna(numero = "12")
	private String motiviCollegamento;

	public String getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}

	public String getPathFolder() {
		return pathFolder;
	}

	public void setPathFolder(String pathFolder) {
		this.pathFolder = pathFolder;
	}

	public String getAnnoFascicolo() {
		return annoFascicolo;
	}

	public void setAnnoFascicolo(String annoFascicolo) {
		this.annoFascicolo = annoFascicolo;
	}

	public String getLivelliClassificazione() {
		return livelliClassificazione;
	}

	public void setLivelliClassificazione(String livelliClassificazione) {
		this.livelliClassificazione = livelliClassificazione;
	}

	public String getRiferimentoFascicolo() {
		return riferimentoFascicolo;
	}

	public void setRiferimentoFascicolo(String riferimentoFascicolo) {
		this.riferimentoFascicolo = riferimentoFascicolo;
	}

	public String getValoreFisso() {
		return valoreFisso;
	}

	public void setValoreFisso(String valoreFisso) {
		this.valoreFisso = valoreFisso;
	}

	public String getMotiviCollegamento() {
		return motiviCollegamento;
	}

	public void setMotiviCollegamento(String motiviCollegamento) {
		this.motiviCollegamento = motiviCollegamento;
	}
		
}
