/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityTestappartenenzaagruppoBean")
public class DmpkUtilityTestappartenenzaagruppoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_TESTAPPARTENENZAAGRUPPO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idgruppoin;
	private java.lang.String flgtpobjtotestin;
	private java.math.BigDecimal idobjtotestin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdgruppoin(){return idgruppoin;}
    public java.lang.String getFlgtpobjtotestin(){return flgtpobjtotestin;}
    public java.math.BigDecimal getIdobjtotestin(){return idobjtotestin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdgruppoin(java.math.BigDecimal value){this.idgruppoin=value;}
    public void setFlgtpobjtotestin(java.lang.String value){this.flgtpobjtotestin=value;}
    public void setIdobjtotestin(java.math.BigDecimal value){this.idobjtotestin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    