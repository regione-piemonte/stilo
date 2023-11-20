/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class WSNotificaDocFolderBean implements Serializable {

	
    private List listaDestinatari;    
    private String flgTypeObjToNotifOut;
    private BigDecimal idObjToNotifOut;
    
    private String recipientsXml;
    
    private String senderType;
	private BigDecimal idSender;
	private String motivoNotifica; 
	private String codMotivoNotifica; 
	private String messaggioNotifica; 
	private BigDecimal livelloPriorita; 
	private Integer richConfermaPresaVis; 
	private Integer FlgEmailNotifPresaVis; 
	private String IndEmailNotifPresaVis; 
	private Integer NotNoPresaVisEntroGG; 
	private Integer FlgEmailNoPresaVis; 
	private String IndEmailNoPresaVis; 		
	private String tsDecorrenzaNotifica; 
	private Integer flgNotificaEmailNotif; 
	private String indXNotifEmailNotif; 
	
	private String mittenteEmail;
	private String oggettoEmail; 
	private String bodyEmail; 
	private Integer flgNotificaSMSNotif; 
	private String nriCellXNotifSMSNotif; 
	private String testoSMS;
	
	private String URIXml;
	
	
	
	public List getListaDestinatari() {
		return listaDestinatari;
	}
	public void setListaDestinatari(List listaDestinatari) {
		this.listaDestinatari = listaDestinatari;
	}
	public String getFlgTypeObjToNotifOut() {
		return flgTypeObjToNotifOut;
	}
	public void setFlgTypeObjToNotifOut(String flgTypeObjToNotifOut) {
		this.flgTypeObjToNotifOut = flgTypeObjToNotifOut;
	}
	
	
	public String getMotivoNotifica() {
		return motivoNotifica;
	}
	public void setMotivoNotifica(String motivoNotifica) {
		this.motivoNotifica = motivoNotifica;
	}
	public String getCodMotivoNotifica() {
		return codMotivoNotifica;
	}
	public void setCodMotivoNotifica(String codMotivoNotifica) {
		this.codMotivoNotifica = codMotivoNotifica;
	}
	public String getMessaggioNotifica() {
		return messaggioNotifica;
	}
	public void setMessaggioNotifica(String messaggioNotifica) {
		this.messaggioNotifica = messaggioNotifica;
	}
	
	
	
	public String getIndEmailNotifPresaVis() {
		return IndEmailNotifPresaVis;
	}
	public void setIndEmailNotifPresaVis(String indEmailNotifPresaVis) {
		IndEmailNotifPresaVis = indEmailNotifPresaVis;
	}
	
	public String getIndEmailNoPresaVis() {
		return IndEmailNoPresaVis;
	}
	public void setIndEmailNoPresaVis(String indEmailNoPresaVis) {
		IndEmailNoPresaVis = indEmailNoPresaVis;
	}
	public String getTsDecorrenzaNotifica() {
		return tsDecorrenzaNotifica;
	}
	public void setTsDecorrenzaNotifica(String tsDecorrenzaNotifica) {
		this.tsDecorrenzaNotifica = tsDecorrenzaNotifica;
	}
	public String getIndXNotifEmailNotif() {
		return indXNotifEmailNotif;
	}
	public void setIndXNotifEmailNotif(String indXNotifEmailNotif) {
		this.indXNotifEmailNotif = indXNotifEmailNotif;
	}
	public String getOggettoEmail() {
		return oggettoEmail;
	}
	public void setOggettoEmail(String oggettoEmail) {
		this.oggettoEmail = oggettoEmail;
	}
	public String getBodyEmail() {
		return bodyEmail;
	}
	public void setBodyEmail(String bodyEmail) {
		this.bodyEmail = bodyEmail;
	}
	public String getNriCellXNotifSMSNotif() {
		return nriCellXNotifSMSNotif;
	}
	public void setNriCellXNotifSMSNotif(String nriCellXNotifSMSNotif) {
		this.nriCellXNotifSMSNotif = nriCellXNotifSMSNotif;
	}
	public String getTestoSMS() {
		return testoSMS;
	}
	public void setTestoSMS(String testoSMS) {
		this.testoSMS = testoSMS;
	}
	public BigDecimal getIdObjToNotifOut() {
		return idObjToNotifOut;
	}
	public void setIdObjToNotifOut(BigDecimal idObjToNotifOut) {
		this.idObjToNotifOut = idObjToNotifOut;
	}
	public BigDecimal getIdSender() {
		return idSender;
	}
	public void setIdSender(BigDecimal idSender) {
		this.idSender = idSender;
	}
	public BigDecimal getLivelloPriorita() {
		return livelloPriorita;
	}
	public void setLivelloPriorita(BigDecimal livelloPriorita) {
		this.livelloPriorita = livelloPriorita;
	}
	public Integer getRichConfermaPresaVis() {
		return richConfermaPresaVis;
	}
	public void setRichConfermaPresaVis(Integer richConfermaPresaVis) {
		this.richConfermaPresaVis = richConfermaPresaVis;
	}
	public Integer getFlgEmailNotifPresaVis() {
		return FlgEmailNotifPresaVis;
	}
	public void setFlgEmailNotifPresaVis(Integer flgEmailNotifPresaVis) {
		FlgEmailNotifPresaVis = flgEmailNotifPresaVis;
	}
	public Integer getNotNoPresaVisEntroGG() {
		return NotNoPresaVisEntroGG;
	}
	public void setNotNoPresaVisEntroGG(Integer notNoPresaVisEntroGG) {
		NotNoPresaVisEntroGG = notNoPresaVisEntroGG;
	}
	public Integer getFlgEmailNoPresaVis() {
		return FlgEmailNoPresaVis;
	}
	public void setFlgEmailNoPresaVis(Integer flgEmailNoPresaVis) {
		FlgEmailNoPresaVis = flgEmailNoPresaVis;
	}
	public Integer getFlgNotificaSMSNotif() {
		return flgNotificaSMSNotif;
	}
	public void setFlgNotificaSMSNotif(Integer flgNotificaSMSNotif) {
		this.flgNotificaSMSNotif = flgNotificaSMSNotif;
	}
	public String getSenderType() {
		return senderType;
	}
	public void setSenderType(String senderType) {
		this.senderType = senderType;
	}
	public Integer getFlgNotificaEmailNotif() {
		return flgNotificaEmailNotif;
	}
	public void setFlgNotificaEmailNotif(Integer flgNotificaEmailNotif) {
		this.flgNotificaEmailNotif = flgNotificaEmailNotif;
	}
	public String getRecipientsXml() {
		return recipientsXml;
	}
	public void setRecipientsXml(String recipientsXml) {
		this.recipientsXml = recipientsXml;
	}
	public String getURIXml() {
		return URIXml;
	}
	public void setURIXml(String uRIXml) {
		URIXml = uRIXml;
	}
	public String getMittenteEmail() {
		return mittenteEmail;
	}
	public void setMittenteEmail(String mittenteEmail) {
		this.mittenteEmail = mittenteEmail;
	}
	
}
