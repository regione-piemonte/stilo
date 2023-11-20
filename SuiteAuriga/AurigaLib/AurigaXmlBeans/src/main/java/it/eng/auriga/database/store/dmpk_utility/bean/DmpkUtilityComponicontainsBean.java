/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityComponicontainsBean")
public class DmpkUtilityComponicontainsBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_COMPONICONTAINS";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioautin;
	private java.lang.String selcolnamein;
	private java.lang.String selcolfilterin;
	private java.lang.String desselcolumnin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddominioautin(){return iddominioautin;}
    public java.lang.String getSelcolnamein(){return selcolnamein;}
    public java.lang.String getSelcolfilterin(){return selcolfilterin;}
    public java.lang.String getDesselcolumnin(){return desselcolumnin;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setIddominioautin(java.math.BigDecimal value){this.iddominioautin=value;}
    public void setSelcolnamein(java.lang.String value){this.selcolnamein=value;}
    public void setSelcolfilterin(java.lang.String value){this.selcolfilterin=value;}
    public void setDesselcolumnin(java.lang.String value){this.desselcolumnin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    