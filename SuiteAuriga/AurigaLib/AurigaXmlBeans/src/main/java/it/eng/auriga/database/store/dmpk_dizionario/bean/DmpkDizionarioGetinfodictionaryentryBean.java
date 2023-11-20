/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDizionarioGetinfodictionaryentryBean")
public class DmpkDizionarioGetinfodictionaryentryBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DIZIONARIO_GETINFODICTIONARYENTRY";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String dictionaryentryin;
	private java.lang.Integer flgcodobbligatorioout;
	private java.lang.String dictentryvincoloout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getDictionaryentryin(){return dictionaryentryin;}
    public java.lang.Integer getFlgcodobbligatorioout(){return flgcodobbligatorioout;}
    public java.lang.String getDictentryvincoloout(){return dictentryvincoloout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setDictionaryentryin(java.lang.String value){this.dictionaryentryin=value;}
    public void setFlgcodobbligatorioout(java.lang.Integer value){this.flgcodobbligatorioout=value;}
    public void setDictentryvincoloout(java.lang.String value){this.dictentryvincoloout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    