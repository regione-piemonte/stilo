/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityTo_datetimexmltagBean")
public class DmpkUtilityTo_datetimexmltagBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_TO_DATETIMEXMLTAG";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.util.Date datain;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.util.Date getDatain(){return datain;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setDatain(java.util.Date value){this.datain=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    