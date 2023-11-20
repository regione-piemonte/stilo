/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGetiduosuperioreBean")
public class DmpkUtilityGetiduosuperioreBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETIDUOSUPERIORE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iduoin;
	private java.math.BigDecimal livellogerarchicosupin;
	private java.lang.String tsrifin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIduoin(){return iduoin;}
    public java.math.BigDecimal getLivellogerarchicosupin(){return livellogerarchicosupin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIduoin(java.math.BigDecimal value){this.iduoin=value;}
    public void setLivellogerarchicosupin(java.math.BigDecimal value){this.livellogerarchicosupin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    