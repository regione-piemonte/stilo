/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkEfactGetconnectiontokensocietaBean")
public class DmpkEfactGetconnectiontokensocietaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_EFACT_GETCONNECTIONTOKENSOCIETA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String idciapplicazionein;
	private java.lang.String idciistapplicazionein;
	private java.lang.String useridin;
	private java.lang.String passwordin;
	private java.lang.String connectiontokenout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getIdciapplicazionein(){return idciapplicazionein;}
    public java.lang.String getIdciistapplicazionein(){return idciistapplicazionein;}
    public java.lang.String getUseridin(){return useridin;}
    public java.lang.String getPasswordin(){return passwordin;}
    public java.lang.String getConnectiontokenout(){return connectiontokenout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdciapplicazionein(java.lang.String value){this.idciapplicazionein=value;}
    public void setIdciistapplicazionein(java.lang.String value){this.idciistapplicazionein=value;}
    public void setUseridin(java.lang.String value){this.useridin=value;}
    public void setPasswordin(java.lang.String value){this.passwordin=value;}
    public void setConnectiontokenout(java.lang.String value){this.connectiontokenout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    