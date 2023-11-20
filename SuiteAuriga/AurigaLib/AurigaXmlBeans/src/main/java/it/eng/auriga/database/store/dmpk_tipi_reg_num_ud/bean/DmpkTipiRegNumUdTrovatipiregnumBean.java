/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkTipiRegNumUdTrovatipiregnumBean")
public class DmpkTipiRegNumUdTrovatipiregnumBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_TIPI_REG_NUM_UD_TROVATIPIREGNUM";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer flgpreimpostafiltroin;
	private java.math.BigDecimal idtiporegnumio;
	private java.lang.String codcategoriaio;
	private java.lang.String siglaio;
	private java.lang.String descrizioneio;
	private java.lang.Integer flginternaio;
	private java.lang.String flgstatoregistroio;
	private java.lang.Integer periodicitadianniio;
	private java.lang.Integer periodicitanondianniio;
	private java.math.BigDecimal iddoctypeammessoio;
	private java.lang.String nomedoctypeammessoio;
	private java.math.BigDecimal iddoctypeesclusoio;
	private java.lang.String nomedoctypeesclusoio;
	private java.lang.String ciprovtiporegnumio;
	private java.math.BigDecimal flgsolovldio;
	private java.lang.String tsvldio;
	private java.lang.String gruppoappio;
	private java.lang.String altricriteriio;
	private java.lang.String colorderbyio;
	private java.lang.String flgdescorderbyio;
	private java.lang.Integer flgsenzapaginazionein;
	private java.lang.Integer nropaginaio;
	private java.lang.Integer bachsizeio;
	private java.lang.Integer overflowlimitin;
	private java.lang.Integer flgsenzatotin;
	private java.lang.Integer nrototrecout;
	private java.lang.Integer nrorecinpaginaout;
	private java.lang.String listaxmlout;
	private java.lang.Integer flgabilinsout;
	private java.lang.Integer flgabilupdout;
	private java.lang.Integer flgabildelout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgrichabilxvisualizzin;
	private java.lang.Integer flgrichabilxgestin;
	private java.lang.Integer flgrichabilxassegnin;
	private java.lang.Integer flgrichabilxfirmain;
	private java.lang.Integer flgstatoabilin;
	private java.lang.String flgtpdestabilin;
	private java.math.BigDecimal iddestabilin;
	private java.lang.String opzioniabilin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getFlgpreimpostafiltroin(){return flgpreimpostafiltroin;}
    public java.math.BigDecimal getIdtiporegnumio(){return idtiporegnumio;}
    public java.lang.String getCodcategoriaio(){return codcategoriaio;}
    public java.lang.String getSiglaio(){return siglaio;}
    public java.lang.String getDescrizioneio(){return descrizioneio;}
    public java.lang.Integer getFlginternaio(){return flginternaio;}
    public java.lang.String getFlgstatoregistroio(){return flgstatoregistroio;}
    public java.lang.Integer getPeriodicitadianniio(){return periodicitadianniio;}
    public java.lang.Integer getPeriodicitanondianniio(){return periodicitanondianniio;}
    public java.math.BigDecimal getIddoctypeammessoio(){return iddoctypeammessoio;}
    public java.lang.String getNomedoctypeammessoio(){return nomedoctypeammessoio;}
    public java.math.BigDecimal getIddoctypeesclusoio(){return iddoctypeesclusoio;}
    public java.lang.String getNomedoctypeesclusoio(){return nomedoctypeesclusoio;}
    public java.lang.String getCiprovtiporegnumio(){return ciprovtiporegnumio;}
    public java.math.BigDecimal getFlgsolovldio(){return flgsolovldio;}
    public java.lang.String getTsvldio(){return tsvldio;}
    public java.lang.String getGruppoappio(){return gruppoappio;}
    public java.lang.String getAltricriteriio(){return altricriteriio;}
    public java.lang.String getColorderbyio(){return colorderbyio;}
    public java.lang.String getFlgdescorderbyio(){return flgdescorderbyio;}
    public java.lang.Integer getFlgsenzapaginazionein(){return flgsenzapaginazionein;}
    public java.lang.Integer getNropaginaio(){return nropaginaio;}
    public java.lang.Integer getBachsizeio(){return bachsizeio;}
    public java.lang.Integer getOverflowlimitin(){return overflowlimitin;}
    public java.lang.Integer getFlgsenzatotin(){return flgsenzatotin;}
    public java.lang.Integer getNrototrecout(){return nrototrecout;}
    public java.lang.Integer getNrorecinpaginaout(){return nrorecinpaginaout;}
    public java.lang.String getListaxmlout(){return listaxmlout;}
    public java.lang.Integer getFlgabilinsout(){return flgabilinsout;}
    public java.lang.Integer getFlgabilupdout(){return flgabilupdout;}
    public java.lang.Integer getFlgabildelout(){return flgabildelout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgrichabilxvisualizzin(){return flgrichabilxvisualizzin;}
    public java.lang.Integer getFlgrichabilxgestin(){return flgrichabilxgestin;}
    public java.lang.Integer getFlgrichabilxassegnin(){return flgrichabilxassegnin;}
    public java.lang.Integer getFlgrichabilxfirmain(){return flgrichabilxfirmain;}
    public java.lang.Integer getFlgstatoabilin(){return flgstatoabilin;}
    public java.lang.String getFlgtpdestabilin(){return flgtpdestabilin;}
    public java.math.BigDecimal getIddestabilin(){return iddestabilin;}
    public java.lang.String getOpzioniabilin(){return opzioniabilin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgpreimpostafiltroin(java.lang.Integer value){this.flgpreimpostafiltroin=value;}
    public void setIdtiporegnumio(java.math.BigDecimal value){this.idtiporegnumio=value;}
    public void setCodcategoriaio(java.lang.String value){this.codcategoriaio=value;}
    public void setSiglaio(java.lang.String value){this.siglaio=value;}
    public void setDescrizioneio(java.lang.String value){this.descrizioneio=value;}
    public void setFlginternaio(java.lang.Integer value){this.flginternaio=value;}
    public void setFlgstatoregistroio(java.lang.String value){this.flgstatoregistroio=value;}
    public void setPeriodicitadianniio(java.lang.Integer value){this.periodicitadianniio=value;}
    public void setPeriodicitanondianniio(java.lang.Integer value){this.periodicitanondianniio=value;}
    public void setIddoctypeammessoio(java.math.BigDecimal value){this.iddoctypeammessoio=value;}
    public void setNomedoctypeammessoio(java.lang.String value){this.nomedoctypeammessoio=value;}
    public void setIddoctypeesclusoio(java.math.BigDecimal value){this.iddoctypeesclusoio=value;}
    public void setNomedoctypeesclusoio(java.lang.String value){this.nomedoctypeesclusoio=value;}
    public void setCiprovtiporegnumio(java.lang.String value){this.ciprovtiporegnumio=value;}
    public void setFlgsolovldio(java.math.BigDecimal value){this.flgsolovldio=value;}
    public void setTsvldio(java.lang.String value){this.tsvldio=value;}
    public void setGruppoappio(java.lang.String value){this.gruppoappio=value;}
    public void setAltricriteriio(java.lang.String value){this.altricriteriio=value;}
    public void setColorderbyio(java.lang.String value){this.colorderbyio=value;}
    public void setFlgdescorderbyio(java.lang.String value){this.flgdescorderbyio=value;}
    public void setFlgsenzapaginazionein(java.lang.Integer value){this.flgsenzapaginazionein=value;}
    public void setNropaginaio(java.lang.Integer value){this.nropaginaio=value;}
    public void setBachsizeio(java.lang.Integer value){this.bachsizeio=value;}
    public void setOverflowlimitin(java.lang.Integer value){this.overflowlimitin=value;}
    public void setFlgsenzatotin(java.lang.Integer value){this.flgsenzatotin=value;}
    public void setNrototrecout(java.lang.Integer value){this.nrototrecout=value;}
    public void setNrorecinpaginaout(java.lang.Integer value){this.nrorecinpaginaout=value;}
    public void setListaxmlout(java.lang.String value){this.listaxmlout=value;}
    public void setFlgabilinsout(java.lang.Integer value){this.flgabilinsout=value;}
    public void setFlgabilupdout(java.lang.Integer value){this.flgabilupdout=value;}
    public void setFlgabildelout(java.lang.Integer value){this.flgabildelout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgrichabilxvisualizzin(java.lang.Integer value){this.flgrichabilxvisualizzin=value;}
    public void setFlgrichabilxgestin(java.lang.Integer value){this.flgrichabilxgestin=value;}
    public void setFlgrichabilxassegnin(java.lang.Integer value){this.flgrichabilxassegnin=value;}
    public void setFlgrichabilxfirmain(java.lang.Integer value){this.flgrichabilxfirmain=value;}
    public void setFlgstatoabilin(java.lang.Integer value){this.flgstatoabilin=value;}
    public void setFlgtpdestabilin(java.lang.String value){this.flgtpdestabilin=value;}
    public void setIddestabilin(java.math.BigDecimal value){this.iddestabilin=value;}
    public void setOpzioniabilin(java.lang.String value){this.opzioniabilin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    