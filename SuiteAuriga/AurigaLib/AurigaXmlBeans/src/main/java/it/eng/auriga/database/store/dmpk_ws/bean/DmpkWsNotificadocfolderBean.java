/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWsNotificadocfolderBean")
public class DmpkWsNotificadocfolderBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WS_NOTIFICADOCFOLDER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String xmlin;
	private java.lang.String flgtypeobjtonotifout;
	private java.math.BigDecimal idobjtonotifout;
	private java.lang.String recipientsxmlout;
	private java.lang.String sendertypeout;
	private java.math.BigDecimal senderidout;
	private java.lang.String codmotivonotifout;
	private java.lang.String motivonotifout;
	private java.lang.String messaggionotifout;
	private java.math.BigDecimal livelloprioritaout;
	private java.lang.Integer richconfermapresavisout;
	private java.lang.Integer flgemailnotifpresavisout;
	private java.lang.String indemailnotifpresavisout;
	private java.lang.Integer notnopresavisentroggout;
	private java.lang.Integer flgemailnopresavisout;
	private java.lang.String indemailnopresavisout;
	private java.lang.String tsdecorrenzanotifout;
	private java.lang.Integer flgnotificaemailnotifout;
	private java.lang.String indxnotifemailnotifout;
	private java.lang.String oggemailout;
	private java.lang.String bodyemailout;
	private java.lang.Integer flgnotificasmsnotifout;
	private java.lang.String nricellxnotifsmsnotifout;
	private java.lang.String testosmsout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getXmlin(){return xmlin;}
    public java.lang.String getFlgtypeobjtonotifout(){return flgtypeobjtonotifout;}
    public java.math.BigDecimal getIdobjtonotifout(){return idobjtonotifout;}
    public java.lang.String getRecipientsxmlout(){return recipientsxmlout;}
    public java.lang.String getSendertypeout(){return sendertypeout;}
    public java.math.BigDecimal getSenderidout(){return senderidout;}
    public java.lang.String getCodmotivonotifout(){return codmotivonotifout;}
    public java.lang.String getMotivonotifout(){return motivonotifout;}
    public java.lang.String getMessaggionotifout(){return messaggionotifout;}
    public java.math.BigDecimal getLivelloprioritaout(){return livelloprioritaout;}
    public java.lang.Integer getRichconfermapresavisout(){return richconfermapresavisout;}
    public java.lang.Integer getFlgemailnotifpresavisout(){return flgemailnotifpresavisout;}
    public java.lang.String getIndemailnotifpresavisout(){return indemailnotifpresavisout;}
    public java.lang.Integer getNotnopresavisentroggout(){return notnopresavisentroggout;}
    public java.lang.Integer getFlgemailnopresavisout(){return flgemailnopresavisout;}
    public java.lang.String getIndemailnopresavisout(){return indemailnopresavisout;}
    public java.lang.String getTsdecorrenzanotifout(){return tsdecorrenzanotifout;}
    public java.lang.Integer getFlgnotificaemailnotifout(){return flgnotificaemailnotifout;}
    public java.lang.String getIndxnotifemailnotifout(){return indxnotifemailnotifout;}
    public java.lang.String getOggemailout(){return oggemailout;}
    public java.lang.String getBodyemailout(){return bodyemailout;}
    public java.lang.Integer getFlgnotificasmsnotifout(){return flgnotificasmsnotifout;}
    public java.lang.String getNricellxnotifsmsnotifout(){return nricellxnotifsmsnotifout;}
    public java.lang.String getTestosmsout(){return testosmsout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setXmlin(java.lang.String value){this.xmlin=value;}
    public void setFlgtypeobjtonotifout(java.lang.String value){this.flgtypeobjtonotifout=value;}
    public void setIdobjtonotifout(java.math.BigDecimal value){this.idobjtonotifout=value;}
    public void setRecipientsxmlout(java.lang.String value){this.recipientsxmlout=value;}
    public void setSendertypeout(java.lang.String value){this.sendertypeout=value;}
    public void setSenderidout(java.math.BigDecimal value){this.senderidout=value;}
    public void setCodmotivonotifout(java.lang.String value){this.codmotivonotifout=value;}
    public void setMotivonotifout(java.lang.String value){this.motivonotifout=value;}
    public void setMessaggionotifout(java.lang.String value){this.messaggionotifout=value;}
    public void setLivelloprioritaout(java.math.BigDecimal value){this.livelloprioritaout=value;}
    public void setRichconfermapresavisout(java.lang.Integer value){this.richconfermapresavisout=value;}
    public void setFlgemailnotifpresavisout(java.lang.Integer value){this.flgemailnotifpresavisout=value;}
    public void setIndemailnotifpresavisout(java.lang.String value){this.indemailnotifpresavisout=value;}
    public void setNotnopresavisentroggout(java.lang.Integer value){this.notnopresavisentroggout=value;}
    public void setFlgemailnopresavisout(java.lang.Integer value){this.flgemailnopresavisout=value;}
    public void setIndemailnopresavisout(java.lang.String value){this.indemailnopresavisout=value;}
    public void setTsdecorrenzanotifout(java.lang.String value){this.tsdecorrenzanotifout=value;}
    public void setFlgnotificaemailnotifout(java.lang.Integer value){this.flgnotificaemailnotifout=value;}
    public void setIndxnotifemailnotifout(java.lang.String value){this.indxnotifemailnotifout=value;}
    public void setOggemailout(java.lang.String value){this.oggemailout=value;}
    public void setBodyemailout(java.lang.String value){this.bodyemailout=value;}
    public void setFlgnotificasmsnotifout(java.lang.Integer value){this.flgnotificasmsnotifout=value;}
    public void setNricellxnotifsmsnotifout(java.lang.String value){this.nricellxnotifsmsnotifout=value;}
    public void setTestosmsout(java.lang.String value){this.testosmsout=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    