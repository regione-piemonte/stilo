/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkBmanagerGetfeedbackelabdocslistBean")
public class DmpkBmanagerGetfeedbackelabdocslistBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_BMANAGER_GETFEEDBACKELABDOCSLIST";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String useridapplin;
	private java.lang.String passwordappin;
	private java.lang.String tiporichiestain;
	private java.lang.String idlottoin;
	private java.lang.String xmlagglottiout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getUseridapplin(){return useridapplin;}
    public java.lang.String getPasswordappin(){return passwordappin;}
    public java.lang.String getTiporichiestain(){return tiporichiestain;}
    public java.lang.String getIdlottoin(){return idlottoin;}
    public java.lang.String getXmlagglottiout(){return xmlagglottiout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setUseridapplin(java.lang.String value){this.useridapplin=value;}
    public void setPasswordappin(java.lang.String value){this.passwordappin=value;}
    public void setTiporichiestain(java.lang.String value){this.tiporichiestain=value;}
    public void setIdlottoin(java.lang.String value){this.idlottoin=value;}
    public void setXmlagglottiout(java.lang.String value){this.xmlagglottiout=value;}
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