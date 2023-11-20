/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkTitolarioLoaddettpianoclassifBean")
public class DmpkTitolarioLoaddettpianoclassifBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_TITOLARIO_LOADDETTPIANOCLASSIF";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idpianoclassifin;
	private java.lang.String dtadozioneout;
	private java.lang.String dtdismissioneout;
	private java.lang.String noteout;
	private java.lang.String provcipianoout;
	private java.lang.String xmllivellipianoout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdpianoclassifin(){return idpianoclassifin;}
    public java.lang.String getDtadozioneout(){return dtadozioneout;}
    public java.lang.String getDtdismissioneout(){return dtdismissioneout;}
    public java.lang.String getNoteout(){return noteout;}
    public java.lang.String getProvcipianoout(){return provcipianoout;}
    public java.lang.String getXmllivellipianoout(){return xmllivellipianoout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdpianoclassifin(java.math.BigDecimal value){this.idpianoclassifin=value;}
    public void setDtadozioneout(java.lang.String value){this.dtadozioneout=value;}
    public void setDtdismissioneout(java.lang.String value){this.dtdismissioneout=value;}
    public void setNoteout(java.lang.String value){this.noteout=value;}
    public void setProvcipianoout(java.lang.String value){this.provcipianoout=value;}
    public void setXmllivellipianoout(java.lang.String value){this.xmllivellipianoout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    