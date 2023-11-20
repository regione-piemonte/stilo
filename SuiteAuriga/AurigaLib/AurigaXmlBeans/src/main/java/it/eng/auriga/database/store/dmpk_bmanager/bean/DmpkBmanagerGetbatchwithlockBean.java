/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkBmanagerGetbatchwithlockBean")
public class DmpkBmanagerGetbatchwithlockBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_BMANAGER_GETBATCHWITHLOCK";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String batchcontaineridin;
	private java.math.BigDecimal iddominioin;
	private java.lang.String tipojobin;
	private java.lang.String statijobin;
	private java.lang.Integer nrojobtoextractin;
	private java.lang.String listajobout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getBatchcontaineridin(){return batchcontaineridin;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.lang.String getTipojobin(){return tipojobin;}
    public java.lang.String getStatijobin(){return statijobin;}
    public java.lang.Integer getNrojobtoextractin(){return nrojobtoextractin;}
    public java.lang.String getListajobout(){return listajobout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setBatchcontaineridin(java.lang.String value){this.batchcontaineridin=value;}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setTipojobin(java.lang.String value){this.tipojobin=value;}
    public void setStatijobin(java.lang.String value){this.statijobin=value;}
    public void setNrojobtoextractin(java.lang.Integer value){this.nrojobtoextractin=value;}
    public void setListajobout(java.lang.String value){this.listajobout=value;}
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