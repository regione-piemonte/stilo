/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmoInfoStoricoGetattrvaluesBean")
public class DmoInfoStoricoGetattrvaluesBean extends StoreBean implements Serializable{

	private static final String storeName = "DMO_INFO_STORICO_GETATTRVALUES";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Object self;
	private java.lang.String attrnamein;
	private java.lang.String valfmtin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.Object getSelf(){return self;}
    public java.lang.String getAttrnamein(){return attrnamein;}
    public java.lang.String getValfmtin(){return valfmtin;}
    
    public void setSelf(java.lang.Object value){this.self=value;}
    public void setAttrnamein(java.lang.String value){this.attrnamein=value;}
    public void setValfmtin(java.lang.String value){this.valfmtin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    