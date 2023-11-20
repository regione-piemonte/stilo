/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkTipiRegNumUdLoaddetttiporegnumBean")
public class DmpkTipiRegNumUdLoaddetttiporegnumBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_TIPI_REG_NUM_UD_LOADDETTTIPOREGNUM";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idtiporennumio;
	private java.lang.String codcategoriaout;
	private java.lang.String siglaout;
	private java.lang.String descrizioneout;
	private java.lang.String dtiniziovldout;
	private java.lang.String dtfinevldout;
	private java.lang.Integer flginternaout;
	private java.lang.String flgstatoregistroout;
	private java.lang.String tsiniziostatoregistroout;
	private java.lang.Integer periodicitadianniout;
	private java.lang.Integer annoinizionumerazioneout;
	private java.lang.Integer flgrichabilxvisualizzout;
	private java.lang.Integer flgrichabilxgestout;
	private java.lang.Integer flgrichabilxassegnout;
	private java.lang.Integer flgrichabilxfirmaout;
	private java.lang.String ciprovtiporegnumout;
	private java.lang.Integer flglockedout;
	private java.lang.Integer flgbuchiammessiout;
	private java.lang.String gruppoappout;
	private java.lang.String restrizioniversoregout;
	private java.math.BigDecimal flgctrabiluomittout;
	private java.math.BigDecimal nroinizialeout;
	private java.math.BigDecimal ultimonrogenearatoout;
	private java.lang.String dataultnrogeneratoout;
	private java.lang.String rowidout;
	private java.lang.String flgammescxtipidocout;
	private java.lang.String xmltipidocammescout;
	private java.lang.String xmlattraddxdocdeltipoout;
	private java.lang.String attributiaddout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdtiporennumio(){return idtiporennumio;}
    public java.lang.String getCodcategoriaout(){return codcategoriaout;}
    public java.lang.String getSiglaout(){return siglaout;}
    public java.lang.String getDescrizioneout(){return descrizioneout;}
    public java.lang.String getDtiniziovldout(){return dtiniziovldout;}
    public java.lang.String getDtfinevldout(){return dtfinevldout;}
    public java.lang.Integer getFlginternaout(){return flginternaout;}
    public java.lang.String getFlgstatoregistroout(){return flgstatoregistroout;}
    public java.lang.String getTsiniziostatoregistroout(){return tsiniziostatoregistroout;}
    public java.lang.Integer getPeriodicitadianniout(){return periodicitadianniout;}
    public java.lang.Integer getAnnoinizionumerazioneout(){return annoinizionumerazioneout;}
    public java.lang.Integer getFlgrichabilxvisualizzout(){return flgrichabilxvisualizzout;}
    public java.lang.Integer getFlgrichabilxgestout(){return flgrichabilxgestout;}
    public java.lang.Integer getFlgrichabilxassegnout(){return flgrichabilxassegnout;}
    public java.lang.Integer getFlgrichabilxfirmaout(){return flgrichabilxfirmaout;}
    public java.lang.String getCiprovtiporegnumout(){return ciprovtiporegnumout;}
    public java.lang.Integer getFlglockedout(){return flglockedout;}
    public java.lang.Integer getFlgbuchiammessiout(){return flgbuchiammessiout;}
    public java.lang.String getGruppoappout(){return gruppoappout;}
    public java.lang.String getRestrizioniversoregout(){return restrizioniversoregout;}
    public java.math.BigDecimal getFlgctrabiluomittout(){return flgctrabiluomittout;}
    public java.math.BigDecimal getNroinizialeout(){return nroinizialeout;}
    public java.math.BigDecimal getUltimonrogenearatoout(){return ultimonrogenearatoout;}
    public java.lang.String getDataultnrogeneratoout(){return dataultnrogeneratoout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getFlgammescxtipidocout(){return flgammescxtipidocout;}
    public java.lang.String getXmltipidocammescout(){return xmltipidocammescout;}
    public java.lang.String getXmlattraddxdocdeltipoout(){return xmlattraddxdocdeltipoout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdtiporennumio(java.math.BigDecimal value){this.idtiporennumio=value;}
    public void setCodcategoriaout(java.lang.String value){this.codcategoriaout=value;}
    public void setSiglaout(java.lang.String value){this.siglaout=value;}
    public void setDescrizioneout(java.lang.String value){this.descrizioneout=value;}
    public void setDtiniziovldout(java.lang.String value){this.dtiniziovldout=value;}
    public void setDtfinevldout(java.lang.String value){this.dtfinevldout=value;}
    public void setFlginternaout(java.lang.Integer value){this.flginternaout=value;}
    public void setFlgstatoregistroout(java.lang.String value){this.flgstatoregistroout=value;}
    public void setTsiniziostatoregistroout(java.lang.String value){this.tsiniziostatoregistroout=value;}
    public void setPeriodicitadianniout(java.lang.Integer value){this.periodicitadianniout=value;}
    public void setAnnoinizionumerazioneout(java.lang.Integer value){this.annoinizionumerazioneout=value;}
    public void setFlgrichabilxvisualizzout(java.lang.Integer value){this.flgrichabilxvisualizzout=value;}
    public void setFlgrichabilxgestout(java.lang.Integer value){this.flgrichabilxgestout=value;}
    public void setFlgrichabilxassegnout(java.lang.Integer value){this.flgrichabilxassegnout=value;}
    public void setFlgrichabilxfirmaout(java.lang.Integer value){this.flgrichabilxfirmaout=value;}
    public void setCiprovtiporegnumout(java.lang.String value){this.ciprovtiporegnumout=value;}
    public void setFlglockedout(java.lang.Integer value){this.flglockedout=value;}
    public void setFlgbuchiammessiout(java.lang.Integer value){this.flgbuchiammessiout=value;}
    public void setGruppoappout(java.lang.String value){this.gruppoappout=value;}
    public void setRestrizioniversoregout(java.lang.String value){this.restrizioniversoregout=value;}
    public void setFlgctrabiluomittout(java.math.BigDecimal value){this.flgctrabiluomittout=value;}
    public void setNroinizialeout(java.math.BigDecimal value){this.nroinizialeout=value;}
    public void setUltimonrogenearatoout(java.math.BigDecimal value){this.ultimonrogenearatoout=value;}
    public void setDataultnrogeneratoout(java.lang.String value){this.dataultnrogeneratoout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setFlgammescxtipidocout(java.lang.String value){this.flgammescxtipidocout=value;}
    public void setXmltipidocammescout(java.lang.String value){this.xmltipidocammescout=value;}
    public void setXmlattraddxdocdeltipoout(java.lang.String value){this.xmlattraddxdocdeltipoout=value;}
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