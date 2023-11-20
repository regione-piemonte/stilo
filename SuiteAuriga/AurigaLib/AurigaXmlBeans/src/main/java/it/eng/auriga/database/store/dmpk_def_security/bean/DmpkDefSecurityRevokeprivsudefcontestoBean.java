/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityRevokeprivsudefcontestoBean")
public class DmpkDefSecurityRevokeprivsudefcontestoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_REVOKEPRIVSUDEFCONTESTO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String flgtpobjprivtoin;
	private java.math.BigDecimal idobjprivtoin;
	private java.lang.String desobjprivtoin;
	private java.lang.String livelliuoprivtoin;
	private java.lang.String desuoappscrivaniatoin;
	private java.lang.String desuserscrivaniatoin;
	private java.lang.String listaobjtorevokexmlin;
	private java.lang.String listaprivilegiin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getFlgtpobjprivtoin(){return flgtpobjprivtoin;}
    public java.math.BigDecimal getIdobjprivtoin(){return idobjprivtoin;}
    public java.lang.String getDesobjprivtoin(){return desobjprivtoin;}
    public java.lang.String getLivelliuoprivtoin(){return livelliuoprivtoin;}
    public java.lang.String getDesuoappscrivaniatoin(){return desuoappscrivaniatoin;}
    public java.lang.String getDesuserscrivaniatoin(){return desuserscrivaniatoin;}
    public java.lang.String getListaobjtorevokexmlin(){return listaobjtorevokexmlin;}
    public java.lang.String getListaprivilegiin(){return listaprivilegiin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgtpobjprivtoin(java.lang.String value){this.flgtpobjprivtoin=value;}
    public void setIdobjprivtoin(java.math.BigDecimal value){this.idobjprivtoin=value;}
    public void setDesobjprivtoin(java.lang.String value){this.desobjprivtoin=value;}
    public void setLivelliuoprivtoin(java.lang.String value){this.livelliuoprivtoin=value;}
    public void setDesuoappscrivaniatoin(java.lang.String value){this.desuoappscrivaniatoin=value;}
    public void setDesuserscrivaniatoin(java.lang.String value){this.desuserscrivaniatoin=value;}
    public void setListaobjtorevokexmlin(java.lang.String value){this.listaobjtorevokexmlin=value;}
    public void setListaprivilegiin(java.lang.String value){this.listaprivilegiin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    