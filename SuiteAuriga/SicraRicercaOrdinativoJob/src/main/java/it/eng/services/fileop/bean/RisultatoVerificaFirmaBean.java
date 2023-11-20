/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.fileOperation.clientws.VerificationStatusType;

public class RisultatoVerificaFirmaBean {

	private VerificationStatusType esitoVerifica;
	private String descrizioneErrore;
	
	public VerificationStatusType getEsitoVerifica() {
		return esitoVerifica;
	}
	public void setEsitoVerifica(VerificationStatusType esitoVerifica) {
		this.esitoVerifica = esitoVerifica;
	}
	public String getDescrizioneErrore() {
		return descrizioneErrore;
	}
	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}
	public RisultatoVerificaFirmaBean() {
		super();
	}
	@Override
	public String toString() {
		return "RisultatoVerificaFirmaBean [esitoVerifica=" + esitoVerifica
				+ ", descrizioneErrore=" + descrizioneErrore + "]";
	}
	
	
}
