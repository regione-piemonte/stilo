/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFindscrivaniavirtBean")
public class DmpkUtilityFindscrivaniavirtBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDSCRIVANIAVIRT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.math.BigDecimal idscrivaniain;
	private java.lang.String intestazionescrivin;
	private java.math.BigDecimal iduoin;
	private java.lang.String livellistrin;
	private java.lang.String desuoin;
	private java.math.BigDecimal iduserin;
	private java.lang.String desuserin;
	private java.math.BigDecimal idruoloin;
	private java.lang.String desruolouserin;
	private java.lang.Integer flgsolovldin;
	private java.lang.String tsrifin;
	private java.lang.Integer flglivelliparzin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.math.BigDecimal getIdscrivaniain(){return idscrivaniain;}
    public java.lang.String getIntestazionescrivin(){return intestazionescrivin;}
    public java.math.BigDecimal getIduoin(){return iduoin;}
    public java.lang.String getLivellistrin(){return livellistrin;}
    public java.lang.String getDesuoin(){return desuoin;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.lang.String getDesuserin(){return desuserin;}
    public java.math.BigDecimal getIdruoloin(){return idruoloin;}
    public java.lang.String getDesruolouserin(){return desruolouserin;}
    public java.lang.Integer getFlgsolovldin(){return flgsolovldin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    public java.lang.Integer getFlglivelliparzin(){return flglivelliparzin;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setIdscrivaniain(java.math.BigDecimal value){this.idscrivaniain=value;}
    public void setIntestazionescrivin(java.lang.String value){this.intestazionescrivin=value;}
    public void setIduoin(java.math.BigDecimal value){this.iduoin=value;}
    public void setLivellistrin(java.lang.String value){this.livellistrin=value;}
    public void setDesuoin(java.lang.String value){this.desuoin=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setDesuserin(java.lang.String value){this.desuserin=value;}
    public void setIdruoloin(java.math.BigDecimal value){this.idruoloin=value;}
    public void setDesruolouserin(java.lang.String value){this.desruolouserin=value;}
    public void setFlgsolovldin(java.lang.Integer value){this.flgsolovldin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    public void setFlglivelliparzin(java.lang.Integer value){this.flglivelliparzin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    