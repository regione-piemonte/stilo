/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRepositoryGuiGetobjrecentlyaccessedBean")
public class DmpkRepositoryGuiGetobjrecentlyaccessedBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_REPOSITORY_GUI_GETOBJRECENTLYACCESSED";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String cookiein;
	private java.lang.String usernamein;
	private java.lang.String flgtyobjin;
	private java.lang.String flgaccessoinlavorazionein;
	private java.lang.Integer nromaxoggettiin;
	private java.lang.String listaxmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getCookiein(){return cookiein;}
    public java.lang.String getUsernamein(){return usernamein;}
    public java.lang.String getFlgtyobjin(){return flgtyobjin;}
    public java.lang.String getFlgaccessoinlavorazionein(){return flgaccessoinlavorazionein;}
    public java.lang.Integer getNromaxoggettiin(){return nromaxoggettiin;}
    public java.lang.String getListaxmlout(){return listaxmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setCookiein(java.lang.String value){this.cookiein=value;}
    public void setUsernamein(java.lang.String value){this.usernamein=value;}
    public void setFlgtyobjin(java.lang.String value){this.flgtyobjin=value;}
    public void setFlgaccessoinlavorazionein(java.lang.String value){this.flgaccessoinlavorazionein=value;}
    public void setNromaxoggettiin(java.lang.Integer value){this.nromaxoggettiin=value;}
    public void setListaxmlout(java.lang.String value){this.listaxmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    