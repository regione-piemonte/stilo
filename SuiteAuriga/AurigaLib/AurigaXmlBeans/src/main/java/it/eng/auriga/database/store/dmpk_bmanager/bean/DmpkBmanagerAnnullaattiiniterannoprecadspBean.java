/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.database.store.dmpk_bmanager.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkBmanagerAnnullaattiiniterannoprecadspBean")
public class DmpkBmanagerAnnullaattiiniterannoprecadspBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_BMANAGER_ANNULLAATTIINITERANNOPRECADSP";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String tipoattoin;
	private java.lang.String fasein;
	private java.lang.String rilcontabilein;
	private java.math.BigDecimal nroattiannullatiout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getTipoattoin(){return tipoattoin;}
    public java.lang.String getFasein(){return fasein;}
    public java.lang.String getRilcontabilein(){return rilcontabilein;}
    public java.math.BigDecimal getNroattiannullatiout(){return nroattiannullatiout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setTipoattoin(java.lang.String value){this.tipoattoin=value;}
    public void setFasein(java.lang.String value){this.fasein=value;}
    public void setRilcontabilein(java.lang.String value){this.rilcontabilein=value;}
    public void setNroattiannullatiout(java.math.BigDecimal value){this.nroattiannullatiout=value;}
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
