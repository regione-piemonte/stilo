/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkBmanagerIufoglioximportBean")
public class DmpkBmanagerIufoglioximportBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_BMANAGER_IUFOGLIOXIMPORT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String codsocietain;
	private java.lang.String idfoglioio;
	private java.lang.String tipocontenutoin;
	private java.lang.String uriin;
	private java.lang.String statoin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String improntain;
	private java.lang.String algoritmoimprontain;
	private java.lang.String encodingimprontain;
	private java.lang.String infoelaborazionein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getCodsocietain(){return codsocietain;}
    public java.lang.String getIdfoglioio(){return idfoglioio;}
    public java.lang.String getTipocontenutoin(){return tipocontenutoin;}
    public java.lang.String getUriin(){return uriin;}
    public java.lang.String getStatoin(){return statoin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getImprontain(){return improntain;}
    public java.lang.String getAlgoritmoimprontain(){return algoritmoimprontain;}
    public java.lang.String getEncodingimprontain(){return encodingimprontain;}
    public java.lang.String getInfoelaborazionein(){return infoelaborazionein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setCodsocietain(java.lang.String value){this.codsocietain=value;}
    public void setIdfoglioio(java.lang.String value){this.idfoglioio=value;}
    public void setTipocontenutoin(java.lang.String value){this.tipocontenutoin=value;}
    public void setUriin(java.lang.String value){this.uriin=value;}
    public void setStatoin(java.lang.String value){this.statoin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setImprontain(java.lang.String value){this.improntain=value;}
    public void setAlgoritmoimprontain(java.lang.String value){this.algoritmoimprontain=value;}
    public void setEncodingimprontain(java.lang.String value){this.encodingimprontain=value;}
    public void setInfoelaborazionein(java.lang.String value){this.infoelaborazionein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    