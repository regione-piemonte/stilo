/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkPolicyGetsoggintcorrelatiutenteBean")
public class DmpkPolicyGetsoggintcorrelatiutenteBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_POLICY_GETSOGGINTCORRELATIUTENTE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iduserin;
	private java.math.BigDecimal iddominioin;
	private java.lang.String flgtpsoggin;
	private java.lang.String flgtprelin;
	private java.lang.String tsrifin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.lang.String getFlgtpsoggin(){return flgtpsoggin;}
    public java.lang.String getFlgtprelin(){return flgtprelin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setFlgtpsoggin(java.lang.String value){this.flgtpsoggin=value;}
    public void setFlgtprelin(java.lang.String value){this.flgtprelin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    