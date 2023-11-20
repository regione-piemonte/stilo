/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkFolderTypesLoaddettfoldertypeBean")
public class DmpkFolderTypesLoaddettfoldertypeBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_FOLDER_TYPES_LOADDETTFOLDERTYPE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idfoldertypeio;
	private java.lang.String nomeout;
	private java.math.BigDecimal idfoldertypegenout;
	private java.lang.String nomefoldertypegenout;
	private java.lang.Integer flgdascansionareout;
	private java.lang.Integer flgconservpermout;
	private java.math.BigDecimal periodoconservout;
	private java.lang.String codsupportoconservout;
	private java.math.BigDecimal idclassificazioneout;
	private java.lang.String livelliclassificazioneout;
	private java.lang.String desclassificazioneout;
	private java.lang.String annotazioniout;
	private java.lang.Integer flgrichabilxvisualizzout;
	private java.lang.Integer flgrichabilxgestout;
	private java.lang.Integer flgrichabilxassegnout;
	private java.lang.String ciprovfoldertypeout;
	private java.lang.String templatenomeout;
	private java.lang.String templatetimbroout;
	private java.lang.String templatecodeout;
	private java.math.BigDecimal idprocesstypeout;
	private java.lang.String nomeprocesstypeout;
	private java.lang.Integer flglockedout;
	private java.lang.String rowidout;
	private java.lang.String xmlattraddxfolderdeltipoout;
	private java.lang.String attributiaddout;
	private java.lang.Integer bachsizeout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdfoldertypeio(){return idfoldertypeio;}
    public java.lang.String getNomeout(){return nomeout;}
    public java.math.BigDecimal getIdfoldertypegenout(){return idfoldertypegenout;}
    public java.lang.String getNomefoldertypegenout(){return nomefoldertypegenout;}
    public java.lang.Integer getFlgdascansionareout(){return flgdascansionareout;}
    public java.lang.Integer getFlgconservpermout(){return flgconservpermout;}
    public java.math.BigDecimal getPeriodoconservout(){return periodoconservout;}
    public java.lang.String getCodsupportoconservout(){return codsupportoconservout;}
    public java.math.BigDecimal getIdclassificazioneout(){return idclassificazioneout;}
    public java.lang.String getLivelliclassificazioneout(){return livelliclassificazioneout;}
    public java.lang.String getDesclassificazioneout(){return desclassificazioneout;}
    public java.lang.String getAnnotazioniout(){return annotazioniout;}
    public java.lang.Integer getFlgrichabilxvisualizzout(){return flgrichabilxvisualizzout;}
    public java.lang.Integer getFlgrichabilxgestout(){return flgrichabilxgestout;}
    public java.lang.Integer getFlgrichabilxassegnout(){return flgrichabilxassegnout;}
    public java.lang.String getCiprovfoldertypeout(){return ciprovfoldertypeout;}
    public java.lang.String getTemplatenomeout(){return templatenomeout;}
    public java.lang.String getTemplatetimbroout(){return templatetimbroout;}
    public java.lang.String getTemplatecodeout(){return templatecodeout;}
    public java.math.BigDecimal getIdprocesstypeout(){return idprocesstypeout;}
    public java.lang.String getNomeprocesstypeout(){return nomeprocesstypeout;}
    public java.lang.Integer getFlglockedout(){return flglockedout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getXmlattraddxfolderdeltipoout(){return xmlattraddxfolderdeltipoout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getBachsizeout(){return bachsizeout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdfoldertypeio(java.math.BigDecimal value){this.idfoldertypeio=value;}
    public void setNomeout(java.lang.String value){this.nomeout=value;}
    public void setIdfoldertypegenout(java.math.BigDecimal value){this.idfoldertypegenout=value;}
    public void setNomefoldertypegenout(java.lang.String value){this.nomefoldertypegenout=value;}
    public void setFlgdascansionareout(java.lang.Integer value){this.flgdascansionareout=value;}
    public void setFlgconservpermout(java.lang.Integer value){this.flgconservpermout=value;}
    public void setPeriodoconservout(java.math.BigDecimal value){this.periodoconservout=value;}
    public void setCodsupportoconservout(java.lang.String value){this.codsupportoconservout=value;}
    public void setIdclassificazioneout(java.math.BigDecimal value){this.idclassificazioneout=value;}
    public void setLivelliclassificazioneout(java.lang.String value){this.livelliclassificazioneout=value;}
    public void setDesclassificazioneout(java.lang.String value){this.desclassificazioneout=value;}
    public void setAnnotazioniout(java.lang.String value){this.annotazioniout=value;}
    public void setFlgrichabilxvisualizzout(java.lang.Integer value){this.flgrichabilxvisualizzout=value;}
    public void setFlgrichabilxgestout(java.lang.Integer value){this.flgrichabilxgestout=value;}
    public void setFlgrichabilxassegnout(java.lang.Integer value){this.flgrichabilxassegnout=value;}
    public void setCiprovfoldertypeout(java.lang.String value){this.ciprovfoldertypeout=value;}
    public void setTemplatenomeout(java.lang.String value){this.templatenomeout=value;}
    public void setTemplatetimbroout(java.lang.String value){this.templatetimbroout=value;}
    public void setTemplatecodeout(java.lang.String value){this.templatecodeout=value;}
    public void setIdprocesstypeout(java.math.BigDecimal value){this.idprocesstypeout=value;}
    public void setNomeprocesstypeout(java.lang.String value){this.nomeprocesstypeout=value;}
    public void setFlglockedout(java.lang.Integer value){this.flglockedout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setXmlattraddxfolderdeltipoout(java.lang.String value){this.xmlattraddxfolderdeltipoout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setBachsizeout(java.lang.Integer value){this.bachsizeout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    