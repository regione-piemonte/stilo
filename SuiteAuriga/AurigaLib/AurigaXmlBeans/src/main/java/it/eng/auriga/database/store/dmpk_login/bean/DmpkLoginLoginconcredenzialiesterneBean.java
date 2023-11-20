/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkLoginLoginconcredenzialiesterneBean")
public class DmpkLoginLoginconcredenzialiesterneBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_LOGIN_LOGINCONCREDENZIALIESTERNE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codapplicazionein;
	private java.lang.String codistanzaapplin;
	private java.lang.String usernamein;
	private java.lang.String passwordin;
	private java.lang.String codidconnectiontokenout;
	private java.lang.String desuserout;
	private java.math.BigDecimal iddominioout;
	private java.lang.String desdominioout;
	private java.lang.String parametriconfigout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgnoctrlpasswordin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodapplicazionein(){return codapplicazionein;}
    public java.lang.String getCodistanzaapplin(){return codistanzaapplin;}
    public java.lang.String getUsernamein(){return usernamein;}
    public java.lang.String getPasswordin(){return passwordin;}
    public java.lang.String getCodidconnectiontokenout(){return codidconnectiontokenout;}
    public java.lang.String getDesuserout(){return desuserout;}
    public java.math.BigDecimal getIddominioout(){return iddominioout;}
    public java.lang.String getDesdominioout(){return desdominioout;}
    public java.lang.String getParametriconfigout(){return parametriconfigout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgnoctrlpasswordin(){return flgnoctrlpasswordin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodapplicazionein(java.lang.String value){this.codapplicazionein=value;}
    public void setCodistanzaapplin(java.lang.String value){this.codistanzaapplin=value;}
    public void setUsernamein(java.lang.String value){this.usernamein=value;}
    public void setPasswordin(java.lang.String value){this.passwordin=value;}
    public void setCodidconnectiontokenout(java.lang.String value){this.codidconnectiontokenout=value;}
    public void setDesuserout(java.lang.String value){this.desuserout=value;}
    public void setIddominioout(java.math.BigDecimal value){this.iddominioout=value;}
    public void setDesdominioout(java.lang.String value){this.desdominioout=value;}
    public void setParametriconfigout(java.lang.String value){this.parametriconfigout=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgnoctrlpasswordin(java.lang.Integer value){this.flgnoctrlpasswordin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    