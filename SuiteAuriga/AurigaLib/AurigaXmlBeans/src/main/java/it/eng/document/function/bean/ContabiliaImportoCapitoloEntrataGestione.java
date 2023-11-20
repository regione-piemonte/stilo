/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaImportoCapitoloEntrataGestione extends ContabiliaImporto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal disponibilitaAccertare;
	private BigDecimal stanziamento;
	private BigDecimal stanziamentoCassa;
	private BigDecimal stanziamentoResiduo;
	
	public BigDecimal getDisponibilitaAccertare() {
		return disponibilitaAccertare;
	}
	
	public void setDisponibilitaAccertare(BigDecimal disponibilitaAccertare) {
		this.disponibilitaAccertare = disponibilitaAccertare;
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
		return "ContabiliaImportoCapitoloEntrataGestione [disponibilitaAccertare=" + disponibilitaAccertare
				+ ", stanziamento=" + stanziamento + ", stanziamentoCassa=" + stanziamentoCassa
				+ ", stanziamentoResiduo=" + stanziamentoResiduo + "]";
	}
	
}
