/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaImpegno extends ContabiliaMovimentoGestione implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer annoImpegno;
	private Integer annoImpegnoOrigine;
	private Integer annoImpegnoRiaccertato;
	private BigDecimal disponibilitaLiquidare;
	private Integer numImpegnoOrigine;
	private Integer numImpegnoRiaccertato;
	private Integer numeroImpegno;
	private List<ContabiliaSubImpegno> subImpegni;
	private String tipoImpegno;
	
	public Integer getAnnoImpegno() {
		return annoImpegno;
	}
	
	public void setAnnoImpegno(Integer annoImpegno) {
		this.annoImpegno = annoImpegno;
	}
	
	public Integer getAnnoImpegnoOrigine() {
		return annoImpegnoOrigine;
	}
	
	public void setAnnoImpegnoOrigine(Integer annoImpegnoOrigine) {
		this.annoImpegnoOrigine = annoImpegnoOrigine;
	}
	
	public Integer getAnnoImpegnoRiaccertato() {
		return annoImpegnoRiaccertato;
	}
	
	public void setAnnoImpegnoRiaccertato(Integer annoImpegnoRiaccertato) {
		this.annoImpegnoRiaccertato = annoImpegnoRiaccertato;
	}
	
	public BigDecimal getDisponibilitaLiquidare() {
		return disponibilitaLiquidare;
	}
	
	public void setDisponibilitaLiquidare(BigDecimal disponibilitaLiquidare) {
		this.disponibilitaLiquidare = disponibilitaLiquidare;
	}
	
	public Integer getNumImpegnoOrigine() {
		return numImpegnoOrigine;
	}
	
	public void setNumImpegnoOrigine(Integer numImpegnoOrigine) {
		this.numImpegnoOrigine = numImpegnoOrigine;
	}
	
	public Integer getNumImpegnoRiaccertato() {
		return numImpegnoRiaccertato;
	}
	
	public void setNumImpegnoRiaccertato(Integer numImpegnoRiaccertato) {
		this.numImpegnoRiaccertato = numImpegnoRiaccertato;
	}
	
	public Integer getNumeroImpegno() {
		return numeroImpegno;
	}
	
	public void setNumeroImpegno(Integer numeroImpegno) {
		this.numeroImpegno = numeroImpegno;
	}
	
	public List<ContabiliaSubImpegno> getSubImpegni() {
		return subImpegni;
	}
	
	public void setSubImpegni(List<ContabiliaSubImpegno> subImpegni) {
		this.subImpegni = subImpegni;
	}
	
	public String getTipoImpegno() {
		return tipoImpegno;
	}
	
	public void setTipoImpegno(String tipoImpegno) {
		this.tipoImpegno = tipoImpegno;
	}
	
	@Override
	public String toString() {
		return "ContabiliaImpegno [annoImpegno=" + annoImpegno + ", annoImpegnoOrigine=" + annoImpegnoOrigine
				+ ", annoImpegnoRiaccertato=" + annoImpegnoRiaccertato + ", disponibilitaLiquidare="
				+ disponibilitaLiquidare + ", numImpegnoOrigine=" + numImpegnoOrigine + ", numImpegnoRiaccertato="
				+ numImpegnoRiaccertato + ", numeroImpegno=" + numeroImpegno + ", subImpegni=" + subImpegni
				+ ", tipoImpegno=" + tipoImpegno + "]";
	}
	
}
