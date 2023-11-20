/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCore2FindnodoarchivioBean")
public class DmpkCore2FindnodoarchivioBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_2_FINDNODOARCHIVIO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.math.BigDecimal idlibraryin;
	private java.lang.String nomelibraryin;
	private java.lang.String pathin;
	private java.lang.String flgtiponodoin;
	private java.lang.Integer flgignoredellogin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.math.BigDecimal getIdlibraryin(){return idlibraryin;}
    public java.lang.String getNomelibraryin(){return nomelibraryin;}
    public java.lang.String getPathin(){return pathin;}
    public java.lang.String getFlgtiponodoin(){return flgtiponodoin;}
    public java.lang.Integer getFlgignoredellogin(){return flgignoredellogin;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setIdlibraryin(java.math.BigDecimal value){this.idlibraryin=value;}
    public void setNomelibraryin(java.lang.String value){this.nomelibraryin=value;}
    public void setPathin(java.lang.String value){this.pathin=value;}
    public void setFlgtiponodoin(java.lang.String value){this.flgtiponodoin=value;}
    public void setFlgignoredellogin(java.lang.Integer value){this.flgignoredellogin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    