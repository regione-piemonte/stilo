/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWfTestisattivita_byidBean")
public class DmpkWfTestisattivita_byidBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WF_TESTISATTIVITA_BYID";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String activitynamein;
	private java.lang.String citypeflussowfin;
	private java.util.Date tsbeginflussoin;
	private java.lang.String displaynameattout;
	private java.lang.Integer flgconesitiout;
	private java.lang.String variabileesitoout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getActivitynamein(){return activitynamein;}
    public java.lang.String getCitypeflussowfin(){return citypeflussowfin;}
    public java.util.Date getTsbeginflussoin(){return tsbeginflussoin;}
    public java.lang.String getDisplaynameattout(){return displaynameattout;}
    public java.lang.Integer getFlgconesitiout(){return flgconesitiout;}
    public java.lang.String getVariabileesitoout(){return variabileesitoout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setActivitynamein(java.lang.String value){this.activitynamein=value;}
    public void setCitypeflussowfin(java.lang.String value){this.citypeflussowfin=value;}
    public void setTsbeginflussoin(java.util.Date value){this.tsbeginflussoin=value;}
    public void setDisplaynameattout(java.lang.String value){this.displaynameattout=value;}
    public void setFlgconesitiout(java.lang.Integer value){this.flgconesitiout=value;}
    public void setVariabileesitoout(java.lang.String value){this.variabileesitoout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    