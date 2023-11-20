/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWsSendbyfaxBean")
public class DmpkWsSendbyfaxBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WS_SENDBYFAX";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String xmlin;
	private java.lang.String nrofaxdestinatarioout;
	private java.lang.String faxserveraddressout;
	private java.lang.String usernamefaxserverout;
	private java.lang.String passwordfaxserverout;
	private java.lang.String doctoextractout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getXmlin(){return xmlin;}
    public java.lang.String getNrofaxdestinatarioout(){return nrofaxdestinatarioout;}
    public java.lang.String getFaxserveraddressout(){return faxserveraddressout;}
    public java.lang.String getUsernamefaxserverout(){return usernamefaxserverout;}
    public java.lang.String getPasswordfaxserverout(){return passwordfaxserverout;}
    public java.lang.String getDoctoextractout(){return doctoextractout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setXmlin(java.lang.String value){this.xmlin=value;}
    public void setNrofaxdestinatarioout(java.lang.String value){this.nrofaxdestinatarioout=value;}
    public void setFaxserveraddressout(java.lang.String value){this.faxserveraddressout=value;}
    public void setUsernamefaxserverout(java.lang.String value){this.usernamefaxserverout=value;}
    public void setPasswordfaxserverout(java.lang.String value){this.passwordfaxserverout=value;}
    public void setDoctoextractout(java.lang.String value){this.doctoextractout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    