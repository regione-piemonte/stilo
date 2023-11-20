/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDocTypesTrovadoctypesBean")
public class DmpkDocTypesTrovadoctypesBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DOC_TYPES_TROVADOCTYPES";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer flgpreimpostafiltroin;
	private java.math.BigDecimal iddoctypeio;
	private java.lang.String nomeio;
	private java.lang.String descrizioneio;
	private java.math.BigDecimal iddoctypegenio;
	private java.lang.String nomedoctypegenio;
	private java.lang.Integer flgallegatoio;
	private java.lang.String ciprovdoctypeio;
	private java.lang.String codnaturaio;
	private java.lang.String flgtipoprovio;
	private java.lang.String codsupportoorigio;
	private java.lang.Integer periododiconservazionedaio;
	private java.lang.String codsupportoconservio;
	private java.lang.Integer flgdascansionareio;
	private java.math.BigDecimal idclassificazioneio;
	private java.lang.String livelliclassificazioneio;
	private java.lang.String desclassificazioneio;
	private java.math.BigDecimal idformatoelio;
	private java.lang.String nomeformatoelio;
	private java.lang.String estensioneformatoelio;
	private java.lang.Integer flgconfolderizzautoio;
	private java.lang.Integer flgconmodelloio;
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
	private java.lang.Integer flgrichabilxfirmain;
	private java.lang.Integer flgstatoabilin;
	private java.lang.String flgtpdestabilin;
	private java.math.BigDecimal iddestabilin;
	private java.lang.String opzioniabilin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getFlgpreimpostafiltroin(){return flgpreimpostafiltroin;}
    public java.math.BigDecimal getIddoctypeio(){return iddoctypeio;}
    public java.lang.String getNomeio(){return nomeio;}
    public java.lang.String getDescrizioneio(){return descrizioneio;}
    public java.math.BigDecimal getIddoctypegenio(){return iddoctypegenio;}
    public java.lang.String getNomedoctypegenio(){return nomedoctypegenio;}
    public java.lang.Integer getFlgallegatoio(){return flgallegatoio;}
    public java.lang.String getCiprovdoctypeio(){return ciprovdoctypeio;}
    public java.lang.String getCodnaturaio(){return codnaturaio;}
    public java.lang.String getFlgtipoprovio(){return flgtipoprovio;}
    public java.lang.String getCodsupportoorigio(){return codsupportoorigio;}
    public java.lang.Integer getPeriododiconservazionedaio(){return periododiconservazionedaio;}
    public java.lang.String getCodsupportoconservio(){return codsupportoconservio;}
    public java.lang.Integer getFlgdascansionareio(){return flgdascansionareio;}
    public java.math.BigDecimal getIdclassificazioneio(){return idclassificazioneio;}
    public java.lang.String getLivelliclassificazioneio(){return livelliclassificazioneio;}
    public java.lang.String getDesclassificazioneio(){return desclassificazioneio;}
    public java.math.BigDecimal getIdformatoelio(){return idformatoelio;}
    public java.lang.String getNomeformatoelio(){return nomeformatoelio;}
    public java.lang.String getEstensioneformatoelio(){return estensioneformatoelio;}
    public java.lang.Integer getFlgconfolderizzautoio(){return flgconfolderizzautoio;}
    public java.lang.Integer getFlgconmodelloio(){return flgconmodelloio;}
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
    public java.lang.Integer getFlgrichabilxfirmain(){return flgrichabilxfirmain;}
    public java.lang.Integer getFlgstatoabilin(){return flgstatoabilin;}
    public java.lang.String getFlgtpdestabilin(){return flgtpdestabilin;}
    public java.math.BigDecimal getIddestabilin(){return iddestabilin;}
    public java.lang.String getOpzioniabilin(){return opzioniabilin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgpreimpostafiltroin(java.lang.Integer value){this.flgpreimpostafiltroin=value;}
    public void setIddoctypeio(java.math.BigDecimal value){this.iddoctypeio=value;}
    public void setNomeio(java.lang.String value){this.nomeio=value;}
    public void setDescrizioneio(java.lang.String value){this.descrizioneio=value;}
    public void setIddoctypegenio(java.math.BigDecimal value){this.iddoctypegenio=value;}
    public void setNomedoctypegenio(java.lang.String value){this.nomedoctypegenio=value;}
    public void setFlgallegatoio(java.lang.Integer value){this.flgallegatoio=value;}
    public void setCiprovdoctypeio(java.lang.String value){this.ciprovdoctypeio=value;}
    public void setCodnaturaio(java.lang.String value){this.codnaturaio=value;}
    public void setFlgtipoprovio(java.lang.String value){this.flgtipoprovio=value;}
    public void setCodsupportoorigio(java.lang.String value){this.codsupportoorigio=value;}
    public void setPeriododiconservazionedaio(java.lang.Integer value){this.periododiconservazionedaio=value;}
    public void setCodsupportoconservio(java.lang.String value){this.codsupportoconservio=value;}
    public void setFlgdascansionareio(java.lang.Integer value){this.flgdascansionareio=value;}
    public void setIdclassificazioneio(java.math.BigDecimal value){this.idclassificazioneio=value;}
    public void setLivelliclassificazioneio(java.lang.String value){this.livelliclassificazioneio=value;}
    public void setDesclassificazioneio(java.lang.String value){this.desclassificazioneio=value;}
    public void setIdformatoelio(java.math.BigDecimal value){this.idformatoelio=value;}
    public void setNomeformatoelio(java.lang.String value){this.nomeformatoelio=value;}
    public void setEstensioneformatoelio(java.lang.String value){this.estensioneformatoelio=value;}
    public void setFlgconfolderizzautoio(java.lang.Integer value){this.flgconfolderizzautoio=value;}
    public void setFlgconmodelloio(java.lang.Integer value){this.flgconmodelloio=value;}
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