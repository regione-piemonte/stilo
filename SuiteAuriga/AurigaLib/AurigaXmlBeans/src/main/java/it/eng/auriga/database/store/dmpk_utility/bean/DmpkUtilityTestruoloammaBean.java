/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityTestruoloammaBean")
public class DmpkUtilityTestruoloammaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_TESTRUOLOAMMA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idruoloammin;
	private java.lang.String flgtpobjtotestin;
	private java.math.BigDecimal idobjtotestin;
	private java.math.BigDecimal iddominioin;
	private java.math.BigDecimal livuolimtestin;
	private java.lang.String codtipouolimtestin;
	private java.math.BigDecimal iduolimtestin;
	private java.lang.Integer flginclsottouoin;
	private java.lang.String tsrifin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdruoloammin(){return idruoloammin;}
    public java.lang.String getFlgtpobjtotestin(){return flgtpobjtotestin;}
    public java.math.BigDecimal getIdobjtotestin(){return idobjtotestin;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.math.BigDecimal getLivuolimtestin(){return livuolimtestin;}
    public java.lang.String getCodtipouolimtestin(){return codtipouolimtestin;}
    public java.math.BigDecimal getIduolimtestin(){return iduolimtestin;}
    public java.lang.Integer getFlginclsottouoin(){return flginclsottouoin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdruoloammin(java.math.BigDecimal value){this.idruoloammin=value;}
    public void setFlgtpobjtotestin(java.lang.String value){this.flgtpobjtotestin=value;}
    public void setIdobjtotestin(java.math.BigDecimal value){this.idobjtotestin=value;}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setLivuolimtestin(java.math.BigDecimal value){this.livuolimtestin=value;}
    public void setCodtipouolimtestin(java.lang.String value){this.codtipouolimtestin=value;}
    public void setIduolimtestin(java.math.BigDecimal value){this.iduolimtestin=value;}
    public void setFlginclsottouoin(java.lang.Integer value){this.flginclsottouoin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    