/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFindsoggettoinrubricaBean")
public class DmpkUtilityFindsoggettoinrubricaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDSOGGETTOINRUBRICA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioin;
	private java.lang.String datisoggxmlio;
	private java.lang.Integer flgsolovldin;
	private java.lang.String tsrifin;
	private java.lang.Integer flgcompletadatidarubrin;
	private java.math.BigDecimal idsogginrubricaout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String flginorganigrammain;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String finalitain;
	private java.math.BigDecimal idudin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.lang.String getDatisoggxmlio(){return datisoggxmlio;}
    public java.lang.Integer getFlgsolovldin(){return flgsolovldin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    public java.lang.Integer getFlgcompletadatidarubrin(){return flgcompletadatidarubrin;}
    public java.math.BigDecimal getIdsogginrubricaout(){return idsogginrubricaout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getFlginorganigrammain(){return flginorganigrammain;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getFinalitain(){return finalitain;}
    public java.math.BigDecimal getIdudin(){return idudin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setDatisoggxmlio(java.lang.String value){this.datisoggxmlio=value;}
    public void setFlgsolovldin(java.lang.Integer value){this.flgsolovldin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    public void setFlgcompletadatidarubrin(java.lang.Integer value){this.flgcompletadatidarubrin=value;}
    public void setIdsogginrubricaout(java.math.BigDecimal value){this.idsogginrubricaout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlginorganigrammain(java.lang.String value){this.flginorganigrammain=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFinalitain(java.lang.String value){this.finalitain=value;}
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    