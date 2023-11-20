/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessTypesLoaddettdoctypeproctyBean")
public class DmpkProcessTypesLoaddettdoctypeproctyBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESS_TYPES_LOADDETTDOCTYPEPROCTY";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal iddoctypein;
	private java.math.BigDecimal idprocesstypein;
	private java.lang.String nomeprocesstypeout;
	private java.lang.String nomedoctypeout;
	private java.lang.Integer nroposizioneout;
	private java.lang.String flgaqcprodout;
	private java.lang.String codruolodocout;
	private java.math.BigDecimal maxnumdocsout;
	private java.lang.String dtiniziovldout;
	private java.lang.String dtfinevldout;
	private java.lang.Integer flglockedout;
	private java.lang.String xmlcontrolliout;
	private java.lang.String xmlmodelliout;
	private java.lang.String xmlspecforaccessout;
	private java.lang.String rowidout;
	private java.lang.Integer bachsizeout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIddoctypein(){return iddoctypein;}
    public java.math.BigDecimal getIdprocesstypein(){return idprocesstypein;}
    public java.lang.String getNomeprocesstypeout(){return nomeprocesstypeout;}
    public java.lang.String getNomedoctypeout(){return nomedoctypeout;}
    public java.lang.Integer getNroposizioneout(){return nroposizioneout;}
    public java.lang.String getFlgaqcprodout(){return flgaqcprodout;}
    public java.lang.String getCodruolodocout(){return codruolodocout;}
    public java.math.BigDecimal getMaxnumdocsout(){return maxnumdocsout;}
    public java.lang.String getDtiniziovldout(){return dtiniziovldout;}
    public java.lang.String getDtfinevldout(){return dtfinevldout;}
    public java.lang.Integer getFlglockedout(){return flglockedout;}
    public java.lang.String getXmlcontrolliout(){return xmlcontrolliout;}
    public java.lang.String getXmlmodelliout(){return xmlmodelliout;}
    public java.lang.String getXmlspecforaccessout(){return xmlspecforaccessout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.Integer getBachsizeout(){return bachsizeout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIddoctypein(java.math.BigDecimal value){this.iddoctypein=value;}
    public void setIdprocesstypein(java.math.BigDecimal value){this.idprocesstypein=value;}
    public void setNomeprocesstypeout(java.lang.String value){this.nomeprocesstypeout=value;}
    public void setNomedoctypeout(java.lang.String value){this.nomedoctypeout=value;}
    public void setNroposizioneout(java.lang.Integer value){this.nroposizioneout=value;}
    public void setFlgaqcprodout(java.lang.String value){this.flgaqcprodout=value;}
    public void setCodruolodocout(java.lang.String value){this.codruolodocout=value;}
    public void setMaxnumdocsout(java.math.BigDecimal value){this.maxnumdocsout=value;}
    public void setDtiniziovldout(java.lang.String value){this.dtiniziovldout=value;}
    public void setDtfinevldout(java.lang.String value){this.dtfinevldout=value;}
    public void setFlglockedout(java.lang.Integer value){this.flglockedout=value;}
    public void setXmlcontrolliout(java.lang.String value){this.xmlcontrolliout=value;}
    public void setXmlmodelliout(java.lang.String value){this.xmlmodelliout=value;}
    public void setXmlspecforaccessout(java.lang.String value){this.xmlspecforaccessout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setBachsizeout(java.lang.Integer value){this.bachsizeout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    