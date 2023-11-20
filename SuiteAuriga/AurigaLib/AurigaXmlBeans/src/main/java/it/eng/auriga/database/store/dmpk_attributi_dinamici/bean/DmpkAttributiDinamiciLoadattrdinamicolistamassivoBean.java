/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAttributiDinamiciLoadattrdinamicolistamassivoBean")
public class DmpkAttributiDinamiciLoadattrdinamicolistamassivoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ATTRIBUTI_DINAMICI_LOADATTRDINAMICOLISTAMASSIVO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String nometabellain;
	private java.lang.String rowidin;
	private java.lang.String nomeattrlistain;
	private java.lang.String titololistaout;
	private java.lang.String datideflistaout;
	private java.lang.String datidettlistaout;
	private java.lang.String valorilistaout;
	private java.lang.String flgdativariatiout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String nomeflussowfin;
	private java.lang.String processnamewfin;
	private java.lang.String activitynamewfin;
	private java.lang.Integer flgdatistoricitaskin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getNometabellain(){return nometabellain;}
    public java.lang.String getRowidin(){return rowidin;}
    public java.lang.String getNomeattrlistain(){return nomeattrlistain;}
    public java.lang.String getTitololistaout(){return titololistaout;}
    public java.lang.String getDatideflistaout(){return datideflistaout;}
    public java.lang.String getDatidettlistaout(){return datidettlistaout;}
    public java.lang.String getValorilistaout(){return valorilistaout;}
    public java.lang.String getFlgdativariatiout(){return flgdativariatiout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getNomeflussowfin(){return nomeflussowfin;}
    public java.lang.String getProcessnamewfin(){return processnamewfin;}
    public java.lang.String getActivitynamewfin(){return activitynamewfin;}
    public java.lang.Integer getFlgdatistoricitaskin(){return flgdatistoricitaskin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setNometabellain(java.lang.String value){this.nometabellain=value;}
    public void setRowidin(java.lang.String value){this.rowidin=value;}
    public void setNomeattrlistain(java.lang.String value){this.nomeattrlistain=value;}
    public void setTitololistaout(java.lang.String value){this.titololistaout=value;}
    public void setDatideflistaout(java.lang.String value){this.datideflistaout=value;}
    public void setDatidettlistaout(java.lang.String value){this.datidettlistaout=value;}
    public void setValorilistaout(java.lang.String value){this.valorilistaout=value;}
    public void setFlgdativariatiout(java.lang.String value){this.flgdativariatiout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setNomeflussowfin(java.lang.String value){this.nomeflussowfin=value;}
    public void setProcessnamewfin(java.lang.String value){this.processnamewfin=value;}
    public void setActivitynamewfin(java.lang.String value){this.activitynamewfin=value;}
    public void setFlgdatistoricitaskin(java.lang.Integer value){this.flgdatistoricitaskin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    