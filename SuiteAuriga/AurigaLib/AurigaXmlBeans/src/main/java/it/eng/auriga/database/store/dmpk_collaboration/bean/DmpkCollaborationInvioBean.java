/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCollaborationInvioBean")
public class DmpkCollaborationInvioBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_COLLABORATION_INVIO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String flgtypeobjtosendin;
	private java.math.BigDecimal idobjtosendin;
	private java.lang.Integer flgsendcontenutiin;
	private java.lang.String recipientsxmlin;
	private java.lang.String sendertypein;
	private java.math.BigDecimal senderidin;
	private java.lang.String codmotivoinvioin;
	private java.lang.String motivoinvioin;
	private java.lang.String messaggioinvioin;
	private java.math.BigDecimal livelloprioritain;
	private java.lang.Integer richconfermapresavisin;
	private java.lang.Integer richconfermaaccettazin;
	private java.lang.String tsdecorrenzaassegnazin;
	private java.lang.Integer flgnotificaemailinvioin;
	private java.lang.String indxnotifemailinvioio;
	private java.lang.String oggemailout;
	private java.lang.String bodyemailout;
	private java.lang.Integer flgnotificasmsinvioin;
	private java.lang.String nricellxnotifsmsinvioio;
	private java.lang.String testosmsout;
	private java.lang.String altrenotificherichxmlin;
	private java.lang.String copieudinviatexmlout;
	private java.lang.String urixmlout;
	private java.lang.Integer flgignorewarningin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String warningmsgout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgcallbyguiin;
	private java.lang.Integer flgmantienicopiaudin;
	private java.lang.Integer flgforceinvioin;
	private java.lang.Integer flgskipctrlcartaceoin;
	private java.lang.Integer flginviofascicoloin;
	private java.lang.Integer flginvioudcollegatein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getFlgtypeobjtosendin(){return flgtypeobjtosendin;}
    public java.math.BigDecimal getIdobjtosendin(){return idobjtosendin;}
    public java.lang.Integer getFlgsendcontenutiin(){return flgsendcontenutiin;}
    public java.lang.String getRecipientsxmlin(){return recipientsxmlin;}
    public java.lang.String getSendertypein(){return sendertypein;}
    public java.math.BigDecimal getSenderidin(){return senderidin;}
    public java.lang.String getCodmotivoinvioin(){return codmotivoinvioin;}
    public java.lang.String getMotivoinvioin(){return motivoinvioin;}
    public java.lang.String getMessaggioinvioin(){return messaggioinvioin;}
    public java.math.BigDecimal getLivelloprioritain(){return livelloprioritain;}
    public java.lang.Integer getRichconfermapresavisin(){return richconfermapresavisin;}
    public java.lang.Integer getRichconfermaaccettazin(){return richconfermaaccettazin;}
    public java.lang.String getTsdecorrenzaassegnazin(){return tsdecorrenzaassegnazin;}
    public java.lang.Integer getFlgnotificaemailinvioin(){return flgnotificaemailinvioin;}
    public java.lang.String getIndxnotifemailinvioio(){return indxnotifemailinvioio;}
    public java.lang.String getOggemailout(){return oggemailout;}
    public java.lang.String getBodyemailout(){return bodyemailout;}
    public java.lang.Integer getFlgnotificasmsinvioin(){return flgnotificasmsinvioin;}
    public java.lang.String getNricellxnotifsmsinvioio(){return nricellxnotifsmsinvioio;}
    public java.lang.String getTestosmsout(){return testosmsout;}
    public java.lang.String getAltrenotificherichxmlin(){return altrenotificherichxmlin;}
    public java.lang.String getCopieudinviatexmlout(){return copieudinviatexmlout;}
    public java.lang.String getUrixmlout(){return urixmlout;}
    public java.lang.Integer getFlgignorewarningin(){return flgignorewarningin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgcallbyguiin(){return flgcallbyguiin;}
    public java.lang.Integer getFlgmantienicopiaudin(){return flgmantienicopiaudin;}
    public java.lang.Integer getFlgforceinvioin(){return flgforceinvioin;}
    public java.lang.Integer getFlgskipctrlcartaceoin(){return flgskipctrlcartaceoin;}
    public java.lang.Integer getFlginviofascicoloin(){return flginviofascicoloin;}
    public java.lang.Integer getFlginvioudcollegatein(){return flginvioudcollegatein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgtypeobjtosendin(java.lang.String value){this.flgtypeobjtosendin=value;}
    public void setIdobjtosendin(java.math.BigDecimal value){this.idobjtosendin=value;}
    public void setFlgsendcontenutiin(java.lang.Integer value){this.flgsendcontenutiin=value;}
    public void setRecipientsxmlin(java.lang.String value){this.recipientsxmlin=value;}
    public void setSendertypein(java.lang.String value){this.sendertypein=value;}
    public void setSenderidin(java.math.BigDecimal value){this.senderidin=value;}
    public void setCodmotivoinvioin(java.lang.String value){this.codmotivoinvioin=value;}
    public void setMotivoinvioin(java.lang.String value){this.motivoinvioin=value;}
    public void setMessaggioinvioin(java.lang.String value){this.messaggioinvioin=value;}
    public void setLivelloprioritain(java.math.BigDecimal value){this.livelloprioritain=value;}
    public void setRichconfermapresavisin(java.lang.Integer value){this.richconfermapresavisin=value;}
    public void setRichconfermaaccettazin(java.lang.Integer value){this.richconfermaaccettazin=value;}
    public void setTsdecorrenzaassegnazin(java.lang.String value){this.tsdecorrenzaassegnazin=value;}
    public void setFlgnotificaemailinvioin(java.lang.Integer value){this.flgnotificaemailinvioin=value;}
    public void setIndxnotifemailinvioio(java.lang.String value){this.indxnotifemailinvioio=value;}
    public void setOggemailout(java.lang.String value){this.oggemailout=value;}
    public void setBodyemailout(java.lang.String value){this.bodyemailout=value;}
    public void setFlgnotificasmsinvioin(java.lang.Integer value){this.flgnotificasmsinvioin=value;}
    public void setNricellxnotifsmsinvioio(java.lang.String value){this.nricellxnotifsmsinvioio=value;}
    public void setTestosmsout(java.lang.String value){this.testosmsout=value;}
    public void setAltrenotificherichxmlin(java.lang.String value){this.altrenotificherichxmlin=value;}
    public void setCopieudinviatexmlout(java.lang.String value){this.copieudinviatexmlout=value;}
    public void setUrixmlout(java.lang.String value){this.urixmlout=value;}
    public void setFlgignorewarningin(java.lang.Integer value){this.flgignorewarningin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgcallbyguiin(java.lang.Integer value){this.flgcallbyguiin=value;}
    public void setFlgmantienicopiaudin(java.lang.Integer value){this.flgmantienicopiaudin=value;}
    public void setFlgforceinvioin(java.lang.Integer value){this.flgforceinvioin=value;}
    public void setFlgskipctrlcartaceoin(java.lang.Integer value){this.flgskipctrlcartaceoin=value;}
    public void setFlginviofascicoloin(java.lang.Integer value){this.flginviofascicoloin=value;}
    public void setFlginvioudcollegatein(java.lang.Integer value){this.flginvioudcollegatein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    