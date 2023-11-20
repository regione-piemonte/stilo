/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityTrovagruppisoggintBean")
public class DmpkDefSecurityTrovagruppisoggintBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_TROVAGRUPPISOGGINT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer flgpreimpostafiltroin;
	private java.math.BigDecimal idgruppoio;
	private java.lang.String nomeio;
	private java.lang.Integer flgdidefinaclio;
	private java.lang.String ciprovgruppoio;
	private java.math.BigDecimal flgincludinonvldio;
	private java.lang.String tsvldio;
	private java.lang.String codapplownerio;
	private java.lang.String codistapplownerio;
	private java.math.BigDecimal flgrestrapplownerio;
	private java.lang.String flgtpobjappartenenteio;
	private java.math.BigDecimal idobjappartenenteio;
	private java.lang.String desobjappartenenteio;
	private java.lang.String livelliuoappartenenteio;
	private java.lang.String desuoappscrivaniatoio;
	private java.lang.String desuserscrivaniatoio;
	private java.lang.Integer flgancheappartindirettaio;
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
	private java.lang.String rigatagnamein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getFlgpreimpostafiltroin(){return flgpreimpostafiltroin;}
    public java.math.BigDecimal getIdgruppoio(){return idgruppoio;}
    public java.lang.String getNomeio(){return nomeio;}
    public java.lang.Integer getFlgdidefinaclio(){return flgdidefinaclio;}
    public java.lang.String getCiprovgruppoio(){return ciprovgruppoio;}
    public java.math.BigDecimal getFlgincludinonvldio(){return flgincludinonvldio;}
    public java.lang.String getTsvldio(){return tsvldio;}
    public java.lang.String getCodapplownerio(){return codapplownerio;}
    public java.lang.String getCodistapplownerio(){return codistapplownerio;}
    public java.math.BigDecimal getFlgrestrapplownerio(){return flgrestrapplownerio;}
    public java.lang.String getFlgtpobjappartenenteio(){return flgtpobjappartenenteio;}
    public java.math.BigDecimal getIdobjappartenenteio(){return idobjappartenenteio;}
    public java.lang.String getDesobjappartenenteio(){return desobjappartenenteio;}
    public java.lang.String getLivelliuoappartenenteio(){return livelliuoappartenenteio;}
    public java.lang.String getDesuoappscrivaniatoio(){return desuoappscrivaniatoio;}
    public java.lang.String getDesuserscrivaniatoio(){return desuserscrivaniatoio;}
    public java.lang.Integer getFlgancheappartindirettaio(){return flgancheappartindirettaio;}
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
    public java.lang.String getRigatagnamein(){return rigatagnamein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgpreimpostafiltroin(java.lang.Integer value){this.flgpreimpostafiltroin=value;}
    public void setIdgruppoio(java.math.BigDecimal value){this.idgruppoio=value;}
    public void setNomeio(java.lang.String value){this.nomeio=value;}
    public void setFlgdidefinaclio(java.lang.Integer value){this.flgdidefinaclio=value;}
    public void setCiprovgruppoio(java.lang.String value){this.ciprovgruppoio=value;}
    public void setFlgincludinonvldio(java.math.BigDecimal value){this.flgincludinonvldio=value;}
    public void setTsvldio(java.lang.String value){this.tsvldio=value;}
    public void setCodapplownerio(java.lang.String value){this.codapplownerio=value;}
    public void setCodistapplownerio(java.lang.String value){this.codistapplownerio=value;}
    public void setFlgrestrapplownerio(java.math.BigDecimal value){this.flgrestrapplownerio=value;}
    public void setFlgtpobjappartenenteio(java.lang.String value){this.flgtpobjappartenenteio=value;}
    public void setIdobjappartenenteio(java.math.BigDecimal value){this.idobjappartenenteio=value;}
    public void setDesobjappartenenteio(java.lang.String value){this.desobjappartenenteio=value;}
    public void setLivelliuoappartenenteio(java.lang.String value){this.livelliuoappartenenteio=value;}
    public void setDesuoappscrivaniatoio(java.lang.String value){this.desuoappscrivaniatoio=value;}
    public void setDesuserscrivaniatoio(java.lang.String value){this.desuserscrivaniatoio=value;}
    public void setFlgancheappartindirettaio(java.lang.Integer value){this.flgancheappartindirettaio=value;}
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
    public void setRigatagnamein(java.lang.String value){this.rigatagnamein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    