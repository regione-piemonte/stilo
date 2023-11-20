/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntMgoEmailLoaddettregolaregautoBean")
public class DmpkIntMgoEmailLoaddettregolaregautoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_MGO_EMAIL_LOADDETTREGOLAREGAUTO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idregolain;
	private java.lang.String nomeregolaout;
	private java.lang.String desregolaout;
	private java.lang.String xmlfiltroout;
	private java.lang.String xmldatiregout;
	private java.lang.String dtattivazioneout;
	private java.lang.String dtcessazioneout;
	private java.lang.String dtiniziosospensioneout;
	private java.lang.String dtfinesospensioneout;
	private java.lang.String statoout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdregolain(){return idregolain;}
    public java.lang.String getNomeregolaout(){return nomeregolaout;}
    public java.lang.String getDesregolaout(){return desregolaout;}
    public java.lang.String getXmlfiltroout(){return xmlfiltroout;}
    public java.lang.String getXmldatiregout(){return xmldatiregout;}
    public java.lang.String getDtattivazioneout(){return dtattivazioneout;}
    public java.lang.String getDtcessazioneout(){return dtcessazioneout;}
    public java.lang.String getDtiniziosospensioneout(){return dtiniziosospensioneout;}
    public java.lang.String getDtfinesospensioneout(){return dtfinesospensioneout;}
    public java.lang.String getStatoout(){return statoout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdregolain(java.math.BigDecimal value){this.idregolain=value;}
    public void setNomeregolaout(java.lang.String value){this.nomeregolaout=value;}
    public void setDesregolaout(java.lang.String value){this.desregolaout=value;}
    public void setXmlfiltroout(java.lang.String value){this.xmlfiltroout=value;}
    public void setXmldatiregout(java.lang.String value){this.xmldatiregout=value;}
    public void setDtattivazioneout(java.lang.String value){this.dtattivazioneout=value;}
    public void setDtcessazioneout(java.lang.String value){this.dtcessazioneout=value;}
    public void setDtiniziosospensioneout(java.lang.String value){this.dtiniziosospensioneout=value;}
    public void setDtfinesospensioneout(java.lang.String value){this.dtfinesospensioneout=value;}
    public void setStatoout(java.lang.String value){this.statoout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    