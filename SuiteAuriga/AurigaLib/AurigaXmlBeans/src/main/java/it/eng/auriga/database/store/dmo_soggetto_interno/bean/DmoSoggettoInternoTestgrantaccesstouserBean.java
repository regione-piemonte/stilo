/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmoSoggettoInternoTestgrantaccesstouserBean")
public class DmoSoggettoInternoTestgrantaccesstouserBean extends StoreBean implements Serializable{

	private static final String storeName = "DMO_SOGGETTO_INTERNO_TESTGRANTACCESSTOUSER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Object self;
	private java.math.BigDecimal iduserin;
	private java.math.BigDecimal idspaoorepofin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.Object getSelf(){return self;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.math.BigDecimal getIdspaoorepofin(){return idspaoorepofin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setSelf(java.lang.Object value){this.self=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setIdspaoorepofin(java.math.BigDecimal value){this.idspaoorepofin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    