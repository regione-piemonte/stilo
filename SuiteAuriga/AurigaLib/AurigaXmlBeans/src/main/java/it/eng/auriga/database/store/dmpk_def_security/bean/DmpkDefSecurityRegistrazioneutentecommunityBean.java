/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityRegistrazioneutentecommunityBean")
public class DmpkDefSecurityRegistrazioneutentecommunityBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_REGISTRAZIONEUTENTECOMMUNITY";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String desuserin;
	private java.lang.String emailin;
	private java.lang.String usernamein;
	private java.lang.String passwordin;
	private java.lang.String confermapasswordin;
	private java.math.BigDecimal iduserout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getDesuserin(){return desuserin;}
    public java.lang.String getEmailin(){return emailin;}
    public java.lang.String getUsernamein(){return usernamein;}
    public java.lang.String getPasswordin(){return passwordin;}
    public java.lang.String getConfermapasswordin(){return confermapasswordin;}
    public java.math.BigDecimal getIduserout(){return iduserout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setDesuserin(java.lang.String value){this.desuserin=value;}
    public void setEmailin(java.lang.String value){this.emailin=value;}
    public void setUsernamein(java.lang.String value){this.usernamein=value;}
    public void setPasswordin(java.lang.String value){this.passwordin=value;}
    public void setConfermapasswordin(java.lang.String value){this.confermapasswordin=value;}
    public void setIduserout(java.math.BigDecimal value){this.iduserout=value;}
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