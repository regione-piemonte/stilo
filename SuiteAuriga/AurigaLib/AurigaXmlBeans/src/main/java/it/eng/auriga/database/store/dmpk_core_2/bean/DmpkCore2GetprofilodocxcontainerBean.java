/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCore2GetprofilodocxcontainerBean")
public class DmpkCore2GetprofilodocxcontainerBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_2_GETPROFILODOCXCONTAINER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String containertypein;
	private java.lang.String containeraliasin;
	private java.math.BigDecimal iddocin;
	private java.lang.Integer flgprofnotneededin;
	private java.lang.String usernameout;
	private java.lang.String passwordout;
	private java.lang.String profiloxmlout;
	private java.lang.String attributiverin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getContainertypein(){return containertypein;}
    public java.lang.String getContaineraliasin(){return containeraliasin;}
    public java.math.BigDecimal getIddocin(){return iddocin;}
    public java.lang.Integer getFlgprofnotneededin(){return flgprofnotneededin;}
    public java.lang.String getUsernameout(){return usernameout;}
    public java.lang.String getPasswordout(){return passwordout;}
    public java.lang.String getProfiloxmlout(){return profiloxmlout;}
    public java.lang.String getAttributiverin(){return attributiverin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setContainertypein(java.lang.String value){this.containertypein=value;}
    public void setContaineraliasin(java.lang.String value){this.containeraliasin=value;}
    public void setIddocin(java.math.BigDecimal value){this.iddocin=value;}
    public void setFlgprofnotneededin(java.lang.Integer value){this.flgprofnotneededin=value;}
    public void setUsernameout(java.lang.String value){this.usernameout=value;}
    public void setPasswordout(java.lang.String value){this.passwordout=value;}
    public void setProfiloxmlout(java.lang.String value){this.profiloxmlout=value;}
    public void setAttributiverin(java.lang.String value){this.attributiverin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    