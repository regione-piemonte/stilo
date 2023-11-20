/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkLoginLoginapplicazioneBean")
public class DmpkLoginLoginapplicazioneBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_LOGIN_LOGINAPPLICAZIONE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String useridapplicazionein;
	private java.lang.String passwordin;
	private java.lang.Integer flgtpdominioautout;
	private java.math.BigDecimal iddominioautout;
	private java.lang.String codidconnectiontokenout;
	private java.math.BigDecimal iduserout;
	private java.lang.String desuserout;
	private java.lang.String desdominioout;
	private java.lang.String parametriconfigout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgnoctrlpasswordin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getUseridapplicazionein(){return useridapplicazionein;}
    public java.lang.String getPasswordin(){return passwordin;}
    public java.lang.Integer getFlgtpdominioautout(){return flgtpdominioautout;}
    public java.math.BigDecimal getIddominioautout(){return iddominioautout;}
    public java.lang.String getCodidconnectiontokenout(){return codidconnectiontokenout;}
    public java.math.BigDecimal getIduserout(){return iduserout;}
    public java.lang.String getDesuserout(){return desuserout;}
    public java.lang.String getDesdominioout(){return desdominioout;}
    public java.lang.String getParametriconfigout(){return parametriconfigout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgnoctrlpasswordin(){return flgnoctrlpasswordin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setUseridapplicazionein(java.lang.String value){this.useridapplicazionein=value;}
    public void setPasswordin(java.lang.String value){this.passwordin=value;}
    public void setFlgtpdominioautout(java.lang.Integer value){this.flgtpdominioautout=value;}
    public void setIddominioautout(java.math.BigDecimal value){this.iddominioautout=value;}
    public void setCodidconnectiontokenout(java.lang.String value){this.codidconnectiontokenout=value;}
    public void setIduserout(java.math.BigDecimal value){this.iduserout=value;}
    public void setDesuserout(java.lang.String value){this.desuserout=value;}
    public void setDesdominioout(java.lang.String value){this.desdominioout=value;}
    public void setParametriconfigout(java.lang.String value){this.parametriconfigout=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgnoctrlpasswordin(java.lang.Integer value){this.flgnoctrlpasswordin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    