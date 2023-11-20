/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkPolicyTestreproducibilityBean")
public class DmpkPolicyTestreproducibilityBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_POLICY_TESTREPRODUCIBILITY";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Integer abilreproddocnorestrictionin;
	private java.lang.Integer abilreprodalldocin;
	private java.math.BigDecimal iddoctypein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.Integer getAbilreproddocnorestrictionin(){return abilreproddocnorestrictionin;}
    public java.lang.Integer getAbilreprodalldocin(){return abilreprodalldocin;}
    public java.math.BigDecimal getIddoctypein(){return iddoctypein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setAbilreproddocnorestrictionin(java.lang.Integer value){this.abilreproddocnorestrictionin=value;}
    public void setAbilreprodalldocin(java.lang.Integer value){this.abilreprodalldocin=value;}
    public void setIddoctypein(java.math.BigDecimal value){this.iddoctypein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    