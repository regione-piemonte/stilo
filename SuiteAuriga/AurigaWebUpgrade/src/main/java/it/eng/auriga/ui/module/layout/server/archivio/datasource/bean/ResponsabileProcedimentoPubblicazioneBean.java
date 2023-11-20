/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;


public class ResponsabileProcedimentoPubblicazioneBean {

	@NumeroColonna(numero="1")
	private String idResponsabileProcedimentoPubblicazione;
		
	@NumeroColonna(numero="2")
	private String descrizioneResponsabileProcedimentoPubblicazione;
	
	@NumeroColonna(numero="3")
	private String codRapidoResponsabileProcedimentoPubblicazione;
	
	@NumeroColonna(numero="4")
	private String usernameResponsabileProcedimentoPubblicazione;

	public String getIdResponsabileProcedimentoPubblicazione() {
		return idResponsabileProcedimentoPubblicazione;
	}

	public void setIdResponsabileProcedimentoPubblicazione(String idResponsabileProcedimentoPubblicazione) {
		this.idResponsabileProcedimentoPubblicazione = idResponsabileProcedimentoPubblicazione;
	}

	public String getDescrizioneResponsabileProcedimentoPubblicazione() {
		return descrizioneResponsabileProcedimentoPubblicazione;
	}

	public void setDescrizioneResponsabileProcedimentoPubblicazione(
			String descrizioneResponsabileProcedimentoPubblicazione) {
		this.descrizioneResponsabileProcedimentoPubblicazione = descrizioneResponsabileProcedimentoPubblicazione;
	}

	public String getCodRapidoResponsabileProcedimentoPubblicazione() {
		return codRapidoResponsabileProcedimentoPubblicazione;
	}

	public void setCodRapidoResponsabileProcedimentoPubblicazione(String codRapidoResponsabileProcedimentoPubblicazione) {
		this.codRapidoResponsabileProcedimentoPubblicazione = codRapidoResponsabileProcedimentoPubblicazione;
	}

	public String getUsernameResponsabileProcedimentoPubblicazione() {
		return usernameResponsabileProcedimentoPubblicazione;
	}

	public void setUsernameResponsabileProcedimentoPubblicazione(String usernameResponsabileProcedimentoPubblicazione) {
		this.usernameResponsabileProcedimentoPubblicazione = usernameResponsabileProcedimentoPubblicazione;
	}
	



	
}
