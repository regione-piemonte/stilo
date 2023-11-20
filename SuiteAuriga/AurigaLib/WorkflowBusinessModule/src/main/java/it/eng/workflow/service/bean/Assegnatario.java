/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.Flag;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

public class Assegnatario {

	@NumeroColonna(numero = "1")
	private String tipo = "UO";
	
	@NumeroColonna(numero = "2")
	private String idUo;
	
	@NumeroColonna(numero = "19")
	private String flgOpzioniAccesso = "FC";
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getFlgOpzioniAccesso() {
		return flgOpzioniAccesso;
	}

	public void setFlgOpzioniAccesso(String flgOpzioniAccesso) {
		this.flgOpzioniAccesso = flgOpzioniAccesso;
	}
	
}
