/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaImportoCapitoloUscitaGestione extends ContabiliaImporto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal disponibilitaImpegnare;
	private BigDecimal stanziamento;
	private BigDecimal stanziamentoCassa;
	private BigDecimal stanziamentoResiduo;
	
	public BigDecimal getDisponibilitaImpegnare() {
		return disponibilitaImpegnare;
	}
	
	public void setDisponibilitaImpegnare(BigDecimal disponibilitaImpegnare) {
		this.disponibilitaImpegnare = disponibilitaImpegnare;
	}
	
	public BigDecimal getStanziamento() {
		return stanziamento;
	}
	
	public void setStanziamento(BigDecimal stanziamento) {
		this.stanziamento = stanziamento;
	}
	
	public BigDecimal getStanziamentoCassa() {
		return stanziamentoCassa;
	}
	
	public void setStanziamentoCassa(BigDecimal stanziamentoCassa) {
		this.stanziamentoCassa = stanziamentoCassa;
	}
	
	public BigDecimal getStanziamentoResiduo() {
		return stanziamentoResiduo;
	}
	
	public void setStanziamentoResiduo(BigDecimal stanziamentoResiduo) {
		this.stanziamentoResiduo = stanziamentoResiduo;
	}
	
	@Override
	public String toString() {
		return "ContabiliaImportoCapitoloUscitaGestione [disponibilitaImpegnare=" + disponibilitaImpegnare
				+ ", stanziamento=" + stanziamento + ", stanziamentoCassa=" + stanziamentoCassa
				+ ", stanziamentoResiduo=" + stanziamentoResiduo + "]";
	}
	
}
