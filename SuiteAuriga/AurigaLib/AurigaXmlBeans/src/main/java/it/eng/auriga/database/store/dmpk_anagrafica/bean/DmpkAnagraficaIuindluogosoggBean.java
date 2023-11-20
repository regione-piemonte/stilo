/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAnagraficaIuindluogosoggBean")
public class DmpkAnagraficaIuindluogosoggBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ANAGRAFICA_IUINDLUOGOSOGG";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String ciindluogoio;
	private java.math.BigDecimal idsoggrubrio;
	private java.lang.String codtipoindluogoin;
	private java.lang.String dettaglitipoindluogoin;
	private java.lang.String dtiniziovldindluogoin;
	private java.lang.String dtfinevldindluogoin;
	private java.lang.String citoponindirizzoin;
	private java.lang.String tipotoponimoin;
	private java.lang.String indirizzoin;
	private java.lang.String civicoin;
	private java.lang.String appendicicivicoin;
	private java.lang.String internocivin;
	private java.lang.String scalacivin;
	private java.lang.Integer pianoin;
	private java.lang.String capin;
	private java.lang.String localitain;
	private java.lang.String zonain;
	private java.lang.String complementoindin;
	private java.lang.String codistatcomunein;
	private java.lang.String nomecomunecittain;
	private java.lang.String codistatstatoin;
	private java.lang.String nomestatoin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getCiindluogoio(){return ciindluogoio;}
    public java.math.BigDecimal getIdsoggrubrio(){return idsoggrubrio;}
    public java.lang.String getCodtipoindluogoin(){return codtipoindluogoin;}
    public java.lang.String getDettaglitipoindluogoin(){return dettaglitipoindluogoin;}
    public java.lang.String getDtiniziovldindluogoin(){return dtiniziovldindluogoin;}
    public java.lang.String getDtfinevldindluogoin(){return dtfinevldindluogoin;}
    public java.lang.String getCitoponindirizzoin(){return citoponindirizzoin;}
    public java.lang.String getTipotoponimoin(){return tipotoponimoin;}
    public java.lang.String getIndirizzoin(){return indirizzoin;}
    public java.lang.String getCivicoin(){return civicoin;}
    public java.lang.String getAppendicicivicoin(){return appendicicivicoin;}
    public java.lang.String getInternocivin(){return internocivin;}
    public java.lang.String getScalacivin(){return scalacivin;}
    public java.lang.Integer getPianoin(){return pianoin;}
    public java.lang.String getCapin(){return capin;}
    public java.lang.String getLocalitain(){return localitain;}
    public java.lang.String getZonain(){return zonain;}
    public java.lang.String getComplementoindin(){return complementoindin;}
    public java.lang.String getCodistatcomunein(){return codistatcomunein;}
    public java.lang.String getNomecomunecittain(){return nomecomunecittain;}
    public java.lang.String getCodistatstatoin(){return codistatstatoin;}
    public java.lang.String getNomestatoin(){return nomestatoin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setCiindluogoio(java.lang.String value){this.ciindluogoio=value;}
    public void setIdsoggrubrio(java.math.BigDecimal value){this.idsoggrubrio=value;}
    public void setCodtipoindluogoin(java.lang.String value){this.codtipoindluogoin=value;}
    public void setDettaglitipoindluogoin(java.lang.String value){this.dettaglitipoindluogoin=value;}
    public void setDtiniziovldindluogoin(java.lang.String value){this.dtiniziovldindluogoin=value;}
    public void setDtfinevldindluogoin(java.lang.String value){this.dtfinevldindluogoin=value;}
    public void setCitoponindirizzoin(java.lang.String value){this.citoponindirizzoin=value;}
    public void setTipotoponimoin(java.lang.String value){this.tipotoponimoin=value;}
    public void setIndirizzoin(java.lang.String value){this.indirizzoin=value;}
    public void setCivicoin(java.lang.String value){this.civicoin=value;}
    public void setAppendicicivicoin(java.lang.String value){this.appendicicivicoin=value;}
    public void setInternocivin(java.lang.String value){this.internocivin=value;}
    public void setScalacivin(java.lang.String value){this.scalacivin=value;}
    public void setPianoin(java.lang.Integer value){this.pianoin=value;}
    public void setCapin(java.lang.String value){this.capin=value;}
    public void setLocalitain(java.lang.String value){this.localitain=value;}
    public void setZonain(java.lang.String value){this.zonain=value;}
    public void setComplementoindin(java.lang.String value){this.complementoindin=value;}
    public void setCodistatcomunein(java.lang.String value){this.codistatcomunein=value;}
    public void setNomecomunecittain(java.lang.String value){this.nomecomunecittain=value;}
    public void setCodistatstatoin(java.lang.String value){this.codistatstatoin=value;}
    public void setNomestatoin(java.lang.String value){this.nomestatoin=value;}
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