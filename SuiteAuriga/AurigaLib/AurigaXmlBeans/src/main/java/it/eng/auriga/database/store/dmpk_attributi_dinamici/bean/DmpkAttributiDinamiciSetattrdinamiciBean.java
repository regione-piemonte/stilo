/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAttributiDinamiciSetattrdinamiciBean")
public class DmpkAttributiDinamiciSetattrdinamiciBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ATTRIBUTI_DINAMICI_SETATTRDINAMICI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String nometabellain;
	private java.lang.String rowidin;
	private java.lang.String attrvaluesxmlin;
	private java.lang.Integer flgignoreattrnonprevistiin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgignoraobbligin;
	private java.lang.String activitynamewfin;
	private java.lang.String esitoactivitywfin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getNometabellain(){return nometabellain;}
    public java.lang.String getRowidin(){return rowidin;}
    public java.lang.String getAttrvaluesxmlin(){return attrvaluesxmlin;}
    public java.lang.Integer getFlgignoreattrnonprevistiin(){return flgignoreattrnonprevistiin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgignoraobbligin(){return flgignoraobbligin;}
    public java.lang.String getActivitynamewfin(){return activitynamewfin;}
    public java.lang.String getEsitoactivitywfin(){return esitoactivitywfin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setNometabellain(java.lang.String value){this.nometabellain=value;}
    public void setRowidin(java.lang.String value){this.rowidin=value;}
    public void setAttrvaluesxmlin(java.lang.String value){this.attrvaluesxmlin=value;}
    public void setFlgignoreattrnonprevistiin(java.lang.Integer value){this.flgignoreattrnonprevistiin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgignoraobbligin(java.lang.Integer value){this.flgignoraobbligin=value;}
    public void setActivitynamewfin(java.lang.String value){this.activitynamewfin=value;}
    public void setEsitoactivitywfin(java.lang.String value){this.esitoactivitywfin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    