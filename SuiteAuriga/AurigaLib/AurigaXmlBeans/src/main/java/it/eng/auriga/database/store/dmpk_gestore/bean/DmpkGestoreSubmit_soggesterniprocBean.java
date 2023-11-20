/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkGestoreSubmit_soggesterniprocBean")
public class DmpkGestoreSubmit_soggesterniprocBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_GESTORE_SUBMIT_SOGGESTERNIPROC";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.Integer flgvldsoggvsrubricain;
	private java.lang.Integer flgalimentarubricain;
	private java.lang.Integer flgcfopivaobblin;
	private java.lang.String listaxmlin;
	private java.lang.String notein;
	private java.lang.String msgattin;
	private java.lang.String urltoredirecttoout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.Integer getFlgvldsoggvsrubricain(){return flgvldsoggvsrubricain;}
    public java.lang.Integer getFlgalimentarubricain(){return flgalimentarubricain;}
    public java.lang.Integer getFlgcfopivaobblin(){return flgcfopivaobblin;}
    public java.lang.String getListaxmlin(){return listaxmlin;}
    public java.lang.String getNotein(){return notein;}
    public java.lang.String getMsgattin(){return msgattin;}
    public java.lang.String getUrltoredirecttoout(){return urltoredirecttoout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setFlgvldsoggvsrubricain(java.lang.Integer value){this.flgvldsoggvsrubricain=value;}
    public void setFlgalimentarubricain(java.lang.Integer value){this.flgalimentarubricain=value;}
    public void setFlgcfopivaobblin(java.lang.Integer value){this.flgcfopivaobblin=value;}
    public void setListaxmlin(java.lang.String value){this.listaxmlin=value;}
    public void setNotein(java.lang.String value){this.notein=value;}
    public void setMsgattin(java.lang.String value){this.msgattin=value;}
    public void setUrltoredirecttoout(java.lang.String value){this.urltoredirecttoout=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    