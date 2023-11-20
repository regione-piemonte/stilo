/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAmmTraspUpdrecconttabellaBean")
public class DmpkAmmTraspUpdrecconttabellaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_AMM_TRASP_UPDRECCONTTABELLA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idcontenutosezin;
	private java.lang.String idrigain;
	private java.lang.String valorirectoupdin;
	private java.lang.String tspubbldalin;
	private java.lang.String tspubblalin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdcontenutosezin(){return idcontenutosezin;}
    public java.lang.String getIdrigain(){return idrigain;}
    public java.lang.String getValorirectoupdin(){return valorirectoupdin;}
    public java.lang.String getTspubbldalin(){return tspubbldalin;}
    public java.lang.String getTspubblalin(){return tspubblalin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdcontenutosezin(java.math.BigDecimal value){this.idcontenutosezin=value;}
    public void setIdrigain(java.lang.String value){this.idrigain=value;}
    public void setValorirectoupdin(java.lang.String value){this.valorirectoupdin=value;}
    public void setTspubbldalin(java.lang.String value){this.tspubbldalin=value;}
    public void setTspubblalin(java.lang.String value){this.tspubblalin=value;}
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