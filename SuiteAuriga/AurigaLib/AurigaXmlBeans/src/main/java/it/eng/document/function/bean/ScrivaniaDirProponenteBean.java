/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class ScrivaniaDirProponenteBean {

	@NumeroColonna(numero = "1")
	private String idSV;
	
	@NumeroColonna(numero = "2")
	private String codUO;
			
	@NumeroColonna(numero = "3")
	private String descrizione;
	
	@NumeroColonna(numero = "4")
	private Boolean flgFirmatario;
	
	@NumeroColonna(numero = "5")
	private String cognomeNome;
	
	@NumeroColonna(numero = "6")
	private String motivi;

	public String getIdSV() {
		return idSV;
	}

	public void setIdSV(String idSV) {
		this.idSV = idSV;
	}

	public String getCodUO() {
		return codUO;
	}

	public void setCodUO(String codUO) {
		this.codUO = codUO;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Boolean getFlgFirmatario() {
		return flgFirmatario;
	}

	public void setFlgFirmatario(Boolean flgFirmatario) {
		this.flgFirmatario = flgFirmatario;
	}

	public String getCognomeNome() {
		return cognomeNome;
	}

	public void setCognomeNome(String cognomeNome) {
		this.cognomeNome = cognomeNome;
	}

	public String getMotivi() {
		return motivi;
	}

	public void setMotivi(String motivi) {
		this.motivi = motivi;
	}

}
