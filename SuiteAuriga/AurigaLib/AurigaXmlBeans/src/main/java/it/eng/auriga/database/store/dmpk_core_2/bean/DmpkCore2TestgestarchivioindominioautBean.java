/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCore2TestgestarchivioindominioautBean")
public class DmpkCore2TestgestarchivioindominioautBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_2_TESTGESTARCHIVIOINDOMINIOAUT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioautin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddominioautin(){return iddominioautin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIddominioautin(java.math.BigDecimal value){this.iddominioautin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    