/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaSubAccertamento extends ContabiliaMovimentoGestione implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer annoSubAccertamento;
	private BigDecimal disponibilitaIncassare;
	private Integer numeroSubAccertamento;
	
	public Integer getAnnoSubAccertamento() {
		return annoSubAccertamento;
	}
	
	public void setAnnoSubAccertamento(Integer annoSubAccertamento) {
		this.annoSubAccertamento = annoSubAccertamento;
	}
	
	public BigDecimal getDisponibilitaIncassare() {
		return disponibilitaIncassare;
	}
	
	public void setDisponibilitaIncassare(BigDecimal disponibilitaIncassare) {
		this.disponibilitaIncassare = disponibilitaIncassare;
	}
	
	public Integer getNumeroSubAccertamento() {
		return numeroSubAccertamento;
	}
	
	public void setNumeroSubAccertamento(Integer numeroSubAccertamento) {
		this.numeroSubAccertamento = numeroSubAccertamento;
	}
	
	@Override
	public String toString() {
		return "ContabiliaSubAccertamento [annoSubAccertamento=" + annoSubAccertamento + ", disponibilitaIncassare="
				+ disponibilitaIncassare + ", numeroSubAccertamento=" + numeroSubAccertamento + "]";
	}
	
}
