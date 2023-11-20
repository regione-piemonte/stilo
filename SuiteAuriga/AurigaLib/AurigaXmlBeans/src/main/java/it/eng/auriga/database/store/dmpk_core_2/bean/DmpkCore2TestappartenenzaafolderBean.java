/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCore2TestappartenenzaafolderBean")
public class DmpkCore2TestappartenenzaafolderBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_2_TESTAPPARTENENZAAFOLDER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idfolderouterin;
	private java.lang.String flgtpobjinnerin;
	private java.math.BigDecimal idobjinnerin;
	private java.lang.Integer flgappdirettain;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdfolderouterin(){return idfolderouterin;}
    public java.lang.String getFlgtpobjinnerin(){return flgtpobjinnerin;}
    public java.math.BigDecimal getIdobjinnerin(){return idobjinnerin;}
    public java.lang.Integer getFlgappdirettain(){return flgappdirettain;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdfolderouterin(java.math.BigDecimal value){this.idfolderouterin=value;}
    public void setFlgtpobjinnerin(java.lang.String value){this.flgtpobjinnerin=value;}
    public void setIdobjinnerin(java.math.BigDecimal value){this.idobjinnerin=value;}
    public void setFlgappdirettain(java.lang.Integer value){this.flgappdirettain=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    