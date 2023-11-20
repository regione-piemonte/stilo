/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkTipiRegNumUdIutiporegnumBean")
public class DmpkTipiRegNumUdIutiporegnumBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_TIPI_REG_NUM_UD_IUTIPOREGNUM";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idtiporennumio;
	private java.lang.String codcategoriain;
	private java.lang.String siglain;
	private java.lang.String descrizionein;
	private java.lang.String dtiniziovldin;
	private java.lang.String dtfinevldin;
	private java.lang.Integer flginternain;
	private java.lang.String flgstatoregistroin;
	private java.lang.Integer periodicitadianniin;
	private java.lang.Integer annoinizionumerazionein;
	private java.lang.Integer flgrichabilxvisualizzin;
	private java.lang.Integer flgrichabilxgestin;
	private java.lang.Integer flgrichabilxassegnin;
	private java.lang.Integer flgrichabilxfirmain;
	private java.lang.String ciprovtiporegnumin;
	private java.lang.Integer flglockedin;
	private java.lang.Integer flgbuchiammessiin;
	private java.lang.String gruppoappin;
	private java.lang.String restrizioniversoregin;
	private java.math.BigDecimal flgctrabiluomittin;
	private java.math.BigDecimal nroinizialein;
	private java.lang.String flgammescxtipidocin;
	private java.lang.String flgmodtipidocammesclin;
	private java.lang.String xmltipidocammescin;
	private java.lang.String flgmodattraddxdocdeltipoin;
	private java.lang.String xmlattraddxdocdeltipoin;
	private java.lang.String attributiaddin;
	private java.lang.String rowidout;
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
    public java.math.BigDecimal getIdtiporennumio(){return idtiporennumio;}
    public java.lang.String getCodcategoriain(){return codcategoriain;}
    public java.lang.String getSiglain(){return siglain;}
    public java.lang.String getDescrizionein(){return descrizionein;}
    public java.lang.String getDtiniziovldin(){return dtiniziovldin;}
    public java.lang.String getDtfinevldin(){return dtfinevldin;}
    public java.lang.Integer getFlginternain(){return flginternain;}
    public java.lang.String getFlgstatoregistroin(){return flgstatoregistroin;}
    public java.lang.Integer getPeriodicitadianniin(){return periodicitadianniin;}
    public java.lang.Integer getAnnoinizionumerazionein(){return annoinizionumerazionein;}
    public java.lang.Integer getFlgrichabilxvisualizzin(){return flgrichabilxvisualizzin;}
    public java.lang.Integer getFlgrichabilxgestin(){return flgrichabilxgestin;}
    public java.lang.Integer getFlgrichabilxassegnin(){return flgrichabilxassegnin;}
    public java.lang.Integer getFlgrichabilxfirmain(){return flgrichabilxfirmain;}
    public java.lang.String getCiprovtiporegnumin(){return ciprovtiporegnumin;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.Integer getFlgbuchiammessiin(){return flgbuchiammessiin;}
    public java.lang.String getGruppoappin(){return gruppoappin;}
    public java.lang.String getRestrizioniversoregin(){return restrizioniversoregin;}
    public java.math.BigDecimal getFlgctrabiluomittin(){return flgctrabiluomittin;}
    public java.math.BigDecimal getNroinizialein(){return nroinizialein;}
    public java.lang.String getFlgammescxtipidocin(){return flgammescxtipidocin;}
    public java.lang.String getFlgmodtipidocammesclin(){return flgmodtipidocammesclin;}
    public java.lang.String getXmltipidocammescin(){return xmltipidocammescin;}
    public java.lang.String getFlgmodattraddxdocdeltipoin(){return flgmodattraddxdocdeltipoin;}
    public java.lang.String getXmlattraddxdocdeltipoin(){return xmlattraddxdocdeltipoin;}
    public java.lang.String getAttributiaddin(){return attributiaddin;}
    public java.lang.String getRowidout(){return rowidout;}
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
    public void setIdtiporennumio(java.math.BigDecimal value){this.idtiporennumio=value;}
    public void setCodcategoriain(java.lang.String value){this.codcategoriain=value;}
    public void setSiglain(java.lang.String value){this.siglain=value;}
    public void setDescrizionein(java.lang.String value){this.descrizionein=value;}
    public void setDtiniziovldin(java.lang.String value){this.dtiniziovldin=value;}
    public void setDtfinevldin(java.lang.String value){this.dtfinevldin=value;}
    public void setFlginternain(java.lang.Integer value){this.flginternain=value;}
    public void setFlgstatoregistroin(java.lang.String value){this.flgstatoregistroin=value;}
    public void setPeriodicitadianniin(java.lang.Integer value){this.periodicitadianniin=value;}
    public void setAnnoinizionumerazionein(java.lang.Integer value){this.annoinizionumerazionein=value;}
    public void setFlgrichabilxvisualizzin(java.lang.Integer value){this.flgrichabilxvisualizzin=value;}
    public void setFlgrichabilxgestin(java.lang.Integer value){this.flgrichabilxgestin=value;}
    public void setFlgrichabilxassegnin(java.lang.Integer value){this.flgrichabilxassegnin=value;}
    public void setFlgrichabilxfirmain(java.lang.Integer value){this.flgrichabilxfirmain=value;}
    public void setCiprovtiporegnumin(java.lang.String value){this.ciprovtiporegnumin=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setFlgbuchiammessiin(java.lang.Integer value){this.flgbuchiammessiin=value;}
    public void setGruppoappin(java.lang.String value){this.gruppoappin=value;}
    public void setRestrizioniversoregin(java.lang.String value){this.restrizioniversoregin=value;}
    public void setFlgctrabiluomittin(java.math.BigDecimal value){this.flgctrabiluomittin=value;}
    public void setNroinizialein(java.math.BigDecimal value){this.nroinizialein=value;}
    public void setFlgammescxtipidocin(java.lang.String value){this.flgammescxtipidocin=value;}
    public void setFlgmodtipidocammesclin(java.lang.String value){this.flgmodtipidocammesclin=value;}
    public void setXmltipidocammescin(java.lang.String value){this.xmltipidocammescin=value;}
    public void setFlgmodattraddxdocdeltipoin(java.lang.String value){this.flgmodattraddxdocdeltipoin=value;}
    public void setXmlattraddxdocdeltipoin(java.lang.String value){this.xmlattraddxdocdeltipoin=value;}
    public void setAttributiaddin(java.lang.String value){this.attributiaddin=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
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