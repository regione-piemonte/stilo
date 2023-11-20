/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityTestrelvsuoBean")
public class DmpkUtilityTestrelvsuoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_TESTRELVSUO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iduoouterin;
	private java.lang.String flgtpobjinnerin;
	private java.math.BigDecimal idobjinnerin;
	private java.lang.String flgtprelin;
	private java.lang.Integer flgappdirettain;
	private java.lang.String tsrifin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIduoouterin(){return iduoouterin;}
    public java.lang.String getFlgtpobjinnerin(){return flgtpobjinnerin;}
    public java.math.BigDecimal getIdobjinnerin(){return idobjinnerin;}
    public java.lang.String getFlgtprelin(){return flgtprelin;}
    public java.lang.Integer getFlgappdirettain(){return flgappdirettain;}
    public java.lang.String getTsrifin(){return tsrifin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIduoouterin(java.math.BigDecimal value){this.iduoouterin=value;}
    public void setFlgtpobjinnerin(java.lang.String value){this.flgtpobjinnerin=value;}
    public void setIdobjinnerin(java.math.BigDecimal value){this.idobjinnerin=value;}
    public void setFlgtprelin(java.lang.String value){this.flgtprelin=value;}
    public void setFlgappdirettain(java.lang.Integer value){this.flgappdirettain=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    