/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkLoginVerificacredenzialiBean")
public class DmpkLoginVerificacredenzialiBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_LOGIN_VERIFICACREDENZIALI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String usernamein;
	private java.lang.String passwordin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getUsernamein(){return usernamein;}
    public java.lang.String getPasswordin(){return passwordin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setUsernamein(java.lang.String value){this.usernamein=value;}
    public void setPasswordin(java.lang.String value){this.passwordin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    