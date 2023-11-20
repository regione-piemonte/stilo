/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCore2ContaoggomonimiinfolderBean")
public class DmpkCore2ContaoggomonimiinfolderBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_2_CONTAOGGOMONIMIINFOLDER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idfolderin;
	private java.lang.String nomeoggettiin;
	private java.lang.String flgtipooggettoin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdfolderin(){return idfolderin;}
    public java.lang.String getNomeoggettiin(){return nomeoggettiin;}
    public java.lang.String getFlgtipooggettoin(){return flgtipooggettoin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdfolderin(java.math.BigDecimal value){this.idfolderin=value;}
    public void setNomeoggettiin(java.lang.String value){this.nomeoggettiin=value;}
    public void setFlgtipooggettoin(java.lang.String value){this.flgtipooggettoin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    