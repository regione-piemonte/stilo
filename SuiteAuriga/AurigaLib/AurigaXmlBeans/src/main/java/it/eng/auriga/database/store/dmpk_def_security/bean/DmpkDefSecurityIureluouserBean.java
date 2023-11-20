/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityIureluouserBean")
public class DmpkDefSecurityIureluouserBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_IURELUOUSER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String cireluseruoio;
	private java.math.BigDecimal iduoin;
	private java.lang.String nrilivelliuoin;
	private java.lang.String denominazioneuoin;
	private java.math.BigDecimal iduserin;
	private java.lang.String desuserin;
	private java.lang.String usernamein;
	private java.lang.String dtiniziovldin;
	private java.lang.String dtfinevldin;
	private java.lang.String flgtiporelin;
	private java.lang.Integer flginclsottouoin;
	private java.lang.Integer flginclscrivvirtin;
	private java.lang.String flgxscrivaniain;
	private java.math.BigDecimal idscrivaniaio;
	private java.lang.String intestazionescrivaniain;
	private java.lang.String nuovaintestazionescrivaniaio;
	private java.math.BigDecimal idruoloammin;
	private java.lang.String desruoloammin;
	private java.lang.String listaesclusioniuoppin;
	private java.lang.Integer flgprimarioconruoloin;
	private java.math.BigDecimal idprofiloin;
	private java.lang.String nomeprofiloin;
	private java.lang.String xmldatiprofiloin;
	private java.lang.String flgmodgruppiprivin;
	private java.lang.String xmlgruppiprivin;
	private java.lang.String flgmodgruppiappin;
	private java.lang.String xmlgruppiappin;
	private java.lang.String rowidscrivaniaout;
	private java.lang.String attributiaddin;
	private java.lang.Integer flgstoricizzadatiin;
	private java.lang.Integer flgignorewarningin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String warningmsgout;
	private java.lang.Integer flgdatidastoricizzareout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgignoresingolaappin;
	private java.lang.String flgtipodestdocin;
	private java.math.BigDecimal iduosvdestdocin;
	private java.lang.String callingfuncin;
	private java.lang.Integer flgaccessodoclimsvin;
	private java.lang.Integer flgregistrazioneein;
	private java.lang.Integer flgregistrazioneuiin;
	private java.lang.Integer flggestattiin;
	private java.lang.Integer flgvispropattiiniterin;
	private java.lang.Integer flggestattiallin;
	private java.lang.String listaidproctygestattiin;
	private java.lang.Integer flgvispropattiallin;
	private java.lang.String listaidproctyvispropattiin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getCireluseruoio(){return cireluseruoio;}
    public java.math.BigDecimal getIduoin(){return iduoin;}
    public java.lang.String getNrilivelliuoin(){return nrilivelliuoin;}
    public java.lang.String getDenominazioneuoin(){return denominazioneuoin;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.lang.String getDesuserin(){return desuserin;}
    public java.lang.String getUsernamein(){return usernamein;}
    public java.lang.String getDtiniziovldin(){return dtiniziovldin;}
    public java.lang.String getDtfinevldin(){return dtfinevldin;}
    public java.lang.String getFlgtiporelin(){return flgtiporelin;}
    public java.lang.Integer getFlginclsottouoin(){return flginclsottouoin;}
    public java.lang.Integer getFlginclscrivvirtin(){return flginclscrivvirtin;}
    public java.lang.String getFlgxscrivaniain(){return flgxscrivaniain;}
    public java.math.BigDecimal getIdscrivaniaio(){return idscrivaniaio;}
    public java.lang.String getIntestazionescrivaniain(){return intestazionescrivaniain;}
    public java.lang.String getNuovaintestazionescrivaniaio(){return nuovaintestazionescrivaniaio;}
    public java.math.BigDecimal getIdruoloammin(){return idruoloammin;}
    public java.lang.String getDesruoloammin(){return desruoloammin;}
    public java.lang.String getListaesclusioniuoppin(){return listaesclusioniuoppin;}
    public java.lang.Integer getFlgprimarioconruoloin(){return flgprimarioconruoloin;}
    public java.math.BigDecimal getIdprofiloin(){return idprofiloin;}
    public java.lang.String getNomeprofiloin(){return nomeprofiloin;}
    public java.lang.String getXmldatiprofiloin(){return xmldatiprofiloin;}
    public java.lang.String getFlgmodgruppiprivin(){return flgmodgruppiprivin;}
    public java.lang.String getXmlgruppiprivin(){return xmlgruppiprivin;}
    public java.lang.String getFlgmodgruppiappin(){return flgmodgruppiappin;}
    public java.lang.String getXmlgruppiappin(){return xmlgruppiappin;}
    public java.lang.String getRowidscrivaniaout(){return rowidscrivaniaout;}
    public java.lang.String getAttributiaddin(){return attributiaddin;}
    public java.lang.Integer getFlgstoricizzadatiin(){return flgstoricizzadatiin;}
    public java.lang.Integer getFlgignorewarningin(){return flgignorewarningin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.Integer getFlgdatidastoricizzareout(){return flgdatidastoricizzareout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgignoresingolaappin(){return flgignoresingolaappin;}
    public java.lang.String getFlgtipodestdocin(){return flgtipodestdocin;}
    public java.math.BigDecimal getIduosvdestdocin(){return iduosvdestdocin;}
    public java.lang.String getCallingfuncin(){return callingfuncin;}
    public java.lang.Integer getFlgaccessodoclimsvin(){return flgaccessodoclimsvin;}
    public java.lang.Integer getFlgregistrazioneein(){return flgregistrazioneein;}
    public java.lang.Integer getFlgregistrazioneuiin(){return flgregistrazioneuiin;}
    public java.lang.Integer getFlggestattiin(){return flggestattiin;}
    public java.lang.Integer getFlgvispropattiiniterin(){return flgvispropattiiniterin;}
    public java.lang.Integer getFlggestattiallin(){return flggestattiallin;}
    public java.lang.String getListaidproctygestattiin(){return listaidproctygestattiin;}
    public java.lang.Integer getFlgvispropattiallin(){return flgvispropattiallin;}
    public java.lang.String getListaidproctyvispropattiin(){return listaidproctyvispropattiin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setCireluseruoio(java.lang.String value){this.cireluseruoio=value;}
    public void setIduoin(java.math.BigDecimal value){this.iduoin=value;}
    public void setNrilivelliuoin(java.lang.String value){this.nrilivelliuoin=value;}
    public void setDenominazioneuoin(java.lang.String value){this.denominazioneuoin=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setDesuserin(java.lang.String value){this.desuserin=value;}
    public void setUsernamein(java.lang.String value){this.usernamein=value;}
    public void setDtiniziovldin(java.lang.String value){this.dtiniziovldin=value;}
    public void setDtfinevldin(java.lang.String value){this.dtfinevldin=value;}
    public void setFlgtiporelin(java.lang.String value){this.flgtiporelin=value;}
    public void setFlginclsottouoin(java.lang.Integer value){this.flginclsottouoin=value;}
    public void setFlginclscrivvirtin(java.lang.Integer value){this.flginclscrivvirtin=value;}
    public void setFlgxscrivaniain(java.lang.String value){this.flgxscrivaniain=value;}
    public void setIdscrivaniaio(java.math.BigDecimal value){this.idscrivaniaio=value;}
    public void setIntestazionescrivaniain(java.lang.String value){this.intestazionescrivaniain=value;}
    public void setNuovaintestazionescrivaniaio(java.lang.String value){this.nuovaintestazionescrivaniaio=value;}
    public void setIdruoloammin(java.math.BigDecimal value){this.idruoloammin=value;}
    public void setDesruoloammin(java.lang.String value){this.desruoloammin=value;}
    public void setListaesclusioniuoppin(java.lang.String value){this.listaesclusioniuoppin=value;}
    public void setFlgprimarioconruoloin(java.lang.Integer value){this.flgprimarioconruoloin=value;}
    public void setIdprofiloin(java.math.BigDecimal value){this.idprofiloin=value;}
    public void setNomeprofiloin(java.lang.String value){this.nomeprofiloin=value;}
    public void setXmldatiprofiloin(java.lang.String value){this.xmldatiprofiloin=value;}
    public void setFlgmodgruppiprivin(java.lang.String value){this.flgmodgruppiprivin=value;}
    public void setXmlgruppiprivin(java.lang.String value){this.xmlgruppiprivin=value;}
    public void setFlgmodgruppiappin(java.lang.String value){this.flgmodgruppiappin=value;}
    public void setXmlgruppiappin(java.lang.String value){this.xmlgruppiappin=value;}
    public void setRowidscrivaniaout(java.lang.String value){this.rowidscrivaniaout=value;}
    public void setAttributiaddin(java.lang.String value){this.attributiaddin=value;}
    public void setFlgstoricizzadatiin(java.lang.Integer value){this.flgstoricizzadatiin=value;}
    public void setFlgignorewarningin(java.lang.Integer value){this.flgignorewarningin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setFlgdatidastoricizzareout(java.lang.Integer value){this.flgdatidastoricizzareout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgignoresingolaappin(java.lang.Integer value){this.flgignoresingolaappin=value;}
    public void setFlgtipodestdocin(java.lang.String value){this.flgtipodestdocin=value;}
    public void setIduosvdestdocin(java.math.BigDecimal value){this.iduosvdestdocin=value;}
    public void setCallingfuncin(java.lang.String value){this.callingfuncin=value;}
    public void setFlgaccessodoclimsvin(java.lang.Integer value){this.flgaccessodoclimsvin=value;}
    public void setFlgregistrazioneein(java.lang.Integer value){this.flgregistrazioneein=value;}
    public void setFlgregistrazioneuiin(java.lang.Integer value){this.flgregistrazioneuiin=value;}
    public void setFlggestattiin(java.lang.Integer value){this.flggestattiin=value;}
    public void setFlgvispropattiiniterin(java.lang.Integer value){this.flgvispropattiiniterin=value;}
    public void setFlggestattiallin(java.lang.Integer value){this.flggestattiallin=value;}
    public void setListaidproctygestattiin(java.lang.String value){this.listaidproctygestattiin=value;}
    public void setFlgvispropattiallin(java.lang.Integer value){this.flgvispropattiallin=value;}
    public void setListaidproctyvispropattiin(java.lang.String value){this.listaidproctyvispropattiin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    