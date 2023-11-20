/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessesInsupdprocessBean")
public class DmpkProcessesInsupdprocessBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESSES_INSUPDPROCESS";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idprocessio;
	private java.math.BigDecimal idprocesstypein;
	private java.lang.String ciprovprocessin;
	private java.lang.String citypeflussowfin;
	private java.lang.String ciflussowfin;
	private java.lang.String tsavvioprocin;
	private java.lang.String dtdecorrenzaprocin;
	private java.lang.String oggettoin;
	private java.lang.String flgtpobjprocessonin;
	private java.math.BigDecimal idobjprocessonin;
	private java.math.BigDecimal idprocessparentin;
	private java.lang.String motivazioniavvioin;
	private java.lang.String noteprocin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String attributiaddavvioin;
	private java.lang.Integer flginsbyfoin;
	private java.lang.String etichettaproponentein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdprocessio(){return idprocessio;}
    public java.math.BigDecimal getIdprocesstypein(){return idprocesstypein;}
    public java.lang.String getCiprovprocessin(){return ciprovprocessin;}
    public java.lang.String getCitypeflussowfin(){return citypeflussowfin;}
    public java.lang.String getCiflussowfin(){return ciflussowfin;}
    public java.lang.String getTsavvioprocin(){return tsavvioprocin;}
    public java.lang.String getDtdecorrenzaprocin(){return dtdecorrenzaprocin;}
    public java.lang.String getOggettoin(){return oggettoin;}
    public java.lang.String getFlgtpobjprocessonin(){return flgtpobjprocessonin;}
    public java.math.BigDecimal getIdobjprocessonin(){return idobjprocessonin;}
    public java.math.BigDecimal getIdprocessparentin(){return idprocessparentin;}
    public java.lang.String getMotivazioniavvioin(){return motivazioniavvioin;}
    public java.lang.String getNoteprocin(){return noteprocin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getAttributiaddavvioin(){return attributiaddavvioin;}
    public java.lang.Integer getFlginsbyfoin(){return flginsbyfoin;}
    public java.lang.String getEtichettaproponentein(){return etichettaproponentein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdprocessio(java.math.BigDecimal value){this.idprocessio=value;}
    public void setIdprocesstypein(java.math.BigDecimal value){this.idprocesstypein=value;}
    public void setCiprovprocessin(java.lang.String value){this.ciprovprocessin=value;}
    public void setCitypeflussowfin(java.lang.String value){this.citypeflussowfin=value;}
    public void setCiflussowfin(java.lang.String value){this.ciflussowfin=value;}
    public void setTsavvioprocin(java.lang.String value){this.tsavvioprocin=value;}
    public void setDtdecorrenzaprocin(java.lang.String value){this.dtdecorrenzaprocin=value;}
    public void setOggettoin(java.lang.String value){this.oggettoin=value;}
    public void setFlgtpobjprocessonin(java.lang.String value){this.flgtpobjprocessonin=value;}
    public void setIdobjprocessonin(java.math.BigDecimal value){this.idobjprocessonin=value;}
    public void setIdprocessparentin(java.math.BigDecimal value){this.idprocessparentin=value;}
    public void setMotivazioniavvioin(java.lang.String value){this.motivazioniavvioin=value;}
    public void setNoteprocin(java.lang.String value){this.noteprocin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setAttributiaddavvioin(java.lang.String value){this.attributiaddavvioin=value;}
    public void setFlginsbyfoin(java.lang.Integer value){this.flginsbyfoin=value;}
    public void setEtichettaproponentein(java.lang.String value){this.etichettaproponentein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    