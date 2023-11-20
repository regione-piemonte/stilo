/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilitySplitrigaxmlstdinvarrayBean")
public class DmpkUtilitySplitrigaxmlstdinvarrayBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_SPLITRIGAXMLSTDINVARRAY";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String rigaxmlstdin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getRigaxmlstdin(){return rigaxmlstdin;}
    
    public void setRigaxmlstdin(java.lang.String value){this.rigaxmlstdin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    