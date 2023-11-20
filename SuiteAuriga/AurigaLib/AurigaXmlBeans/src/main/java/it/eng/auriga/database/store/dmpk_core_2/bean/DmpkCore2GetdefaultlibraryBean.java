/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCore2GetdefaultlibraryBean")
public class DmpkCore2GetdefaultlibraryBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_2_GETDEFAULTLIBRARY";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iduserloggedin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer flgtpdominioautin;
	private java.math.BigDecimal iddominioautin;
	private java.lang.String codapplesternain;
	private java.lang.String codistanzaapplestin;
	private java.lang.String accesstypein;
	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal idlibraryout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIduserloggedin(){return iduserloggedin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getFlgtpdominioautin(){return flgtpdominioautin;}
    public java.math.BigDecimal getIddominioautin(){return iddominioautin;}
    public java.lang.String getCodapplesternain(){return codapplesternain;}
    public java.lang.String getCodistanzaapplestin(){return codistanzaapplestin;}
    public java.lang.String getAccesstypein(){return accesstypein;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIdlibraryout(){return idlibraryout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIduserloggedin(java.math.BigDecimal value){this.iduserloggedin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgtpdominioautin(java.lang.Integer value){this.flgtpdominioautin=value;}
    public void setIddominioautin(java.math.BigDecimal value){this.iddominioautin=value;}
    public void setCodapplesternain(java.lang.String value){this.codapplesternain=value;}
    public void setCodistanzaapplestin(java.lang.String value){this.codistanzaapplestin=value;}
    public void setAccesstypein(java.lang.String value){this.accesstypein=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIdlibraryout(java.math.BigDecimal value){this.idlibraryout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    