/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCore2CheckoutdocBean")
public class DmpkCore2CheckoutdocBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_2_CHECKOUTDOC";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal iddocin;
	private java.math.BigDecimal nroprogrverout;
	private java.lang.String displayfilenameverout;
	private java.lang.String uriverout;
	private java.lang.String improntaverout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIddocin(){return iddocin;}
    public java.math.BigDecimal getNroprogrverout(){return nroprogrverout;}
    public java.lang.String getDisplayfilenameverout(){return displayfilenameverout;}
    public java.lang.String getUriverout(){return uriverout;}
    public java.lang.String getImprontaverout(){return improntaverout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIddocin(java.math.BigDecimal value){this.iddocin=value;}
    public void setNroprogrverout(java.math.BigDecimal value){this.nroprogrverout=value;}
    public void setDisplayfilenameverout(java.lang.String value){this.displayfilenameverout=value;}
    public void setUriverout(java.lang.String value){this.uriverout=value;}
    public void setImprontaverout(java.lang.String value){this.improntaverout=value;}
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