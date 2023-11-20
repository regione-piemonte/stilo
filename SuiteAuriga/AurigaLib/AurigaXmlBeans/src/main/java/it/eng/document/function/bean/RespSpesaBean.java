/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class RespSpesaBean {

	@NumeroColonna(numero = "1")
	private String idSV;

	@NumeroColonna(numero = "2")
	private String codUO;
	
	@NumeroColonna(numero = "3")
	private String descrizione;
	
	@NumeroColonna(numero = "4")
	private String descrizionePerModello;
	
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
	
	public String getDescrizionePerModello() {
		return descrizionePerModello;
	}
	
	public void setDescrizionePerModello(String descrizionePerModello) {
		this.descrizionePerModello = descrizionePerModello;
	}

}
