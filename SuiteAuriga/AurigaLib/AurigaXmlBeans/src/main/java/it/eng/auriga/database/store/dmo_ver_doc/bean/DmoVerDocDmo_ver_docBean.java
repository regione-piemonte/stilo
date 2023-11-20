/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmoVerDocDmo_ver_docBean")
public class DmoVerDocDmo_ver_docBean extends StoreBean implements Serializable{

	private static final String storeName = "DMO_VER_DOC_DMO_VER_DOC";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Object self;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.Object getSelf(){return self;}
    
    public void setSelf(java.lang.Object value){this.self=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    