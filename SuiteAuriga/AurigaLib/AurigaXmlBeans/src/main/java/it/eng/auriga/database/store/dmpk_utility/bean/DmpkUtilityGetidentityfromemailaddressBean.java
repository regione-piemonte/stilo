/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGetidentityfromemailaddressBean")
public class DmpkUtilityGetidentityfromemailaddressBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETIDENTITYFROMEMAILADDRESS";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String emailaddressin;
	private java.lang.Integer flgsolointerniin;
	private java.lang.String flgtipoout;
	private java.math.BigDecimal idsoggout;
	private java.lang.Integer flagfisicaout;
	private java.lang.String denominazione_cognomeout;
	private java.lang.String nomeout;
	private java.lang.String codfiscaleout;
	private java.lang.String partitaivaout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getEmailaddressin(){return emailaddressin;}
    public java.lang.Integer getFlgsolointerniin(){return flgsolointerniin;}
    public java.lang.String getFlgtipoout(){return flgtipoout;}
    public java.math.BigDecimal getIdsoggout(){return idsoggout;}
    public java.lang.Integer getFlagfisicaout(){return flagfisicaout;}
    public java.lang.String getDenominazione_cognomeout(){return denominazione_cognomeout;}
    public java.lang.String getNomeout(){return nomeout;}
    public java.lang.String getCodfiscaleout(){return codfiscaleout;}
    public java.lang.String getPartitaivaout(){return partitaivaout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setEmailaddressin(java.lang.String value){this.emailaddressin=value;}
    public void setFlgsolointerniin(java.lang.Integer value){this.flgsolointerniin=value;}
    public void setFlgtipoout(java.lang.String value){this.flgtipoout=value;}
    public void setIdsoggout(java.math.BigDecimal value){this.idsoggout=value;}
    public void setFlagfisicaout(java.lang.Integer value){this.flagfisicaout=value;}
    public void setDenominazione_cognomeout(java.lang.String value){this.denominazione_cognomeout=value;}
    public void setNomeout(java.lang.String value){this.nomeout=value;}
    public void setCodfiscaleout(java.lang.String value){this.codfiscaleout=value;}
    public void setPartitaivaout(java.lang.String value){this.partitaivaout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    