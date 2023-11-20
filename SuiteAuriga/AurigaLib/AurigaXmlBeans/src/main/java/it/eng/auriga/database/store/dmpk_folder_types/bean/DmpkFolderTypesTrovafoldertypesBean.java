/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkFolderTypesTrovafoldertypesBean")
public class DmpkFolderTypesTrovafoldertypesBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_FOLDER_TYPES_TROVAFOLDERTYPES";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer flgpreimpostafiltroin;
	private java.math.BigDecimal idfoldertypeio;
	private java.lang.String nomeio;
	private java.math.BigDecimal idfoldertypegenio;
	private java.lang.String nomefoldertypegenio;
	private java.lang.String ciprovfoldertypeio;
	private java.lang.Integer periododiconservazionedaio;
	private java.lang.String codsupportoconservio;
	private java.lang.Integer flgdascansionareio;
	private java.math.BigDecimal idclassificazioneio;
	private java.lang.String livelliclassificazioneio;
	private java.lang.String desclassificazioneio;
	private java.lang.Integer flgconfolderizzautoio;
	private java.lang.String codapplicazioneio;
	private java.lang.String codistapplicazioneio;
	private java.math.BigDecimal flginclannullatiio;
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
	private java.lang.Integer flgstatoabilin;
	private java.lang.String flgtpdestabilin;
	private java.math.BigDecimal iddestabilin;
	private java.lang.String opzioniabilin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getFlgpreimpostafiltroin(){return flgpreimpostafiltroin;}
    public java.math.BigDecimal getIdfoldertypeio(){return idfoldertypeio;}
    public java.lang.String getNomeio(){return nomeio;}
    public java.math.BigDecimal getIdfoldertypegenio(){return idfoldertypegenio;}
    public java.lang.String getNomefoldertypegenio(){return nomefoldertypegenio;}
    public java.lang.String getCiprovfoldertypeio(){return ciprovfoldertypeio;}
    public java.lang.Integer getPeriododiconservazionedaio(){return periododiconservazionedaio;}
    public java.lang.String getCodsupportoconservio(){return codsupportoconservio;}
    public java.lang.Integer getFlgdascansionareio(){return flgdascansionareio;}
    public java.math.BigDecimal getIdclassificazioneio(){return idclassificazioneio;}
    public java.lang.String getLivelliclassificazioneio(){return livelliclassificazioneio;}
    public java.lang.String getDesclassificazioneio(){return desclassificazioneio;}
    public java.lang.Integer getFlgconfolderizzautoio(){return flgconfolderizzautoio;}
    public java.lang.String getCodapplicazioneio(){return codapplicazioneio;}
    public java.lang.String getCodistapplicazioneio(){return codistapplicazioneio;}
    public java.math.BigDecimal getFlginclannullatiio(){return flginclannullatiio;}
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
    public java.lang.Integer getFlgstatoabilin(){return flgstatoabilin;}
    public java.lang.String getFlgtpdestabilin(){return flgtpdestabilin;}
    public java.math.BigDecimal getIddestabilin(){return iddestabilin;}
    public java.lang.String getOpzioniabilin(){return opzioniabilin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgpreimpostafiltroin(java.lang.Integer value){this.flgpreimpostafiltroin=value;}
    public void setIdfoldertypeio(java.math.BigDecimal value){this.idfoldertypeio=value;}
    public void setNomeio(java.lang.String value){this.nomeio=value;}
    public void setIdfoldertypegenio(java.math.BigDecimal value){this.idfoldertypegenio=value;}
    public void setNomefoldertypegenio(java.lang.String value){this.nomefoldertypegenio=value;}
    public void setCiprovfoldertypeio(java.lang.String value){this.ciprovfoldertypeio=value;}
    public void setPeriododiconservazionedaio(java.lang.Integer value){this.periododiconservazionedaio=value;}
    public void setCodsupportoconservio(java.lang.String value){this.codsupportoconservio=value;}
    public void setFlgdascansionareio(java.lang.Integer value){this.flgdascansionareio=value;}
    public void setIdclassificazioneio(java.math.BigDecimal value){this.idclassificazioneio=value;}
    public void setLivelliclassificazioneio(java.lang.String value){this.livelliclassificazioneio=value;}
    public void setDesclassificazioneio(java.lang.String value){this.desclassificazioneio=value;}
    public void setFlgconfolderizzautoio(java.lang.Integer value){this.flgconfolderizzautoio=value;}
    public void setCodapplicazioneio(java.lang.String value){this.codapplicazioneio=value;}
    public void setCodistapplicazioneio(java.lang.String value){this.codistapplicazioneio=value;}
    public void setFlginclannullatiio(java.math.BigDecimal value){this.flginclannullatiio=value;}
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
    public void setFlgstatoabilin(java.lang.Integer value){this.flgstatoabilin=value;}
    public void setFlgtpdestabilin(java.lang.String value){this.flgtpdestabilin=value;}
    public void setIddestabilin(java.math.BigDecimal value){this.iddestabilin=value;}
    public void setOpzioniabilin(java.lang.String value){this.opzioniabilin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    