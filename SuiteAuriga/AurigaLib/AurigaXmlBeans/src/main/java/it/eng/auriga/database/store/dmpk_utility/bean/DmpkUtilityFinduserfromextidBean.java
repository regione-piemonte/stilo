/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFinduserfromextidBean")
public class DmpkUtilityFinduserfromextidBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDUSERFROMEXTID";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.lang.String codapplesternain;
	private java.lang.String codistanzaapplestin;
	private java.lang.String codiduseresternoin;
	private java.lang.Integer flgsolovldin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.lang.String getCodapplesternain(){return codapplesternain;}
    public java.lang.String getCodistanzaapplestin(){return codistanzaapplestin;}
    public java.lang.String getCodiduseresternoin(){return codiduseresternoin;}
    public java.lang.Integer getFlgsolovldin(){return flgsolovldin;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setCodapplesternain(java.lang.String value){this.codapplesternain=value;}
    public void setCodistanzaapplestin(java.lang.String value){this.codistanzaapplestin=value;}
    public void setCodiduseresternoin(java.lang.String value){this.codiduseresternoin=value;}
    public void setFlgsolovldin(java.lang.Integer value){this.flgsolovldin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    