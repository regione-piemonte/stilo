/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityIuuserBean")
public class DmpkDefSecurityIuuserBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_IUUSER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal iduserio;
	private java.lang.String desuserin;
	private java.lang.String usernamein;
	private java.lang.String passwordin;
	private java.lang.String confermapasswordin;
	private java.lang.String dtiniziovldin;
	private java.lang.String dtfinevldin;
	private java.lang.String dtinizioaccredindomin;
	private java.lang.String dtfineaccredindomin;
	private java.math.BigDecimal idprofiloin;
	private java.lang.String nomeprofiloin;
	private java.lang.String xmldatiprofiloin;
	private java.lang.String flgmodgruppiprivin;
	private java.lang.String xmlgruppiprivin;
	private java.math.BigDecimal idsoggrubricain;
	private java.lang.String titoloin;
	private java.lang.String qualificain;
	private java.lang.String nromatricolain;
	private java.lang.String emailin;
	private java.lang.Integer flgsenzaaccessoalsistemain;
	private java.lang.String ciprovuserin;
	private java.lang.Integer flglockedin;
	private java.lang.String flgmodapplestaccredin;
	private java.lang.String xmlapplestaccredin;
	private java.lang.String flgmodrelconuoin;
	private java.lang.String xmlrelconuoin;
	private java.lang.String flgmoduovisdocassinvin;
	private java.lang.String visdocassinvauoxmlin;
	private java.lang.String flgmoduomoddocassin;
	private java.lang.String moddocassauoxmlin;
	private java.lang.String flgmoddeleghein;
	private java.lang.String xmldeleghein;
	private java.lang.String flgmodgruppiappin;
	private java.lang.String xmlgruppiappin;
	private java.lang.String rowidout;
	private java.lang.String attributiaddin;
	private java.lang.Integer flgignorewarningin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String warningmsgout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String codrapidoin;
	private java.lang.String codfiscalein;
	private java.lang.String flgmodindirizziin;
	private java.lang.String xmlindirizziin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIduserio(){return iduserio;}
    public java.lang.String getDesuserin(){return desuserin;}
    public java.lang.String getUsernamein(){return usernamein;}
    public java.lang.String getPasswordin(){return passwordin;}
    public java.lang.String getConfermapasswordin(){return confermapasswordin;}
    public java.lang.String getDtiniziovldin(){return dtiniziovldin;}
    public java.lang.String getDtfinevldin(){return dtfinevldin;}
    public java.lang.String getDtinizioaccredindomin(){return dtinizioaccredindomin;}
    public java.lang.String getDtfineaccredindomin(){return dtfineaccredindomin;}
    public java.math.BigDecimal getIdprofiloin(){return idprofiloin;}
    public java.lang.String getNomeprofiloin(){return nomeprofiloin;}
    public java.lang.String getXmldatiprofiloin(){return xmldatiprofiloin;}
    public java.lang.String getFlgmodgruppiprivin(){return flgmodgruppiprivin;}
    public java.lang.String getXmlgruppiprivin(){return xmlgruppiprivin;}
    public java.math.BigDecimal getIdsoggrubricain(){return idsoggrubricain;}
    public java.lang.String getTitoloin(){return titoloin;}
    public java.lang.String getQualificain(){return qualificain;}
    public java.lang.String getNromatricolain(){return nromatricolain;}
    public java.lang.String getEmailin(){return emailin;}
    public java.lang.Integer getFlgsenzaaccessoalsistemain(){return flgsenzaaccessoalsistemain;}
    public java.lang.String getCiprovuserin(){return ciprovuserin;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.String getFlgmodapplestaccredin(){return flgmodapplestaccredin;}
    public java.lang.String getXmlapplestaccredin(){return xmlapplestaccredin;}
    public java.lang.String getFlgmodrelconuoin(){return flgmodrelconuoin;}
    public java.lang.String getXmlrelconuoin(){return xmlrelconuoin;}
    public java.lang.String getFlgmoduovisdocassinvin(){return flgmoduovisdocassinvin;}
    public java.lang.String getVisdocassinvauoxmlin(){return visdocassinvauoxmlin;}
    public java.lang.String getFlgmoduomoddocassin(){return flgmoduomoddocassin;}
    public java.lang.String getModdocassauoxmlin(){return moddocassauoxmlin;}
    public java.lang.String getFlgmoddeleghein(){return flgmoddeleghein;}
    public java.lang.String getXmldeleghein(){return xmldeleghein;}
    public java.lang.String getFlgmodgruppiappin(){return flgmodgruppiappin;}
    public java.lang.String getXmlgruppiappin(){return xmlgruppiappin;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getAttributiaddin(){return attributiaddin;}
    public java.lang.Integer getFlgignorewarningin(){return flgignorewarningin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getCodrapidoin(){return codrapidoin;}
    public java.lang.String getCodfiscalein(){return codfiscalein;}
    public java.lang.String getFlgmodindirizziin(){return flgmodindirizziin;}
    public java.lang.String getXmlindirizziin(){return xmlindirizziin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIduserio(java.math.BigDecimal value){this.iduserio=value;}
    public void setDesuserin(java.lang.String value){this.desuserin=value;}
    public void setUsernamein(java.lang.String value){this.usernamein=value;}
    public void setPasswordin(java.lang.String value){this.passwordin=value;}
    public void setConfermapasswordin(java.lang.String value){this.confermapasswordin=value;}
    public void setDtiniziovldin(java.lang.String value){this.dtiniziovldin=value;}
    public void setDtfinevldin(java.lang.String value){this.dtfinevldin=value;}
    public void setDtinizioaccredindomin(java.lang.String value){this.dtinizioaccredindomin=value;}
    public void setDtfineaccredindomin(java.lang.String value){this.dtfineaccredindomin=value;}
    public void setIdprofiloin(java.math.BigDecimal value){this.idprofiloin=value;}
    public void setNomeprofiloin(java.lang.String value){this.nomeprofiloin=value;}
    public void setXmldatiprofiloin(java.lang.String value){this.xmldatiprofiloin=value;}
    public void setFlgmodgruppiprivin(java.lang.String value){this.flgmodgruppiprivin=value;}
    public void setXmlgruppiprivin(java.lang.String value){this.xmlgruppiprivin=value;}
    public void setIdsoggrubricain(java.math.BigDecimal value){this.idsoggrubricain=value;}
    public void setTitoloin(java.lang.String value){this.titoloin=value;}
    public void setQualificain(java.lang.String value){this.qualificain=value;}
    public void setNromatricolain(java.lang.String value){this.nromatricolain=value;}
    public void setEmailin(java.lang.String value){this.emailin=value;}
    public void setFlgsenzaaccessoalsistemain(java.lang.Integer value){this.flgsenzaaccessoalsistemain=value;}
    public void setCiprovuserin(java.lang.String value){this.ciprovuserin=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setFlgmodapplestaccredin(java.lang.String value){this.flgmodapplestaccredin=value;}
    public void setXmlapplestaccredin(java.lang.String value){this.xmlapplestaccredin=value;}
    public void setFlgmodrelconuoin(java.lang.String value){this.flgmodrelconuoin=value;}
    public void setXmlrelconuoin(java.lang.String value){this.xmlrelconuoin=value;}
    public void setFlgmoduovisdocassinvin(java.lang.String value){this.flgmoduovisdocassinvin=value;}
    public void setVisdocassinvauoxmlin(java.lang.String value){this.visdocassinvauoxmlin=value;}
    public void setFlgmoduomoddocassin(java.lang.String value){this.flgmoduomoddocassin=value;}
    public void setModdocassauoxmlin(java.lang.String value){this.moddocassauoxmlin=value;}
    public void setFlgmoddeleghein(java.lang.String value){this.flgmoddeleghein=value;}
    public void setXmldeleghein(java.lang.String value){this.xmldeleghein=value;}
    public void setFlgmodgruppiappin(java.lang.String value){this.flgmodgruppiappin=value;}
    public void setXmlgruppiappin(java.lang.String value){this.xmlgruppiappin=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setAttributiaddin(java.lang.String value){this.attributiaddin=value;}
    public void setFlgignorewarningin(java.lang.Integer value){this.flgignorewarningin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setCodrapidoin(java.lang.String value){this.codrapidoin=value;}
    public void setCodfiscalein(java.lang.String value){this.codfiscalein=value;}
    public void setFlgmodindirizziin(java.lang.String value){this.flgmodindirizziin=value;}
    public void setXmlindirizziin(java.lang.String value){this.xmlindirizziin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    