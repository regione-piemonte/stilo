/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessTypesIuprocesstypeBean")
public class DmpkProcessTypesIuprocesstypeBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESS_TYPES_IUPROCESSTYPE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idprocesstypeio;
	private java.lang.String nomein;
	private java.lang.String descrizionein;
	private java.lang.String dtiniziovldin;
	private java.lang.String dtfinevldin;
	private java.math.BigDecimal idprocesstypegenin;
	private java.lang.String nomeprocesstypegenin;
	private java.lang.Integer flgprocammin;
	private java.lang.String flgtipoiniziativain;
	private java.math.BigDecimal ggdurataprevistain;
	private java.lang.String basenormativain;
	private java.lang.Integer flgsospendibilein;
	private java.math.BigDecimal nromaxsospensioniin;
	private java.lang.Integer flginterrompibilein;
	private java.math.BigDecimal nromaxggxinterrin;
	private java.lang.Integer flgpartiesternein;
	private java.lang.Integer flgsilenzioassensoin;
	private java.math.BigDecimal ggsilenzioassensoin;
	private java.lang.String flgfascsfin;
	private java.math.BigDecimal idclassificazionein;
	private java.lang.String livelliclassificazionein;
	private java.lang.String desclassificazionein;
	private java.math.BigDecimal idfoldertypein;
	private java.lang.String nomefoldertypein;
	private java.lang.Integer flgconservpermin;
	private java.math.BigDecimal periodoconservin;
	private java.lang.String codsupportoconservin;
	private java.lang.String annotazioniin;
	private java.lang.Integer flgrichabilxvisualizzin;
	private java.lang.Integer flgrichabilxgestin;
	private java.lang.Integer flgrichabilxassegnin;
	private java.lang.String ciprovprocesstypein;
	private java.lang.Integer flglockedin;
	private java.lang.String flgmodflussiwfin;
	private java.lang.String xmlflussiwfin;
	private java.math.BigDecimal iduserrespin;
	private java.lang.String desuserrespin;
	private java.math.BigDecimal iduocompetentein;
	private java.lang.String livelliuocompetentein;
	private java.lang.String desuocompetentein;
	private java.lang.String attributiaddin;
	private java.lang.Integer flgignorewarningin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String warningmsgout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdprocesstypeio(){return idprocesstypeio;}
    public java.lang.String getNomein(){return nomein;}
    public java.lang.String getDescrizionein(){return descrizionein;}
    public java.lang.String getDtiniziovldin(){return dtiniziovldin;}
    public java.lang.String getDtfinevldin(){return dtfinevldin;}
    public java.math.BigDecimal getIdprocesstypegenin(){return idprocesstypegenin;}
    public java.lang.String getNomeprocesstypegenin(){return nomeprocesstypegenin;}
    public java.lang.Integer getFlgprocammin(){return flgprocammin;}
    public java.lang.String getFlgtipoiniziativain(){return flgtipoiniziativain;}
    public java.math.BigDecimal getGgdurataprevistain(){return ggdurataprevistain;}
    public java.lang.String getBasenormativain(){return basenormativain;}
    public java.lang.Integer getFlgsospendibilein(){return flgsospendibilein;}
    public java.math.BigDecimal getNromaxsospensioniin(){return nromaxsospensioniin;}
    public java.lang.Integer getFlginterrompibilein(){return flginterrompibilein;}
    public java.math.BigDecimal getNromaxggxinterrin(){return nromaxggxinterrin;}
    public java.lang.Integer getFlgpartiesternein(){return flgpartiesternein;}
    public java.lang.Integer getFlgsilenzioassensoin(){return flgsilenzioassensoin;}
    public java.math.BigDecimal getGgsilenzioassensoin(){return ggsilenzioassensoin;}
    public java.lang.String getFlgfascsfin(){return flgfascsfin;}
    public java.math.BigDecimal getIdclassificazionein(){return idclassificazionein;}
    public java.lang.String getLivelliclassificazionein(){return livelliclassificazionein;}
    public java.lang.String getDesclassificazionein(){return desclassificazionein;}
    public java.math.BigDecimal getIdfoldertypein(){return idfoldertypein;}
    public java.lang.String getNomefoldertypein(){return nomefoldertypein;}
    public java.lang.Integer getFlgconservpermin(){return flgconservpermin;}
    public java.math.BigDecimal getPeriodoconservin(){return periodoconservin;}
    public java.lang.String getCodsupportoconservin(){return codsupportoconservin;}
    public java.lang.String getAnnotazioniin(){return annotazioniin;}
    public java.lang.Integer getFlgrichabilxvisualizzin(){return flgrichabilxvisualizzin;}
    public java.lang.Integer getFlgrichabilxgestin(){return flgrichabilxgestin;}
    public java.lang.Integer getFlgrichabilxassegnin(){return flgrichabilxassegnin;}
    public java.lang.String getCiprovprocesstypein(){return ciprovprocesstypein;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.String getFlgmodflussiwfin(){return flgmodflussiwfin;}
    public java.lang.String getXmlflussiwfin(){return xmlflussiwfin;}
    public java.math.BigDecimal getIduserrespin(){return iduserrespin;}
    public java.lang.String getDesuserrespin(){return desuserrespin;}
    public java.math.BigDecimal getIduocompetentein(){return iduocompetentein;}
    public java.lang.String getLivelliuocompetentein(){return livelliuocompetentein;}
    public java.lang.String getDesuocompetentein(){return desuocompetentein;}
    public java.lang.String getAttributiaddin(){return attributiaddin;}
    public java.lang.Integer getFlgignorewarningin(){return flgignorewarningin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdprocesstypeio(java.math.BigDecimal value){this.idprocesstypeio=value;}
    public void setNomein(java.lang.String value){this.nomein=value;}
    public void setDescrizionein(java.lang.String value){this.descrizionein=value;}
    public void setDtiniziovldin(java.lang.String value){this.dtiniziovldin=value;}
    public void setDtfinevldin(java.lang.String value){this.dtfinevldin=value;}
    public void setIdprocesstypegenin(java.math.BigDecimal value){this.idprocesstypegenin=value;}
    public void setNomeprocesstypegenin(java.lang.String value){this.nomeprocesstypegenin=value;}
    public void setFlgprocammin(java.lang.Integer value){this.flgprocammin=value;}
    public void setFlgtipoiniziativain(java.lang.String value){this.flgtipoiniziativain=value;}
    public void setGgdurataprevistain(java.math.BigDecimal value){this.ggdurataprevistain=value;}
    public void setBasenormativain(java.lang.String value){this.basenormativain=value;}
    public void setFlgsospendibilein(java.lang.Integer value){this.flgsospendibilein=value;}
    public void setNromaxsospensioniin(java.math.BigDecimal value){this.nromaxsospensioniin=value;}
    public void setFlginterrompibilein(java.lang.Integer value){this.flginterrompibilein=value;}
    public void setNromaxggxinterrin(java.math.BigDecimal value){this.nromaxggxinterrin=value;}
    public void setFlgpartiesternein(java.lang.Integer value){this.flgpartiesternein=value;}
    public void setFlgsilenzioassensoin(java.lang.Integer value){this.flgsilenzioassensoin=value;}
    public void setGgsilenzioassensoin(java.math.BigDecimal value){this.ggsilenzioassensoin=value;}
    public void setFlgfascsfin(java.lang.String value){this.flgfascsfin=value;}
    public void setIdclassificazionein(java.math.BigDecimal value){this.idclassificazionein=value;}
    public void setLivelliclassificazionein(java.lang.String value){this.livelliclassificazionein=value;}
    public void setDesclassificazionein(java.lang.String value){this.desclassificazionein=value;}
    public void setIdfoldertypein(java.math.BigDecimal value){this.idfoldertypein=value;}
    public void setNomefoldertypein(java.lang.String value){this.nomefoldertypein=value;}
    public void setFlgconservpermin(java.lang.Integer value){this.flgconservpermin=value;}
    public void setPeriodoconservin(java.math.BigDecimal value){this.periodoconservin=value;}
    public void setCodsupportoconservin(java.lang.String value){this.codsupportoconservin=value;}
    public void setAnnotazioniin(java.lang.String value){this.annotazioniin=value;}
    public void setFlgrichabilxvisualizzin(java.lang.Integer value){this.flgrichabilxvisualizzin=value;}
    public void setFlgrichabilxgestin(java.lang.Integer value){this.flgrichabilxgestin=value;}
    public void setFlgrichabilxassegnin(java.lang.Integer value){this.flgrichabilxassegnin=value;}
    public void setCiprovprocesstypein(java.lang.String value){this.ciprovprocesstypein=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setFlgmodflussiwfin(java.lang.String value){this.flgmodflussiwfin=value;}
    public void setXmlflussiwfin(java.lang.String value){this.xmlflussiwfin=value;}
    public void setIduserrespin(java.math.BigDecimal value){this.iduserrespin=value;}
    public void setDesuserrespin(java.lang.String value){this.desuserrespin=value;}
    public void setIduocompetentein(java.math.BigDecimal value){this.iduocompetentein=value;}
    public void setLivelliuocompetentein(java.lang.String value){this.livelliuocompetentein=value;}
    public void setDesuocompetentein(java.lang.String value){this.desuocompetentein=value;}
    public void setAttributiaddin(java.lang.String value){this.attributiaddin=value;}
    public void setFlgignorewarningin(java.lang.Integer value){this.flgignorewarningin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    