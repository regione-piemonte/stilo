/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntMgoEmailGetricevuteBean")
public class DmpkIntMgoEmailGetricevuteBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_MGO_EMAIL_GETRICEVUTE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String idemailin;
	private java.math.BigDecimal idudin;
	private java.lang.String categoriaregin;
	private java.lang.String siglaregistroin;
	private java.math.BigDecimal annoregin;
	private java.math.BigDecimal numeroregin;
	private java.math.BigDecimal idsoggrubricain;
	private java.lang.String indirizzoemaildestin;
	private java.lang.String listaxmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getIdemailin(){return idemailin;}
    public java.math.BigDecimal getIdudin(){return idudin;}
    public java.lang.String getCategoriaregin(){return categoriaregin;}
    public java.lang.String getSiglaregistroin(){return siglaregistroin;}
    public java.math.BigDecimal getAnnoregin(){return annoregin;}
    public java.math.BigDecimal getNumeroregin(){return numeroregin;}
    public java.math.BigDecimal getIdsoggrubricain(){return idsoggrubricain;}
    public java.lang.String getIndirizzoemaildestin(){return indirizzoemaildestin;}
    public java.lang.String getListaxmlout(){return listaxmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdemailin(java.lang.String value){this.idemailin=value;}
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    public void setCategoriaregin(java.lang.String value){this.categoriaregin=value;}
    public void setSiglaregistroin(java.lang.String value){this.siglaregistroin=value;}
    public void setAnnoregin(java.math.BigDecimal value){this.annoregin=value;}
    public void setNumeroregin(java.math.BigDecimal value){this.numeroregin=value;}
    public void setIdsoggrubricain(java.math.BigDecimal value){this.idsoggrubricain=value;}
    public void setIndirizzoemaildestin(java.lang.String value){this.indirizzoemaildestin=value;}
    public void setListaxmlout(java.lang.String value){this.listaxmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    