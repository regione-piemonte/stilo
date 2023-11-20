/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityLoaddettuoestesaBean")
public class DmpkDefSecurityLoaddettuoestesaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_LOADDETTUOESTESA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal iduoio;
	private java.lang.String nrilivelliuoio;
	private java.lang.String denominazioneio;
	private java.lang.String tsrifin;
	private java.math.BigDecimal iduosupout;
	private java.math.BigDecimal nrolivellogerarchicouosupout;
	private java.math.BigDecimal nrolivellogerarchicoout;
	private java.lang.String codtipoout;
	private java.lang.String destipoout;
	private java.lang.String dtistituzioneout;
	private java.lang.String dtsoppressioneout;
	private java.math.BigDecimal nroposizioneout;
	private java.lang.String competenzeout;
	private java.lang.String storiaout;
	private java.math.BigDecimal idprofiloout;
	private java.lang.String nomeprofiloout;
	private java.lang.String xmldatiprofiloout;
	private java.lang.String xmlgruppiprivout;
	private java.lang.Integer flgdichipaout;
	private java.lang.String dtdichipaout;
	private java.lang.String ciprovuoout;
	private java.lang.Integer flglockedout;
	private java.lang.String xmlgruppiappout;
	private java.lang.String xmlcontattiout;
	private java.lang.String rowidout;
	private java.lang.String attributiaddout;
	private java.lang.Integer bachsizeout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.math.BigDecimal idorganigrammain;
	private java.lang.String xmlpuntiprotocolloout;
	private java.lang.String xmlprececessoriout;
	private java.lang.String xmlsuccessoriout;
	private java.lang.Integer flgpresentimailout;
	private java.math.BigDecimal iduodestmailout;
	private java.lang.String livelliuodestmailout;
	private java.lang.String desuodestmailout;
	private java.lang.String statospostmailout;
	private java.lang.String tsiniziospostmailout;
	private java.lang.String tsfinespostmailout;
	private java.math.BigDecimal nromailassout;
	private java.lang.String tsrilevnromailassout;
	private java.lang.Integer flgpresentidocfascout;
	private java.math.BigDecimal iduodestdocfascout;
	private java.lang.String livelliuodestdocfascout;
	private java.lang.String desuodestdocfascout;
	private java.lang.String statospostdocfascout;
	private java.lang.String tsiniziospostdocfascout;
	private java.lang.String tsfinespostdocfascout;
	private java.math.BigDecimal nrodocassout;
	private java.lang.String tsrilevnrodocassout;
	private java.math.BigDecimal nrofascassout;
	private java.lang.String tsrilevnrofascassout;
	private java.lang.Integer flgassinvioupout;
	private java.lang.Integer flgpropagaassinvioupout;
	private java.lang.Integer flgereditaassinvioupout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIduoio(){return iduoio;}
    public java.lang.String getNrilivelliuoio(){return nrilivelliuoio;}
    public java.lang.String getDenominazioneio(){return denominazioneio;}
    public java.lang.String getTsrifin(){return tsrifin;}
    public java.math.BigDecimal getIduosupout(){return iduosupout;}
    public java.math.BigDecimal getNrolivellogerarchicouosupout(){return nrolivellogerarchicouosupout;}
    public java.math.BigDecimal getNrolivellogerarchicoout(){return nrolivellogerarchicoout;}
    public java.lang.String getCodtipoout(){return codtipoout;}
    public java.lang.String getDestipoout(){return destipoout;}
    public java.lang.String getDtistituzioneout(){return dtistituzioneout;}
    public java.lang.String getDtsoppressioneout(){return dtsoppressioneout;}
    public java.math.BigDecimal getNroposizioneout(){return nroposizioneout;}
    public java.lang.String getCompetenzeout(){return competenzeout;}
    public java.lang.String getStoriaout(){return storiaout;}
    public java.math.BigDecimal getIdprofiloout(){return idprofiloout;}
    public java.lang.String getNomeprofiloout(){return nomeprofiloout;}
    public java.lang.String getXmldatiprofiloout(){return xmldatiprofiloout;}
    public java.lang.String getXmlgruppiprivout(){return xmlgruppiprivout;}
    public java.lang.Integer getFlgdichipaout(){return flgdichipaout;}
    public java.lang.String getDtdichipaout(){return dtdichipaout;}
    public java.lang.String getCiprovuoout(){return ciprovuoout;}
    public java.lang.Integer getFlglockedout(){return flglockedout;}
    public java.lang.String getXmlgruppiappout(){return xmlgruppiappout;}
    public java.lang.String getXmlcontattiout(){return xmlcontattiout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getBachsizeout(){return bachsizeout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.math.BigDecimal getIdorganigrammain(){return idorganigrammain;}
    public java.lang.String getXmlpuntiprotocolloout(){return xmlpuntiprotocolloout;}
    public java.lang.String getXmlprececessoriout(){return xmlprececessoriout;}
    public java.lang.String getXmlsuccessoriout(){return xmlsuccessoriout;}
    public java.lang.Integer getFlgpresentimailout(){return flgpresentimailout;}
    public java.math.BigDecimal getIduodestmailout(){return iduodestmailout;}
    public java.lang.String getLivelliuodestmailout(){return livelliuodestmailout;}
    public java.lang.String getDesuodestmailout(){return desuodestmailout;}
    public java.lang.String getStatospostmailout(){return statospostmailout;}
    public java.lang.String getTsiniziospostmailout(){return tsiniziospostmailout;}
    public java.lang.String getTsfinespostmailout(){return tsfinespostmailout;}
    public java.math.BigDecimal getNromailassout(){return nromailassout;}
    public java.lang.String getTsrilevnromailassout(){return tsrilevnromailassout;}
    public java.lang.Integer getFlgpresentidocfascout(){return flgpresentidocfascout;}
    public java.math.BigDecimal getIduodestdocfascout(){return iduodestdocfascout;}
    public java.lang.String getLivelliuodestdocfascout(){return livelliuodestdocfascout;}
    public java.lang.String getDesuodestdocfascout(){return desuodestdocfascout;}
    public java.lang.String getStatospostdocfascout(){return statospostdocfascout;}
    public java.lang.String getTsiniziospostdocfascout(){return tsiniziospostdocfascout;}
    public java.lang.String getTsfinespostdocfascout(){return tsfinespostdocfascout;}
    public java.math.BigDecimal getNrodocassout(){return nrodocassout;}
    public java.lang.String getTsrilevnrodocassout(){return tsrilevnrodocassout;}
    public java.math.BigDecimal getNrofascassout(){return nrofascassout;}
    public java.lang.String getTsrilevnrofascassout(){return tsrilevnrofascassout;}
    public java.lang.Integer getFlgassinvioupout(){return flgassinvioupout;}
    public java.lang.Integer getFlgpropagaassinvioupout(){return flgpropagaassinvioupout;}
    public java.lang.Integer getFlgereditaassinvioupout(){return flgereditaassinvioupout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIduoio(java.math.BigDecimal value){this.iduoio=value;}
    public void setNrilivelliuoio(java.lang.String value){this.nrilivelliuoio=value;}
    public void setDenominazioneio(java.lang.String value){this.denominazioneio=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    public void setIduosupout(java.math.BigDecimal value){this.iduosupout=value;}
    public void setNrolivellogerarchicouosupout(java.math.BigDecimal value){this.nrolivellogerarchicouosupout=value;}
    public void setNrolivellogerarchicoout(java.math.BigDecimal value){this.nrolivellogerarchicoout=value;}
    public void setCodtipoout(java.lang.String value){this.codtipoout=value;}
    public void setDestipoout(java.lang.String value){this.destipoout=value;}
    public void setDtistituzioneout(java.lang.String value){this.dtistituzioneout=value;}
    public void setDtsoppressioneout(java.lang.String value){this.dtsoppressioneout=value;}
    public void setNroposizioneout(java.math.BigDecimal value){this.nroposizioneout=value;}
    public void setCompetenzeout(java.lang.String value){this.competenzeout=value;}
    public void setStoriaout(java.lang.String value){this.storiaout=value;}
    public void setIdprofiloout(java.math.BigDecimal value){this.idprofiloout=value;}
    public void setNomeprofiloout(java.lang.String value){this.nomeprofiloout=value;}
    public void setXmldatiprofiloout(java.lang.String value){this.xmldatiprofiloout=value;}
    public void setXmlgruppiprivout(java.lang.String value){this.xmlgruppiprivout=value;}
    public void setFlgdichipaout(java.lang.Integer value){this.flgdichipaout=value;}
    public void setDtdichipaout(java.lang.String value){this.dtdichipaout=value;}
    public void setCiprovuoout(java.lang.String value){this.ciprovuoout=value;}
    public void setFlglockedout(java.lang.Integer value){this.flglockedout=value;}
    public void setXmlgruppiappout(java.lang.String value){this.xmlgruppiappout=value;}
    public void setXmlcontattiout(java.lang.String value){this.xmlcontattiout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setBachsizeout(java.lang.Integer value){this.bachsizeout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setIdorganigrammain(java.math.BigDecimal value){this.idorganigrammain=value;}
    public void setXmlpuntiprotocolloout(java.lang.String value){this.xmlpuntiprotocolloout=value;}
    public void setXmlprececessoriout(java.lang.String value){this.xmlprececessoriout=value;}
    public void setXmlsuccessoriout(java.lang.String value){this.xmlsuccessoriout=value;}
    public void setFlgpresentimailout(java.lang.Integer value){this.flgpresentimailout=value;}
    public void setIduodestmailout(java.math.BigDecimal value){this.iduodestmailout=value;}
    public void setLivelliuodestmailout(java.lang.String value){this.livelliuodestmailout=value;}
    public void setDesuodestmailout(java.lang.String value){this.desuodestmailout=value;}
    public void setStatospostmailout(java.lang.String value){this.statospostmailout=value;}
    public void setTsiniziospostmailout(java.lang.String value){this.tsiniziospostmailout=value;}
    public void setTsfinespostmailout(java.lang.String value){this.tsfinespostmailout=value;}
    public void setNromailassout(java.math.BigDecimal value){this.nromailassout=value;}
    public void setTsrilevnromailassout(java.lang.String value){this.tsrilevnromailassout=value;}
    public void setFlgpresentidocfascout(java.lang.Integer value){this.flgpresentidocfascout=value;}
    public void setIduodestdocfascout(java.math.BigDecimal value){this.iduodestdocfascout=value;}
    public void setLivelliuodestdocfascout(java.lang.String value){this.livelliuodestdocfascout=value;}
    public void setDesuodestdocfascout(java.lang.String value){this.desuodestdocfascout=value;}
    public void setStatospostdocfascout(java.lang.String value){this.statospostdocfascout=value;}
    public void setTsiniziospostdocfascout(java.lang.String value){this.tsiniziospostdocfascout=value;}
    public void setTsfinespostdocfascout(java.lang.String value){this.tsfinespostdocfascout=value;}
    public void setNrodocassout(java.math.BigDecimal value){this.nrodocassout=value;}
    public void setTsrilevnrodocassout(java.lang.String value){this.tsrilevnrodocassout=value;}
    public void setNrofascassout(java.math.BigDecimal value){this.nrofascassout=value;}
    public void setTsrilevnrofascassout(java.lang.String value){this.tsrilevnrofascassout=value;}
    public void setFlgassinvioupout(java.lang.Integer value){this.flgassinvioupout=value;}
    public void setFlgpropagaassinvioupout(java.lang.Integer value){this.flgpropagaassinvioupout=value;}
    public void setFlgereditaassinvioupout(java.lang.Integer value){this.flgereditaassinvioupout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    