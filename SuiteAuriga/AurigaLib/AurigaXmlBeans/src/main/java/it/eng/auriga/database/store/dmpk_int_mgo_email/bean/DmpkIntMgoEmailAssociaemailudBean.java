/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntMgoEmailAssociaemailudBean")
public class DmpkIntMgoEmailAssociaemailudBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_MGO_EMAIL_ASSOCIAEMAILUD";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String idemailin;
	private java.lang.String idudin;
	private java.lang.String categoriaregudin;
	private java.lang.String numregudin;
	private java.lang.String siglaregudin;
	private java.lang.String annoregudin;
	private java.lang.String tsregudin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getIdemailin(){return idemailin;}
    public java.lang.String getIdudin(){return idudin;}
    public java.lang.String getCategoriaregudin(){return categoriaregudin;}
    public java.lang.String getNumregudin(){return numregudin;}
    public java.lang.String getSiglaregudin(){return siglaregudin;}
    public java.lang.String getAnnoregudin(){return annoregudin;}
    public java.lang.String getTsregudin(){return tsregudin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdemailin(java.lang.String value){this.idemailin=value;}
    public void setIdudin(java.lang.String value){this.idudin=value;}
    public void setCategoriaregudin(java.lang.String value){this.categoriaregudin=value;}
    public void setNumregudin(java.lang.String value){this.numregudin=value;}
    public void setSiglaregudin(java.lang.String value){this.siglaregudin=value;}
    public void setAnnoregudin(java.lang.String value){this.annoregudin=value;}
    public void setTsregudin(java.lang.String value){this.tsregudin=value;}
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