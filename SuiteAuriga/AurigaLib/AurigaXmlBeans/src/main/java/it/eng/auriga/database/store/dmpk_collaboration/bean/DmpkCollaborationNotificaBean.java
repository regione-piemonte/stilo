/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCollaborationNotificaBean")
public class DmpkCollaborationNotificaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_COLLABORATION_NOTIFICA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String flgtypeobjtonotifin;
	private java.math.BigDecimal idobjtonotifin;
	private java.lang.String recipientsxmlin;
	private java.lang.String sendertypein;
	private java.math.BigDecimal senderidin;
	private java.lang.String codmotivonotifin;
	private java.lang.String motivonotifin;
	private java.lang.String messaggionotifin;
	private java.math.BigDecimal livelloprioritain;
	private java.lang.Integer richconfermapresavisin;
	private java.lang.Integer flgemailnotifpresavisin;
	private java.lang.String indemailnotifpresavisin;
	private java.lang.Integer notnopresavisentroggin;
	private java.lang.Integer flgemailnopresavisin;
	private java.lang.String indemailnopresavisin;
	private java.lang.String tsdecorrenzanotifin;
	private java.lang.Integer flgnotificaemailnotifin;
	private java.lang.String indxnotifemailnotifio;
	private java.lang.String oggemailout;
	private java.lang.String bodyemailout;
	private java.lang.Integer flgnotificasmsnotifin;
	private java.lang.String nricellxnotifsmsnotifio;
	private java.lang.String testosmsout;
	private java.lang.String urixmlout;
	private java.lang.Integer flgignorewarningin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String warningmsgout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgcallbyguiin;
	private java.lang.Integer flginviofascicoloin;
	private java.lang.Integer flginvioudcollegatein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getFlgtypeobjtonotifin(){return flgtypeobjtonotifin;}
    public java.math.BigDecimal getIdobjtonotifin(){return idobjtonotifin;}
    public java.lang.String getRecipientsxmlin(){return recipientsxmlin;}
    public java.lang.String getSendertypein(){return sendertypein;}
    public java.math.BigDecimal getSenderidin(){return senderidin;}
    public java.lang.String getCodmotivonotifin(){return codmotivonotifin;}
    public java.lang.String getMotivonotifin(){return motivonotifin;}
    public java.lang.String getMessaggionotifin(){return messaggionotifin;}
    public java.math.BigDecimal getLivelloprioritain(){return livelloprioritain;}
    public java.lang.Integer getRichconfermapresavisin(){return richconfermapresavisin;}
    public java.lang.Integer getFlgemailnotifpresavisin(){return flgemailnotifpresavisin;}
    public java.lang.String getIndemailnotifpresavisin(){return indemailnotifpresavisin;}
    public java.lang.Integer getNotnopresavisentroggin(){return notnopresavisentroggin;}
    public java.lang.Integer getFlgemailnopresavisin(){return flgemailnopresavisin;}
    public java.lang.String getIndemailnopresavisin(){return indemailnopresavisin;}
    public java.lang.String getTsdecorrenzanotifin(){return tsdecorrenzanotifin;}
    public java.lang.Integer getFlgnotificaemailnotifin(){return flgnotificaemailnotifin;}
    public java.lang.String getIndxnotifemailnotifio(){return indxnotifemailnotifio;}
    public java.lang.String getOggemailout(){return oggemailout;}
    public java.lang.String getBodyemailout(){return bodyemailout;}
    public java.lang.Integer getFlgnotificasmsnotifin(){return flgnotificasmsnotifin;}
    public java.lang.String getNricellxnotifsmsnotifio(){return nricellxnotifsmsnotifio;}
    public java.lang.String getTestosmsout(){return testosmsout;}
    public java.lang.String getUrixmlout(){return urixmlout;}
    public java.lang.Integer getFlgignorewarningin(){return flgignorewarningin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgcallbyguiin(){return flgcallbyguiin;}
    public java.lang.Integer getFlginviofascicoloin(){return flginviofascicoloin;}
    public java.lang.Integer getFlginvioudcollegatein(){return flginvioudcollegatein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgtypeobjtonotifin(java.lang.String value){this.flgtypeobjtonotifin=value;}
    public void setIdobjtonotifin(java.math.BigDecimal value){this.idobjtonotifin=value;}
    public void setRecipientsxmlin(java.lang.String value){this.recipientsxmlin=value;}
    public void setSendertypein(java.lang.String value){this.sendertypein=value;}
    public void setSenderidin(java.math.BigDecimal value){this.senderidin=value;}
    public void setCodmotivonotifin(java.lang.String value){this.codmotivonotifin=value;}
    public void setMotivonotifin(java.lang.String value){this.motivonotifin=value;}
    public void setMessaggionotifin(java.lang.String value){this.messaggionotifin=value;}
    public void setLivelloprioritain(java.math.BigDecimal value){this.livelloprioritain=value;}
    public void setRichconfermapresavisin(java.lang.Integer value){this.richconfermapresavisin=value;}
    public void setFlgemailnotifpresavisin(java.lang.Integer value){this.flgemailnotifpresavisin=value;}
    public void setIndemailnotifpresavisin(java.lang.String value){this.indemailnotifpresavisin=value;}
    public void setNotnopresavisentroggin(java.lang.Integer value){this.notnopresavisentroggin=value;}
    public void setFlgemailnopresavisin(java.lang.Integer value){this.flgemailnopresavisin=value;}
    public void setIndemailnopresavisin(java.lang.String value){this.indemailnopresavisin=value;}
    public void setTsdecorrenzanotifin(java.lang.String value){this.tsdecorrenzanotifin=value;}
    public void setFlgnotificaemailnotifin(java.lang.Integer value){this.flgnotificaemailnotifin=value;}
    public void setIndxnotifemailnotifio(java.lang.String value){this.indxnotifemailnotifio=value;}
    public void setOggemailout(java.lang.String value){this.oggemailout=value;}
    public void setBodyemailout(java.lang.String value){this.bodyemailout=value;}
    public void setFlgnotificasmsnotifin(java.lang.Integer value){this.flgnotificasmsnotifin=value;}
    public void setNricellxnotifsmsnotifio(java.lang.String value){this.nricellxnotifsmsnotifio=value;}
    public void setTestosmsout(java.lang.String value){this.testosmsout=value;}
    public void setUrixmlout(java.lang.String value){this.urixmlout=value;}
    public void setFlgignorewarningin(java.lang.Integer value){this.flgignorewarningin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgcallbyguiin(java.lang.Integer value){this.flgcallbyguiin=value;}
    public void setFlginviofascicoloin(java.lang.Integer value){this.flginviofascicoloin=value;}
    public void setFlginvioudcollegatein(java.lang.Integer value){this.flginvioudcollegatein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    