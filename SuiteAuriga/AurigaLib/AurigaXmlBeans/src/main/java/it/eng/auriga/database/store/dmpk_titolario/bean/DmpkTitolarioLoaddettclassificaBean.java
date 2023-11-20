/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkTitolarioLoaddettclassificaBean")
public class DmpkTitolarioLoaddettclassificaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_TITOLARIO_LOADDETTCLASSIFICA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idclassificazionein;
	private java.math.BigDecimal idpianoclassifout;
	private java.math.BigDecimal idclassificazionesupout;
	private java.lang.String nrilivelliclassifsupout;
	private java.lang.String nrolivelloout;
	private java.lang.String desclassificazioneout;
	private java.lang.String dtiniziovldout;
	private java.lang.String dtfinevldout;
	private java.lang.String parolechiaveout;
	private java.math.BigDecimal nroposizioneout;
	private java.lang.String codsupportoconservout;
	private java.lang.String dessupportoconservout;
	private java.lang.Integer flgconservpermanenteout;
	private java.lang.Integer periodoconservazionout;
	private java.lang.Integer flgnumcontinuaout;
	private java.lang.Integer flgfolderizzaxannoout;
	private java.lang.Integer flgrichabilxvisout;
	private java.lang.Integer flgrichabilxgestout;
	private java.lang.Integer flgrichabilxfirmaout;
	private java.lang.Integer flgrichabilxassegnout;
	private java.lang.Integer flgrichabilxaperfascout;
	private java.lang.Integer flgclassifinibitaout;
	private java.lang.String provciclassifout;
	private java.lang.String xmltemplatexfascout;
	private java.lang.String rowidout;
	private java.lang.String attributiaddout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdclassificazionein(){return idclassificazionein;}
    public java.math.BigDecimal getIdpianoclassifout(){return idpianoclassifout;}
    public java.math.BigDecimal getIdclassificazionesupout(){return idclassificazionesupout;}
    public java.lang.String getNrilivelliclassifsupout(){return nrilivelliclassifsupout;}
    public java.lang.String getNrolivelloout(){return nrolivelloout;}
    public java.lang.String getDesclassificazioneout(){return desclassificazioneout;}
    public java.lang.String getDtiniziovldout(){return dtiniziovldout;}
    public java.lang.String getDtfinevldout(){return dtfinevldout;}
    public java.lang.String getParolechiaveout(){return parolechiaveout;}
    public java.math.BigDecimal getNroposizioneout(){return nroposizioneout;}
    public java.lang.String getCodsupportoconservout(){return codsupportoconservout;}
    public java.lang.String getDessupportoconservout(){return dessupportoconservout;}
    public java.lang.Integer getFlgconservpermanenteout(){return flgconservpermanenteout;}
    public java.lang.Integer getPeriodoconservazionout(){return periodoconservazionout;}
    public java.lang.Integer getFlgnumcontinuaout(){return flgnumcontinuaout;}
    public java.lang.Integer getFlgfolderizzaxannoout(){return flgfolderizzaxannoout;}
    public java.lang.Integer getFlgrichabilxvisout(){return flgrichabilxvisout;}
    public java.lang.Integer getFlgrichabilxgestout(){return flgrichabilxgestout;}
    public java.lang.Integer getFlgrichabilxfirmaout(){return flgrichabilxfirmaout;}
    public java.lang.Integer getFlgrichabilxassegnout(){return flgrichabilxassegnout;}
    public java.lang.Integer getFlgrichabilxaperfascout(){return flgrichabilxaperfascout;}
    public java.lang.Integer getFlgclassifinibitaout(){return flgclassifinibitaout;}
    public java.lang.String getProvciclassifout(){return provciclassifout;}
    public java.lang.String getXmltemplatexfascout(){return xmltemplatexfascout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdclassificazionein(java.math.BigDecimal value){this.idclassificazionein=value;}
    public void setIdpianoclassifout(java.math.BigDecimal value){this.idpianoclassifout=value;}
    public void setIdclassificazionesupout(java.math.BigDecimal value){this.idclassificazionesupout=value;}
    public void setNrilivelliclassifsupout(java.lang.String value){this.nrilivelliclassifsupout=value;}
    public void setNrolivelloout(java.lang.String value){this.nrolivelloout=value;}
    public void setDesclassificazioneout(java.lang.String value){this.desclassificazioneout=value;}
    public void setDtiniziovldout(java.lang.String value){this.dtiniziovldout=value;}
    public void setDtfinevldout(java.lang.String value){this.dtfinevldout=value;}
    public void setParolechiaveout(java.lang.String value){this.parolechiaveout=value;}
    public void setNroposizioneout(java.math.BigDecimal value){this.nroposizioneout=value;}
    public void setCodsupportoconservout(java.lang.String value){this.codsupportoconservout=value;}
    public void setDessupportoconservout(java.lang.String value){this.dessupportoconservout=value;}
    public void setFlgconservpermanenteout(java.lang.Integer value){this.flgconservpermanenteout=value;}
    public void setPeriodoconservazionout(java.lang.Integer value){this.periodoconservazionout=value;}
    public void setFlgnumcontinuaout(java.lang.Integer value){this.flgnumcontinuaout=value;}
    public void setFlgfolderizzaxannoout(java.lang.Integer value){this.flgfolderizzaxannoout=value;}
    public void setFlgrichabilxvisout(java.lang.Integer value){this.flgrichabilxvisout=value;}
    public void setFlgrichabilxgestout(java.lang.Integer value){this.flgrichabilxgestout=value;}
    public void setFlgrichabilxfirmaout(java.lang.Integer value){this.flgrichabilxfirmaout=value;}
    public void setFlgrichabilxassegnout(java.lang.Integer value){this.flgrichabilxassegnout=value;}
    public void setFlgrichabilxaperfascout(java.lang.Integer value){this.flgrichabilxaperfascout=value;}
    public void setFlgclassifinibitaout(java.lang.Integer value){this.flgclassifinibitaout=value;}
    public void setProvciclassifout(java.lang.String value){this.provciclassifout=value;}
    public void setXmltemplatexfascout(java.lang.String value){this.xmltemplatexfascout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    