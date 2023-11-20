/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityLoaddettuserBean")
public class DmpkDefSecurityLoaddettuserBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_LOADDETTUSER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal iduserio;
	private java.lang.String desuserio;
	private java.lang.String usernameio;
	private java.lang.String dtiniziovldout;
	private java.lang.String dtfinevldout;
	private java.lang.String dtinizioaccredindomout;
	private java.lang.String dtfineaccredindomout;
	private java.math.BigDecimal idprofiloout;
	private java.lang.String nomeprofiloout;
	private java.lang.String xmldatiprofiloout;
	private java.lang.String xmlgruppiprivout;
	private java.math.BigDecimal idsoggrubricaout;
	private java.lang.String titoloout;
	private java.lang.String qualificaout;
	private java.lang.String nromatricolaout;
	private java.lang.String emailout;
	private java.lang.Integer flgsenzaaccessoalsistemaout;
	private java.lang.String ciprovuserout;
	private java.lang.Integer flglockedout;
	private java.lang.String rowidout;
	private java.lang.String xmlapplestaccredout;
	private java.lang.String xmlrelconuoout;
	private java.lang.String visdocassinvauoxmlout;
	private java.lang.String moddocassauoxmlout;
	private java.lang.String xmluocaselleemailvisbout;
	private java.lang.String xmldelegheout;
	private java.lang.String xmlgruppiappout;
	private java.lang.String indirizziout;
	private java.lang.String attributiaddout;
	private java.lang.Integer bachsizeout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String codrapidoout;
	private java.lang.String codfiscaleout;
	private java.lang.Integer accountdeflockedout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIduserio(){return iduserio;}
    public java.lang.String getDesuserio(){return desuserio;}
    public java.lang.String getUsernameio(){return usernameio;}
    public java.lang.String getDtiniziovldout(){return dtiniziovldout;}
    public java.lang.String getDtfinevldout(){return dtfinevldout;}
    public java.lang.String getDtinizioaccredindomout(){return dtinizioaccredindomout;}
    public java.lang.String getDtfineaccredindomout(){return dtfineaccredindomout;}
    public java.math.BigDecimal getIdprofiloout(){return idprofiloout;}
    public java.lang.String getNomeprofiloout(){return nomeprofiloout;}
    public java.lang.String getXmldatiprofiloout(){return xmldatiprofiloout;}
    public java.lang.String getXmlgruppiprivout(){return xmlgruppiprivout;}
    public java.math.BigDecimal getIdsoggrubricaout(){return idsoggrubricaout;}
    public java.lang.String getTitoloout(){return titoloout;}
    public java.lang.String getQualificaout(){return qualificaout;}
    public java.lang.String getNromatricolaout(){return nromatricolaout;}
    public java.lang.String getEmailout(){return emailout;}
    public java.lang.Integer getFlgsenzaaccessoalsistemaout(){return flgsenzaaccessoalsistemaout;}
    public java.lang.String getCiprovuserout(){return ciprovuserout;}
    public java.lang.Integer getFlglockedout(){return flglockedout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getXmlapplestaccredout(){return xmlapplestaccredout;}
    public java.lang.String getXmlrelconuoout(){return xmlrelconuoout;}
    public java.lang.String getVisdocassinvauoxmlout(){return visdocassinvauoxmlout;}
    public java.lang.String getModdocassauoxmlout(){return moddocassauoxmlout;}
    public java.lang.String getXmluocaselleemailvisbout(){return xmluocaselleemailvisbout;}
    public java.lang.String getXmldelegheout(){return xmldelegheout;}
    public java.lang.String getXmlgruppiappout(){return xmlgruppiappout;}
    public java.lang.String getIndirizziout(){return indirizziout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getBachsizeout(){return bachsizeout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getCodrapidoout(){return codrapidoout;}
    public java.lang.String getCodfiscaleout(){return codfiscaleout;}
    public java.lang.Integer getAccountdeflockedout(){return accountdeflockedout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIduserio(java.math.BigDecimal value){this.iduserio=value;}
    public void setDesuserio(java.lang.String value){this.desuserio=value;}
    public void setUsernameio(java.lang.String value){this.usernameio=value;}
    public void setDtiniziovldout(java.lang.String value){this.dtiniziovldout=value;}
    public void setDtfinevldout(java.lang.String value){this.dtfinevldout=value;}
    public void setDtinizioaccredindomout(java.lang.String value){this.dtinizioaccredindomout=value;}
    public void setDtfineaccredindomout(java.lang.String value){this.dtfineaccredindomout=value;}
    public void setIdprofiloout(java.math.BigDecimal value){this.idprofiloout=value;}
    public void setNomeprofiloout(java.lang.String value){this.nomeprofiloout=value;}
    public void setXmldatiprofiloout(java.lang.String value){this.xmldatiprofiloout=value;}
    public void setXmlgruppiprivout(java.lang.String value){this.xmlgruppiprivout=value;}
    public void setIdsoggrubricaout(java.math.BigDecimal value){this.idsoggrubricaout=value;}
    public void setTitoloout(java.lang.String value){this.titoloout=value;}
    public void setQualificaout(java.lang.String value){this.qualificaout=value;}
    public void setNromatricolaout(java.lang.String value){this.nromatricolaout=value;}
    public void setEmailout(java.lang.String value){this.emailout=value;}
    public void setFlgsenzaaccessoalsistemaout(java.lang.Integer value){this.flgsenzaaccessoalsistemaout=value;}
    public void setCiprovuserout(java.lang.String value){this.ciprovuserout=value;}
    public void setFlglockedout(java.lang.Integer value){this.flglockedout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setXmlapplestaccredout(java.lang.String value){this.xmlapplestaccredout=value;}
    public void setXmlrelconuoout(java.lang.String value){this.xmlrelconuoout=value;}
    public void setVisdocassinvauoxmlout(java.lang.String value){this.visdocassinvauoxmlout=value;}
    public void setModdocassauoxmlout(java.lang.String value){this.moddocassauoxmlout=value;}
    public void setXmluocaselleemailvisbout(java.lang.String value){this.xmluocaselleemailvisbout=value;}
    public void setXmldelegheout(java.lang.String value){this.xmldelegheout=value;}
    public void setXmlgruppiappout(java.lang.String value){this.xmlgruppiappout=value;}
    public void setIndirizziout(java.lang.String value){this.indirizziout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setBachsizeout(java.lang.Integer value){this.bachsizeout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setCodrapidoout(java.lang.String value){this.codrapidoout=value;}
    public void setCodfiscaleout(java.lang.String value){this.codfiscaleout=value;}
    public void setAccountdeflockedout(java.lang.Integer value){this.accountdeflockedout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    