/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGetbasicinfoprocessBean")
public class DmpkUtilityGetbasicinfoprocessBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETBASICINFOPROCESS";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idprocessin;
	private java.math.BigDecimal idprocesstypeout;
	private java.lang.String citypeflussowfout;
	private java.lang.String ciflussowfout;
	private java.lang.String flgtpobjprocessonout;
	private java.math.BigDecimal idobjprocessonout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.math.BigDecimal getIdprocesstypeout(){return idprocesstypeout;}
    public java.lang.String getCitypeflussowfout(){return citypeflussowfout;}
    public java.lang.String getCiflussowfout(){return ciflussowfout;}
    public java.lang.String getFlgtpobjprocessonout(){return flgtpobjprocessonout;}
    public java.math.BigDecimal getIdobjprocessonout(){return idobjprocessonout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setIdprocesstypeout(java.math.BigDecimal value){this.idprocesstypeout=value;}
    public void setCitypeflussowfout(java.lang.String value){this.citypeflussowfout=value;}
    public void setCiflussowfout(java.lang.String value){this.ciflussowfout=value;}
    public void setFlgtpobjprocessonout(java.lang.String value){this.flgtpobjprocessonout=value;}
    public void setIdobjprocessonout(java.math.BigDecimal value){this.idobjprocessonout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    