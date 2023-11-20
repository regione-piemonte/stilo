/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkPolicyTestprivsuoggdefcontestoBean")
public class DmpkPolicyTestprivsuoggdefcontestoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_POLICY_TESTPRIVSUOGGDEFCONTESTO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String flgtpobjprivonin;
	private java.lang.String ciobjprivonin;
	private java.lang.String flgtpobjprivtoin;
	private java.math.BigDecimal idobjprivtoin;
	private java.lang.Integer flgtpdominioin;
	private java.math.BigDecimal iddominioin;
	private java.lang.String opzprivilegioin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getFlgtpobjprivonin(){return flgtpobjprivonin;}
    public java.lang.String getCiobjprivonin(){return ciobjprivonin;}
    public java.lang.String getFlgtpobjprivtoin(){return flgtpobjprivtoin;}
    public java.math.BigDecimal getIdobjprivtoin(){return idobjprivtoin;}
    public java.lang.Integer getFlgtpdominioin(){return flgtpdominioin;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.lang.String getOpzprivilegioin(){return opzprivilegioin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setFlgtpobjprivonin(java.lang.String value){this.flgtpobjprivonin=value;}
    public void setCiobjprivonin(java.lang.String value){this.ciobjprivonin=value;}
    public void setFlgtpobjprivtoin(java.lang.String value){this.flgtpobjprivtoin=value;}
    public void setIdobjprivtoin(java.math.BigDecimal value){this.idobjprivtoin=value;}
    public void setFlgtpdominioin(java.lang.Integer value){this.flgtpdominioin=value;}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setOpzprivilegioin(java.lang.String value){this.opzprivilegioin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    