/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCredentialVaultGetconntokenBean")
public class DmpkCredentialVaultGetconntokenBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CREDENTIAL_VAULT_GETCONNTOKEN";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String usernamein;
	private java.lang.String passwordin;
	private java.math.BigDecimal iddominioautin;
	private java.lang.String codapplicazioneestin;
	private java.lang.String codistanzaapplestin;
	private java.lang.String codidconnectiontokenout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getUsernamein(){return usernamein;}
    public java.lang.String getPasswordin(){return passwordin;}
    public java.math.BigDecimal getIddominioautin(){return iddominioautin;}
    public java.lang.String getCodapplicazioneestin(){return codapplicazioneestin;}
    public java.lang.String getCodistanzaapplestin(){return codistanzaapplestin;}
    public java.lang.String getCodidconnectiontokenout(){return codidconnectiontokenout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setUsernamein(java.lang.String value){this.usernamein=value;}
    public void setPasswordin(java.lang.String value){this.passwordin=value;}
    public void setIddominioautin(java.math.BigDecimal value){this.iddominioautin=value;}
    public void setCodapplicazioneestin(java.lang.String value){this.codapplicazioneestin=value;}
    public void setCodistanzaapplestin(java.lang.String value){this.codistanzaapplestin=value;}
    public void setCodidconnectiontokenout(java.lang.String value){this.codidconnectiontokenout=value;}
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