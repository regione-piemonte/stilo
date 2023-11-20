/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityAccreditauserinapplestBean")
public class DmpkDefSecurityAccreditauserinapplestBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_ACCREDITAUSERINAPPLEST";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal iduserin;
	private java.lang.String usernamein;
	private java.lang.String desuserin;
	private java.lang.String codapplicazioneesternain;
	private java.lang.String codistapplicazioneesternain;
	private java.lang.String usernameinapplestin;
	private java.lang.String passwordinapplestin;
	private java.lang.String ciuserinapplestin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.lang.String getUsernamein(){return usernamein;}
    public java.lang.String getDesuserin(){return desuserin;}
    public java.lang.String getCodapplicazioneesternain(){return codapplicazioneesternain;}
    public java.lang.String getCodistapplicazioneesternain(){return codistapplicazioneesternain;}
    public java.lang.String getUsernameinapplestin(){return usernameinapplestin;}
    public java.lang.String getPasswordinapplestin(){return passwordinapplestin;}
    public java.lang.String getCiuserinapplestin(){return ciuserinapplestin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setUsernamein(java.lang.String value){this.usernamein=value;}
    public void setDesuserin(java.lang.String value){this.desuserin=value;}
    public void setCodapplicazioneesternain(java.lang.String value){this.codapplicazioneesternain=value;}
    public void setCodistapplicazioneesternain(java.lang.String value){this.codistapplicazioneesternain=value;}
    public void setUsernameinapplestin(java.lang.String value){this.usernameinapplestin=value;}
    public void setPasswordinapplestin(java.lang.String value){this.passwordinapplestin=value;}
    public void setCiuserinapplestin(java.lang.String value){this.ciuserinapplestin=value;}
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