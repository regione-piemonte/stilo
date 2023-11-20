/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.TipoData;
import it.eng.document.XmlVariabile;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile.TipoVariabile;
import java.util.Date;
import java.util.List;

@XmlRootElement
public class CreaModDocBiAutoInBean extends CreaModDocumentoInBean {

	private static final long serialVersionUID = -6609881844566442763L;

	@XmlVariabile(nome = "TIPO_CONTRATTO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String tipoContratto;

    @XmlVariabile(nome = "TIPO_PAGAMENTO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String tipoPagamento;

    @XmlVariabile(nome = "TIPO_SERVIZIO_Doc", tipo = TipoVariabile.LISTA)
	private List<TipoServizioXmlBean> tipoServizio;

    
	@XmlVariabile(nome = "AZIENDA_PIVA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String pivaAzienda;

	@XmlVariabile(nome = "RESP_VENDITA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String responsabileVendita;

	@XmlVariabile(nome = "FLG_PERMUTA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgPermuta;

	@XmlVariabile(nome = "SOGGETTO_PIVA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String cfPivaSoggetto;

	@XmlVariabile(nome = "VEICOLO_BRAND_Doc", tipo = TipoVariabile.SEMPLICE)
	private String tipoBrand;
	
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome = "CONTRATTO_DATA_Doc", tipo = TipoVariabile.SEMPLICE)
	private Date dataContratto;
	
	@XmlVariabile(nome = "NRO_CONTRATTO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String numeroContratto;

	@XmlVariabile(nome = "DES_CUSTOMER_Doc", tipo = TipoVariabile.SEMPLICE)
	private String ragioneSocialeSoggetto;

	@XmlVariabile(nome = "NRO_ORDINE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String numeroOrdine;
	
	public String getTipoContratto() {
		return tipoContratto;
	}

	public void setTipoContratto(String tipoContratto) {
		this.tipoContratto = tipoContratto;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public String getPivaAzienda() {
		return pivaAzienda;
	}

	public void setPivaAzienda(String pivaAzienda) {
		this.pivaAzienda = pivaAzienda;
	}

	public String getResponsabileVendita() {
		return responsabileVendita;
	}

	public void setResponsabileVendita(String responsabileVendita) {
		this.responsabileVendita = responsabileVendita;
	}

	public String getFlgPermuta() {
		return flgPermuta;
	}

	public void setFlgPermuta(String flgPermuta) {
		this.flgPermuta = flgPermuta;
	}

	public String getCfPivaSoggetto() {
		return cfPivaSoggetto;
	}

	public void setCfPivaSoggetto(String cfPivaSoggetto) {
		this.cfPivaSoggetto = cfPivaSoggetto;
	}

	public String getNumeroContratto() {
		return numeroContratto;
	}

	public void setNumeroContratto(String numeroContratto) {
		this.numeroContratto = numeroContratto;
	}

	public Date getDataContratto() {
		return dataContratto;
	}

	public void setDataContratto(Date dataContratto) {
		this.dataContratto = dataContratto;
	}

	public String getRagioneSocialeSoggetto() {
		return ragioneSocialeSoggetto;
	}

	public void setRagioneSocialeSoggetto(String ragioneSocialeSoggetto) {
		this.ragioneSocialeSoggetto = ragioneSocialeSoggetto;
	}

	public List<TipoServizioXmlBean> getTipoServizio() {
		return tipoServizio;
	}

	public void setTipoServizio(List<TipoServizioXmlBean> tipoServizio) {
		this.tipoServizio = tipoServizio;
	}

	public String getNumeroOrdine() {
		return numeroOrdine;
	}

	public void setNumeroOrdine(String numeroOrdine) {
		this.numeroOrdine = numeroOrdine;
	}

	public String getTipoBrand() {
		return tipoBrand;
	}

	public void setTipoBrand(String tipoBrand) {
		this.tipoBrand = tipoBrand;
	}


	
}
