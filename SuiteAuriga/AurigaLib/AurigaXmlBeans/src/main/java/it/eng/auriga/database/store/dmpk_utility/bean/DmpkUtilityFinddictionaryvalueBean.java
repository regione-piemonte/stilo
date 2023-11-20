/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFinddictionaryvalueBean")
public class DmpkUtilityFinddictionaryvalueBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDDICTIONARYVALUE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String targettablenamein;
	private java.lang.String targetcolnamein;
	private java.lang.String codvalueio;
	private java.lang.String valueio;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String tsrifin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getTargettablenamein(){return targettablenamein;}
    public java.lang.String getTargetcolnamein(){return targetcolnamein;}
    public java.lang.String getCodvalueio(){return codvalueio;}
    public java.lang.String getValueio(){return valueio;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getTsrifin(){return tsrifin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setTargettablenamein(java.lang.String value){this.targettablenamein=value;}
    public void setTargetcolnamein(java.lang.String value){this.targetcolnamein=value;}
    public void setCodvalueio(java.lang.String value){this.codvalueio=value;}
    public void setValueio(java.lang.String value){this.valueio=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    