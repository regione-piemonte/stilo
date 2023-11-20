/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityTestdeluservsuserBean")
public class DmpkUtilityTestdeluservsuserBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_TESTDELUSERVSUSER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iduserdelegatoin;
	private java.math.BigDecimal iduserdelegavsin;
	private java.lang.String tsrifin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIduserdelegatoin(){return iduserdelegatoin;}
    public java.math.BigDecimal getIduserdelegavsin(){return iduserdelegavsin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIduserdelegatoin(java.math.BigDecimal value){this.iduserdelegatoin=value;}
    public void setIduserdelegavsin(java.math.BigDecimal value){this.iduserdelegavsin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    