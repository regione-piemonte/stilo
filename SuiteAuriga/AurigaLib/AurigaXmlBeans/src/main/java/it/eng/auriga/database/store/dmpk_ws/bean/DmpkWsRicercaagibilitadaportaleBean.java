/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWsRicercaagibilitadaportaleBean")
public class DmpkWsRicercaagibilitadaportaleBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WS_RICERCAAGIBILITADAPORTALE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String xmlrequestin;
	private java.lang.String conntokenout;
	private java.math.BigDecimal iddominioout;
	private java.math.BigDecimal idudrichiestaout;
	private java.math.BigDecimal iddocrichiestaout;
	private java.lang.String estremiprorichout;
	private java.lang.String listaagibilitaout;
	private java.lang.String listafileout;
	private java.lang.String modalitafilexportaleout;
	private java.math.BigDecimal idudrispostaout;
	private java.math.BigDecimal iddocrispostaout;
	private java.lang.String estremiprorispostaout;
	private java.lang.String idutenteinviomailout;
	private java.lang.String accountmittmailtosendout;
	private java.lang.String subjectmailtosendout;
	private java.lang.String bodymailtosendout;
	private java.lang.Integer fileallegatimailout;
	private java.math.BigDecimal idtemplaterispostaout;
	private java.lang.String uritemplaterispostaout;
	private java.lang.String nometemplaterispostaout;
	private java.lang.String tipotemplateout;
	private java.lang.String xmldatixmodelloout;
	private java.lang.String contenutobarcodeout;
	private java.lang.String testoinchiarobarcodeout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getXmlrequestin(){return xmlrequestin;}
    public java.lang.String getConntokenout(){return conntokenout;}
    public java.math.BigDecimal getIddominioout(){return iddominioout;}
    public java.math.BigDecimal getIdudrichiestaout(){return idudrichiestaout;}
    public java.math.BigDecimal getIddocrichiestaout(){return iddocrichiestaout;}
    public java.lang.String getEstremiprorichout(){return estremiprorichout;}
    public java.lang.String getListaagibilitaout(){return listaagibilitaout;}
    public java.lang.String getListafileout(){return listafileout;}
    public java.lang.String getModalitafilexportaleout(){return modalitafilexportaleout;}
    public java.math.BigDecimal getIdudrispostaout(){return idudrispostaout;}
    public java.math.BigDecimal getIddocrispostaout(){return iddocrispostaout;}
    public java.lang.String getEstremiprorispostaout(){return estremiprorispostaout;}
    public java.lang.String getIdutenteinviomailout(){return idutenteinviomailout;}
    public java.lang.String getAccountmittmailtosendout(){return accountmittmailtosendout;}
    public java.lang.String getSubjectmailtosendout(){return subjectmailtosendout;}
    public java.lang.String getBodymailtosendout(){return bodymailtosendout;}
    public java.lang.Integer getFileallegatimailout(){return fileallegatimailout;}
    public java.math.BigDecimal getIdtemplaterispostaout(){return idtemplaterispostaout;}
    public java.lang.String getUritemplaterispostaout(){return uritemplaterispostaout;}
    public java.lang.String getNometemplaterispostaout(){return nometemplaterispostaout;}
    public java.lang.String getTipotemplateout(){return tipotemplateout;}
    public java.lang.String getXmldatixmodelloout(){return xmldatixmodelloout;}
    public java.lang.String getContenutobarcodeout(){return contenutobarcodeout;}
    public java.lang.String getTestoinchiarobarcodeout(){return testoinchiarobarcodeout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setXmlrequestin(java.lang.String value){this.xmlrequestin=value;}
    public void setConntokenout(java.lang.String value){this.conntokenout=value;}
    public void setIddominioout(java.math.BigDecimal value){this.iddominioout=value;}
    public void setIdudrichiestaout(java.math.BigDecimal value){this.idudrichiestaout=value;}
    public void setIddocrichiestaout(java.math.BigDecimal value){this.iddocrichiestaout=value;}
    public void setEstremiprorichout(java.lang.String value){this.estremiprorichout=value;}
    public void setListaagibilitaout(java.lang.String value){this.listaagibilitaout=value;}
    public void setListafileout(java.lang.String value){this.listafileout=value;}
    public void setModalitafilexportaleout(java.lang.String value){this.modalitafilexportaleout=value;}
    public void setIdudrispostaout(java.math.BigDecimal value){this.idudrispostaout=value;}
    public void setIddocrispostaout(java.math.BigDecimal value){this.iddocrispostaout=value;}
    public void setEstremiprorispostaout(java.lang.String value){this.estremiprorispostaout=value;}
    public void setIdutenteinviomailout(java.lang.String value){this.idutenteinviomailout=value;}
    public void setAccountmittmailtosendout(java.lang.String value){this.accountmittmailtosendout=value;}
    public void setSubjectmailtosendout(java.lang.String value){this.subjectmailtosendout=value;}
    public void setBodymailtosendout(java.lang.String value){this.bodymailtosendout=value;}
    public void setFileallegatimailout(java.lang.Integer value){this.fileallegatimailout=value;}
    public void setIdtemplaterispostaout(java.math.BigDecimal value){this.idtemplaterispostaout=value;}
    public void setUritemplaterispostaout(java.lang.String value){this.uritemplaterispostaout=value;}
    public void setNometemplaterispostaout(java.lang.String value){this.nometemplaterispostaout=value;}
    public void setTipotemplateout(java.lang.String value){this.tipotemplateout=value;}
    public void setXmldatixmodelloout(java.lang.String value){this.xmldatixmodelloout=value;}
    public void setContenutobarcodeout(java.lang.String value){this.contenutobarcodeout=value;}
    public void setTestoinchiarobarcodeout(java.lang.String value){this.testoinchiarobarcodeout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    