/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.bean.StoreBean.StoreType;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWfGetprocdefidflussowfBean")
public class DmpkWfGetprocdefidflussowfBean implements Serializable{

	private static final String storeName = "DMPK_WF_GETPROCDEFIDFLUSSOWF";

	private static final long serialVersionUID = 1L;
	private String parametro_1;

	private java.lang.String ciflussowfin;
	public java.lang.String getParametro_1(){return parametro_1;}
    public java.lang.String getCiflussowfin(){return ciflussowfin;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=value;}
    public void setCiflussowfin(java.lang.String value){this.ciflussowfin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    