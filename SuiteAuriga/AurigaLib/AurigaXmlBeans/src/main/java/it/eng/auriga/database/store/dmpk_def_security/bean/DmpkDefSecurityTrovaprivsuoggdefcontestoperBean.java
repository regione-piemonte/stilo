/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityTrovaprivsuoggdefcontestoperBean")
public class DmpkDefSecurityTrovaprivsuoggdefcontestoperBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_TROVAPRIVSUOGGDEFCONTESTOPER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer flgpreimpostafiltroin;
	private java.lang.String flgtpobjprivtoin;
	private java.math.BigDecimal idobjprivtoin;
	private java.lang.String desobjprivtoin;
	private java.lang.String livelliuoprivtoin;
	private java.lang.String desuoappscrivaniatoin;
	private java.lang.String desuserscrivaniatoin;
	private java.lang.String flgtpobjprivonin;
	private java.lang.Integer flgsoloprivilegidirettiin;
	private java.lang.String flgtpprivilegioin;
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
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String rigatagnamein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getFlgpreimpostafiltroin(){return flgpreimpostafiltroin;}
    public java.lang.String getFlgtpobjprivtoin(){return flgtpobjprivtoin;}
    public java.math.BigDecimal getIdobjprivtoin(){return idobjprivtoin;}
    public java.lang.String getDesobjprivtoin(){return desobjprivtoin;}
    public java.lang.String getLivelliuoprivtoin(){return livelliuoprivtoin;}
    public java.lang.String getDesuoappscrivaniatoin(){return desuoappscrivaniatoin;}
    public java.lang.String getDesuserscrivaniatoin(){return desuserscrivaniatoin;}
    public java.lang.String getFlgtpobjprivonin(){return flgtpobjprivonin;}
    public java.lang.Integer getFlgsoloprivilegidirettiin(){return flgsoloprivilegidirettiin;}
    public java.lang.String getFlgtpprivilegioin(){return flgtpprivilegioin;}
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
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getRigatagnamein(){return rigatagnamein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgpreimpostafiltroin(java.lang.Integer value){this.flgpreimpostafiltroin=value;}
    public void setFlgtpobjprivtoin(java.lang.String value){this.flgtpobjprivtoin=value;}
    public void setIdobjprivtoin(java.math.BigDecimal value){this.idobjprivtoin=value;}
    public void setDesobjprivtoin(java.lang.String value){this.desobjprivtoin=value;}
    public void setLivelliuoprivtoin(java.lang.String value){this.livelliuoprivtoin=value;}
    public void setDesuoappscrivaniatoin(java.lang.String value){this.desuoappscrivaniatoin=value;}
    public void setDesuserscrivaniatoin(java.lang.String value){this.desuserscrivaniatoin=value;}
    public void setFlgtpobjprivonin(java.lang.String value){this.flgtpobjprivonin=value;}
    public void setFlgsoloprivilegidirettiin(java.lang.Integer value){this.flgsoloprivilegidirettiin=value;}
    public void setFlgtpprivilegioin(java.lang.String value){this.flgtpprivilegioin=value;}
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
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setRigatagnamein(java.lang.String value){this.rigatagnamein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    