/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRegistrazionedocStamparegistroBean")
public class DmpkRegistrazionedocStamparegistroBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_REGISTRAZIONEDOC_STAMPAREGISTRO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String parametriregistroin;
	private java.math.BigDecimal idjobio;
	private java.lang.String datiregistrazionixmlout;
	private java.lang.String dativarregistrazionixmlout;
	private java.lang.String datiudstampaout;
	private java.lang.String headerregistroout;
	private java.lang.String headervarregistroout;
	private java.lang.String footerregistroout;
	private java.lang.String footervarregistroout;
	private java.lang.Integer nroregistrazioniout;
	private java.lang.Integer nrovarregistrazioniout;
	private java.lang.String xmldatiregistroxconservout;
	private java.lang.String tiporeportout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getParametriregistroin(){return parametriregistroin;}
    public java.math.BigDecimal getIdjobio(){return idjobio;}
    public java.lang.String getDatiregistrazionixmlout(){return datiregistrazionixmlout;}
    public java.lang.String getDativarregistrazionixmlout(){return dativarregistrazionixmlout;}
    public java.lang.String getDatiudstampaout(){return datiudstampaout;}
    public java.lang.String getHeaderregistroout(){return headerregistroout;}
    public java.lang.String getHeadervarregistroout(){return headervarregistroout;}
    public java.lang.String getFooterregistroout(){return footerregistroout;}
    public java.lang.String getFootervarregistroout(){return footervarregistroout;}
    public java.lang.Integer getNroregistrazioniout(){return nroregistrazioniout;}
    public java.lang.Integer getNrovarregistrazioniout(){return nrovarregistrazioniout;}
    public java.lang.String getXmldatiregistroxconservout(){return xmldatiregistroxconservout;}
    public java.lang.String getTiporeportout(){return tiporeportout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setParametriregistroin(java.lang.String value){this.parametriregistroin=value;}
    public void setIdjobio(java.math.BigDecimal value){this.idjobio=value;}
    public void setDatiregistrazionixmlout(java.lang.String value){this.datiregistrazionixmlout=value;}
    public void setDativarregistrazionixmlout(java.lang.String value){this.dativarregistrazionixmlout=value;}
    public void setDatiudstampaout(java.lang.String value){this.datiudstampaout=value;}
    public void setHeaderregistroout(java.lang.String value){this.headerregistroout=value;}
    public void setHeadervarregistroout(java.lang.String value){this.headervarregistroout=value;}
    public void setFooterregistroout(java.lang.String value){this.footerregistroout=value;}
    public void setFootervarregistroout(java.lang.String value){this.footervarregistroout=value;}
    public void setNroregistrazioniout(java.lang.Integer value){this.nroregistrazioniout=value;}
    public void setNrovarregistrazioniout(java.lang.Integer value){this.nrovarregistrazioniout=value;}
    public void setXmldatiregistroxconservout(java.lang.String value){this.xmldatiregistroxconservout=value;}
    public void setTiporeportout(java.lang.String value){this.tiporeportout=value;}
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