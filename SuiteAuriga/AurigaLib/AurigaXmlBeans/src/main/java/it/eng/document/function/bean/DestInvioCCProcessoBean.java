/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DestInvioCCProcessoBean {
	
	@NumeroColonna(numero = "1")
	private String idDestinatario; // i destinatari possono essere UO, scrivanie o gruppi (es.: UO1234, SV1234, LD1234)
	
	@NumeroColonna(numero = "2")
	private String codDestinatario;
			
	@NumeroColonna(numero = "3")
	private String desDestinatario;

	public String getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(String idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public String getCodDestinatario() {
		return codDestinatario;
	}

	public void setCodDestinatario(String codDestinatario) {
		this.codDestinatario = codDestinatario;
	}

	public String getDesDestinatario() {
		return desDestinatario;
	}

	public void setDesDestinatario(String desDestinatario) {
		this.desDestinatario = desDestinatario;
	}

}
