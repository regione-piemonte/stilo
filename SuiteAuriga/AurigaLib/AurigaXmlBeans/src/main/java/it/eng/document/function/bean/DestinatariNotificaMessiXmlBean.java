/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class DestinatariNotificaMessiXmlBean {
	
	@NumeroColonna(numero = "1")
	private String descrizione;

	@NumeroColonna(numero = "2")
	private String email;

	@NumeroColonna(numero = "3")
	private String indirizzo;
	
	@NumeroColonna(numero = "4")
	private String altriDati;
	
	@NumeroColonna(numero = "5")
	private String numeroNotifica;
	
	@NumeroColonna(numero = "6")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataNotifica;
	
	@NumeroColonna(numero = "7")
	private String mezzoNotifica;

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getAltriDati() {
		return altriDati;
	}

	public void setAltriDati(String altriDati) {
		this.altriDati = altriDati;
	}

	public String getNumeroNotifica() {
		return numeroNotifica;
	}

	public void setNumeroNotifica(String numeroNotifica) {
		this.numeroNotifica = numeroNotifica;
	}

	public Date getDataNotifica() {
		return dataNotifica;
	}

	public void setDataNotifica(Date dataNotifica) {
		this.dataNotifica = dataNotifica;
	}

	public String getMezzoNotifica() {
		return mezzoNotifica;
	}

	public void setMezzoNotifica(String mezzoNotifica) {
		this.mezzoNotifica = mezzoNotifica;
	}

}
