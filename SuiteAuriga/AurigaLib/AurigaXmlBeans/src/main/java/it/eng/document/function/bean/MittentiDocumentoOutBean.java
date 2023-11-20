/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MittentiDocumentoOutBean extends MittentiDocumentoBean{
	
	@NumeroColonna(numero = "17")
	private String codRapido;
	
	@NumeroColonna(numero = "18")
	private String tipoMittente;

	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}

	public String getCodRapido() {
		return codRapido;
	}
	
	public String getTipoMittente() {
		return tipoMittente;
	}

	public void setTipoMittente(String tipoMittente) {
		this.tipoMittente = tipoMittente;
	}

}
