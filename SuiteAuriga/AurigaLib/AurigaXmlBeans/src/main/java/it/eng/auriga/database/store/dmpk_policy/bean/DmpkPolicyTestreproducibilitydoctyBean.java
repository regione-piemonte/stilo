/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkPolicyTestreproducibilitydoctyBean")
public class DmpkPolicyTestreproducibilitydoctyBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_POLICY_TESTREPRODUCIBILITYDOCTY";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Integer flgtpdominioin;
	private java.math.BigDecimal iddominioin;
	private java.math.BigDecimal iduserin;
	private java.math.BigDecimal iddoctypein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.Integer getFlgtpdominioin(){return flgtpdominioin;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.math.BigDecimal getIddoctypein(){return iddoctypein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setFlgtpdominioin(java.lang.Integer value){this.flgtpdominioin=value;}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setIddoctypein(java.math.BigDecimal value){this.iddoctypein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    