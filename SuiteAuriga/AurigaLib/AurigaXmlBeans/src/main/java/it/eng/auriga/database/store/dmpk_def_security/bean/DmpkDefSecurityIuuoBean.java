/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityIuuoBean")
public class DmpkDefSecurityIuuoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_IUUO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal iduoio;
	private java.math.BigDecimal iduosupin;
	private java.lang.String nrilivelliuosupin;
	private java.lang.String codtipoin;
	private java.lang.String nrolivelloin;
	private java.lang.String denominazionein;
	private java.lang.String dtistituzionein;
	private java.lang.String dtsoppressionein;
	private java.math.BigDecimal nroposizionein;
	private java.lang.String competenzein;
	private java.lang.String storiain;
	private java.math.BigDecimal idprofiloin;
	private java.lang.String nomeprofiloin;
	private java.lang.String xmldatiprofiloin;
	private java.lang.String flgmodgruppiprivin;
	private java.lang.String xmlgruppiprivin;
	private java.lang.Integer flgdichipain;
	private java.lang.String dtdichipain;
	private java.lang.String ciprovuoin;
	private java.lang.Integer flglockedin;
	private java.lang.String flgmodgruppiappin;
	private java.lang.String xmlgruppiappin;
	private java.lang.String flgmodcontattiin;
	private java.lang.String xmlcontattiin;
	private java.lang.String rowidout;
	private java.lang.String attributiaddin;
	private java.lang.Integer flgstoricizzadatiin;
	private java.lang.String tsvariazionedatiin;
	private java.lang.String motivivariazionein;
	private java.lang.String codcategattovariazionein;
	private java.lang.String siglaattovariazionein;
	private java.lang.Integer annoattovariazionein;
	private java.lang.Integer nroattovariazionein;
	private java.lang.String tsattovariazionein;
	private java.lang.Integer flgignorewarningin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String warningmsgout;
	private java.lang.Integer flgdatidastoricizzareout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.math.BigDecimal idorganigrammain;
	private java.lang.String desbreveacronimoin;
	private java.lang.String xmlpuntiprotocolloin;
	private java.lang.String xmlprececessoriin;
	private java.lang.String xmlsuccessoriin;
	private java.math.BigDecimal iduodestmailin;
	private java.math.BigDecimal iduodestdocin;
	private java.lang.Integer flgassinvioupin;
	private java.lang.Integer flgpropagaassinvioupin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIduoio(){return iduoio;}
    public java.math.BigDecimal getIduosupin(){return iduosupin;}
    public java.lang.String getNrilivelliuosupin(){return nrilivelliuosupin;}
    public java.lang.String getCodtipoin(){return codtipoin;}
    public java.lang.String getNrolivelloin(){return nrolivelloin;}
    public java.lang.String getDenominazionein(){return denominazionein;}
    public java.lang.String getDtistituzionein(){return dtistituzionein;}
    public java.lang.String getDtsoppressionein(){return dtsoppressionein;}
    public java.math.BigDecimal getNroposizionein(){return nroposizionein;}
    public java.lang.String getCompetenzein(){return competenzein;}
    public java.lang.String getStoriain(){return storiain;}
    public java.math.BigDecimal getIdprofiloin(){return idprofiloin;}
    public java.lang.String getNomeprofiloin(){return nomeprofiloin;}
    public java.lang.String getXmldatiprofiloin(){return xmldatiprofiloin;}
    public java.lang.String getFlgmodgruppiprivin(){return flgmodgruppiprivin;}
    public java.lang.String getXmlgruppiprivin(){return xmlgruppiprivin;}
    public java.lang.Integer getFlgdichipain(){return flgdichipain;}
    public java.lang.String getDtdichipain(){return dtdichipain;}
    public java.lang.String getCiprovuoin(){return ciprovuoin;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.String getFlgmodgruppiappin(){return flgmodgruppiappin;}
    public java.lang.String getXmlgruppiappin(){return xmlgruppiappin;}
    public java.lang.String getFlgmodcontattiin(){return flgmodcontattiin;}
    public java.lang.String getXmlcontattiin(){return xmlcontattiin;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getAttributiaddin(){return attributiaddin;}
    public java.lang.Integer getFlgstoricizzadatiin(){return flgstoricizzadatiin;}
    public java.lang.String getTsvariazionedatiin(){return tsvariazionedatiin;}
    public java.lang.String getMotivivariazionein(){return motivivariazionein;}
    public java.lang.String getCodcategattovariazionein(){return codcategattovariazionein;}
    public java.lang.String getSiglaattovariazionein(){return siglaattovariazionein;}
    public java.lang.Integer getAnnoattovariazionein(){return annoattovariazionein;}
    public java.lang.Integer getNroattovariazionein(){return nroattovariazionein;}
    public java.lang.String getTsattovariazionein(){return tsattovariazionein;}
    public java.lang.Integer getFlgignorewarningin(){return flgignorewarningin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.Integer getFlgdatidastoricizzareout(){return flgdatidastoricizzareout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.math.BigDecimal getIdorganigrammain(){return idorganigrammain;}
    public java.lang.String getDesbreveacronimoin(){return desbreveacronimoin;}
    public java.lang.String getXmlpuntiprotocolloin(){return xmlpuntiprotocolloin;}
    public java.lang.String getXmlprececessoriin(){return xmlprececessoriin;}
    public java.lang.String getXmlsuccessoriin(){return xmlsuccessoriin;}
    public java.math.BigDecimal getIduodestmailin(){return iduodestmailin;}
    public java.math.BigDecimal getIduodestdocin(){return iduodestdocin;}
    public java.lang.Integer getFlgassinvioupin(){return flgassinvioupin;}
    public java.lang.Integer getFlgpropagaassinvioupin(){return flgpropagaassinvioupin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIduoio(java.math.BigDecimal value){this.iduoio=value;}
    public void setIduosupin(java.math.BigDecimal value){this.iduosupin=value;}
    public void setNrilivelliuosupin(java.lang.String value){this.nrilivelliuosupin=value;}
    public void setCodtipoin(java.lang.String value){this.codtipoin=value;}
    public void setNrolivelloin(java.lang.String value){this.nrolivelloin=value;}
    public void setDenominazionein(java.lang.String value){this.denominazionein=value;}
    public void setDtistituzionein(java.lang.String value){this.dtistituzionein=value;}
    public void setDtsoppressionein(java.lang.String value){this.dtsoppressionein=value;}
    public void setNroposizionein(java.math.BigDecimal value){this.nroposizionein=value;}
    public void setCompetenzein(java.lang.String value){this.competenzein=value;}
    public void setStoriain(java.lang.String value){this.storiain=value;}
    public void setIdprofiloin(java.math.BigDecimal value){this.idprofiloin=value;}
    public void setNomeprofiloin(java.lang.String value){this.nomeprofiloin=value;}
    public void setXmldatiprofiloin(java.lang.String value){this.xmldatiprofiloin=value;}
    public void setFlgmodgruppiprivin(java.lang.String value){this.flgmodgruppiprivin=value;}
    public void setXmlgruppiprivin(java.lang.String value){this.xmlgruppiprivin=value;}
    public void setFlgdichipain(java.lang.Integer value){this.flgdichipain=value;}
    public void setDtdichipain(java.lang.String value){this.dtdichipain=value;}
    public void setCiprovuoin(java.lang.String value){this.ciprovuoin=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setFlgmodgruppiappin(java.lang.String value){this.flgmodgruppiappin=value;}
    public void setXmlgruppiappin(java.lang.String value){this.xmlgruppiappin=value;}
    public void setFlgmodcontattiin(java.lang.String value){this.flgmodcontattiin=value;}
    public void setXmlcontattiin(java.lang.String value){this.xmlcontattiin=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setAttributiaddin(java.lang.String value){this.attributiaddin=value;}
    public void setFlgstoricizzadatiin(java.lang.Integer value){this.flgstoricizzadatiin=value;}
    public void setTsvariazionedatiin(java.lang.String value){this.tsvariazionedatiin=value;}
    public void setMotivivariazionein(java.lang.String value){this.motivivariazionein=value;}
    public void setCodcategattovariazionein(java.lang.String value){this.codcategattovariazionein=value;}
    public void setSiglaattovariazionein(java.lang.String value){this.siglaattovariazionein=value;}
    public void setAnnoattovariazionein(java.lang.Integer value){this.annoattovariazionein=value;}
    public void setNroattovariazionein(java.lang.Integer value){this.nroattovariazionein=value;}
    public void setTsattovariazionein(java.lang.String value){this.tsattovariazionein=value;}
    public void setFlgignorewarningin(java.lang.Integer value){this.flgignorewarningin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setFlgdatidastoricizzareout(java.lang.Integer value){this.flgdatidastoricizzareout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setIdorganigrammain(java.math.BigDecimal value){this.idorganigrammain=value;}
    public void setDesbreveacronimoin(java.lang.String value){this.desbreveacronimoin=value;}
    public void setXmlpuntiprotocolloin(java.lang.String value){this.xmlpuntiprotocolloin=value;}
    public void setXmlprececessoriin(java.lang.String value){this.xmlprececessoriin=value;}
    public void setXmlsuccessoriin(java.lang.String value){this.xmlsuccessoriin=value;}
    public void setIduodestmailin(java.math.BigDecimal value){this.iduodestmailin=value;}
    public void setIduodestdocin(java.math.BigDecimal value){this.iduodestdocin=value;}
    public void setFlgassinvioupin(java.lang.Integer value){this.flgassinvioupin=value;}
    public void setFlgpropagaassinvioupin(java.lang.Integer value){this.flgpropagaassinvioupin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    