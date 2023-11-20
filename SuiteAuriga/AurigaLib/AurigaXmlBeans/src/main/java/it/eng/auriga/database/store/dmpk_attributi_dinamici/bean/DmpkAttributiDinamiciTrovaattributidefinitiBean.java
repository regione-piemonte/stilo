/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAttributiDinamiciTrovaattributidefinitiBean")
public class DmpkAttributiDinamiciTrovaattributidefinitiBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ATTRIBUTI_DINAMICI_TROVAATTRIBUTIDEFINITI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer flgpreimpostafiltroin;
	private java.lang.String nomeattrio;
	private java.lang.String labelattrio;
	private java.lang.String tipoattrio;
	private java.lang.String desattrio;
	private java.math.BigDecimal flginclannullatiio;
	private java.math.BigDecimal flgescludisottoattrio;
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
	private java.lang.Integer flgmostraaclout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String nomeattrsupin;
	private java.math.BigDecimal idmodellonotin;
	private java.lang.String nomeattrtoforcein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getFlgpreimpostafiltroin(){return flgpreimpostafiltroin;}
    public java.lang.String getNomeattrio(){return nomeattrio;}
    public java.lang.String getLabelattrio(){return labelattrio;}
    public java.lang.String getTipoattrio(){return tipoattrio;}
    public java.lang.String getDesattrio(){return desattrio;}
    public java.math.BigDecimal getFlginclannullatiio(){return flginclannullatiio;}
    public java.math.BigDecimal getFlgescludisottoattrio(){return flgescludisottoattrio;}
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
    public java.lang.Integer getFlgmostraaclout(){return flgmostraaclout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getNomeattrsupin(){return nomeattrsupin;}
    public java.math.BigDecimal getIdmodellonotin(){return idmodellonotin;}
    public java.lang.String getNomeattrtoforcein(){return nomeattrtoforcein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgpreimpostafiltroin(java.lang.Integer value){this.flgpreimpostafiltroin=value;}
    public void setNomeattrio(java.lang.String value){this.nomeattrio=value;}
    public void setLabelattrio(java.lang.String value){this.labelattrio=value;}
    public void setTipoattrio(java.lang.String value){this.tipoattrio=value;}
    public void setDesattrio(java.lang.String value){this.desattrio=value;}
    public void setFlginclannullatiio(java.math.BigDecimal value){this.flginclannullatiio=value;}
    public void setFlgescludisottoattrio(java.math.BigDecimal value){this.flgescludisottoattrio=value;}
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
    public void setFlgmostraaclout(java.lang.Integer value){this.flgmostraaclout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setNomeattrsupin(java.lang.String value){this.nomeattrsupin=value;}
    public void setIdmodellonotin(java.math.BigDecimal value){this.idmodellonotin=value;}
    public void setNomeattrtoforcein(java.lang.String value){this.nomeattrtoforcein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    