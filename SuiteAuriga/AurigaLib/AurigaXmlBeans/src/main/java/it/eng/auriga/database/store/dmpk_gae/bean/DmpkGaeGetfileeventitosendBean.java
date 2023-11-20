/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkGaeGetfileeventitosendBean")
public class DmpkGaeGetfileeventitosendBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_GAE_GETFILEEVENTITOSEND";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String xmlrequestin;
	private java.math.BigDecimal nroeventiinfileout;
	private java.lang.String xmldatifiledainviareout;
	private java.lang.String contenutofileeventiout;
	private java.lang.String idfileout;
	private java.lang.Integer flgfinitieventiout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getXmlrequestin(){return xmlrequestin;}
    public java.math.BigDecimal getNroeventiinfileout(){return nroeventiinfileout;}
    public java.lang.String getXmldatifiledainviareout(){return xmldatifiledainviareout;}
    public java.lang.String getContenutofileeventiout(){return contenutofileeventiout;}
    public java.lang.String getIdfileout(){return idfileout;}
    public java.lang.Integer getFlgfinitieventiout(){return flgfinitieventiout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setXmlrequestin(java.lang.String value){this.xmlrequestin=value;}
    public void setNroeventiinfileout(java.math.BigDecimal value){this.nroeventiinfileout=value;}
    public void setXmldatifiledainviareout(java.lang.String value){this.xmldatifiledainviareout=value;}
    public void setContenutofileeventiout(java.lang.String value){this.contenutofileeventiout=value;}
    public void setIdfileout(java.lang.String value){this.idfileout=value;}
    public void setFlgfinitieventiout(java.lang.Integer value){this.flgfinitieventiout=value;}
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