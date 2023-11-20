/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGetuserextcidBean")
public class DmpkUtilityGetuserextcidBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETUSEREXTCID";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iduserin;
	private java.lang.String codapplesternain;
	private java.lang.String codistanzaapplestin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.lang.String getCodapplesternain(){return codapplesternain;}
    public java.lang.String getCodistanzaapplestin(){return codistanzaapplestin;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setCodapplesternain(java.lang.String value){this.codapplesternain=value;}
    public void setCodistanzaapplestin(java.lang.String value){this.codistanzaapplestin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    