/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWfExecattflussoBean")
public class DmpkWfExecattflussoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WF_EXECATTFLUSSO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idprocessin;
	private java.lang.String activitynamein;
	private java.lang.String messaggioin;
	private java.lang.String loginfoin;
	private java.lang.String xmlassegnatariin;
	private java.lang.String attributiaddin;
	private java.lang.Integer flgacttocompleteinwfout;
	private java.lang.String variabileesitoout;
	private java.lang.String esitoout;
	private java.lang.Integer flgignorewarningin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String variabiliesitiout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.lang.String getActivitynamein(){return activitynamein;}
    public java.lang.String getMessaggioin(){return messaggioin;}
    public java.lang.String getLoginfoin(){return loginfoin;}
    public java.lang.String getXmlassegnatariin(){return xmlassegnatariin;}
    public java.lang.String getAttributiaddin(){return attributiaddin;}
    public java.lang.Integer getFlgacttocompleteinwfout(){return flgacttocompleteinwfout;}
    public java.lang.String getVariabileesitoout(){return variabileesitoout;}
    public java.lang.String getEsitoout(){return esitoout;}
    public java.lang.Integer getFlgignorewarningin(){return flgignorewarningin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getVariabiliesitiout(){return variabiliesitiout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setActivitynamein(java.lang.String value){this.activitynamein=value;}
    public void setMessaggioin(java.lang.String value){this.messaggioin=value;}
    public void setLoginfoin(java.lang.String value){this.loginfoin=value;}
    public void setXmlassegnatariin(java.lang.String value){this.xmlassegnatariin=value;}
    public void setAttributiaddin(java.lang.String value){this.attributiaddin=value;}
    public void setFlgacttocompleteinwfout(java.lang.Integer value){this.flgacttocompleteinwfout=value;}
    public void setVariabileesitoout(java.lang.String value){this.variabileesitoout=value;}
    public void setEsitoout(java.lang.String value){this.esitoout=value;}
    public void setFlgignorewarningin(java.lang.Integer value){this.flgignorewarningin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setVariabiliesitiout(java.lang.String value){this.variabiliesitiout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    