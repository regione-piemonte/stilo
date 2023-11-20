/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGetclobfromtabcolBean")
public class DmpkUtilityGetclobfromtabcolBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETCLOBFROMTABCOL";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String nometabellain;
	private java.lang.String nomecolonnain;
	private java.lang.String rowidrecin;
	private java.lang.String clobout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getNometabellain(){return nometabellain;}
    public java.lang.String getNomecolonnain(){return nomecolonnain;}
    public java.lang.String getRowidrecin(){return rowidrecin;}
    public java.lang.String getClobout(){return clobout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setNometabellain(java.lang.String value){this.nometabellain=value;}
    public void setNomecolonnain(java.lang.String value){this.nomecolonnain=value;}
    public void setRowidrecin(java.lang.String value){this.rowidrecin=value;}
    public void setClobout(java.lang.String value){this.clobout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    