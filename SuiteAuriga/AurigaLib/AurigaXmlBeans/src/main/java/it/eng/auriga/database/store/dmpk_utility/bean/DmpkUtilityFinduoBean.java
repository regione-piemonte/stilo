/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFinduoBean")
public class DmpkUtilityFinduoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDUO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.math.BigDecimal iduoin;
	private java.lang.String livellistrin;
	private java.lang.String desuoin;
	private java.lang.Integer flgsolovldin;
	private java.lang.String tsrifin;
	private java.lang.String codtipoin;
	private java.math.BigDecimal livellogerarchicoin;
	private java.lang.Integer flglivelliparzin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.math.BigDecimal getIduoin(){return iduoin;}
    public java.lang.String getLivellistrin(){return livellistrin;}
    public java.lang.String getDesuoin(){return desuoin;}
    public java.lang.Integer getFlgsolovldin(){return flgsolovldin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    public java.lang.String getCodtipoin(){return codtipoin;}
    public java.math.BigDecimal getLivellogerarchicoin(){return livellogerarchicoin;}
    public java.lang.Integer getFlglivelliparzin(){return flglivelliparzin;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setIduoin(java.math.BigDecimal value){this.iduoin=value;}
    public void setLivellistrin(java.lang.String value){this.livellistrin=value;}
    public void setDesuoin(java.lang.String value){this.desuoin=value;}
    public void setFlgsolovldin(java.lang.Integer value){this.flgsolovldin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    public void setCodtipoin(java.lang.String value){this.codtipoin=value;}
    public void setLivellogerarchicoin(java.math.BigDecimal value){this.livellogerarchicoin=value;}
    public void setFlglivelliparzin(java.lang.Integer value){this.flglivelliparzin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    