/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkFolderTypesIufoldertypeBean")
public class DmpkFolderTypesIufoldertypeBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_FOLDER_TYPES_IUFOLDERTYPE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idfoldertypeio;
	private java.lang.String nomein;
	private java.math.BigDecimal idfoldertypegenin;
	private java.lang.String nomefoldertypegenin;
	private java.lang.Integer flgdascansionarein;
	private java.lang.Integer flgconservpermin;
	private java.math.BigDecimal periodoconservin;
	private java.lang.String codsupportoconservin;
	private java.math.BigDecimal idclassificazionein;
	private java.lang.String livelliclassificazionein;
	private java.lang.String desclassificazionein;
	private java.lang.String annotazioniin;
	private java.lang.Integer flgrichabilxvisualizzin;
	private java.lang.Integer flgrichabilxgestin;
	private java.lang.Integer flgrichabilxassegnin;
	private java.lang.String ciprovfoldertypein;
	private java.lang.Integer flglockedin;
	private java.lang.String flgmodattraddxfolderdeltipoin;
	private java.lang.String xmlattraddxfolderdeltipoin;
	private java.lang.String attributiaddin;
	private java.lang.Integer flgignorewarningin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String warningmsgout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String templatenomein;
	private java.lang.String templatetimbroin;
	private java.lang.String templatecodein;
	private java.math.BigDecimal idprocesstypein;
	private java.lang.String nomeprocesstypein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdfoldertypeio(){return idfoldertypeio;}
    public java.lang.String getNomein(){return nomein;}
    public java.math.BigDecimal getIdfoldertypegenin(){return idfoldertypegenin;}
    public java.lang.String getNomefoldertypegenin(){return nomefoldertypegenin;}
    public java.lang.Integer getFlgdascansionarein(){return flgdascansionarein;}
    public java.lang.Integer getFlgconservpermin(){return flgconservpermin;}
    public java.math.BigDecimal getPeriodoconservin(){return periodoconservin;}
    public java.lang.String getCodsupportoconservin(){return codsupportoconservin;}
    public java.math.BigDecimal getIdclassificazionein(){return idclassificazionein;}
    public java.lang.String getLivelliclassificazionein(){return livelliclassificazionein;}
    public java.lang.String getDesclassificazionein(){return desclassificazionein;}
    public java.lang.String getAnnotazioniin(){return annotazioniin;}
    public java.lang.Integer getFlgrichabilxvisualizzin(){return flgrichabilxvisualizzin;}
    public java.lang.Integer getFlgrichabilxgestin(){return flgrichabilxgestin;}
    public java.lang.Integer getFlgrichabilxassegnin(){return flgrichabilxassegnin;}
    public java.lang.String getCiprovfoldertypein(){return ciprovfoldertypein;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.String getFlgmodattraddxfolderdeltipoin(){return flgmodattraddxfolderdeltipoin;}
    public java.lang.String getXmlattraddxfolderdeltipoin(){return xmlattraddxfolderdeltipoin;}
    public java.lang.String getAttributiaddin(){return attributiaddin;}
    public java.lang.Integer getFlgignorewarningin(){return flgignorewarningin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getTemplatenomein(){return templatenomein;}
    public java.lang.String getTemplatetimbroin(){return templatetimbroin;}
    public java.lang.String getTemplatecodein(){return templatecodein;}
    public java.math.BigDecimal getIdprocesstypein(){return idprocesstypein;}
    public java.lang.String getNomeprocesstypein(){return nomeprocesstypein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdfoldertypeio(java.math.BigDecimal value){this.idfoldertypeio=value;}
    public void setNomein(java.lang.String value){this.nomein=value;}
    public void setIdfoldertypegenin(java.math.BigDecimal value){this.idfoldertypegenin=value;}
    public void setNomefoldertypegenin(java.lang.String value){this.nomefoldertypegenin=value;}
    public void setFlgdascansionarein(java.lang.Integer value){this.flgdascansionarein=value;}
    public void setFlgconservpermin(java.lang.Integer value){this.flgconservpermin=value;}
    public void setPeriodoconservin(java.math.BigDecimal value){this.periodoconservin=value;}
    public void setCodsupportoconservin(java.lang.String value){this.codsupportoconservin=value;}
    public void setIdclassificazionein(java.math.BigDecimal value){this.idclassificazionein=value;}
    public void setLivelliclassificazionein(java.lang.String value){this.livelliclassificazionein=value;}
    public void setDesclassificazionein(java.lang.String value){this.desclassificazionein=value;}
    public void setAnnotazioniin(java.lang.String value){this.annotazioniin=value;}
    public void setFlgrichabilxvisualizzin(java.lang.Integer value){this.flgrichabilxvisualizzin=value;}
    public void setFlgrichabilxgestin(java.lang.Integer value){this.flgrichabilxgestin=value;}
    public void setFlgrichabilxassegnin(java.lang.Integer value){this.flgrichabilxassegnin=value;}
    public void setCiprovfoldertypein(java.lang.String value){this.ciprovfoldertypein=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setFlgmodattraddxfolderdeltipoin(java.lang.String value){this.flgmodattraddxfolderdeltipoin=value;}
    public void setXmlattraddxfolderdeltipoin(java.lang.String value){this.xmlattraddxfolderdeltipoin=value;}
    public void setAttributiaddin(java.lang.String value){this.attributiaddin=value;}
    public void setFlgignorewarningin(java.lang.Integer value){this.flgignorewarningin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setTemplatenomein(java.lang.String value){this.templatenomein=value;}
    public void setTemplatetimbroin(java.lang.String value){this.templatetimbroin=value;}
    public void setTemplatecodein(java.lang.String value){this.templatecodein=value;}
    public void setIdprocesstypein(java.math.BigDecimal value){this.idprocesstypein=value;}
    public void setNomeprocesstypein(java.lang.String value){this.nomeprocesstypein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    