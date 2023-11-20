/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRepositoryGuiLoaddettfolderBean")
public class DmpkRepositoryGuiLoaddettfolderBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_REPOSITORY_GUI_LOADDETTFOLDER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idfolderio;
	private java.lang.String flgparticolaritafolderout;
	private java.lang.String nomefolderout;
	private java.lang.String dtaperturaout;
	private java.math.BigDecimal idclassificazioneout;
	private java.lang.String livelliclassificazioneout;
	private java.lang.String desclassificazioneout;
	private java.math.BigDecimal annoaperturaout;
	private java.math.BigDecimal nroprogrfascout;
	private java.math.BigDecimal nroprogrsottofascout;
	private java.math.BigDecimal nroprogrinsertoout;
	private java.math.BigDecimal idtipofolderout;
	private java.lang.String nometipofolderout;
	private java.lang.String nrosecondarioout;
	private java.lang.String oggettoout;
	private java.lang.String dtchiusuraout;
	private java.lang.Integer flgmovimentatoout;
	private java.lang.String estremisoggincaricoaout;
	private java.lang.String soggettiesterniout;
	private java.lang.String contenitoriout;
	private java.lang.String listarelazionivsfolderout;
	private java.lang.String parolechiaveout;
	private java.lang.String codstatoout;
	private java.lang.String desstatoout;
	private java.lang.String noteout;
	private java.lang.String abilitazioniazioniout;
	private java.lang.String infosulockout;
	private java.math.BigDecimal idprocessout;
	private java.lang.Integer flgprocguidatodawfout;
	private java.lang.String rowidout;
	private java.lang.String attributiaddout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdfolderio(){return idfolderio;}
    public java.lang.String getFlgparticolaritafolderout(){return flgparticolaritafolderout;}
    public java.lang.String getNomefolderout(){return nomefolderout;}
    public java.lang.String getDtaperturaout(){return dtaperturaout;}
    public java.math.BigDecimal getIdclassificazioneout(){return idclassificazioneout;}
    public java.lang.String getLivelliclassificazioneout(){return livelliclassificazioneout;}
    public java.lang.String getDesclassificazioneout(){return desclassificazioneout;}
    public java.math.BigDecimal getAnnoaperturaout(){return annoaperturaout;}
    public java.math.BigDecimal getNroprogrfascout(){return nroprogrfascout;}
    public java.math.BigDecimal getNroprogrsottofascout(){return nroprogrsottofascout;}
    public java.math.BigDecimal getNroprogrinsertoout(){return nroprogrinsertoout;}
    public java.math.BigDecimal getIdtipofolderout(){return idtipofolderout;}
    public java.lang.String getNometipofolderout(){return nometipofolderout;}
    public java.lang.String getNrosecondarioout(){return nrosecondarioout;}
    public java.lang.String getOggettoout(){return oggettoout;}
    public java.lang.String getDtchiusuraout(){return dtchiusuraout;}
    public java.lang.Integer getFlgmovimentatoout(){return flgmovimentatoout;}
    public java.lang.String getEstremisoggincaricoaout(){return estremisoggincaricoaout;}
    public java.lang.String getSoggettiesterniout(){return soggettiesterniout;}
    public java.lang.String getContenitoriout(){return contenitoriout;}
    public java.lang.String getListarelazionivsfolderout(){return listarelazionivsfolderout;}
    public java.lang.String getParolechiaveout(){return parolechiaveout;}
    public java.lang.String getCodstatoout(){return codstatoout;}
    public java.lang.String getDesstatoout(){return desstatoout;}
    public java.lang.String getNoteout(){return noteout;}
    public java.lang.String getAbilitazioniazioniout(){return abilitazioniazioniout;}
    public java.lang.String getInfosulockout(){return infosulockout;}
    public java.math.BigDecimal getIdprocessout(){return idprocessout;}
    public java.lang.Integer getFlgprocguidatodawfout(){return flgprocguidatodawfout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdfolderio(java.math.BigDecimal value){this.idfolderio=value;}
    public void setFlgparticolaritafolderout(java.lang.String value){this.flgparticolaritafolderout=value;}
    public void setNomefolderout(java.lang.String value){this.nomefolderout=value;}
    public void setDtaperturaout(java.lang.String value){this.dtaperturaout=value;}
    public void setIdclassificazioneout(java.math.BigDecimal value){this.idclassificazioneout=value;}
    public void setLivelliclassificazioneout(java.lang.String value){this.livelliclassificazioneout=value;}
    public void setDesclassificazioneout(java.lang.String value){this.desclassificazioneout=value;}
    public void setAnnoaperturaout(java.math.BigDecimal value){this.annoaperturaout=value;}
    public void setNroprogrfascout(java.math.BigDecimal value){this.nroprogrfascout=value;}
    public void setNroprogrsottofascout(java.math.BigDecimal value){this.nroprogrsottofascout=value;}
    public void setNroprogrinsertoout(java.math.BigDecimal value){this.nroprogrinsertoout=value;}
    public void setIdtipofolderout(java.math.BigDecimal value){this.idtipofolderout=value;}
    public void setNometipofolderout(java.lang.String value){this.nometipofolderout=value;}
    public void setNrosecondarioout(java.lang.String value){this.nrosecondarioout=value;}
    public void setOggettoout(java.lang.String value){this.oggettoout=value;}
    public void setDtchiusuraout(java.lang.String value){this.dtchiusuraout=value;}
    public void setFlgmovimentatoout(java.lang.Integer value){this.flgmovimentatoout=value;}
    public void setEstremisoggincaricoaout(java.lang.String value){this.estremisoggincaricoaout=value;}
    public void setSoggettiesterniout(java.lang.String value){this.soggettiesterniout=value;}
    public void setContenitoriout(java.lang.String value){this.contenitoriout=value;}
    public void setListarelazionivsfolderout(java.lang.String value){this.listarelazionivsfolderout=value;}
    public void setParolechiaveout(java.lang.String value){this.parolechiaveout=value;}
    public void setCodstatoout(java.lang.String value){this.codstatoout=value;}
    public void setDesstatoout(java.lang.String value){this.desstatoout=value;}
    public void setNoteout(java.lang.String value){this.noteout=value;}
    public void setAbilitazioniazioniout(java.lang.String value){this.abilitazioniazioniout=value;}
    public void setInfosulockout(java.lang.String value){this.infosulockout=value;}
    public void setIdprocessout(java.math.BigDecimal value){this.idprocessout=value;}
    public void setFlgprocguidatodawfout(java.lang.Integer value){this.flgprocguidatodawfout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    