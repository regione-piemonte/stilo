/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityAddvarlistaxmlsezcacheBean")
public class DmpkUtilityAddvarlistaxmlsezcacheBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_ADDVARLISTAXMLSEZCACHE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String xmlsezionecacheio;
	private java.lang.String nomevariabilein;
	private java.lang.String xmllistain;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getXmlsezionecacheio(){return xmlsezionecacheio;}
    public java.lang.String getNomevariabilein(){return nomevariabilein;}
    public java.lang.String getXmllistain(){return xmllistain;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setXmlsezionecacheio(java.lang.String value){this.xmlsezionecacheio=value;}
    public void setNomevariabilein(java.lang.String value){this.nomevariabilein=value;}
    public void setXmllistain(java.lang.String value){this.xmllistain=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    