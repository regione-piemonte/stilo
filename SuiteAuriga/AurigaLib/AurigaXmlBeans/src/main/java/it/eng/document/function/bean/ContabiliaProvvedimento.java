/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaProvvedimento extends ContabiliaEntitaBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer annoProvvedimento;
	private String codiceTipoProvvedimento;
	private Integer numeroProvvedimento;
	private ContabiliaStrutturaAmministrativa sac;
	private String statoProvvedimento;
	
	public Integer getAnnoProvvedimento() {
		return annoProvvedimento;
	}
	
	public void setAnnoProvvedimento(Integer annoProvvedimento) {
		this.annoProvvedimento = annoProvvedimento;
	}
	
	public String getCodiceTipoProvvedimento() {
		return codiceTipoProvvedimento;
	}
	
	public void setCodiceTipoProvvedimento(String codiceTipoProvvedimento) {
		this.codiceTipoProvvedimento = codiceTipoProvvedimento;
	}
	
	public Integer getNumeroProvvedimento() {
		return numeroProvvedimento;
	}
	
	public void setNumeroProvvedimento(Integer numeroProvvedimento) {
		this.numeroProvvedimento = numeroProvvedimento;
	}
	
	public ContabiliaStrutturaAmministrativa getSac() {
		return sac;
	}
	
	public void setSac(ContabiliaStrutturaAmministrativa sac) {
		this.sac = sac;
	}
	
	public String getStatoProvvedimento() {
		return statoProvvedimento;
	}
	
	public void setStatoProvvedimento(String statoProvvedimento) {
		this.statoProvvedimento = statoProvvedimento;
	}
	
	@Override
	public String toString() {
		return "ContabiliaProvvedimento [annoProvvedimento=" + annoProvvedimento + ", codiceTipoProvvedimento="
				+ codiceTipoProvvedimento + ", numeroProvvedimento=" + numeroProvvedimento + ", sac=" + sac
				+ ", statoProvvedimento=" + statoProvvedimento + "]";
	}
	
}
