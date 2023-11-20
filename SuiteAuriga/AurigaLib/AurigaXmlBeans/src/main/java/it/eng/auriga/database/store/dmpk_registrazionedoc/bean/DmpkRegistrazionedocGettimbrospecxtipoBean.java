/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRegistrazionedocGettimbrospecxtipoBean")
public class DmpkRegistrazionedocGettimbrospecxtipoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_REGISTRAZIONEDOC_GETTIMBROSPECXTIPO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String flgdocfolderin;
	private java.math.BigDecimal iddocfolderin;
	private java.lang.String contenutobarcodeout;
	private java.lang.String testoinchiaroout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getFlgdocfolderin(){return flgdocfolderin;}
    public java.math.BigDecimal getIddocfolderin(){return iddocfolderin;}
    public java.lang.String getContenutobarcodeout(){return contenutobarcodeout;}
    public java.lang.String getTestoinchiaroout(){return testoinchiaroout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgdocfolderin(java.lang.String value){this.flgdocfolderin=value;}
    public void setIddocfolderin(java.math.BigDecimal value){this.iddocfolderin=value;}
    public void setContenutobarcodeout(java.lang.String value){this.contenutobarcodeout=value;}
    public void setTestoinchiaroout(java.lang.String value){this.testoinchiaroout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    