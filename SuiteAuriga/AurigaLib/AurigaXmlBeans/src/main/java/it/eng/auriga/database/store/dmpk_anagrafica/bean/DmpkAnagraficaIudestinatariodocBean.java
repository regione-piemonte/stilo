/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAnagraficaIudestinatariodocBean")
public class DmpkAnagraficaIudestinatariodocBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ANAGRAFICA_IUDESTINATARIODOC";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal iddestinatarioio;
	private java.math.BigDecimal idsoggrubrio;
	private java.math.BigDecimal flgpersonafisicain;
	private java.lang.String denomcognomein;
	private java.lang.String nomein;
	private java.lang.String cfin;
	private java.lang.String pivain;
	private java.lang.String codtiposottotipoin;
	private java.lang.String codcondgiuridicain;
	private java.lang.String dtnascitain;
	private java.lang.String codistatcomunenascin;
	private java.lang.String nomecomunenascin;
	private java.lang.String codistatstatonascin;
	private java.lang.String nomestatonascin;
	private java.lang.String dtcessazionein;
	private java.lang.String codcausalecessazionein;
	private java.lang.String flgsexin;
	private java.lang.String codistatstatocittin;
	private java.lang.String nomestatocittin;
	private java.lang.String titoloin;
	private java.lang.String codindpain;
	private java.lang.String codammipain;
	private java.lang.String codaooipain;
	private java.math.BigDecimal idsoggappin;
	private java.lang.String ciprovsoggin;
	private java.lang.String ciprovdestdocin;
	private java.lang.String usernamein;
	private java.lang.String passwordin;
	private java.lang.String codrapidoin;
	private java.math.BigDecimal flglockedin;
	private java.lang.String flgmodindirizziin;
	private java.lang.String xmlindirizziin;
	private java.lang.String flgmodaltredenomin;
	private java.lang.String xmlaltredenominazioniin;
	private java.lang.String flgmodcontattiin;
	private java.lang.String xmlcontattiin;
	private java.lang.String flgmodgruppiappin;
	private java.lang.String xmlgruppiappin;
	private java.lang.String flgmodutenzeassociatein;
	private java.lang.String utenzeassociatein;
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
    public java.math.BigDecimal getIddestinatarioio(){return iddestinatarioio;}
    public java.math.BigDecimal getIdsoggrubrio(){return idsoggrubrio;}
    public java.math.BigDecimal getFlgpersonafisicain(){return flgpersonafisicain;}
    public java.lang.String getDenomcognomein(){return denomcognomein;}
    public java.lang.String getNomein(){return nomein;}
    public java.lang.String getCfin(){return cfin;}
    public java.lang.String getPivain(){return pivain;}
    public java.lang.String getCodtiposottotipoin(){return codtiposottotipoin;}
    public java.lang.String getCodcondgiuridicain(){return codcondgiuridicain;}
    public java.lang.String getDtnascitain(){return dtnascitain;}
    public java.lang.String getCodistatcomunenascin(){return codistatcomunenascin;}
    public java.lang.String getNomecomunenascin(){return nomecomunenascin;}
    public java.lang.String getCodistatstatonascin(){return codistatstatonascin;}
    public java.lang.String getNomestatonascin(){return nomestatonascin;}
    public java.lang.String getDtcessazionein(){return dtcessazionein;}
    public java.lang.String getCodcausalecessazionein(){return codcausalecessazionein;}
    public java.lang.String getFlgsexin(){return flgsexin;}
    public java.lang.String getCodistatstatocittin(){return codistatstatocittin;}
    public java.lang.String getNomestatocittin(){return nomestatocittin;}
    public java.lang.String getTitoloin(){return titoloin;}
    public java.lang.String getCodindpain(){return codindpain;}
    public java.lang.String getCodammipain(){return codammipain;}
    public java.lang.String getCodaooipain(){return codaooipain;}
    public java.math.BigDecimal getIdsoggappin(){return idsoggappin;}
    public java.lang.String getCiprovsoggin(){return ciprovsoggin;}
    public java.lang.String getCiprovdestdocin(){return ciprovdestdocin;}
    public java.lang.String getUsernamein(){return usernamein;}
    public java.lang.String getPasswordin(){return passwordin;}
    public java.lang.String getCodrapidoin(){return codrapidoin;}
    public java.math.BigDecimal getFlglockedin(){return flglockedin;}
    public java.lang.String getFlgmodindirizziin(){return flgmodindirizziin;}
    public java.lang.String getXmlindirizziin(){return xmlindirizziin;}
    public java.lang.String getFlgmodaltredenomin(){return flgmodaltredenomin;}
    public java.lang.String getXmlaltredenominazioniin(){return xmlaltredenominazioniin;}
    public java.lang.String getFlgmodcontattiin(){return flgmodcontattiin;}
    public java.lang.String getXmlcontattiin(){return xmlcontattiin;}
    public java.lang.String getFlgmodgruppiappin(){return flgmodgruppiappin;}
    public java.lang.String getXmlgruppiappin(){return xmlgruppiappin;}
    public java.lang.String getFlgmodutenzeassociatein(){return flgmodutenzeassociatein;}
    public java.lang.String getUtenzeassociatein(){return utenzeassociatein;}
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
    public void setIddestinatarioio(java.math.BigDecimal value){this.iddestinatarioio=value;}
    public void setIdsoggrubrio(java.math.BigDecimal value){this.idsoggrubrio=value;}
    public void setFlgpersonafisicain(java.math.BigDecimal value){this.flgpersonafisicain=value;}
    public void setDenomcognomein(java.lang.String value){this.denomcognomein=value;}
    public void setNomein(java.lang.String value){this.nomein=value;}
    public void setCfin(java.lang.String value){this.cfin=value;}
    public void setPivain(java.lang.String value){this.pivain=value;}
    public void setCodtiposottotipoin(java.lang.String value){this.codtiposottotipoin=value;}
    public void setCodcondgiuridicain(java.lang.String value){this.codcondgiuridicain=value;}
    public void setDtnascitain(java.lang.String value){this.dtnascitain=value;}
    public void setCodistatcomunenascin(java.lang.String value){this.codistatcomunenascin=value;}
    public void setNomecomunenascin(java.lang.String value){this.nomecomunenascin=value;}
    public void setCodistatstatonascin(java.lang.String value){this.codistatstatonascin=value;}
    public void setNomestatonascin(java.lang.String value){this.nomestatonascin=value;}
    public void setDtcessazionein(java.lang.String value){this.dtcessazionein=value;}
    public void setCodcausalecessazionein(java.lang.String value){this.codcausalecessazionein=value;}
    public void setFlgsexin(java.lang.String value){this.flgsexin=value;}
    public void setCodistatstatocittin(java.lang.String value){this.codistatstatocittin=value;}
    public void setNomestatocittin(java.lang.String value){this.nomestatocittin=value;}
    public void setTitoloin(java.lang.String value){this.titoloin=value;}
    public void setCodindpain(java.lang.String value){this.codindpain=value;}
    public void setCodammipain(java.lang.String value){this.codammipain=value;}
    public void setCodaooipain(java.lang.String value){this.codaooipain=value;}
    public void setIdsoggappin(java.math.BigDecimal value){this.idsoggappin=value;}
    public void setCiprovsoggin(java.lang.String value){this.ciprovsoggin=value;}
    public void setCiprovdestdocin(java.lang.String value){this.ciprovdestdocin=value;}
    public void setUsernamein(java.lang.String value){this.usernamein=value;}
    public void setPasswordin(java.lang.String value){this.passwordin=value;}
    public void setCodrapidoin(java.lang.String value){this.codrapidoin=value;}
    public void setFlglockedin(java.math.BigDecimal value){this.flglockedin=value;}
    public void setFlgmodindirizziin(java.lang.String value){this.flgmodindirizziin=value;}
    public void setXmlindirizziin(java.lang.String value){this.xmlindirizziin=value;}
    public void setFlgmodaltredenomin(java.lang.String value){this.flgmodaltredenomin=value;}
    public void setXmlaltredenominazioniin(java.lang.String value){this.xmlaltredenominazioniin=value;}
    public void setFlgmodcontattiin(java.lang.String value){this.flgmodcontattiin=value;}
    public void setXmlcontattiin(java.lang.String value){this.xmlcontattiin=value;}
    public void setFlgmodgruppiappin(java.lang.String value){this.flgmodgruppiappin=value;}
    public void setXmlgruppiappin(java.lang.String value){this.xmlgruppiappin=value;}
    public void setFlgmodutenzeassociatein(java.lang.String value){this.flgmodutenzeassociatein=value;}
    public void setUtenzeassociatein(java.lang.String value){this.utenzeassociatein=value;}
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