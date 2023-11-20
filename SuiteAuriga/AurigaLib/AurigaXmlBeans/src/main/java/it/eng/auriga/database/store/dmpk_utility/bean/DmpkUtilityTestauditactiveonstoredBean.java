/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityTestauditactiveonstoredBean")
public class DmpkUtilityTestauditactiveonstoredBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_TESTAUDITACTIVEONSTORED";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String packagenamein;
	private java.lang.String procfuncnamein;
	private java.lang.String auditingcondin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getPackagenamein(){return packagenamein;}
    public java.lang.String getProcfuncnamein(){return procfuncnamein;}
    public java.lang.String getAuditingcondin(){return auditingcondin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setPackagenamein(java.lang.String value){this.packagenamein=value;}
    public void setProcfuncnamein(java.lang.String value){this.procfuncnamein=value;}
    public void setAuditingcondin(java.lang.String value){this.auditingcondin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    