/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityTestifapplestprivilegiataBean")
public class DmpkUtilityTestifapplestprivilegiataBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_TESTIFAPPLESTPRIVILEGIATA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codapplicazionein;
	private java.lang.String codistanzaapplicazionein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodapplicazionein(){return codapplicazionein;}
    public java.lang.String getCodistanzaapplicazionein(){return codistanzaapplicazionein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodapplicazionein(java.lang.String value){this.codapplicazionein=value;}
    public void setCodistanzaapplicazionein(java.lang.String value){this.codistanzaapplicazionein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    