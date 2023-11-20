/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityTestspecchrBean")
public class DmpkUtilityTestspecchrBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_TESTSPECCHR";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String strin;
	private java.lang.String speccharout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getStrin(){return strin;}
    public java.lang.String getSpeccharout(){return speccharout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setStrin(java.lang.String value){this.strin=value;}
    public void setSpeccharout(java.lang.String value){this.speccharout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    