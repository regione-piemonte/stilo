/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWsCheckinBean")
public class DmpkWsCheckinBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WS_CHECKIN";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String xmlin;
	private java.math.BigDecimal iddocout;
	private java.lang.Integer nroversioneestrattaout;
	private java.lang.Integer flgverificafirmafileout;
	private java.lang.String attributiverxmlout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getXmlin(){return xmlin;}
    public java.math.BigDecimal getIddocout(){return iddocout;}
    public java.lang.Integer getNroversioneestrattaout(){return nroversioneestrattaout;}
    public java.lang.Integer getFlgverificafirmafileout(){return flgverificafirmafileout;}
    public java.lang.String getAttributiverxmlout(){return attributiverxmlout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setXmlin(java.lang.String value){this.xmlin=value;}
    public void setIddocout(java.math.BigDecimal value){this.iddocout=value;}
    public void setNroversioneestrattaout(java.lang.Integer value){this.nroversioneestrattaout=value;}
    public void setFlgverificafirmafileout(java.lang.Integer value){this.flgverificafirmafileout=value;}
    public void setAttributiverxmlout(java.lang.String value){this.attributiverxmlout=value;}
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