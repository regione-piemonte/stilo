/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaVincoloImpegnoStilo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer annoAccertamento;
	private BigDecimal importo;
	private BigDecimal numeroAccertamento;
	private String tipo;
	
	public Integer getAnnoAccertamento() {
		return annoAccertamento;
	}
	
	public void setAnnoAccertamento(Integer annoAccertamento) {
		this.annoAccertamento = annoAccertamento;
	}
	
	public BigDecimal getImporto() {
		return importo;
	}
	
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	
	public BigDecimal getNumeroAccertamento() {
		return numeroAccertamento;
	}
	
	public void setNumeroAccertamento(BigDecimal numeroAccertamento) {
		this.numeroAccertamento = numeroAccertamento;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public String toString() {
		return "ContabiliaVincoloImpegnoStilo [annoAccertamento=" + annoAccertamento + ", importo=" + importo
				+ ", numeroAccertamento=" + numeroAccertamento + ", tipo=" + tipo + "]";
	}
	
}
