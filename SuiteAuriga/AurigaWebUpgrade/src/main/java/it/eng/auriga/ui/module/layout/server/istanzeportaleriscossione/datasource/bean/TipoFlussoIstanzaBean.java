/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class TipoFlussoIstanzaBean {

	@NumeroColonna(numero = "1")
	private String flowTypeId;

	@NumeroColonna(numero = "2")
	private String idProcessType;
	
	@NumeroColonna(numero = "3")
	private String nomeProcessType;
	
	@NumeroColonna(numero = "4")
	private String idDocTypeFinale;

	@NumeroColonna(numero = "5")
	private String nomeDocTypeFinale;

	@NumeroColonna(numero = "6")
	private String flgRichiestaFirmaDigitale;
	
	public String getFlowTypeId() {
		return flowTypeId;
	}

	public void setFlowTypeId(String flowTypeId) {
		this.flowTypeId = flowTypeId;
	}

	public String getIdProcessType() {
		return idProcessType;
	}

	public void setIdProcessType(String idProcessType) {
		this.idProcessType = idProcessType;
	}

	public String getNomeProcessType() {
		return nomeProcessType;
	}

	public void setNomeProcessType(String nomeProcessType) {
		this.nomeProcessType = nomeProcessType;
	}

	public String getIdDocTypeFinale() {
		return idDocTypeFinale;
	}

	public void setIdDocTypeFinale(String idDocTypeFinale) {
		this.idDocTypeFinale = idDocTypeFinale;
	}

	public String getNomeDocTypeFinale() {
		return nomeDocTypeFinale;
	}

	public void setNomeDocTypeFinale(String nomeDocTypeFinale) {
		this.nomeDocTypeFinale = nomeDocTypeFinale;
	}

	public String getFlgRichiestaFirmaDigitale() {
		return flgRichiestaFirmaDigitale;
	}

	public void setFlgRichiestaFirmaDigitale(String flgRichiestaFirmaDigitale) {
		this.flgRichiestaFirmaDigitale = flgRichiestaFirmaDigitale;
	}

}
