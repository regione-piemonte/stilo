/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ActaInputGetClassificazioneEstesa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String aooCode;
	private String structurCode;
	private String indiceClassificazioneEstesa;
	
	public String getAooCode() {
		return aooCode;
	}
	
	public void setAooCode(String aooCode) {
		this.aooCode = aooCode;
	}
	
	public String getStructurCode() {
		return structurCode;
	}
	
	public void setStructurCode(String structurCode) {
		this.structurCode = structurCode;
	}
	
	public String getIndiceClassificazioneEstesa() {
		return indiceClassificazioneEstesa;
	}
	
	public void setIndiceClassificazioneEstesa(String indiceClassificazioneEstesa) {
		this.indiceClassificazioneEstesa = indiceClassificazioneEstesa;
	}

}
