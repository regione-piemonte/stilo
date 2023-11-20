/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

 
@XmlRootElement
public class NotificaSdIBean extends AbstractBean implements Serializable {      

	/**
	 * 
	 */
	private static final long serialVersionUID = 8476671280933149685L;
	
	private BigDecimal idMsgNotifica;
	private String tipoNotifica;
	private String nomeFileNotifica;
	private String messaggio;
	private BigDecimal idMsgTrasmesso;
	private String nomeFileTrasmesso;
	private BigDecimal idFileFattura;
	private String nomeFileFattura;
	private String nroFattura;
	private Short annoFattura;
	private BigDecimal posFattura;
	private Date tsIns;
	private Date tsUltimoAgg;
	private BigDecimal idDoc;
	private boolean flgCarVerAuriga;
	private Date tsCarAuriga;
	private Date tsErrCarAuriga;
	private String errMsgCarAuriga;	
	
	private Integer nroGiorni;
	
	public BigDecimal getIdMsgNotifica() {
		return idMsgNotifica;
	}
	public void setIdMsgNotifica(BigDecimal idMsgNotifica) {
		this.idMsgNotifica = idMsgNotifica;
		this.getUpdatedProperties().add("idMsgNotifica");
	}
	public String getTipoNotifica() {
		return tipoNotifica;
	}
	public void setTipoNotifica(String tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
		this.getUpdatedProperties().add("tipoNotifica");
	}
	public String getNomeFileNotifica() {
		return nomeFileNotifica;
	}
	public void setNomeFileNotifica(String nomeFileNotifica) {
		this.nomeFileNotifica = nomeFileNotifica;
		this.getUpdatedProperties().add("nomeFileNotifica");
	}
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
		this.getUpdatedProperties().add("messaggio");
	}
	public BigDecimal getIdMsgTrasmesso() {
		return idMsgTrasmesso;
	}
	public void setIdMsgTrasmesso(BigDecimal idMsgTrasmesso) {
		this.idMsgTrasmesso = idMsgTrasmesso;
		this.getUpdatedProperties().add("idMsgTrasmesso");
	}
	public String getNomeFileTrasmesso() {
		return nomeFileTrasmesso;
	}
	public void setNomeFileTrasmesso(String nomeFileTrasmesso) {
		this.nomeFileTrasmesso = nomeFileTrasmesso;
		this.getUpdatedProperties().add("nomeFileTrasmesso");
	}
	public BigDecimal getIdFileFattura() {
		return idFileFattura;
	}
	public void setIdFileFattura(BigDecimal idFileFattura) {
		this.idFileFattura = idFileFattura;
		this.getUpdatedProperties().add("idFileFattura");
	}
	public String getNomeFileFattura() {
		return nomeFileFattura;
	}
	public void setNomeFileFattura(String nomeFileFattura) {
		this.nomeFileFattura = nomeFileFattura;
		this.getUpdatedProperties().add("nomeFileFattura");
	}
	public String getNroFattura() {
		return nroFattura;
	}
	public void setNroFattura(String nroFattura) {
		this.nroFattura = nroFattura;
		this.getUpdatedProperties().add("nroFattura");
	}
	public Short getAnnoFattura() {
		return annoFattura;
	}
	public void setAnnoFattura(Short annoFattura) {
		this.annoFattura = annoFattura;
		this.getUpdatedProperties().add("annoFattura");
	}
	public BigDecimal getPosFattura() {
		return posFattura;
	}
	public void setPosFattura(BigDecimal posFattura) {
		this.posFattura = posFattura;
		this.getUpdatedProperties().add("posFattura");
	}
	public Date getTsIns() {
		return tsIns;
	}
	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
		this.getUpdatedProperties().add("tsIns");
	}
	public Date getTsUltimoAgg() {
		return tsUltimoAgg;
	}
	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
		this.getUpdatedProperties().add("tsUltimoAgg");
	}
	public BigDecimal getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
		this.getUpdatedProperties().add("idDoc");
	}
	public boolean isFlgCarVerAuriga() {
		return flgCarVerAuriga;
	}
	public void setFlgCarVerAuriga(boolean flgCarVerAuriga) {
		this.flgCarVerAuriga = flgCarVerAuriga;
		this.getUpdatedProperties().add("flgCarVerAuriga");
	}
	public Date getTsCarAuriga() {
		return tsCarAuriga;
	}
	public void setTsCarAuriga(Date tsCarAuriga) {
		this.tsCarAuriga = tsCarAuriga;
		this.getUpdatedProperties().add("tsCarAuriga");
	}
	public Date getTsErrCarAuriga() {
		return tsErrCarAuriga;
	}
	public void setTsErrCarAuriga(Date tsErrCarAuriga) {
		this.tsErrCarAuriga = tsErrCarAuriga;
		this.getUpdatedProperties().add("tsErrCarAuriga");
	}
	public String getErrMsgCarAuriga() {
		return errMsgCarAuriga;
	}
	public void setErrMsgCarAuriga(String errMsgCarAuriga) {
		this.errMsgCarAuriga = errMsgCarAuriga;
		this.getUpdatedProperties().add("errMsgCarAuriga");
	}
	public Integer getNroGiorni() {
		return nroGiorni;
	}
	public void setNroGiorni(Integer nroGiorni) {
		this.nroGiorni = nroGiorni;
		this.getUpdatedProperties().add("nroGiorni");
	}	
	
}   

