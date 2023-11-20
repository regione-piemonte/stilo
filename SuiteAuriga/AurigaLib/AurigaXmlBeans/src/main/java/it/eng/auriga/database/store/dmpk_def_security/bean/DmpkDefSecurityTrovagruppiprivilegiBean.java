/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityTrovagruppiprivilegiBean")
public class DmpkDefSecurityTrovagruppiprivilegiBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_TROVAGRUPPIPRIVILEGI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer flgpreimpostafiltroin;
	private java.math.BigDecimal idgruppoprivio;
	private java.lang.String nomegruppoprivio;
	private java.lang.String ciprovgruppoprivio;
	private java.math.BigDecimal flginclannullatiio;
	private java.lang.String flgtpobjprivonio;
	private java.lang.String ciobjprivonio;
	private java.lang.String desobjprivonio;
	private java.lang.String livelliclassifprivonio;
	private java.lang.String codapplownerio;
	private java.lang.String codistapplownerio;
	private java.math.BigDecimal flgrestrapplownerio;
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
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgstatoassegnin;
	private java.lang.String flgtpdestassegnin;
	private java.math.BigDecimal iddestassegnin;
	private java.lang.String rigatagnamein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getFlgpreimpostafiltroin(){return flgpreimpostafiltroin;}
    public java.math.BigDecimal getIdgruppoprivio(){return idgruppoprivio;}
    public java.lang.String getNomegruppoprivio(){return nomegruppoprivio;}
    public java.lang.String getCiprovgruppoprivio(){return ciprovgruppoprivio;}
    public java.math.BigDecimal getFlginclannullatiio(){return flginclannullatiio;}
    public java.lang.String getFlgtpobjprivonio(){return flgtpobjprivonio;}
    public java.lang.String getCiobjprivonio(){return ciobjprivonio;}
    public java.lang.String getDesobjprivonio(){return desobjprivonio;}
    public java.lang.String getLivelliclassifprivonio(){return livelliclassifprivonio;}
    public java.lang.String getCodapplownerio(){return codapplownerio;}
    public java.lang.String getCodistapplownerio(){return codistapplownerio;}
    public java.math.BigDecimal getFlgrestrapplownerio(){return flgrestrapplownerio;}
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
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgstatoassegnin(){return flgstatoassegnin;}
    public java.lang.String getFlgtpdestassegnin(){return flgtpdestassegnin;}
    public java.math.BigDecimal getIddestassegnin(){return iddestassegnin;}
    public java.lang.String getRigatagnamein(){return rigatagnamein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgpreimpostafiltroin(java.lang.Integer value){this.flgpreimpostafiltroin=value;}
    public void setIdgruppoprivio(java.math.BigDecimal value){this.idgruppoprivio=value;}
    public void setNomegruppoprivio(java.lang.String value){this.nomegruppoprivio=value;}
    public void setCiprovgruppoprivio(java.lang.String value){this.ciprovgruppoprivio=value;}
    public void setFlginclannullatiio(java.math.BigDecimal value){this.flginclannullatiio=value;}
    public void setFlgtpobjprivonio(java.lang.String value){this.flgtpobjprivonio=value;}
    public void setCiobjprivonio(java.lang.String value){this.ciobjprivonio=value;}
    public void setDesobjprivonio(java.lang.String value){this.desobjprivonio=value;}
    public void setLivelliclassifprivonio(java.lang.String value){this.livelliclassifprivonio=value;}
    public void setCodapplownerio(java.lang.String value){this.codapplownerio=value;}
    public void setCodistapplownerio(java.lang.String value){this.codistapplownerio=value;}
    public void setFlgrestrapplownerio(java.math.BigDecimal value){this.flgrestrapplownerio=value;}
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
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgstatoassegnin(java.lang.Integer value){this.flgstatoassegnin=value;}
    public void setFlgtpdestassegnin(java.lang.String value){this.flgtpdestassegnin=value;}
    public void setIddestassegnin(java.math.BigDecimal value){this.iddestassegnin=value;}
    public void setRigatagnamein(java.lang.String value){this.rigatagnamein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    