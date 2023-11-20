/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAttributiDinamiciIuattributoBean")
public class DmpkAttributiDinamiciIuattributoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ATTRIBUTI_DINAMICI_IUATTRIBUTO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String rowidio;
	private java.lang.String nomein;
	private java.lang.String labelin;
	private java.lang.String tipoin;
	private java.lang.String subtipoin;
	private java.lang.String descrizionein;
	private java.lang.String nomeattrappin;
	private java.lang.String labelattrappin;
	private java.lang.Integer nroordinattrappin;
	private java.lang.Integer nrorigainattrappin;
	private java.lang.Integer flgobbliginattrappin;
	private java.lang.Integer nrocifrecaratteriin;
	private java.lang.Integer precisionedecimalein;
	private java.lang.String formatonumericoin;
	private java.lang.String valoreminin;
	private java.lang.String valoremaxin;
	private java.lang.String restrizionisulcasein;
	private java.lang.Integer larghguiin;
	private java.lang.Integer altezzaguiin;
	private java.lang.String valoredefaultin;
	private java.lang.String querypervaloripossibiliin;
	private java.lang.String urlwsvaloripossibiliin;
	private java.lang.String xmlinwsvaloripossibiliin;
	private java.lang.Integer flgdaindicizzarein;
	private java.lang.Integer flgprotectedin;
	private java.lang.Integer flgvaloriunivociin;
	private java.lang.String regularexprin;
	private java.lang.String flgmodvaloripossibiliin;
	private java.lang.String xmlvaloripossibiliin;
	private java.lang.String flgmodaclin;
	private java.lang.String xmlaclin;
	private java.lang.String nomeidlookupin;
	private java.math.BigDecimal nrocollookupin;
	private java.lang.String filtroinlookupin;
	private java.lang.Integer flgsolovaldalookupin;
	private java.lang.Integer flgignorewarningin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String warningmsgout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgcallbyguiin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getRowidio(){return rowidio;}
    public java.lang.String getNomein(){return nomein;}
    public java.lang.String getLabelin(){return labelin;}
    public java.lang.String getTipoin(){return tipoin;}
    public java.lang.String getSubtipoin(){return subtipoin;}
    public java.lang.String getDescrizionein(){return descrizionein;}
    public java.lang.String getNomeattrappin(){return nomeattrappin;}
    public java.lang.String getLabelattrappin(){return labelattrappin;}
    public java.lang.Integer getNroordinattrappin(){return nroordinattrappin;}
    public java.lang.Integer getNrorigainattrappin(){return nrorigainattrappin;}
    public java.lang.Integer getFlgobbliginattrappin(){return flgobbliginattrappin;}
    public java.lang.Integer getNrocifrecaratteriin(){return nrocifrecaratteriin;}
    public java.lang.Integer getPrecisionedecimalein(){return precisionedecimalein;}
    public java.lang.String getFormatonumericoin(){return formatonumericoin;}
    public java.lang.String getValoreminin(){return valoreminin;}
    public java.lang.String getValoremaxin(){return valoremaxin;}
    public java.lang.String getRestrizionisulcasein(){return restrizionisulcasein;}
    public java.lang.Integer getLarghguiin(){return larghguiin;}
    public java.lang.Integer getAltezzaguiin(){return altezzaguiin;}
    public java.lang.String getValoredefaultin(){return valoredefaultin;}
    public java.lang.String getQuerypervaloripossibiliin(){return querypervaloripossibiliin;}
    public java.lang.String getUrlwsvaloripossibiliin(){return urlwsvaloripossibiliin;}
    public java.lang.String getXmlinwsvaloripossibiliin(){return xmlinwsvaloripossibiliin;}
    public java.lang.Integer getFlgdaindicizzarein(){return flgdaindicizzarein;}
    public java.lang.Integer getFlgprotectedin(){return flgprotectedin;}
    public java.lang.Integer getFlgvaloriunivociin(){return flgvaloriunivociin;}
    public java.lang.String getRegularexprin(){return regularexprin;}
    public java.lang.String getFlgmodvaloripossibiliin(){return flgmodvaloripossibiliin;}
    public java.lang.String getXmlvaloripossibiliin(){return xmlvaloripossibiliin;}
    public java.lang.String getFlgmodaclin(){return flgmodaclin;}
    public java.lang.String getXmlaclin(){return xmlaclin;}
    public java.lang.String getNomeidlookupin(){return nomeidlookupin;}
    public java.math.BigDecimal getNrocollookupin(){return nrocollookupin;}
    public java.lang.String getFiltroinlookupin(){return filtroinlookupin;}
    public java.lang.Integer getFlgsolovaldalookupin(){return flgsolovaldalookupin;}
    public java.lang.Integer getFlgignorewarningin(){return flgignorewarningin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgcallbyguiin(){return flgcallbyguiin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setRowidio(java.lang.String value){this.rowidio=value;}
    public void setNomein(java.lang.String value){this.nomein=value;}
    public void setLabelin(java.lang.String value){this.labelin=value;}
    public void setTipoin(java.lang.String value){this.tipoin=value;}
    public void setSubtipoin(java.lang.String value){this.subtipoin=value;}
    public void setDescrizionein(java.lang.String value){this.descrizionein=value;}
    public void setNomeattrappin(java.lang.String value){this.nomeattrappin=value;}
    public void setLabelattrappin(java.lang.String value){this.labelattrappin=value;}
    public void setNroordinattrappin(java.lang.Integer value){this.nroordinattrappin=value;}
    public void setNrorigainattrappin(java.lang.Integer value){this.nrorigainattrappin=value;}
    public void setFlgobbliginattrappin(java.lang.Integer value){this.flgobbliginattrappin=value;}
    public void setNrocifrecaratteriin(java.lang.Integer value){this.nrocifrecaratteriin=value;}
    public void setPrecisionedecimalein(java.lang.Integer value){this.precisionedecimalein=value;}
    public void setFormatonumericoin(java.lang.String value){this.formatonumericoin=value;}
    public void setValoreminin(java.lang.String value){this.valoreminin=value;}
    public void setValoremaxin(java.lang.String value){this.valoremaxin=value;}
    public void setRestrizionisulcasein(java.lang.String value){this.restrizionisulcasein=value;}
    public void setLarghguiin(java.lang.Integer value){this.larghguiin=value;}
    public void setAltezzaguiin(java.lang.Integer value){this.altezzaguiin=value;}
    public void setValoredefaultin(java.lang.String value){this.valoredefaultin=value;}
    public void setQuerypervaloripossibiliin(java.lang.String value){this.querypervaloripossibiliin=value;}
    public void setUrlwsvaloripossibiliin(java.lang.String value){this.urlwsvaloripossibiliin=value;}
    public void setXmlinwsvaloripossibiliin(java.lang.String value){this.xmlinwsvaloripossibiliin=value;}
    public void setFlgdaindicizzarein(java.lang.Integer value){this.flgdaindicizzarein=value;}
    public void setFlgprotectedin(java.lang.Integer value){this.flgprotectedin=value;}
    public void setFlgvaloriunivociin(java.lang.Integer value){this.flgvaloriunivociin=value;}
    public void setRegularexprin(java.lang.String value){this.regularexprin=value;}
    public void setFlgmodvaloripossibiliin(java.lang.String value){this.flgmodvaloripossibiliin=value;}
    public void setXmlvaloripossibiliin(java.lang.String value){this.xmlvaloripossibiliin=value;}
    public void setFlgmodaclin(java.lang.String value){this.flgmodaclin=value;}
    public void setXmlaclin(java.lang.String value){this.xmlaclin=value;}
    public void setNomeidlookupin(java.lang.String value){this.nomeidlookupin=value;}
    public void setNrocollookupin(java.math.BigDecimal value){this.nrocollookupin=value;}
    public void setFiltroinlookupin(java.lang.String value){this.filtroinlookupin=value;}
    public void setFlgsolovaldalookupin(java.lang.Integer value){this.flgsolovaldalookupin=value;}
    public void setFlgignorewarningin(java.lang.Integer value){this.flgignorewarningin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgcallbyguiin(java.lang.Integer value){this.flgcallbyguiin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    