/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class XmlConteggiXSezioneOutBean {

	@NumeroColonna(numero = "1")
	private String idSezione;
	
	@NumeroColonna(numero = "2")
	private String idSezionePadre;
	
	@NumeroColonna(numero = "3")
	private String nomeSezione;
	
	@NumeroColonna(numero = "4")
	private String nomeSezionePadre;
	
	@NumeroColonna(numero = "5")
	private String contRisultatiSezione;

	public String getIdSezione() {
		return idSezione;
	}

	public void setIdSezione(String idSezione) {
		this.idSezione = idSezione;
	}

	public String getIdSezionePadre() {
		return idSezionePadre;
	}

	public void setIdSezionePadre(String idSezionePadre) {
		this.idSezionePadre = idSezionePadre;
	}

	public String getNomeSezione() {
		return nomeSezione;
	}

	public void setNomeSezione(String nomeSezione) {
		this.nomeSezione = nomeSezione;
	}

	public String getNomeSezionePadre() {
		return nomeSezionePadre;
	}

	public void setNomeSezionePadre(String nomeSezionePadre) {
		this.nomeSezionePadre = nomeSezionePadre;
	}

	public String getContRisultatiSezione() {
		return contRisultatiSezione;
	}

	public void setContRisultatiSezione(String contRisultatiSezione) {
		this.contRisultatiSezione = contRisultatiSezione;
	}

}
