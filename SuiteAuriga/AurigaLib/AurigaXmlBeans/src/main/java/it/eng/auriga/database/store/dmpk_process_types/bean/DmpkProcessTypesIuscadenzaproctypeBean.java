/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessTypesIuscadenzaproctypeBean")
public class DmpkProcessTypesIuscadenzaproctypeBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESS_TYPES_IUSCADENZAPROCTYPE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String ciscadenzaio;
	private java.math.BigDecimal idprocesstypeio;
	private java.lang.String flgscadpuntualeperiodoin;
	private java.lang.String descrizionein;
	private java.math.BigDecimal duratain;
	private java.lang.String dtiniziovldin;
	private java.lang.String dtfinevldin;
	private java.lang.String annotazioniin;
	private java.lang.Integer flglockedin;
	private java.lang.String flgmodstartin;
	private java.lang.String xmlstartin;
	private java.lang.String flgmodendin;
	private java.lang.String xmlendin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getCiscadenzaio(){return ciscadenzaio;}
    public java.math.BigDecimal getIdprocesstypeio(){return idprocesstypeio;}
    public java.lang.String getFlgscadpuntualeperiodoin(){return flgscadpuntualeperiodoin;}
    public java.lang.String getDescrizionein(){return descrizionein;}
    public java.math.BigDecimal getDuratain(){return duratain;}
    public java.lang.String getDtiniziovldin(){return dtiniziovldin;}
    public java.lang.String getDtfinevldin(){return dtfinevldin;}
    public java.lang.String getAnnotazioniin(){return annotazioniin;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.String getFlgmodstartin(){return flgmodstartin;}
    public java.lang.String getXmlstartin(){return xmlstartin;}
    public java.lang.String getFlgmodendin(){return flgmodendin;}
    public java.lang.String getXmlendin(){return xmlendin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setCiscadenzaio(java.lang.String value){this.ciscadenzaio=value;}
    public void setIdprocesstypeio(java.math.BigDecimal value){this.idprocesstypeio=value;}
    public void setFlgscadpuntualeperiodoin(java.lang.String value){this.flgscadpuntualeperiodoin=value;}
    public void setDescrizionein(java.lang.String value){this.descrizionein=value;}
    public void setDuratain(java.math.BigDecimal value){this.duratain=value;}
    public void setDtiniziovldin(java.lang.String value){this.dtiniziovldin=value;}
    public void setDtfinevldin(java.lang.String value){this.dtfinevldin=value;}
    public void setAnnotazioniin(java.lang.String value){this.annotazioniin=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setFlgmodstartin(java.lang.String value){this.flgmodstartin=value;}
    public void setXmlstartin(java.lang.String value){this.xmlstartin=value;}
    public void setFlgmodendin(java.lang.String value){this.flgmodendin=value;}
    public void setXmlendin(java.lang.String value){this.xmlendin=value;}
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