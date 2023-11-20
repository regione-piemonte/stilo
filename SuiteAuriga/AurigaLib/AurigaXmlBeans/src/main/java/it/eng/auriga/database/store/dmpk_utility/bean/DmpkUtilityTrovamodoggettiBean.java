/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityTrovamodoggettiBean")
public class DmpkUtilityTrovamodoggettiBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_TROVAMODOGGETTI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String oggettoin;
	private java.lang.String cinomemodelloin;
	private java.lang.String provcimodelloin;
	private java.math.BigDecimal idmodelloin;
	private java.lang.String flgversoregistrazionein;
	private java.lang.String categoriaregin;
	private java.lang.String siglaregistrazionein;
	private java.lang.Integer flgsolomodutentein;
	private java.lang.Integer flgtuttimodprivatiin;
	private java.lang.Integer flgincludiannullatiin;
	private java.lang.String codapplownerin;
	private java.lang.String codistapplownerin;
	private java.lang.Integer flgrestrapplownerin;
	private java.lang.String colorderbyio;
	private java.lang.String flgdescorderbyio;
	private java.lang.Integer flgsenzapaginazionein;
	private java.lang.Integer nropaginaio;
	private java.lang.Integer bachsizeio;
	private java.lang.Integer overflowlimitin;
	private java.lang.Integer flgsenzatotin;
	private java.lang.Integer nrototrecout;
	private java.lang.Integer nrorecinpaginaout;
	private java.lang.Integer flgbatchsearchin;
	private java.lang.String listaxmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String rigatagnamein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getOggettoin(){return oggettoin;}
    public java.lang.String getCinomemodelloin(){return cinomemodelloin;}
    public java.lang.String getProvcimodelloin(){return provcimodelloin;}
    public java.math.BigDecimal getIdmodelloin(){return idmodelloin;}
    public java.lang.String getFlgversoregistrazionein(){return flgversoregistrazionein;}
    public java.lang.String getCategoriaregin(){return categoriaregin;}
    public java.lang.String getSiglaregistrazionein(){return siglaregistrazionein;}
    public java.lang.Integer getFlgsolomodutentein(){return flgsolomodutentein;}
    public java.lang.Integer getFlgtuttimodprivatiin(){return flgtuttimodprivatiin;}
    public java.lang.Integer getFlgincludiannullatiin(){return flgincludiannullatiin;}
    public java.lang.String getCodapplownerin(){return codapplownerin;}
    public java.lang.String getCodistapplownerin(){return codistapplownerin;}
    public java.lang.Integer getFlgrestrapplownerin(){return flgrestrapplownerin;}
    public java.lang.String getColorderbyio(){return colorderbyio;}
    public java.lang.String getFlgdescorderbyio(){return flgdescorderbyio;}
    public java.lang.Integer getFlgsenzapaginazionein(){return flgsenzapaginazionein;}
    public java.lang.Integer getNropaginaio(){return nropaginaio;}
    public java.lang.Integer getBachsizeio(){return bachsizeio;}
    public java.lang.Integer getOverflowlimitin(){return overflowlimitin;}
    public java.lang.Integer getFlgsenzatotin(){return flgsenzatotin;}
    public java.lang.Integer getNrototrecout(){return nrototrecout;}
    public java.lang.Integer getNrorecinpaginaout(){return nrorecinpaginaout;}
    public java.lang.Integer getFlgbatchsearchin(){return flgbatchsearchin;}
    public java.lang.String getListaxmlout(){return listaxmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getRigatagnamein(){return rigatagnamein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setOggettoin(java.lang.String value){this.oggettoin=value;}
    public void setCinomemodelloin(java.lang.String value){this.cinomemodelloin=value;}
    public void setProvcimodelloin(java.lang.String value){this.provcimodelloin=value;}
    public void setIdmodelloin(java.math.BigDecimal value){this.idmodelloin=value;}
    public void setFlgversoregistrazionein(java.lang.String value){this.flgversoregistrazionein=value;}
    public void setCategoriaregin(java.lang.String value){this.categoriaregin=value;}
    public void setSiglaregistrazionein(java.lang.String value){this.siglaregistrazionein=value;}
    public void setFlgsolomodutentein(java.lang.Integer value){this.flgsolomodutentein=value;}
    public void setFlgtuttimodprivatiin(java.lang.Integer value){this.flgtuttimodprivatiin=value;}
    public void setFlgincludiannullatiin(java.lang.Integer value){this.flgincludiannullatiin=value;}
    public void setCodapplownerin(java.lang.String value){this.codapplownerin=value;}
    public void setCodistapplownerin(java.lang.String value){this.codistapplownerin=value;}
    public void setFlgrestrapplownerin(java.lang.Integer value){this.flgrestrapplownerin=value;}
    public void setColorderbyio(java.lang.String value){this.colorderbyio=value;}
    public void setFlgdescorderbyio(java.lang.String value){this.flgdescorderbyio=value;}
    public void setFlgsenzapaginazionein(java.lang.Integer value){this.flgsenzapaginazionein=value;}
    public void setNropaginaio(java.lang.Integer value){this.nropaginaio=value;}
    public void setBachsizeio(java.lang.Integer value){this.bachsizeio=value;}
    public void setOverflowlimitin(java.lang.Integer value){this.overflowlimitin=value;}
    public void setFlgsenzatotin(java.lang.Integer value){this.flgsenzatotin=value;}
    public void setNrototrecout(java.lang.Integer value){this.nrototrecout=value;}
    public void setNrorecinpaginaout(java.lang.Integer value){this.nrorecinpaginaout=value;}
    public void setFlgbatchsearchin(java.lang.Integer value){this.flgbatchsearchin=value;}
    public void setListaxmlout(java.lang.String value){this.listaxmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setRigatagnamein(java.lang.String value){this.rigatagnamein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    