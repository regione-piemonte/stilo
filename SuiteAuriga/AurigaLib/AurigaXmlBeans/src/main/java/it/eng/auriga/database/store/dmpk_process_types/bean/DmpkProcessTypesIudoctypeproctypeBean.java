/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessTypesIudoctypeproctypeBean")
public class DmpkProcessTypesIudoctypeproctypeBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESS_TYPES_IUDOCTYPEPROCTYPE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String rowidio;
	private java.math.BigDecimal idprocesstypeio;
	private java.math.BigDecimal iddoctypein;
	private java.lang.String nomedoctypein;
	private java.lang.Integer nroposizionein;
	private java.lang.String flgaqcprodin;
	private java.lang.String codruolodocin;
	private java.math.BigDecimal maxnumdocsin;
	private java.lang.String dtiniziovldin;
	private java.lang.String dtfinevldin;
	private java.lang.Integer flglockedin;
	private java.lang.String flgmodcontrolliin;
	private java.lang.String xmlcontrolliin;
	private java.lang.String flgmodmodelliin;
	private java.lang.String xmlmodelliin;
	private java.lang.String flgmodspecforaccessin;
	private java.lang.String xmlspecforaccessin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getRowidio(){return rowidio;}
    public java.math.BigDecimal getIdprocesstypeio(){return idprocesstypeio;}
    public java.math.BigDecimal getIddoctypein(){return iddoctypein;}
    public java.lang.String getNomedoctypein(){return nomedoctypein;}
    public java.lang.Integer getNroposizionein(){return nroposizionein;}
    public java.lang.String getFlgaqcprodin(){return flgaqcprodin;}
    public java.lang.String getCodruolodocin(){return codruolodocin;}
    public java.math.BigDecimal getMaxnumdocsin(){return maxnumdocsin;}
    public java.lang.String getDtiniziovldin(){return dtiniziovldin;}
    public java.lang.String getDtfinevldin(){return dtfinevldin;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.String getFlgmodcontrolliin(){return flgmodcontrolliin;}
    public java.lang.String getXmlcontrolliin(){return xmlcontrolliin;}
    public java.lang.String getFlgmodmodelliin(){return flgmodmodelliin;}
    public java.lang.String getXmlmodelliin(){return xmlmodelliin;}
    public java.lang.String getFlgmodspecforaccessin(){return flgmodspecforaccessin;}
    public java.lang.String getXmlspecforaccessin(){return xmlspecforaccessin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setRowidio(java.lang.String value){this.rowidio=value;}
    public void setIdprocesstypeio(java.math.BigDecimal value){this.idprocesstypeio=value;}
    public void setIddoctypein(java.math.BigDecimal value){this.iddoctypein=value;}
    public void setNomedoctypein(java.lang.String value){this.nomedoctypein=value;}
    public void setNroposizionein(java.lang.Integer value){this.nroposizionein=value;}
    public void setFlgaqcprodin(java.lang.String value){this.flgaqcprodin=value;}
    public void setCodruolodocin(java.lang.String value){this.codruolodocin=value;}
    public void setMaxnumdocsin(java.math.BigDecimal value){this.maxnumdocsin=value;}
    public void setDtiniziovldin(java.lang.String value){this.dtiniziovldin=value;}
    public void setDtfinevldin(java.lang.String value){this.dtfinevldin=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setFlgmodcontrolliin(java.lang.String value){this.flgmodcontrolliin=value;}
    public void setXmlcontrolliin(java.lang.String value){this.xmlcontrolliin=value;}
    public void setFlgmodmodelliin(java.lang.String value){this.flgmodmodelliin=value;}
    public void setXmlmodelliin(java.lang.String value){this.xmlmodelliin=value;}
    public void setFlgmodspecforaccessin(java.lang.String value){this.flgmodspecforaccessin=value;}
    public void setXmlspecforaccessin(java.lang.String value){this.xmlspecforaccessin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    