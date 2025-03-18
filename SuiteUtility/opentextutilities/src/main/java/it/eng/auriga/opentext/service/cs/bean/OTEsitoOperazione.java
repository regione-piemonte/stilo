/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.eng.auriga.opentext.enumeration.EsitoEnum;

public class OTEsitoOperazione implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codiceEsito = EsitoEnum.ESITO_OK.getCodiceEsito();
	private String descrizioneEsito = EsitoEnum.ESITO_OK.getDescrizioneEsito();
	public String getCodiceEsito() {
		return codiceEsito;
	}
	public void setCodiceEsito(String codiceEsito) {
		this.codiceEsito = codiceEsito;
	}
	public String getDescrizioneEsito() {
		return descrizioneEsito;
	}
	public void setDescrizioneEsito(String descrizioneEsito) {
		this.descrizioneEsito = descrizioneEsito;
	}
	
	
	
	

}
