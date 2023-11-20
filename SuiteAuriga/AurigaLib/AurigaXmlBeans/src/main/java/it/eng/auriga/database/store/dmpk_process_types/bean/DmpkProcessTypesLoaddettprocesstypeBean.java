/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessTypesLoaddettprocesstypeBean")
public class DmpkProcessTypesLoaddettprocesstypeBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESS_TYPES_LOADDETTPROCESSTYPE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idprocesstypeio;
	private java.lang.String nomeout;
	private java.lang.String descrizioneout;
	private java.lang.String dtiniziovldout;
	private java.lang.String dtfinevldout;
	private java.math.BigDecimal idprocesstypegenout;
	private java.lang.String nomeprocesstypegenout;
	private java.lang.Integer flgprocammout;
	private java.lang.String flgtipoiniziativaout;
	private java.math.BigDecimal ggdurataprevistaout;
	private java.lang.String basenormativaout;
	private java.lang.Integer flgsospendibileout;
	private java.math.BigDecimal nromaxsospensioniout;
	private java.lang.Integer flginterrompibileout;
	private java.math.BigDecimal nromaxggxinterrout;
	private java.lang.Integer flgpartiesterneout;
	private java.lang.Integer flgsilenzioassensoout;
	private java.math.BigDecimal ggsilenzioassensoout;
	private java.lang.String flgfascsfout;
	private java.math.BigDecimal idclassificazioneout;
	private java.lang.String livelliclassificazioneout;
	private java.lang.String desclassificazioneout;
	private java.math.BigDecimal idfoldertypeout;
	private java.lang.String nomefoldertypeout;
	private java.lang.Integer flgconservpermout;
	private java.math.BigDecimal periodoconservout;
	private java.lang.String codsupportoconservout;
	private java.lang.String annotazioniout;
	private java.lang.Integer flgrichabilxvisualizzout;
	private java.lang.Integer flgrichabilxgestout;
	private java.lang.Integer flgrichabilxassegnout;
	private java.lang.String ciprovprocesstypeout;
	private java.lang.Integer flglockedout;
	private java.lang.String rowidout;
	private java.lang.String xmlflussiwfout;
	private java.math.BigDecimal iduserrespout;
	private java.lang.String desuserrespout;
	private java.math.BigDecimal iduocompetenteout;
	private java.lang.String livelliuocompetenteout;
	private java.lang.String desuocompetenteout;
	private java.lang.String attributiaddout;
	private java.lang.Integer bachsizeout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdprocesstypeio(){return idprocesstypeio;}
    public java.lang.String getNomeout(){return nomeout;}
    public java.lang.String getDescrizioneout(){return descrizioneout;}
    public java.lang.String getDtiniziovldout(){return dtiniziovldout;}
    public java.lang.String getDtfinevldout(){return dtfinevldout;}
    public java.math.BigDecimal getIdprocesstypegenout(){return idprocesstypegenout;}
    public java.lang.String getNomeprocesstypegenout(){return nomeprocesstypegenout;}
    public java.lang.Integer getFlgprocammout(){return flgprocammout;}
    public java.lang.String getFlgtipoiniziativaout(){return flgtipoiniziativaout;}
    public java.math.BigDecimal getGgdurataprevistaout(){return ggdurataprevistaout;}
    public java.lang.String getBasenormativaout(){return basenormativaout;}
    public java.lang.Integer getFlgsospendibileout(){return flgsospendibileout;}
    public java.math.BigDecimal getNromaxsospensioniout(){return nromaxsospensioniout;}
    public java.lang.Integer getFlginterrompibileout(){return flginterrompibileout;}
    public java.math.BigDecimal getNromaxggxinterrout(){return nromaxggxinterrout;}
    public java.lang.Integer getFlgpartiesterneout(){return flgpartiesterneout;}
    public java.lang.Integer getFlgsilenzioassensoout(){return flgsilenzioassensoout;}
    public java.math.BigDecimal getGgsilenzioassensoout(){return ggsilenzioassensoout;}
    public java.lang.String getFlgfascsfout(){return flgfascsfout;}
    public java.math.BigDecimal getIdclassificazioneout(){return idclassificazioneout;}
    public java.lang.String getLivelliclassificazioneout(){return livelliclassificazioneout;}
    public java.lang.String getDesclassificazioneout(){return desclassificazioneout;}
    public java.math.BigDecimal getIdfoldertypeout(){return idfoldertypeout;}
    public java.lang.String getNomefoldertypeout(){return nomefoldertypeout;}
    public java.lang.Integer getFlgconservpermout(){return flgconservpermout;}
    public java.math.BigDecimal getPeriodoconservout(){return periodoconservout;}
    public java.lang.String getCodsupportoconservout(){return codsupportoconservout;}
    public java.lang.String getAnnotazioniout(){return annotazioniout;}
    public java.lang.Integer getFlgrichabilxvisualizzout(){return flgrichabilxvisualizzout;}
    public java.lang.Integer getFlgrichabilxgestout(){return flgrichabilxgestout;}
    public java.lang.Integer getFlgrichabilxassegnout(){return flgrichabilxassegnout;}
    public java.lang.String getCiprovprocesstypeout(){return ciprovprocesstypeout;}
    public java.lang.Integer getFlglockedout(){return flglockedout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getXmlflussiwfout(){return xmlflussiwfout;}
    public java.math.BigDecimal getIduserrespout(){return iduserrespout;}
    public java.lang.String getDesuserrespout(){return desuserrespout;}
    public java.math.BigDecimal getIduocompetenteout(){return iduocompetenteout;}
    public java.lang.String getLivelliuocompetenteout(){return livelliuocompetenteout;}
    public java.lang.String getDesuocompetenteout(){return desuocompetenteout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getBachsizeout(){return bachsizeout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdprocesstypeio(java.math.BigDecimal value){this.idprocesstypeio=value;}
    public void setNomeout(java.lang.String value){this.nomeout=value;}
    public void setDescrizioneout(java.lang.String value){this.descrizioneout=value;}
    public void setDtiniziovldout(java.lang.String value){this.dtiniziovldout=value;}
    public void setDtfinevldout(java.lang.String value){this.dtfinevldout=value;}
    public void setIdprocesstypegenout(java.math.BigDecimal value){this.idprocesstypegenout=value;}
    public void setNomeprocesstypegenout(java.lang.String value){this.nomeprocesstypegenout=value;}
    public void setFlgprocammout(java.lang.Integer value){this.flgprocammout=value;}
    public void setFlgtipoiniziativaout(java.lang.String value){this.flgtipoiniziativaout=value;}
    public void setGgdurataprevistaout(java.math.BigDecimal value){this.ggdurataprevistaout=value;}
    public void setBasenormativaout(java.lang.String value){this.basenormativaout=value;}
    public void setFlgsospendibileout(java.lang.Integer value){this.flgsospendibileout=value;}
    public void setNromaxsospensioniout(java.math.BigDecimal value){this.nromaxsospensioniout=value;}
    public void setFlginterrompibileout(java.lang.Integer value){this.flginterrompibileout=value;}
    public void setNromaxggxinterrout(java.math.BigDecimal value){this.nromaxggxinterrout=value;}
    public void setFlgpartiesterneout(java.lang.Integer value){this.flgpartiesterneout=value;}
    public void setFlgsilenzioassensoout(java.lang.Integer value){this.flgsilenzioassensoout=value;}
    public void setGgsilenzioassensoout(java.math.BigDecimal value){this.ggsilenzioassensoout=value;}
    public void setFlgfascsfout(java.lang.String value){this.flgfascsfout=value;}
    public void setIdclassificazioneout(java.math.BigDecimal value){this.idclassificazioneout=value;}
    public void setLivelliclassificazioneout(java.lang.String value){this.livelliclassificazioneout=value;}
    public void setDesclassificazioneout(java.lang.String value){this.desclassificazioneout=value;}
    public void setIdfoldertypeout(java.math.BigDecimal value){this.idfoldertypeout=value;}
    public void setNomefoldertypeout(java.lang.String value){this.nomefoldertypeout=value;}
    public void setFlgconservpermout(java.lang.Integer value){this.flgconservpermout=value;}
    public void setPeriodoconservout(java.math.BigDecimal value){this.periodoconservout=value;}
    public void setCodsupportoconservout(java.lang.String value){this.codsupportoconservout=value;}
    public void setAnnotazioniout(java.lang.String value){this.annotazioniout=value;}
    public void setFlgrichabilxvisualizzout(java.lang.Integer value){this.flgrichabilxvisualizzout=value;}
    public void setFlgrichabilxgestout(java.lang.Integer value){this.flgrichabilxgestout=value;}
    public void setFlgrichabilxassegnout(java.lang.Integer value){this.flgrichabilxassegnout=value;}
    public void setCiprovprocesstypeout(java.lang.String value){this.ciprovprocesstypeout=value;}
    public void setFlglockedout(java.lang.Integer value){this.flglockedout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setXmlflussiwfout(java.lang.String value){this.xmlflussiwfout=value;}
    public void setIduserrespout(java.math.BigDecimal value){this.iduserrespout=value;}
    public void setDesuserrespout(java.lang.String value){this.desuserrespout=value;}
    public void setIduocompetenteout(java.math.BigDecimal value){this.iduocompetenteout=value;}
    public void setLivelliuocompetenteout(java.lang.String value){this.livelliuocompetenteout=value;}
    public void setDesuocompetenteout(java.lang.String value){this.desuocompetenteout=value;}
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