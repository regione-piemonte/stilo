/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFindeventtypeBean")
public class DmpkUtilityFindeventtypeBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDEVENTTYPE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.math.BigDecimal ideventtypein;
	private java.lang.String deseventtypein;
	private java.lang.Integer flgsolovldin;
	private java.lang.String tsrifin;
	private java.lang.String codapplesternain;
	private java.lang.String codistanzaapplestin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.math.BigDecimal getIdeventtypein(){return ideventtypein;}
    public java.lang.String getDeseventtypein(){return deseventtypein;}
    public java.lang.Integer getFlgsolovldin(){return flgsolovldin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    public java.lang.String getCodapplesternain(){return codapplesternain;}
    public java.lang.String getCodistanzaapplestin(){return codistanzaapplestin;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setIdeventtypein(java.math.BigDecimal value){this.ideventtypein=value;}
    public void setDeseventtypein(java.lang.String value){this.deseventtypein=value;}
    public void setFlgsolovldin(java.lang.Integer value){this.flgsolovldin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
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