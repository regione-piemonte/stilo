/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCredentialVaultPolicyfncxauthBean")
public class DmpkCredentialVaultPolicyfncxauthBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CREDENTIAL_VAULT_POLICYFNCXAUTH";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String schema_name;
	private java.lang.String object_name;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getSchema_name(){return schema_name;}
    public java.lang.String getObject_name(){return object_name;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setSchema_name(java.lang.String value){this.schema_name=value;}
    public void setObject_name(java.lang.String value){this.object_name=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    