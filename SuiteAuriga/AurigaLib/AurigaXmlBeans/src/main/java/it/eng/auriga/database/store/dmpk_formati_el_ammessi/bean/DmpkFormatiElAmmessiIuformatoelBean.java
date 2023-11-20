/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkFormatiElAmmessiIuformatoelBean")
public class DmpkFormatiElAmmessiIuformatoelBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_FORMATI_EL_AMMESSI_IUFORMATOEL";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idformatoelio;
	private java.lang.String nomeformatoelin;
	private java.lang.String estensioneformatoelin;
	private java.lang.String dtinizioammin;
	private java.lang.String dtfineammin;
	private java.lang.Integer flgbinarioin;
	private java.lang.Integer flgdaindicizzarein;
	private java.lang.String ciprovformatoelin;
	private java.lang.String mimetypein;
	private java.lang.String attributiaddin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdformatoelio(){return idformatoelio;}
    public java.lang.String getNomeformatoelin(){return nomeformatoelin;}
    public java.lang.String getEstensioneformatoelin(){return estensioneformatoelin;}
    public java.lang.String getDtinizioammin(){return dtinizioammin;}
    public java.lang.String getDtfineammin(){return dtfineammin;}
    public java.lang.Integer getFlgbinarioin(){return flgbinarioin;}
    public java.lang.Integer getFlgdaindicizzarein(){return flgdaindicizzarein;}
    public java.lang.String getCiprovformatoelin(){return ciprovformatoelin;}
    public java.lang.String getMimetypein(){return mimetypein;}
    public java.lang.String getAttributiaddin(){return attributiaddin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdformatoelio(java.math.BigDecimal value){this.idformatoelio=value;}
    public void setNomeformatoelin(java.lang.String value){this.nomeformatoelin=value;}
    public void setEstensioneformatoelin(java.lang.String value){this.estensioneformatoelin=value;}
    public void setDtinizioammin(java.lang.String value){this.dtinizioammin=value;}
    public void setDtfineammin(java.lang.String value){this.dtfineammin=value;}
    public void setFlgbinarioin(java.lang.Integer value){this.flgbinarioin=value;}
    public void setFlgdaindicizzarein(java.lang.Integer value){this.flgdaindicizzarein=value;}
    public void setCiprovformatoelin(java.lang.String value){this.ciprovformatoelin=value;}
    public void setMimetypein(java.lang.String value){this.mimetypein=value;}
    public void setAttributiaddin(java.lang.String value){this.attributiaddin=value;}
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