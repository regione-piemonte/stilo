/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmoComponenteGruppoEstTestportaingruppoBean")
public class DmoComponenteGruppoEstTestportaingruppoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMO_COMPONENTE_GRUPPO_EST_TESTPORTAINGRUPPO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Object self;
	private java.lang.String flgtpobjinnerin;
	private java.math.BigDecimal idobjinnerin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.Object getSelf(){return self;}
    public java.lang.String getFlgtpobjinnerin(){return flgtpobjinnerin;}
    public java.math.BigDecimal getIdobjinnerin(){return idobjinnerin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setSelf(java.lang.Object value){this.self=value;}
    public void setFlgtpobjinnerin(java.lang.String value){this.flgtpobjinnerin=value;}
    public void setIdobjinnerin(java.math.BigDecimal value){this.idobjinnerin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    