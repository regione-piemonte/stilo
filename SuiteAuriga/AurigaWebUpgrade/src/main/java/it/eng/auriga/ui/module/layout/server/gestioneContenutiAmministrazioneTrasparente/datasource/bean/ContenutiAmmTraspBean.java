/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DettColonnaAttributoListaBean;
import it.eng.auriga.ui.module.layout.server.contenutiAmministrazioneTrasparente.datasource.bean.ContenutiAmmTraspXmlBean;

public class ContenutiAmmTraspBean extends ContenutiAmmTraspXmlBean {

	private String flgPresenteHeader;
	private String flgPresenteRifNormativi;

	private String rifNormativiHtml;
	private String headerHtml;
	private String datiAggHtml;
	private Integer nroContenutiSezione;
	private Integer nroOrdineInSez;
	private String testoHtmlSezione;
	private String testoHtmlDettaglio;
	private Boolean flgTestiHtmlUguali;
	private Boolean flgMostraTastoExport;
	private Boolean flgMostraDatiAggiornamento;
	private Boolean flgPaginaDedicata;

	private String tipoRegNum;
	private String siglaRegNum;
	private String annoRegNum;
	private String nroRegNum;
	private String dataRegNum;
	private String nroAllegato;
	private String nroAllegatoHidden;

	private DatiFileDettContenutoSezXmlBean datiFilePrimario;
	private List<DatiFileDettContenutoSezXmlBean> datiFileAllegati;

	private BigDecimal nroRecPubblInTabella;
	private BigDecimal nroRecDaPubblicare;

	private String motivoCancellazione;

	private List<InfoStrutturaTabellaBean> infoStrutturaTabella;
	private List<DettColonnaAttributoListaBean> listaDettColonnaAttributo;
	private List<HashMap<String, Object>> valoriAttrLista;

	private String flgRecordFuoriPubbl;

	private String statoRichPubbl;
	private String motivoRigetto;
	private String flgAbilAuotorizzRich;
	private String statoAuotorizzRich;
	private String motivoRigettoAuotorizzRich;
	
	private List<FiltroContenutiTabella> listaFiltriContenutiTabella;
	
	private Integer nroRecTotali;
	
	public String getFlgPresenteHeader() {
		return flgPresenteHeader;
	}
	public void setFlgPresenteHeader(String flgPresenteHeader) {
		this.flgPresenteHeader = flgPresenteHeader;
	}
	public String getFlgPresenteRifNormativi() {
		return flgPresenteRifNormativi;
	}
	public void setFlgPresenteRifNormativi(String flgPresenteRifNormativi) {
		this.flgPresenteRifNormativi = flgPresenteRifNormativi;
	}
	public String getRifNormativiHtml() {
		return rifNormativiHtml;
	}
	public void setRifNormativiHtml(String rifNormativiHtml) {
		this.rifNormativiHtml = rifNormativiHtml;
	}
	public String getHeaderHtml() {
		return headerHtml;
	}
	public void setHeaderHtml(String headerHtml) {
		this.headerHtml = headerHtml;
	}
	public String getDatiAggHtml() {
		return datiAggHtml;
	}
	public void setDatiAggHtml(String datiAggHtml) {
		this.datiAggHtml = datiAggHtml;
	}
	public Integer getNroContenutiSezione() {
		return nroContenutiSezione;
	}
	public void setNroContenutiSezione(Integer nroContenutiSezione) {
		this.nroContenutiSezione = nroContenutiSezione;
	}
	public Integer getNroOrdineInSez() {
		return nroOrdineInSez;
	}
	public void setNroOrdineInSez(Integer nroOrdineInSez) {
		this.nroOrdineInSez = nroOrdineInSez;
	}
	public String getTestoHtmlSezione() {
		return testoHtmlSezione;
	}
	public void setTestoHtmlSezione(String testoHtmlSezione) {
		this.testoHtmlSezione = testoHtmlSezione;
	}
	public String getTestoHtmlDettaglio() {
		return testoHtmlDettaglio;
	}
	public void setTestoHtmlDettaglio(String testoHtmlDettaglio) {
		this.testoHtmlDettaglio = testoHtmlDettaglio;
	}
	public Boolean getFlgTestiHtmlUguali() {
		return flgTestiHtmlUguali;
	}
	public void setFlgTestiHtmlUguali(Boolean flgTestiHtmlUguali) {
		this.flgTestiHtmlUguali = flgTestiHtmlUguali;
	}
	public Boolean getFlgMostraTastoExport() {
		return flgMostraTastoExport;
	}
	public void setFlgMostraTastoExport(Boolean flgMostraTastoExport) {
		this.flgMostraTastoExport = flgMostraTastoExport;
	}
	public Boolean getFlgMostraDatiAggiornamento() {
		return flgMostraDatiAggiornamento;
	}
	public void setFlgMostraDatiAggiornamento(Boolean flgMostraDatiAggiornamento) {
		this.flgMostraDatiAggiornamento = flgMostraDatiAggiornamento;
	}
	public Boolean getFlgPaginaDedicata() {
		return flgPaginaDedicata;
	}
	public void setFlgPaginaDedicata(Boolean flgPaginaDedicata) {
		this.flgPaginaDedicata = flgPaginaDedicata;
	}
	public String getTipoRegNum() {
		return tipoRegNum;
	}
	public void setTipoRegNum(String tipoRegNum) {
		this.tipoRegNum = tipoRegNum;
	}
	public String getSiglaRegNum() {
		return siglaRegNum;
	}
	public void setSiglaRegNum(String siglaRegNum) {
		this.siglaRegNum = siglaRegNum;
	}
	public String getAnnoRegNum() {
		return annoRegNum;
	}
	public void setAnnoRegNum(String annoRegNum) {
		this.annoRegNum = annoRegNum;
	}
	public String getNroRegNum() {
		return nroRegNum;
	}
	public void setNroRegNum(String nroRegNum) {
		this.nroRegNum = nroRegNum;
	}
	public String getDataRegNum() {
		return dataRegNum;
	}
	public void setDataRegNum(String dataRegNum) {
		this.dataRegNum = dataRegNum;
	}
	public DatiFileDettContenutoSezXmlBean getDatiFilePrimario() {
		return datiFilePrimario;
	}
	public void setDatiFilePrimario(DatiFileDettContenutoSezXmlBean datiFilePrimario) {
		this.datiFilePrimario = datiFilePrimario;
	}
	public List<DatiFileDettContenutoSezXmlBean> getDatiFileAllegati() {
		return datiFileAllegati;
	}
	public void setDatiFileAllegati(List<DatiFileDettContenutoSezXmlBean> datiFileAllegati) {
		this.datiFileAllegati = datiFileAllegati;
	}
	public BigDecimal getNroRecPubblInTabella() {
		return nroRecPubblInTabella;
	}
	public void setNroRecPubblInTabella(BigDecimal nroRecPubblInTabella) {
		this.nroRecPubblInTabella = nroRecPubblInTabella;
	}
	public BigDecimal getNroRecDaPubblicare() {
		return nroRecDaPubblicare;
	}
	public void setNroRecDaPubblicare(BigDecimal nroRecDaPubblicare) {
		this.nroRecDaPubblicare = nroRecDaPubblicare;
	}
	public String getMotivoCancellazione() {
		return motivoCancellazione;
	}
	public void setMotivoCancellazione(String motivoCancellazione) {
		this.motivoCancellazione = motivoCancellazione;
	}
	public List<InfoStrutturaTabellaBean> getInfoStrutturaTabella() {
		return infoStrutturaTabella;
	}
	public void setInfoStrutturaTabella(List<InfoStrutturaTabellaBean> infoStrutturaTabella) {
		this.infoStrutturaTabella = infoStrutturaTabella;
	}
	public List<DettColonnaAttributoListaBean> getListaDettColonnaAttributo() {
		return listaDettColonnaAttributo;
	}
	public void setListaDettColonnaAttributo(List<DettColonnaAttributoListaBean> listaDettColonnaAttributo) {
		this.listaDettColonnaAttributo = listaDettColonnaAttributo;
	}
	public List<HashMap<String, Object>> getValoriAttrLista() {
		return valoriAttrLista;
	}
	public void setValoriAttrLista(List<HashMap<String, Object>> valoriAttrLista) {
		this.valoriAttrLista = valoriAttrLista;
	}
	public String getFlgRecordFuoriPubbl() {
		return flgRecordFuoriPubbl;
	}
	public void setFlgRecordFuoriPubbl(String flgRecordFuoriPubbl) {
		this.flgRecordFuoriPubbl = flgRecordFuoriPubbl;
	}
	public String getStatoRichPubbl() {
		return statoRichPubbl;
	}
	public void setStatoRichPubbl(String statoRichPubbl) {
		this.statoRichPubbl = statoRichPubbl;
	}
	public String getMotivoRigetto() {
		return motivoRigetto;
	}
	public void setMotivoRigetto(String motivoRigetto) {
		this.motivoRigetto = motivoRigetto;
	}
	public String getFlgAbilAuotorizzRich() {
		return flgAbilAuotorizzRich;
	}
	public void setFlgAbilAuotorizzRich(String flgAbilAuotorizzRich) {
		this.flgAbilAuotorizzRich = flgAbilAuotorizzRich;
	}
	public String getStatoAuotorizzRich() {
		return statoAuotorizzRich;
	}
	public void setStatoAuotorizzRich(String statoAuotorizzRich) {
		this.statoAuotorizzRich = statoAuotorizzRich;
	}
	public String getMotivoRigettoAuotorizzRich() {
		return motivoRigettoAuotorizzRich;
	}
	public void setMotivoRigettoAuotorizzRich(String motivoRigettoAuotorizzRich) {
		this.motivoRigettoAuotorizzRich = motivoRigettoAuotorizzRich;
	}
	public List<FiltroContenutiTabella> getListaFiltriContenutiTabella() {
		return listaFiltriContenutiTabella;
	}
	public void setListaFiltriContenutiTabella(List<FiltroContenutiTabella> listaFiltriContenutiTabella) {
		this.listaFiltriContenutiTabella = listaFiltriContenutiTabella;
	}
	public Integer getNroRecTotali() {
		return nroRecTotali;
	}
	public void setNroRecTotali(Integer nroRecTotali) {
		this.nroRecTotali = nroRecTotali;
	}
	public String getNroAllegato() {
		return nroAllegato;
	}
	public String getNroAllegatoHidden() {
		return nroAllegatoHidden;
	}
	public void setNroAllegato(String nroAllegato) {
		this.nroAllegato = nroAllegato;
	}
	public void setNroAllegatoHidden(String nroAllegatoHidden) {
		this.nroAllegatoHidden = nroAllegatoHidden;
	}	

}
