/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaRicercaAccertamentoRequest extends ContabiliaRicercaMovimentoGestioneRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer annoAccertamento;
	private Integer numeroAccertamento;
	private String idSpAoo;
	
	public Integer getAnnoAccertamento() {
		return annoAccertamento;
	}
	
	public void setAnnoAccertamento(Integer annoAccertamento) {
		this.annoAccertamento = annoAccertamento;
	}
	
	public Integer getNumeroAccertamento() {
		return numeroAccertamento;
	}
	
	public void setNumeroAccertamento(Integer numeroAccertamento) {
		this.numeroAccertamento = numeroAccertamento;
	}
	
	public String getIdSpAoo() {
		return idSpAoo;
	}
	
	public void setIdSpAoo(String idSpAoo) {
		this.idSpAoo = idSpAoo;
	}
	
}
