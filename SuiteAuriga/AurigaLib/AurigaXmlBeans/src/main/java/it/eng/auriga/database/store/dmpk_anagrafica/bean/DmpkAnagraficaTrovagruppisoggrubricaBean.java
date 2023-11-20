/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAnagraficaTrovagruppisoggrubricaBean")
public class DmpkAnagraficaTrovagruppisoggrubricaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ANAGRAFICA_TROVAGRUPPISOGGRUBRICA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String nomegruppoin;
	private java.lang.String codrapidogruppoin;
	private java.lang.String provcigruppoin;
	private java.math.BigDecimal idgruppoin;
	private java.lang.Integer flgsologruppisoggintin;
	private java.lang.Integer flgincludinonvalidiin;
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
    public java.lang.String getNomegruppoin(){return nomegruppoin;}
    public java.lang.String getCodrapidogruppoin(){return codrapidogruppoin;}
    public java.lang.String getProvcigruppoin(){return provcigruppoin;}
    public java.math.BigDecimal getIdgruppoin(){return idgruppoin;}
    public java.lang.Integer getFlgsologruppisoggintin(){return flgsologruppisoggintin;}
    public java.lang.Integer getFlgincludinonvalidiin(){return flgincludinonvalidiin;}
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
    public void setNomegruppoin(java.lang.String value){this.nomegruppoin=value;}
    public void setCodrapidogruppoin(java.lang.String value){this.codrapidogruppoin=value;}
    public void setProvcigruppoin(java.lang.String value){this.provcigruppoin=value;}
    public void setIdgruppoin(java.math.BigDecimal value){this.idgruppoin=value;}
    public void setFlgsologruppisoggintin(java.lang.Integer value){this.flgsologruppisoggintin=value;}
    public void setFlgincludinonvalidiin(java.lang.Integer value){this.flgincludinonvalidiin=value;}
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