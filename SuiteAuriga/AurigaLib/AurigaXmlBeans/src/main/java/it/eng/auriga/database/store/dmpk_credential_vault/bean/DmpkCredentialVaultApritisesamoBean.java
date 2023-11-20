/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCredentialVaultApritisesamoBean")
public class DmpkCredentialVaultApritisesamoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CREDENTIAL_VAULT_APRITISESAMO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String codsistemaesternoin;
	private java.lang.String codistsistemaesternoin;
	private java.lang.String sesamo1out;
	private java.lang.String sesamo2out;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getCodsistemaesternoin(){return codsistemaesternoin;}
    public java.lang.String getCodistsistemaesternoin(){return codistsistemaesternoin;}
    public java.lang.String getSesamo1out(){return sesamo1out;}
    public java.lang.String getSesamo2out(){return sesamo2out;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setCodsistemaesternoin(java.lang.String value){this.codsistemaesternoin=value;}
    public void setCodistsistemaesternoin(java.lang.String value){this.codistsistemaesternoin=value;}
    public void setSesamo1out(java.lang.String value){this.sesamo1out=value;}
    public void setSesamo2out(java.lang.String value){this.sesamo2out=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    