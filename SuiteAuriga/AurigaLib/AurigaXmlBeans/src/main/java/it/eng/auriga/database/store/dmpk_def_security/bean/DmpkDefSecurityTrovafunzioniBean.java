/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityTrovafunzioniBean")
public class DmpkDefSecurityTrovafunzioniBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_TROVAFUNZIONI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer flgpreimpostafiltroin;
	private java.lang.String codfunzp1io;
	private java.lang.String codfunzp2io;
	private java.lang.String codfunzp3io;
	private java.lang.String codfunzpnio;
	private java.lang.String desfunzio;
	private java.lang.Integer livellofunzio;
	private java.lang.Integer flgconsottofunzioniio;
	private java.lang.Integer flgsolodispio;
	private java.lang.Integer flgsoloconopzstdio;
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
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgstatoabilin;
	private java.lang.String flgtpdestabilin;
	private java.math.BigDecimal iddestabilin;
	private java.lang.String opzioniabilin;
	private java.lang.String rigatagnamein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getFlgpreimpostafiltroin(){return flgpreimpostafiltroin;}
    public java.lang.String getCodfunzp1io(){return codfunzp1io;}
    public java.lang.String getCodfunzp2io(){return codfunzp2io;}
    public java.lang.String getCodfunzp3io(){return codfunzp3io;}
    public java.lang.String getCodfunzpnio(){return codfunzpnio;}
    public java.lang.String getDesfunzio(){return desfunzio;}
    public java.lang.Integer getLivellofunzio(){return livellofunzio;}
    public java.lang.Integer getFlgconsottofunzioniio(){return flgconsottofunzioniio;}
    public java.lang.Integer getFlgsolodispio(){return flgsolodispio;}
    public java.lang.Integer getFlgsoloconopzstdio(){return flgsoloconopzstdio;}
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
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgstatoabilin(){return flgstatoabilin;}
    public java.lang.String getFlgtpdestabilin(){return flgtpdestabilin;}
    public java.math.BigDecimal getIddestabilin(){return iddestabilin;}
    public java.lang.String getOpzioniabilin(){return opzioniabilin;}
    public java.lang.String getRigatagnamein(){return rigatagnamein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgpreimpostafiltroin(java.lang.Integer value){this.flgpreimpostafiltroin=value;}
    public void setCodfunzp1io(java.lang.String value){this.codfunzp1io=value;}
    public void setCodfunzp2io(java.lang.String value){this.codfunzp2io=value;}
    public void setCodfunzp3io(java.lang.String value){this.codfunzp3io=value;}
    public void setCodfunzpnio(java.lang.String value){this.codfunzpnio=value;}
    public void setDesfunzio(java.lang.String value){this.desfunzio=value;}
    public void setLivellofunzio(java.lang.Integer value){this.livellofunzio=value;}
    public void setFlgconsottofunzioniio(java.lang.Integer value){this.flgconsottofunzioniio=value;}
    public void setFlgsolodispio(java.lang.Integer value){this.flgsolodispio=value;}
    public void setFlgsoloconopzstdio(java.lang.Integer value){this.flgsoloconopzstdio=value;}
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
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgstatoabilin(java.lang.Integer value){this.flgstatoabilin=value;}
    public void setFlgtpdestabilin(java.lang.String value){this.flgtpdestabilin=value;}
    public void setIddestabilin(java.math.BigDecimal value){this.iddestabilin=value;}
    public void setOpzioniabilin(java.lang.String value){this.opzioniabilin=value;}
    public void setRigatagnamein(java.lang.String value){this.rigatagnamein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    