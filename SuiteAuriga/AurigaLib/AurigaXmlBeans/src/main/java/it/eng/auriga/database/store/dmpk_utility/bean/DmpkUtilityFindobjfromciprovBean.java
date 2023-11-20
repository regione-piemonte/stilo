/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFindobjfromciprovBean")
public class DmpkUtilityFindobjfromciprovBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDOBJFROMCIPROV";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String targettablenamein;
	private java.lang.String ciprovobjio;
	private java.lang.String ciintobjio;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgrestrapplownerin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getTargettablenamein(){return targettablenamein;}
    public java.lang.String getCiprovobjio(){return ciprovobjio;}
    public java.lang.String getCiintobjio(){return ciintobjio;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgrestrapplownerin(){return flgrestrapplownerin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setTargettablenamein(java.lang.String value){this.targettablenamein=value;}
    public void setCiprovobjio(java.lang.String value){this.ciprovobjio=value;}
    public void setCiintobjio(java.lang.String value){this.ciintobjio=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgrestrapplownerin(java.lang.Integer value){this.flgrestrapplownerin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    