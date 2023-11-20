/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntMgoEmailSetsoggconprofilivscasellaBean")
public class DmpkIntMgoEmailSetsoggconprofilivscasellaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_MGO_EMAIL_SETSOGGCONPROFILIVSCASELLA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String idcasellain;
	private java.lang.String idfruitoreenteaooin;
	private java.lang.Integer identeaooin;
	private java.lang.String desenteaooin;
	private java.lang.String xmlfruitoriprofin;
	private java.lang.String xmlutentiprofin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getIdcasellain(){return idcasellain;}
    public java.lang.String getIdfruitoreenteaooin(){return idfruitoreenteaooin;}
    public java.lang.Integer getIdenteaooin(){return identeaooin;}
    public java.lang.String getDesenteaooin(){return desenteaooin;}
    public java.lang.String getXmlfruitoriprofin(){return xmlfruitoriprofin;}
    public java.lang.String getXmlutentiprofin(){return xmlutentiprofin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdcasellain(java.lang.String value){this.idcasellain=value;}
    public void setIdfruitoreenteaooin(java.lang.String value){this.idfruitoreenteaooin=value;}
    public void setIdenteaooin(java.lang.Integer value){this.identeaooin=value;}
    public void setDesenteaooin(java.lang.String value){this.desenteaooin=value;}
    public void setXmlfruitoriprofin(java.lang.String value){this.xmlfruitoriprofin=value;}
    public void setXmlutentiprofin(java.lang.String value){this.xmlutentiprofin=value;}
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