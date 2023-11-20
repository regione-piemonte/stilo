/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaAccertamento extends ContabiliaMovimentoGestione implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer annoAccertamento;
	private Integer annoAccertamentoOrigine;
	private Integer annoAccertamentoRiaccertato;
	private BigDecimal disponibilitaIncassare;
	private Integer numAccertamentoOrigine;
	private Integer numAccertamentoRiaccertato;
	private Integer numeroAccertamento;
	private List<ContabiliaSubAccertamento> subAccertamenti;
	
	public Integer getAnnoAccertamento() {
		return annoAccertamento;
	}
	
	public void setAnnoAccertamento(Integer annoAccertamento) {
		this.annoAccertamento = annoAccertamento;
	}
	
	public Integer getAnnoAccertamentoOrigine() {
		return annoAccertamentoOrigine;
	}
	
	public void setAnnoAccertamentoOrigine(Integer annoAccertamentoOrigine) {
		this.annoAccertamentoOrigine = annoAccertamentoOrigine;
	}
	
	public Integer getAnnoAccertamentoRiaccertato() {
		return annoAccertamentoRiaccertato;
	}
	
	public void setAnnoAccertamentoRiaccertato(Integer annoAccertamentoRiaccertato) {
		this.annoAccertamentoRiaccertato = annoAccertamentoRiaccertato;
	}
	
	public BigDecimal getDisponibilitaIncassare() {
		return disponibilitaIncassare;
	}
	
	public void setDisponibilitaIncassare(BigDecimal disponibilitaIncassare) {
		this.disponibilitaIncassare = disponibilitaIncassare;
	}
	
	public Integer getNumAccertamentoOrigine() {
		return numAccertamentoOrigine;
	}
	
	public void setNumAccertamentoOrigine(Integer numAccertamentoOrigine) {
		this.numAccertamentoOrigine = numAccertamentoOrigine;
	}
	
	public Integer getNumAccertamentoRiaccertato() {
		return numAccertamentoRiaccertato;
	}
	
	public void setNumAccertamentoRiaccertato(Integer numAccertamentoRiaccertato) {
		this.numAccertamentoRiaccertato = numAccertamentoRiaccertato;
	}
	
	public Integer getNumeroAccertamento() {
		return numeroAccertamento;
	}
	
	public void setNumeroAccertamento(Integer numeroAccertamento) {
		this.numeroAccertamento = numeroAccertamento;
	}
	
	public List<ContabiliaSubAccertamento> getSubAccertamenti() {
		return subAccertamenti;
	}
	
	public void setSubAccertamenti(List<ContabiliaSubAccertamento> subAccertamenti) {
		this.subAccertamenti = subAccertamenti;
	}
	
	@Override
	public String toString() {
		return "ContabiliaAccertamento [annoAccertamento=" + annoAccertamento + ", annoAccertamentoOrigine="
				+ annoAccertamentoOrigine + ", annoAccertamentoRiaccertato=" + annoAccertamentoRiaccertato
				+ ", disponibilitaIncassare=" + disponibilitaIncassare + ", numAccertamentoOrigine="
				+ numAccertamentoOrigine + ", numAccertamentoRiaccertato=" + numAccertamentoRiaccertato
				+ ", numeroAccertamento=" + numeroAccertamento + ", subAccertamenti=" + subAccertamenti + "]";
	}
	
}
