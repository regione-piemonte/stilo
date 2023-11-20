/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDocTypesLoaddettdoctypeBean")
public class DmpkDocTypesLoaddettdoctypeBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DOC_TYPES_LOADDETTDOCTYPE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal iddoctypeio;
	private java.lang.String nomeout;
	private java.lang.String descrizioneout;
	private java.math.BigDecimal iddoctypegenout;
	private java.lang.String nomedoctypegenout;
	private java.lang.Integer flgallegatoout;
	private java.lang.String codnaturaout;
	private java.lang.String flgtipoprovout;
	private java.lang.String codsupportoorigout;
	private java.lang.String flgtipocartaceoout;
	private java.lang.Integer flgdascansionareout;
	private java.lang.Integer flgconservpermout;
	private java.math.BigDecimal periodoconservout;
	private java.lang.String codsupportoconservout;
	private java.math.BigDecimal idclassificazioneout;
	private java.lang.String livelliclassificazioneout;
	private java.lang.String desclassificazioneout;
	private java.math.BigDecimal idformatoelout;
	private java.lang.String nomeformatoelout;
	private java.lang.String specaccessibilitaout;
	private java.lang.String specriproducibilitaout;
	private java.lang.String annotazioniout;
	private java.lang.Integer flgrichabilxvisualizzout;
	private java.lang.Integer flgrichabilxgestout;
	private java.lang.Integer flgrichabilxassegnout;
	private java.lang.Integer flgrichabilxfirmaout;
	private java.lang.String ciprovdoctypeout;
	private java.lang.String templatenomeudout;
	private java.lang.String templatetimbroudout;
	private java.math.BigDecimal idprocesstypeout;
	private java.lang.String nomeprocesstypeout;
	private java.lang.Integer flglockedout;
	private java.lang.String rowidout;
	private java.lang.String xmlmodelliout;
	private java.lang.String xmlattraddxdocdeltipoout;
	private java.lang.String attributiaddout;
	private java.lang.String abilitazionipubblout;
	private java.lang.String flgrichfirmadigitaleout;
	private java.lang.Integer bachsizeout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIddoctypeio(){return iddoctypeio;}
    public java.lang.String getNomeout(){return nomeout;}
    public java.lang.String getDescrizioneout(){return descrizioneout;}
    public java.math.BigDecimal getIddoctypegenout(){return iddoctypegenout;}
    public java.lang.String getNomedoctypegenout(){return nomedoctypegenout;}
    public java.lang.Integer getFlgallegatoout(){return flgallegatoout;}
    public java.lang.String getCodnaturaout(){return codnaturaout;}
    public java.lang.String getFlgtipoprovout(){return flgtipoprovout;}
    public java.lang.String getCodsupportoorigout(){return codsupportoorigout;}
    public java.lang.String getFlgtipocartaceoout(){return flgtipocartaceoout;}
    public java.lang.Integer getFlgdascansionareout(){return flgdascansionareout;}
    public java.lang.Integer getFlgconservpermout(){return flgconservpermout;}
    public java.math.BigDecimal getPeriodoconservout(){return periodoconservout;}
    public java.lang.String getCodsupportoconservout(){return codsupportoconservout;}
    public java.math.BigDecimal getIdclassificazioneout(){return idclassificazioneout;}
    public java.lang.String getLivelliclassificazioneout(){return livelliclassificazioneout;}
    public java.lang.String getDesclassificazioneout(){return desclassificazioneout;}
    public java.math.BigDecimal getIdformatoelout(){return idformatoelout;}
    public java.lang.String getNomeformatoelout(){return nomeformatoelout;}
    public java.lang.String getSpecaccessibilitaout(){return specaccessibilitaout;}
    public java.lang.String getSpecriproducibilitaout(){return specriproducibilitaout;}
    public java.lang.String getAnnotazioniout(){return annotazioniout;}
    public java.lang.Integer getFlgrichabilxvisualizzout(){return flgrichabilxvisualizzout;}
    public java.lang.Integer getFlgrichabilxgestout(){return flgrichabilxgestout;}
    public java.lang.Integer getFlgrichabilxassegnout(){return flgrichabilxassegnout;}
    public java.lang.Integer getFlgrichabilxfirmaout(){return flgrichabilxfirmaout;}
    public java.lang.String getCiprovdoctypeout(){return ciprovdoctypeout;}
    public java.lang.String getTemplatenomeudout(){return templatenomeudout;}
    public java.lang.String getTemplatetimbroudout(){return templatetimbroudout;}
    public java.math.BigDecimal getIdprocesstypeout(){return idprocesstypeout;}
    public java.lang.String getNomeprocesstypeout(){return nomeprocesstypeout;}
    public java.lang.Integer getFlglockedout(){return flglockedout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getXmlmodelliout(){return xmlmodelliout;}
    public java.lang.String getXmlattraddxdocdeltipoout(){return xmlattraddxdocdeltipoout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.String getAbilitazionipubblout(){return abilitazionipubblout;}
    public java.lang.String getFlgrichfirmadigitaleout(){return flgrichfirmadigitaleout;}
    public java.lang.Integer getBachsizeout(){return bachsizeout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIddoctypeio(java.math.BigDecimal value){this.iddoctypeio=value;}
    public void setNomeout(java.lang.String value){this.nomeout=value;}
    public void setDescrizioneout(java.lang.String value){this.descrizioneout=value;}
    public void setIddoctypegenout(java.math.BigDecimal value){this.iddoctypegenout=value;}
    public void setNomedoctypegenout(java.lang.String value){this.nomedoctypegenout=value;}
    public void setFlgallegatoout(java.lang.Integer value){this.flgallegatoout=value;}
    public void setCodnaturaout(java.lang.String value){this.codnaturaout=value;}
    public void setFlgtipoprovout(java.lang.String value){this.flgtipoprovout=value;}
    public void setCodsupportoorigout(java.lang.String value){this.codsupportoorigout=value;}
    public void setFlgtipocartaceoout(java.lang.String value){this.flgtipocartaceoout=value;}
    public void setFlgdascansionareout(java.lang.Integer value){this.flgdascansionareout=value;}
    public void setFlgconservpermout(java.lang.Integer value){this.flgconservpermout=value;}
    public void setPeriodoconservout(java.math.BigDecimal value){this.periodoconservout=value;}
    public void setCodsupportoconservout(java.lang.String value){this.codsupportoconservout=value;}
    public void setIdclassificazioneout(java.math.BigDecimal value){this.idclassificazioneout=value;}
    public void setLivelliclassificazioneout(java.lang.String value){this.livelliclassificazioneout=value;}
    public void setDesclassificazioneout(java.lang.String value){this.desclassificazioneout=value;}
    public void setIdformatoelout(java.math.BigDecimal value){this.idformatoelout=value;}
    public void setNomeformatoelout(java.lang.String value){this.nomeformatoelout=value;}
    public void setSpecaccessibilitaout(java.lang.String value){this.specaccessibilitaout=value;}
    public void setSpecriproducibilitaout(java.lang.String value){this.specriproducibilitaout=value;}
    public void setAnnotazioniout(java.lang.String value){this.annotazioniout=value;}
    public void setFlgrichabilxvisualizzout(java.lang.Integer value){this.flgrichabilxvisualizzout=value;}
    public void setFlgrichabilxgestout(java.lang.Integer value){this.flgrichabilxgestout=value;}
    public void setFlgrichabilxassegnout(java.lang.Integer value){this.flgrichabilxassegnout=value;}
    public void setFlgrichabilxfirmaout(java.lang.Integer value){this.flgrichabilxfirmaout=value;}
    public void setCiprovdoctypeout(java.lang.String value){this.ciprovdoctypeout=value;}
    public void setTemplatenomeudout(java.lang.String value){this.templatenomeudout=value;}
    public void setTemplatetimbroudout(java.lang.String value){this.templatetimbroudout=value;}
    public void setIdprocesstypeout(java.math.BigDecimal value){this.idprocesstypeout=value;}
    public void setNomeprocesstypeout(java.lang.String value){this.nomeprocesstypeout=value;}
    public void setFlglockedout(java.lang.Integer value){this.flglockedout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setXmlmodelliout(java.lang.String value){this.xmlmodelliout=value;}
    public void setXmlattraddxdocdeltipoout(java.lang.String value){this.xmlattraddxdocdeltipoout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setAbilitazionipubblout(java.lang.String value){this.abilitazionipubblout=value;}
    public void setFlgrichfirmadigitaleout(java.lang.String value){this.flgrichfirmadigitaleout=value;}
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