/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkTitolarioIuclassificaBean")
public class DmpkTitolarioIuclassificaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_TITOLARIO_IUCLASSIFICA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idpianoclassifin;
	private java.math.BigDecimal idclassificazioneio;
	private java.math.BigDecimal idclassificazionesupin;
	private java.lang.String nrilivelliclassifsupin;
	private java.lang.String nrolivelloin;
	private java.lang.String desclassificazionein;
	private java.lang.String dtiniziovldin;
	private java.lang.String dtfinevldin;
	private java.lang.String parolechiavein;
	private java.math.BigDecimal nroposizionein;
	private java.lang.String codsupportoconservin;
	private java.lang.String dessupportoconservin;
	private java.lang.Integer flgconservpermanentein;
	private java.lang.Integer periodoconservazionein;
	private java.lang.Integer flgnumcontinuain;
	private java.lang.Integer flgfolderizzaxannoin;
	private java.lang.Integer flgrichabilxvisin;
	private java.lang.Integer flgrichabilxgestin;
	private java.lang.Integer flgrichabilxfirmain;
	private java.lang.Integer flgrichabilxassegnin;
	private java.lang.Integer flgrichabilxaperfascin;
	private java.lang.Integer flgclassifinibitain;
	private java.lang.String provciclassifin;
	private java.lang.String flgmodtemplatexfascin;
	private java.lang.String xmltemplatexfascin;
	private java.lang.String rowidout;
	private java.lang.String attributiaddin;
	private java.lang.Integer flgstoricizzadatiin;
	private java.lang.String tsvariazionedatiin;
	private java.lang.String motivivariazionein;
	private java.lang.Integer flgdatidastoricizzareout;
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
    public java.math.BigDecimal getIdpianoclassifin(){return idpianoclassifin;}
    public java.math.BigDecimal getIdclassificazioneio(){return idclassificazioneio;}
    public java.math.BigDecimal getIdclassificazionesupin(){return idclassificazionesupin;}
    public java.lang.String getNrilivelliclassifsupin(){return nrilivelliclassifsupin;}
    public java.lang.String getNrolivelloin(){return nrolivelloin;}
    public java.lang.String getDesclassificazionein(){return desclassificazionein;}
    public java.lang.String getDtiniziovldin(){return dtiniziovldin;}
    public java.lang.String getDtfinevldin(){return dtfinevldin;}
    public java.lang.String getParolechiavein(){return parolechiavein;}
    public java.math.BigDecimal getNroposizionein(){return nroposizionein;}
    public java.lang.String getCodsupportoconservin(){return codsupportoconservin;}
    public java.lang.String getDessupportoconservin(){return dessupportoconservin;}
    public java.lang.Integer getFlgconservpermanentein(){return flgconservpermanentein;}
    public java.lang.Integer getPeriodoconservazionein(){return periodoconservazionein;}
    public java.lang.Integer getFlgnumcontinuain(){return flgnumcontinuain;}
    public java.lang.Integer getFlgfolderizzaxannoin(){return flgfolderizzaxannoin;}
    public java.lang.Integer getFlgrichabilxvisin(){return flgrichabilxvisin;}
    public java.lang.Integer getFlgrichabilxgestin(){return flgrichabilxgestin;}
    public java.lang.Integer getFlgrichabilxfirmain(){return flgrichabilxfirmain;}
    public java.lang.Integer getFlgrichabilxassegnin(){return flgrichabilxassegnin;}
    public java.lang.Integer getFlgrichabilxaperfascin(){return flgrichabilxaperfascin;}
    public java.lang.Integer getFlgclassifinibitain(){return flgclassifinibitain;}
    public java.lang.String getProvciclassifin(){return provciclassifin;}
    public java.lang.String getFlgmodtemplatexfascin(){return flgmodtemplatexfascin;}
    public java.lang.String getXmltemplatexfascin(){return xmltemplatexfascin;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getAttributiaddin(){return attributiaddin;}
    public java.lang.Integer getFlgstoricizzadatiin(){return flgstoricizzadatiin;}
    public java.lang.String getTsvariazionedatiin(){return tsvariazionedatiin;}
    public java.lang.String getMotivivariazionein(){return motivivariazionein;}
    public java.lang.Integer getFlgdatidastoricizzareout(){return flgdatidastoricizzareout;}
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
    public void setIdpianoclassifin(java.math.BigDecimal value){this.idpianoclassifin=value;}
    public void setIdclassificazioneio(java.math.BigDecimal value){this.idclassificazioneio=value;}
    public void setIdclassificazionesupin(java.math.BigDecimal value){this.idclassificazionesupin=value;}
    public void setNrilivelliclassifsupin(java.lang.String value){this.nrilivelliclassifsupin=value;}
    public void setNrolivelloin(java.lang.String value){this.nrolivelloin=value;}
    public void setDesclassificazionein(java.lang.String value){this.desclassificazionein=value;}
    public void setDtiniziovldin(java.lang.String value){this.dtiniziovldin=value;}
    public void setDtfinevldin(java.lang.String value){this.dtfinevldin=value;}
    public void setParolechiavein(java.lang.String value){this.parolechiavein=value;}
    public void setNroposizionein(java.math.BigDecimal value){this.nroposizionein=value;}
    public void setCodsupportoconservin(java.lang.String value){this.codsupportoconservin=value;}
    public void setDessupportoconservin(java.lang.String value){this.dessupportoconservin=value;}
    public void setFlgconservpermanentein(java.lang.Integer value){this.flgconservpermanentein=value;}
    public void setPeriodoconservazionein(java.lang.Integer value){this.periodoconservazionein=value;}
    public void setFlgnumcontinuain(java.lang.Integer value){this.flgnumcontinuain=value;}
    public void setFlgfolderizzaxannoin(java.lang.Integer value){this.flgfolderizzaxannoin=value;}
    public void setFlgrichabilxvisin(java.lang.Integer value){this.flgrichabilxvisin=value;}
    public void setFlgrichabilxgestin(java.lang.Integer value){this.flgrichabilxgestin=value;}
    public void setFlgrichabilxfirmain(java.lang.Integer value){this.flgrichabilxfirmain=value;}
    public void setFlgrichabilxassegnin(java.lang.Integer value){this.flgrichabilxassegnin=value;}
    public void setFlgrichabilxaperfascin(java.lang.Integer value){this.flgrichabilxaperfascin=value;}
    public void setFlgclassifinibitain(java.lang.Integer value){this.flgclassifinibitain=value;}
    public void setProvciclassifin(java.lang.String value){this.provciclassifin=value;}
    public void setFlgmodtemplatexfascin(java.lang.String value){this.flgmodtemplatexfascin=value;}
    public void setXmltemplatexfascin(java.lang.String value){this.xmltemplatexfascin=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setAttributiaddin(java.lang.String value){this.attributiaddin=value;}
    public void setFlgstoricizzadatiin(java.lang.Integer value){this.flgstoricizzadatiin=value;}
    public void setTsvariazionedatiin(java.lang.String value){this.tsvariazionedatiin=value;}
    public void setMotivivariazionein(java.lang.String value){this.motivivariazionein=value;}
    public void setFlgdatidastoricizzareout(java.lang.Integer value){this.flgdatidastoricizzareout=value;}
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