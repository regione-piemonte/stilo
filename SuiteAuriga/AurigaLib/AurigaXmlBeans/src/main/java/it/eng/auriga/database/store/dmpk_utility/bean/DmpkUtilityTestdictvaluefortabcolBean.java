/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityTestdictvaluefortabcolBean")
public class DmpkUtilityTestdictvaluefortabcolBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_TESTDICTVALUEFORTABCOL";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Integer flgtpdominioautin;
	private java.math.BigDecimal iddominioautin;
	private java.lang.String targettablenamein;
	private java.lang.String targetcolnamein;
	private java.lang.String rowidin;
	private java.lang.String tsrifin;
	private java.lang.String valuein;
	private java.lang.String valuegenvincoloin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.Integer getFlgtpdominioautin(){return flgtpdominioautin;}
    public java.math.BigDecimal getIddominioautin(){return iddominioautin;}
    public java.lang.String getTargettablenamein(){return targettablenamein;}
    public java.lang.String getTargetcolnamein(){return targetcolnamein;}
    public java.lang.String getRowidin(){return rowidin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    public java.lang.String getValuein(){return valuein;}
    public java.lang.String getValuegenvincoloin(){return valuegenvincoloin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setFlgtpdominioautin(java.lang.Integer value){this.flgtpdominioautin=value;}
    public void setIddominioautin(java.math.BigDecimal value){this.iddominioautin=value;}
    public void setTargettablenamein(java.lang.String value){this.targettablenamein=value;}
    public void setTargetcolnamein(java.lang.String value){this.targetcolnamein=value;}
    public void setRowidin(java.lang.String value){this.rowidin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    public void setValuein(java.lang.String value){this.valuein=value;}
    public void setValuegenvincoloin(java.lang.String value){this.valuegenvincoloin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    