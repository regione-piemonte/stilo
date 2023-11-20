/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityIugruppoprivilegiBean")
public class DmpkDefSecurityIugruppoprivilegiBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_IUGRUPPOPRIVILEGI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idgruppoprivio;
	private java.lang.String nomegruppoprivin;
	private java.lang.String notegruppoprivin;
	private java.lang.String ciprovgruppoprivin;
	private java.lang.Integer flglockedin;
	private java.lang.String flgmodprivilegiin;
	private java.lang.String xmlprivilegiin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdgruppoprivio(){return idgruppoprivio;}
    public java.lang.String getNomegruppoprivin(){return nomegruppoprivin;}
    public java.lang.String getNotegruppoprivin(){return notegruppoprivin;}
    public java.lang.String getCiprovgruppoprivin(){return ciprovgruppoprivin;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.String getFlgmodprivilegiin(){return flgmodprivilegiin;}
    public java.lang.String getXmlprivilegiin(){return xmlprivilegiin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdgruppoprivio(java.math.BigDecimal value){this.idgruppoprivio=value;}
    public void setNomegruppoprivin(java.lang.String value){this.nomegruppoprivin=value;}
    public void setNotegruppoprivin(java.lang.String value){this.notegruppoprivin=value;}
    public void setCiprovgruppoprivin(java.lang.String value){this.ciprovgruppoprivin=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setFlgmodprivilegiin(java.lang.String value){this.flgmodprivilegiin=value;}
    public void setXmlprivilegiin(java.lang.String value){this.xmlprivilegiin=value;}
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