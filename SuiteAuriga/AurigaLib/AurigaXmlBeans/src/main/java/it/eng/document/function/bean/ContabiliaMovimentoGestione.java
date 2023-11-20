/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaMovimentoGestione extends ContabiliaEntitaBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String cig;
	private String codiceSoggetto;
	private String cup;
	private BigDecimal importo;
	private Integer numeroArticolo;
	private Integer numeroCapitolo;
	private Integer numeroUEB;
	private Boolean parereFinanziario;
	private ContabiliaPianoDeiContiFinanziario pdc;
	private ContabiliaProvvedimento provvedimento;
	
	public String getCig() {
		return cig;
	}
	
	public void setCig(String cig) {
		this.cig = cig;
	}
	
	public String getCodiceSoggetto() {
		return codiceSoggetto;
	}
	
	public void setCodiceSoggetto(String codiceSoggetto) {
		this.codiceSoggetto = codiceSoggetto;
	}
	
	public String getCup() {
		return cup;
	}
	
	public void setCup(String cup) {
		this.cup = cup;
	}
	
	public BigDecimal getImporto() {
		return importo;
	}
	
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	
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
	
	public Boolean getParereFinanziario() {
		return parereFinanziario;
	}
	
	public void setParereFinanziario(Boolean parereFinanziario) {
		this.parereFinanziario = parereFinanziario;
	}
	
	public ContabiliaPianoDeiContiFinanziario getPdc() {
		return pdc;
	}
	
	public void setPdc(ContabiliaPianoDeiContiFinanziario pdc) {
		this.pdc = pdc;
	}
	
	public ContabiliaProvvedimento getProvvedimento() {
		return provvedimento;
	}
	
	public void setProvvedimento(ContabiliaProvvedimento provvedimento) {
		this.provvedimento = provvedimento;
	}
	
	@Override
	public String toString() {
		return "ContabiliaMovimentoGestione [cig=" + cig + ", codiceSoggetto=" + codiceSoggetto + ", cup=" + cup
				+ ", importo=" + importo + ", numeroArticolo=" + numeroArticolo + ", numeroCapitolo=" + numeroCapitolo
				+ ", numeroUEB=" + numeroUEB + ", parereFinanziario=" + parereFinanziario + ", pdc=" + pdc
				+ ", provvedimento=" + provvedimento + "]";
	}
	
}
