/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntMgoEmailPreparaemailnotassinvioccBean")
public class DmpkIntMgoEmailPreparaemailnotassinvioccBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_MGO_EMAIL_PREPARAEMAILNOTASSINVIOCC";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String assinvioccin;
	private java.lang.String udfolderxmlin;
	private java.lang.String destassinvioccxmlin;
	private java.lang.String codmotivoinvioin;
	private java.lang.String motivoinvioin;
	private java.lang.String messaggioinvioin;
	private java.math.BigDecimal livelloprioritain;
	private java.lang.String tsdecorrassinvioccin;
	private java.lang.Integer flgemailtosendout;
	private java.lang.String indirizzomittemailout;
	private java.lang.String indirizzidestemailout;
	private java.lang.String oggemailout;
	private java.lang.String bodyemailout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getAssinvioccin(){return assinvioccin;}
    public java.lang.String getUdfolderxmlin(){return udfolderxmlin;}
    public java.lang.String getDestassinvioccxmlin(){return destassinvioccxmlin;}
    public java.lang.String getCodmotivoinvioin(){return codmotivoinvioin;}
    public java.lang.String getMotivoinvioin(){return motivoinvioin;}
    public java.lang.String getMessaggioinvioin(){return messaggioinvioin;}
    public java.math.BigDecimal getLivelloprioritain(){return livelloprioritain;}
    public java.lang.String getTsdecorrassinvioccin(){return tsdecorrassinvioccin;}
    public java.lang.Integer getFlgemailtosendout(){return flgemailtosendout;}
    public java.lang.String getIndirizzomittemailout(){return indirizzomittemailout;}
    public java.lang.String getIndirizzidestemailout(){return indirizzidestemailout;}
    public java.lang.String getOggemailout(){return oggemailout;}
    public java.lang.String getBodyemailout(){return bodyemailout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setAssinvioccin(java.lang.String value){this.assinvioccin=value;}
    public void setUdfolderxmlin(java.lang.String value){this.udfolderxmlin=value;}
    public void setDestassinvioccxmlin(java.lang.String value){this.destassinvioccxmlin=value;}
    public void setCodmotivoinvioin(java.lang.String value){this.codmotivoinvioin=value;}
    public void setMotivoinvioin(java.lang.String value){this.motivoinvioin=value;}
    public void setMessaggioinvioin(java.lang.String value){this.messaggioinvioin=value;}
    public void setLivelloprioritain(java.math.BigDecimal value){this.livelloprioritain=value;}
    public void setTsdecorrassinvioccin(java.lang.String value){this.tsdecorrassinvioccin=value;}
    public void setFlgemailtosendout(java.lang.Integer value){this.flgemailtosendout=value;}
    public void setIndirizzomittemailout(java.lang.String value){this.indirizzomittemailout=value;}
    public void setIndirizzidestemailout(java.lang.String value){this.indirizzidestemailout=value;}
    public void setOggemailout(java.lang.String value){this.oggemailout=value;}
    public void setBodyemailout(java.lang.String value){this.bodyemailout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    