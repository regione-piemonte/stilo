/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkToponomasticaTestabiluo_modtoponBean")
public class DmpkToponomasticaTestabiluo_modtoponBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_TOPONOMASTICA_TESTABILUO_MODTOPON";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal ciidspaooin;
	private java.lang.String citoponomasticoin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getCiidspaooin(){return ciidspaooin;}
    public java.lang.String getCitoponomasticoin(){return citoponomasticoin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCiidspaooin(java.math.BigDecimal value){this.ciidspaooin=value;}
    public void setCitoponomasticoin(java.lang.String value){this.citoponomasticoin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    