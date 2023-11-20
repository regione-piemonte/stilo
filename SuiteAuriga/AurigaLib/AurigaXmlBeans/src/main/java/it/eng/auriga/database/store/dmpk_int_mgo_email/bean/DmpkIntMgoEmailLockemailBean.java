/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntMgoEmailLockemailBean")
public class DmpkIntMgoEmailLockemailBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_MGO_EMAIL_LOCKEMAIL";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String iduserlockforin;
	private java.lang.Integer flglockimplin;
	private java.lang.String idrecdizoperlockin;
	private java.lang.String desoperlockin;
	private java.lang.String listaidemailin;
	private java.lang.String motiviin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String esitiout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.math.BigDecimal iduolavoroin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getIduserlockforin(){return iduserlockforin;}
    public java.lang.Integer getFlglockimplin(){return flglockimplin;}
    public java.lang.String getIdrecdizoperlockin(){return idrecdizoperlockin;}
    public java.lang.String getDesoperlockin(){return desoperlockin;}
    public java.lang.String getListaidemailin(){return listaidemailin;}
    public java.lang.String getMotiviin(){return motiviin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getEsitiout(){return esitiout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.math.BigDecimal getIduolavoroin(){return iduolavoroin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIduserlockforin(java.lang.String value){this.iduserlockforin=value;}
    public void setFlglockimplin(java.lang.Integer value){this.flglockimplin=value;}
    public void setIdrecdizoperlockin(java.lang.String value){this.idrecdizoperlockin=value;}
    public void setDesoperlockin(java.lang.String value){this.desoperlockin=value;}
    public void setListaidemailin(java.lang.String value){this.listaidemailin=value;}
    public void setMotiviin(java.lang.String value){this.motiviin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setEsitiout(java.lang.String value){this.esitiout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setIduolavoroin(java.math.BigDecimal value){this.iduolavoroin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    