/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkStampeTo_jobBean")
public class DmpkStampeTo_jobBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_STAMPE_TO_JOB";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer numeroin;
	private java.lang.String parametriin;
	private java.lang.String formatoin;
	private java.lang.String tsschedulein;
	private java.lang.String filenamein;
	private java.lang.String aliasfilenamein;
	private java.lang.String jasperfilenamein;
	private java.lang.String ctrlflgin;
	private java.lang.String messagein;
	private java.lang.String statistichein;
	private java.lang.String cntin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getNumeroin(){return numeroin;}
    public java.lang.String getParametriin(){return parametriin;}
    public java.lang.String getFormatoin(){return formatoin;}
    public java.lang.String getTsschedulein(){return tsschedulein;}
    public java.lang.String getFilenamein(){return filenamein;}
    public java.lang.String getAliasfilenamein(){return aliasfilenamein;}
    public java.lang.String getJasperfilenamein(){return jasperfilenamein;}
    public java.lang.String getCtrlflgin(){return ctrlflgin;}
    public java.lang.String getMessagein(){return messagein;}
    public java.lang.String getStatistichein(){return statistichein;}
    public java.lang.String getCntin(){return cntin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setNumeroin(java.lang.Integer value){this.numeroin=value;}
    public void setParametriin(java.lang.String value){this.parametriin=value;}
    public void setFormatoin(java.lang.String value){this.formatoin=value;}
    public void setTsschedulein(java.lang.String value){this.tsschedulein=value;}
    public void setFilenamein(java.lang.String value){this.filenamein=value;}
    public void setAliasfilenamein(java.lang.String value){this.aliasfilenamein=value;}
    public void setJasperfilenamein(java.lang.String value){this.jasperfilenamein=value;}
    public void setCtrlflgin(java.lang.String value){this.ctrlflgin=value;}
    public void setMessagein(java.lang.String value){this.messagein=value;}
    public void setStatistichein(java.lang.String value){this.statistichein=value;}
    public void setCntin(java.lang.String value){this.cntin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    