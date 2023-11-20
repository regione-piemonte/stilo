/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFindruoloammBean")
public class DmpkUtilityFindruoloammBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDRUOLOAMM";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.math.BigDecimal idruoloammin;
	private java.lang.String desruoloammin;
	private java.lang.Integer flgsolovldin;
	private java.lang.String tsrifin;
	private java.lang.Integer flgnopercfinalesudesin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.math.BigDecimal getIdruoloammin(){return idruoloammin;}
    public java.lang.String getDesruoloammin(){return desruoloammin;}
    public java.lang.Integer getFlgsolovldin(){return flgsolovldin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    public java.lang.Integer getFlgnopercfinalesudesin(){return flgnopercfinalesudesin;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setIdruoloammin(java.math.BigDecimal value){this.idruoloammin=value;}
    public void setDesruoloammin(java.lang.String value){this.desruoloammin=value;}
    public void setFlgsolovldin(java.lang.Integer value){this.flgsolovldin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    public void setFlgnopercfinalesudesin(java.lang.Integer value){this.flgnopercfinalesudesin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    