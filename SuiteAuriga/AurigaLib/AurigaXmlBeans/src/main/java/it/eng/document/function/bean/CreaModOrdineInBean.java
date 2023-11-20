/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreaModOrdineInBean extends CreaModDocumentoInBean {

	private static final long serialVersionUID = -6609881844566442763L;

	@XmlVariabile(nome = "ORD_ID_Doc", tipo = TipoVariabile.SEMPLICE)
	private String ordineNumeroDoc;
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome = "ORD_DATA_Doc", tipo = TipoVariabile.SEMPLICE)
	private Date ordineDataDoc;
	@XmlVariabile(nome = "ORD_DATA_SCADENZA_Doc", tipo = TipoVariabile.SEMPLICE)
	private Date ordineDataScadenzaDoc;
	@XmlVariabile(nome = "ORD_BUYER_DES_Doc", tipo = TipoVariabile.SEMPLICE)
	private String buyerDesDoc;
	@XmlVariabile(nome = "ORD_BUYER_CF_Doc", tipo = TipoVariabile.SEMPLICE)
	private String buyerCfDoc;
	@XmlVariabile(nome = "ORD_BUYER_ID_Doc", tipo = TipoVariabile.SEMPLICE)
	private String buyerIdDoc;
	@XmlVariabile(nome = "ORD_BUYER_ID_TYPE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String buyerIdTypeDoc;
	@XmlVariabile(nome = "ORD_SELLER_DES_Doc", tipo = TipoVariabile.SEMPLICE)
	private String sellerDesDoc;
	@XmlVariabile(nome = "ORD_SELLER_ID_Doc", tipo = TipoVariabile.SEMPLICE)
	private String sellerIdDoc;
	@XmlVariabile(nome = "ORD_SELLER_ID_TYPE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String sellerIdTypeDoc;
	@XmlVariabile(nome = "ORD_IMPORTO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String ordineImportoDoc;
	@XmlVariabile(nome = "ORD_DIVISA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String ordineDivisaDoc;
	@XmlVariabile(nome = "ORD_SENDER_Doc", tipo = TipoVariabile.SEMPLICE)
	private String ordineSenderDoc;
	@XmlVariabile(nome = "ORD_RECEIVER_Doc", tipo = TipoVariabile.SEMPLICE)
	private String ordineReceiverDoc;
	@XmlVariabile(nome="ORD_ID_TRASM_SDI_Doc", tipo = TipoVariabile.SEMPLICE)
	private String ordineIdSdiDoc;
	@XmlVariabile(nome = "FLG_RECAPITATO_Ud", tipo = TipoVariabile.SEMPLICE)
	private String flgRecapitatoDoc;
	@XmlVariabile(nome="ORD_ID_FILE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String idFileNSO;
	@XmlVariabile(nome="ORD_COD_INTERMEDIARIO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String ordineCodiceIntermediario;
	@XmlVariabile(nome = "ORD_ID_SUP_Doc", tipo = TipoVariabile.SEMPLICE)
	private String ordineSuperioreNumeroDoc;	
	
	public String getOrdineNumeroDoc() {
		return ordineNumeroDoc;
	}
	public void setOrdineNumeroDoc(String ordineNumeroDoc) {
		this.ordineNumeroDoc = ordineNumeroDoc;
	}
	public Date getOrdineDataDoc() {
		return ordineDataDoc;
	}
	public void setOrdineDataDoc(Date ordineDataDoc) {
		this.ordineDataDoc = ordineDataDoc;
	}
	public Date getOrdineDataScadenzaDoc() {
		return ordineDataScadenzaDoc;
	}
	public void setOrdineDataScadenzaDoc(Date ordineDataScadenzaDoc) {
		this.ordineDataScadenzaDoc = ordineDataScadenzaDoc;
	}
	public String getOrdineDivisaDoc() {
		return ordineDivisaDoc;
	}
	public void setOrdineDivisaDoc(String ordineDivisaDoc) {
		this.ordineDivisaDoc = ordineDivisaDoc;
	}
	public String getOrdineImportoDoc() {
		return ordineImportoDoc;
	}
	public void setOrdineImportoDoc(String ordineImportoDoc) {
		this.ordineImportoDoc = ordineImportoDoc;
	}
	public String getBuyerDesDoc() {
		return buyerDesDoc;
	}
	public void setBuyerDesDoc(String buyerDesDoc) {
		this.buyerDesDoc = buyerDesDoc;
	}
	public String getBuyerCfDoc() {
		return buyerCfDoc;
	}
	public void setBuyerCfDoc(String buyerCfDoc) {
		this.buyerCfDoc = buyerCfDoc;
	}
	public String getSellerDesDoc() {
		return sellerDesDoc;
	}
	public void setSellerDesDoc(String sellerDesDoc) {
		this.sellerDesDoc = sellerDesDoc;
	}
	public String getOrdineSenderDoc() {
		return ordineSenderDoc;
	}
	public void setOrdineSenderDoc(String ordineSenderDoc) {
		this.ordineSenderDoc = ordineSenderDoc;
	}
	public String getOrdineReceiverDoc() {
		return ordineReceiverDoc;
	}
	public void setOrdineReceiverDoc(String ordineReceiverDoc) {
		this.ordineReceiverDoc = ordineReceiverDoc;
	}
	public String getBuyerIdDoc() {
		return buyerIdDoc;
	}
	public void setBuyerIdDoc(String buyerIdDoc) {
		this.buyerIdDoc = buyerIdDoc;
	}
	public String getBuyerIdTypeDoc() {
		return buyerIdTypeDoc;
	}
	public void setBuyerIdTypeDoc(String buyerIdTypeDoc) {
		this.buyerIdTypeDoc = buyerIdTypeDoc;
	}
	public String getSellerIdDoc() {
		return sellerIdDoc;
	}
	public void setSellerIdDoc(String sellerIdDoc) {
		this.sellerIdDoc = sellerIdDoc;
	}
	public String getSellerIdTypeDoc() {
		return sellerIdTypeDoc;
	}
	public void setSellerIdTypeDoc(String sellerIdTypeDoc) {
		this.sellerIdTypeDoc = sellerIdTypeDoc;
	}
	public String getOrdineIdSdiDoc() {
		return ordineIdSdiDoc;
	}
	public void setOrdineIdSdiDoc(String ordineIdSdiDoc) {
		this.ordineIdSdiDoc = ordineIdSdiDoc;
	}
	public String getFlgRecapitatoDoc() {
		return flgRecapitatoDoc;
	}
	public void setFlgRecapitatoDoc(String flgRecapitatoDoc) {
		this.flgRecapitatoDoc = flgRecapitatoDoc;
	}
	public String getIdFileNSO() {
		return idFileNSO;
	}
	public void setIdFileNSO(String idFileNSO) {
		this.idFileNSO = idFileNSO;
	}
	public String getOrdineCodiceIntermediario() {
		return ordineCodiceIntermediario;
	}
	public void setOrdineCodiceIntermediario(String ordineCodiceIntermediario) {
		this.ordineCodiceIntermediario = ordineCodiceIntermediario;
	}
	public String getOrdineSuperioreNumeroDoc() {
		return ordineSuperioreNumeroDoc;
	}
	public void setOrdineSuperioreNumeroDoc(String ordineSuperioreNumeroDoc) {
		this.ordineSuperioreNumeroDoc = ordineSuperioreNumeroDoc;
	}

	
	

}
