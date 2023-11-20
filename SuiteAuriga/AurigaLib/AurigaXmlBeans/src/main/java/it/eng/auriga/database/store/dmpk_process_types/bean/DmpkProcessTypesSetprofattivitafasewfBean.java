/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessTypesSetprofattivitafasewfBean")
public class DmpkProcessTypesSetprofattivitafasewfBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESS_TYPES_SETPROFATTIVITAFASEWF";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String citypeflussowfin;
	private java.lang.String cifasein;
	private java.lang.String ciattivitain;
	private java.lang.Integer nroordinevisin;
	private java.lang.String flgmodaclin;
	private java.lang.String xmlaclin;
	private java.lang.Integer flgassabilin;
	private java.lang.String controlliobblvsfasein;
	private java.lang.String flgmoddettxesitoin;
	private java.lang.String xmldettxesitoin;
	private java.lang.String flgmoddesttrasmin;
	private java.lang.String xmldesttrasmin;
	private java.lang.String flgmodattraddeditabiliin;
	private java.lang.String nomiattraddeditabiliin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String cifaseoldin;
	private java.lang.Integer flgcallbyguiin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getCitypeflussowfin(){return citypeflussowfin;}
    public java.lang.String getCifasein(){return cifasein;}
    public java.lang.String getCiattivitain(){return ciattivitain;}
    public java.lang.Integer getNroordinevisin(){return nroordinevisin;}
    public java.lang.String getFlgmodaclin(){return flgmodaclin;}
    public java.lang.String getXmlaclin(){return xmlaclin;}
    public java.lang.Integer getFlgassabilin(){return flgassabilin;}
    public java.lang.String getControlliobblvsfasein(){return controlliobblvsfasein;}
    public java.lang.String getFlgmoddettxesitoin(){return flgmoddettxesitoin;}
    public java.lang.String getXmldettxesitoin(){return xmldettxesitoin;}
    public java.lang.String getFlgmoddesttrasmin(){return flgmoddesttrasmin;}
    public java.lang.String getXmldesttrasmin(){return xmldesttrasmin;}
    public java.lang.String getFlgmodattraddeditabiliin(){return flgmodattraddeditabiliin;}
    public java.lang.String getNomiattraddeditabiliin(){return nomiattraddeditabiliin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getCifaseoldin(){return cifaseoldin;}
    public java.lang.Integer getFlgcallbyguiin(){return flgcallbyguiin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setCitypeflussowfin(java.lang.String value){this.citypeflussowfin=value;}
    public void setCifasein(java.lang.String value){this.cifasein=value;}
    public void setCiattivitain(java.lang.String value){this.ciattivitain=value;}
    public void setNroordinevisin(java.lang.Integer value){this.nroordinevisin=value;}
    public void setFlgmodaclin(java.lang.String value){this.flgmodaclin=value;}
    public void setXmlaclin(java.lang.String value){this.xmlaclin=value;}
    public void setFlgassabilin(java.lang.Integer value){this.flgassabilin=value;}
    public void setControlliobblvsfasein(java.lang.String value){this.controlliobblvsfasein=value;}
    public void setFlgmoddettxesitoin(java.lang.String value){this.flgmoddettxesitoin=value;}
    public void setXmldettxesitoin(java.lang.String value){this.xmldettxesitoin=value;}
    public void setFlgmoddesttrasmin(java.lang.String value){this.flgmoddesttrasmin=value;}
    public void setXmldesttrasmin(java.lang.String value){this.xmldesttrasmin=value;}
    public void setFlgmodattraddeditabiliin(java.lang.String value){this.flgmodattraddeditabiliin=value;}
    public void setNomiattraddeditabiliin(java.lang.String value){this.nomiattraddeditabiliin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setCifaseoldin(java.lang.String value){this.cifaseoldin=value;}
    public void setFlgcallbyguiin(java.lang.Integer value){this.flgcallbyguiin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    