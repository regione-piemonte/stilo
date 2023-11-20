/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDocTypesIudoctypeBean")
public class DmpkDocTypesIudoctypeBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DOC_TYPES_IUDOCTYPE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal iddoctypeio;
	private java.lang.String nomein;
	private java.lang.String descrizionein;
	private java.math.BigDecimal iddoctypegenin;
	private java.lang.String nomedoctypegenin;
	private java.lang.Integer flgallegatoin;
	private java.lang.String codnaturain;
	private java.lang.String flgtipoprovin;
	private java.lang.String codsupportoorigin;
	private java.lang.String flgtipocartaceoin;
	private java.lang.Integer flgdascansionarein;
	private java.lang.Integer flgconservpermin;
	private java.math.BigDecimal periodoconservin;
	private java.lang.String codsupportoconservin;
	private java.math.BigDecimal idclassificazionein;
	private java.lang.String livelliclassificazionein;
	private java.lang.String desclassificazionein;
	private java.math.BigDecimal idformatoelin;
	private java.lang.String nomeformatoelin;
	private java.lang.String estensioneformatoelin;
	private java.lang.String specaccessibilitain;
	private java.lang.String specriproducibilitain;
	private java.lang.String annotazioniin;
	private java.lang.Integer flgrichabilxvisualizzin;
	private java.lang.Integer flgrichabilxgestin;
	private java.lang.Integer flgrichabilxassegnin;
	private java.lang.Integer flgrichabilxfirmain;
	private java.lang.String ciprovdoctypein;
	private java.lang.Integer flglockedin;
	private java.lang.String flgmodmodelliin;
	private java.lang.String xmlmodelliin;
	private java.lang.String flgmodattraddxdocdeltipoin;
	private java.lang.String xmlattraddxdocdeltipoin;
	private java.lang.String attributiaddin;
	private java.lang.Integer flgignorewarningin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String warningmsgout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String templatenomeudin;
	private java.lang.String templatetimbroudin;
	private java.math.BigDecimal idprocesstypein;
	private java.lang.String nomeprocesstypein;
	private java.lang.String abilitazionipubblin;
	private java.lang.String flgrichfirmadigitalein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIddoctypeio(){return iddoctypeio;}
    public java.lang.String getNomein(){return nomein;}
    public java.lang.String getDescrizionein(){return descrizionein;}
    public java.math.BigDecimal getIddoctypegenin(){return iddoctypegenin;}
    public java.lang.String getNomedoctypegenin(){return nomedoctypegenin;}
    public java.lang.Integer getFlgallegatoin(){return flgallegatoin;}
    public java.lang.String getCodnaturain(){return codnaturain;}
    public java.lang.String getFlgtipoprovin(){return flgtipoprovin;}
    public java.lang.String getCodsupportoorigin(){return codsupportoorigin;}
    public java.lang.String getFlgtipocartaceoin(){return flgtipocartaceoin;}
    public java.lang.Integer getFlgdascansionarein(){return flgdascansionarein;}
    public java.lang.Integer getFlgconservpermin(){return flgconservpermin;}
    public java.math.BigDecimal getPeriodoconservin(){return periodoconservin;}
    public java.lang.String getCodsupportoconservin(){return codsupportoconservin;}
    public java.math.BigDecimal getIdclassificazionein(){return idclassificazionein;}
    public java.lang.String getLivelliclassificazionein(){return livelliclassificazionein;}
    public java.lang.String getDesclassificazionein(){return desclassificazionein;}
    public java.math.BigDecimal getIdformatoelin(){return idformatoelin;}
    public java.lang.String getNomeformatoelin(){return nomeformatoelin;}
    public java.lang.String getEstensioneformatoelin(){return estensioneformatoelin;}
    public java.lang.String getSpecaccessibilitain(){return specaccessibilitain;}
    public java.lang.String getSpecriproducibilitain(){return specriproducibilitain;}
    public java.lang.String getAnnotazioniin(){return annotazioniin;}
    public java.lang.Integer getFlgrichabilxvisualizzin(){return flgrichabilxvisualizzin;}
    public java.lang.Integer getFlgrichabilxgestin(){return flgrichabilxgestin;}
    public java.lang.Integer getFlgrichabilxassegnin(){return flgrichabilxassegnin;}
    public java.lang.Integer getFlgrichabilxfirmain(){return flgrichabilxfirmain;}
    public java.lang.String getCiprovdoctypein(){return ciprovdoctypein;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.String getFlgmodmodelliin(){return flgmodmodelliin;}
    public java.lang.String getXmlmodelliin(){return xmlmodelliin;}
    public java.lang.String getFlgmodattraddxdocdeltipoin(){return flgmodattraddxdocdeltipoin;}
    public java.lang.String getXmlattraddxdocdeltipoin(){return xmlattraddxdocdeltipoin;}
    public java.lang.String getAttributiaddin(){return attributiaddin;}
    public java.lang.Integer getFlgignorewarningin(){return flgignorewarningin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getTemplatenomeudin(){return templatenomeudin;}
    public java.lang.String getTemplatetimbroudin(){return templatetimbroudin;}
    public java.math.BigDecimal getIdprocesstypein(){return idprocesstypein;}
    public java.lang.String getNomeprocesstypein(){return nomeprocesstypein;}
    public java.lang.String getAbilitazionipubblin(){return abilitazionipubblin;}
    public java.lang.String getFlgrichfirmadigitalein(){return flgrichfirmadigitalein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIddoctypeio(java.math.BigDecimal value){this.iddoctypeio=value;}
    public void setNomein(java.lang.String value){this.nomein=value;}
    public void setDescrizionein(java.lang.String value){this.descrizionein=value;}
    public void setIddoctypegenin(java.math.BigDecimal value){this.iddoctypegenin=value;}
    public void setNomedoctypegenin(java.lang.String value){this.nomedoctypegenin=value;}
    public void setFlgallegatoin(java.lang.Integer value){this.flgallegatoin=value;}
    public void setCodnaturain(java.lang.String value){this.codnaturain=value;}
    public void setFlgtipoprovin(java.lang.String value){this.flgtipoprovin=value;}
    public void setCodsupportoorigin(java.lang.String value){this.codsupportoorigin=value;}
    public void setFlgtipocartaceoin(java.lang.String value){this.flgtipocartaceoin=value;}
    public void setFlgdascansionarein(java.lang.Integer value){this.flgdascansionarein=value;}
    public void setFlgconservpermin(java.lang.Integer value){this.flgconservpermin=value;}
    public void setPeriodoconservin(java.math.BigDecimal value){this.periodoconservin=value;}
    public void setCodsupportoconservin(java.lang.String value){this.codsupportoconservin=value;}
    public void setIdclassificazionein(java.math.BigDecimal value){this.idclassificazionein=value;}
    public void setLivelliclassificazionein(java.lang.String value){this.livelliclassificazionein=value;}
    public void setDesclassificazionein(java.lang.String value){this.desclassificazionein=value;}
    public void setIdformatoelin(java.math.BigDecimal value){this.idformatoelin=value;}
    public void setNomeformatoelin(java.lang.String value){this.nomeformatoelin=value;}
    public void setEstensioneformatoelin(java.lang.String value){this.estensioneformatoelin=value;}
    public void setSpecaccessibilitain(java.lang.String value){this.specaccessibilitain=value;}
    public void setSpecriproducibilitain(java.lang.String value){this.specriproducibilitain=value;}
    public void setAnnotazioniin(java.lang.String value){this.annotazioniin=value;}
    public void setFlgrichabilxvisualizzin(java.lang.Integer value){this.flgrichabilxvisualizzin=value;}
    public void setFlgrichabilxgestin(java.lang.Integer value){this.flgrichabilxgestin=value;}
    public void setFlgrichabilxassegnin(java.lang.Integer value){this.flgrichabilxassegnin=value;}
    public void setFlgrichabilxfirmain(java.lang.Integer value){this.flgrichabilxfirmain=value;}
    public void setCiprovdoctypein(java.lang.String value){this.ciprovdoctypein=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setFlgmodmodelliin(java.lang.String value){this.flgmodmodelliin=value;}
    public void setXmlmodelliin(java.lang.String value){this.xmlmodelliin=value;}
    public void setFlgmodattraddxdocdeltipoin(java.lang.String value){this.flgmodattraddxdocdeltipoin=value;}
    public void setXmlattraddxdocdeltipoin(java.lang.String value){this.xmlattraddxdocdeltipoin=value;}
    public void setAttributiaddin(java.lang.String value){this.attributiaddin=value;}
    public void setFlgignorewarningin(java.lang.Integer value){this.flgignorewarningin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setTemplatenomeudin(java.lang.String value){this.templatenomeudin=value;}
    public void setTemplatetimbroudin(java.lang.String value){this.templatetimbroudin=value;}
    public void setIdprocesstypein(java.math.BigDecimal value){this.idprocesstypein=value;}
    public void setNomeprocesstypein(java.lang.String value){this.nomeprocesstypein=value;}
    public void setAbilitazionipubblin(java.lang.String value){this.abilitazionipubblin=value;}
    public void setFlgrichfirmadigitalein(java.lang.String value){this.flgrichfirmadigitalein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    