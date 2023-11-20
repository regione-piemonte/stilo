/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGetstoricoattrnumberBean")
public class DmpkUtilityGetstoricoattrnumberBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETSTORICOATTRNUMBER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String tablenamein;
	private java.lang.String recrowidin;
	private java.lang.String attrnamein;
	private java.lang.Integer nrooccorrenzain;
	private java.lang.String tsrifin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getTablenamein(){return tablenamein;}
    public java.lang.String getRecrowidin(){return recrowidin;}
    public java.lang.String getAttrnamein(){return attrnamein;}
    public java.lang.Integer getNrooccorrenzain(){return nrooccorrenzain;}
    public java.lang.String getTsrifin(){return tsrifin;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setTablenamein(java.lang.String value){this.tablenamein=value;}
    public void setRecrowidin(java.lang.String value){this.recrowidin=value;}
    public void setAttrnamein(java.lang.String value){this.attrnamein=value;}
    public void setNrooccorrenzain(java.lang.Integer value){this.nrooccorrenzain=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    