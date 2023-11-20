/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TipoNumerazioneBean implements Serializable {
	private static final long serialVersionUID = -8474447371182365969L;
	
	@NumeroColonna(numero = "1")
	private String sigla;
	
	@NumeroColonna(numero = "2")
	private String anno;
	
	@NumeroColonna(numero = "3")
	private String categoria;	
	
	@NumeroColonna(numero = "4")
	private String idUo;	
	
	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}
	
	

}
