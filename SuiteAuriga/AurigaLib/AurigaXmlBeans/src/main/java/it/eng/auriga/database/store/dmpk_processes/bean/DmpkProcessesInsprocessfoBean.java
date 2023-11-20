/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessesInsprocessfoBean")
public class DmpkProcessesInsprocessfoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESSES_INSPROCESSFO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String useridproponentein;
	private java.lang.String profiloproponentein;
	private java.math.BigDecimal idprocesstypein;
	private java.lang.String etichettaproponentein;
	private java.lang.String oggettoin;
	private java.lang.String tipoprogettoin;
	private java.lang.String richiedentexmlin;
	private java.lang.String referentirichxmlin;
	private java.math.BigDecimal idprocessout;
	private java.lang.String estremiprocessout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String delegatorapprxmlin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getUseridproponentein(){return useridproponentein;}
    public java.lang.String getProfiloproponentein(){return profiloproponentein;}
    public java.math.BigDecimal getIdprocesstypein(){return idprocesstypein;}
    public java.lang.String getEtichettaproponentein(){return etichettaproponentein;}
    public java.lang.String getOggettoin(){return oggettoin;}
    public java.lang.String getTipoprogettoin(){return tipoprogettoin;}
    public java.lang.String getRichiedentexmlin(){return richiedentexmlin;}
    public java.lang.String getReferentirichxmlin(){return referentirichxmlin;}
    public java.math.BigDecimal getIdprocessout(){return idprocessout;}
    public java.lang.String getEstremiprocessout(){return estremiprocessout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getDelegatorapprxmlin(){return delegatorapprxmlin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setUseridproponentein(java.lang.String value){this.useridproponentein=value;}
    public void setProfiloproponentein(java.lang.String value){this.profiloproponentein=value;}
    public void setIdprocesstypein(java.math.BigDecimal value){this.idprocesstypein=value;}
    public void setEtichettaproponentein(java.lang.String value){this.etichettaproponentein=value;}
    public void setOggettoin(java.lang.String value){this.oggettoin=value;}
    public void setTipoprogettoin(java.lang.String value){this.tipoprogettoin=value;}
    public void setRichiedentexmlin(java.lang.String value){this.richiedentexmlin=value;}
    public void setReferentirichxmlin(java.lang.String value){this.referentirichxmlin=value;}
    public void setIdprocessout(java.math.BigDecimal value){this.idprocessout=value;}
    public void setEstremiprocessout(java.lang.String value){this.estremiprocessout=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setDelegatorapprxmlin(java.lang.String value){this.delegatorapprxmlin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    