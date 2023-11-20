/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntPortaleCrmGestrichsbloccolimagibilitaBean")
public class DmpkIntPortaleCrmGestrichsbloccolimagibilitaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_PORTALE_CRM_GESTRICHSBLOCCOLIMAGIBILITA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String idrichiestain;
	private java.lang.String esitogestionein;
	private java.lang.String motivorespingimentoin;
	private java.math.BigDecimal nuovolimitein;
	private java.lang.String datiemailrispostaout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getIdrichiestain(){return idrichiestain;}
    public java.lang.String getEsitogestionein(){return esitogestionein;}
    public java.lang.String getMotivorespingimentoin(){return motivorespingimentoin;}
    public java.math.BigDecimal getNuovolimitein(){return nuovolimitein;}
    public java.lang.String getDatiemailrispostaout(){return datiemailrispostaout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdrichiestain(java.lang.String value){this.idrichiestain=value;}
    public void setEsitogestionein(java.lang.String value){this.esitogestionein=value;}
    public void setMotivorespingimentoin(java.lang.String value){this.motivorespingimentoin=value;}
    public void setNuovolimitein(java.math.BigDecimal value){this.nuovolimitein=value;}
    public void setDatiemailrispostaout(java.lang.String value){this.datiemailrispostaout=value;}
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