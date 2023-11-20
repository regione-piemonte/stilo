/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGetinfolivelliforguiBean")
public class DmpkUtilityGetinfolivelliforguiBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETINFOLIVELLIFORGUI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String targetin;
	private java.lang.String livellistrin;
	private java.math.BigDecimal idpianoclassifin;
	private java.lang.String separatoreout;
	private java.lang.String livelliout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getTargetin(){return targetin;}
    public java.lang.String getLivellistrin(){return livellistrin;}
    public java.math.BigDecimal getIdpianoclassifin(){return idpianoclassifin;}
    public java.lang.String getSeparatoreout(){return separatoreout;}
    public java.lang.String getLivelliout(){return livelliout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setTargetin(java.lang.String value){this.targetin=value;}
    public void setLivellistrin(java.lang.String value){this.livellistrin=value;}
    public void setIdpianoclassifin(java.math.BigDecimal value){this.idpianoclassifin=value;}
    public void setSeparatoreout(java.lang.String value){this.separatoreout=value;}
    public void setLivelliout(java.lang.String value){this.livelliout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    