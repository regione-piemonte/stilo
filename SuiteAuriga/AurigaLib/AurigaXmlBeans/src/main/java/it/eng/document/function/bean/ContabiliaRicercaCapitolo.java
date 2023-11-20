/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaRicercaCapitolo extends ContabiliaBaseRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer numeroArticolo;
	private Integer numeroCapitolo;
	private Integer numeroUEB;
	
	public Integer getNumeroArticolo() {
		return numeroArticolo;
	}
	
	public void setNumeroArticolo(Integer numeroArticolo) {
		this.numeroArticolo = numeroArticolo;
	}
	
	public Integer getNumeroCapitolo() {
		return numeroCapitolo;
	}
	
	public void setNumeroCapitolo(Integer numeroCapitolo) {
		this.numeroCapitolo = numeroCapitolo;
	}
	
	public Integer getNumeroUEB() {
		return numeroUEB;
	}
	
	public void setNumeroUEB(Integer numeroUEB) {
		this.numeroUEB = numeroUEB;
	}
	
	@Override
	public String toString() {
		return "ContabiliaRicercaCapitolo [numeroArticolo=" + numeroArticolo + ", numeroCapitolo=" + numeroCapitolo
				+ ", numeroUEB=" + numeroUEB + "]";
	}
	
}
