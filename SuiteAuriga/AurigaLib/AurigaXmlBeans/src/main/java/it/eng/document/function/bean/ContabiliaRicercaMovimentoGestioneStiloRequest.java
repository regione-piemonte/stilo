/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaRicercaMovimentoGestioneStiloRequest extends ContabiliaBaseRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer annoProvvedimento;
	private String codiceStruttura;
	private String codiceTipoProvvedimento;
	private String codiceTipoStruttura;
	private Integer numeroProvvedimento;
	private String idSpAoo;
	
	public Integer getAnnoProvvedimento() {
		return annoProvvedimento;
	}
	
	public void setAnnoProvvedimento(Integer annoProvvedimento) {
		this.annoProvvedimento = annoProvvedimento;
	}
	
	public String getCodiceStruttura() {
		return codiceStruttura;
	}
	
	public void setCodiceStruttura(String codiceStruttura) {
		this.codiceStruttura = codiceStruttura;
	}
	
	public String getCodiceTipoProvvedimento() {
		return codiceTipoProvvedimento;
	}
	
	public void setCodiceTipoProvvedimento(String codiceTipoProvvedimento) {
		this.codiceTipoProvvedimento = codiceTipoProvvedimento;
	}
	
	public String getCodiceTipoStruttura() {
		return codiceTipoStruttura;
	}
	
	public void setCodiceTipoStruttura(String codiceTipoStruttura) {
		this.codiceTipoStruttura = codiceTipoStruttura;
	}
	
	public Integer getNumeroProvvedimento() {
		return numeroProvvedimento;
	}
	
	public void setNumeroProvvedimento(Integer numeroProvvedimento) {
		this.numeroProvvedimento = numeroProvvedimento;
	}
	
	public String getIdSpAoo() {
		return idSpAoo;
	}
	
	public void setIdSpAoo(String idSpAoo) {
		this.idSpAoo = idSpAoo;
	}
	
	@Override
	public String toString() {
		return "ContabiliaRicercaMovimentoGestioneStiloRequest [annoProvvedimento=" + annoProvvedimento
				+ ", codiceStruttura=" + codiceStruttura + ", codiceTipoProvvedimento=" + codiceTipoProvvedimento
				+ ", codiceTipoStruttura=" + codiceTipoStruttura + ", numeroProvvedimento=" + numeroProvvedimento
				+ ", idSpAoo=" + idSpAoo + "]";
	}
	
}
