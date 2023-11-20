/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityCreanodovarlistaxmlsezcacheBean")
public class DmpkUtilityCreanodovarlistaxmlsezcacheBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_CREANODOVARLISTAXMLSEZCACHE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String nomevariabilein;
	private java.lang.String xmllistain;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getNomevariabilein(){return nomevariabilein;}
    public java.lang.String getXmllistain(){return xmllistain;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setNomevariabilein(java.lang.String value){this.nomevariabilein=value;}
    public void setXmllistain(java.lang.String value){this.xmllistain=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    