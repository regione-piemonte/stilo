/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkEfactIulogregistrazioneconsensoBean")
public class DmpkEfactIulogregistrazioneconsensoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_EFACT_IULOGREGISTRAZIONECONSENSO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idlogregconsensoio;
	private java.math.BigDecimal iddestinatarioin;
	private java.lang.String coddestinatarioin;
	private java.lang.String ragsocialedestinatarioin;
	private java.lang.String cfpivadestinatarioin;
	private java.lang.String emaildestinatarioin;
	private java.lang.String telefonodestinatarioin;
	private java.lang.String nomerifdestinatarioin;
	private java.lang.String tsrichiestain;
	private java.lang.String esitorichiestin;
	private java.lang.String attributiaddin;
	private java.lang.Integer flgannullatoin;
	private java.lang.Integer flglockedin;
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
    public java.math.BigDecimal getIdlogregconsensoio(){return idlogregconsensoio;}
    public java.math.BigDecimal getIddestinatarioin(){return iddestinatarioin;}
    public java.lang.String getCoddestinatarioin(){return coddestinatarioin;}
    public java.lang.String getRagsocialedestinatarioin(){return ragsocialedestinatarioin;}
    public java.lang.String getCfpivadestinatarioin(){return cfpivadestinatarioin;}
    public java.lang.String getEmaildestinatarioin(){return emaildestinatarioin;}
    public java.lang.String getTelefonodestinatarioin(){return telefonodestinatarioin;}
    public java.lang.String getNomerifdestinatarioin(){return nomerifdestinatarioin;}
    public java.lang.String getTsrichiestain(){return tsrichiestain;}
    public java.lang.String getEsitorichiestin(){return esitorichiestin;}
    public java.lang.String getAttributiaddin(){return attributiaddin;}
    public java.lang.Integer getFlgannullatoin(){return flgannullatoin;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
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
    public void setIdlogregconsensoio(java.math.BigDecimal value){this.idlogregconsensoio=value;}
    public void setIddestinatarioin(java.math.BigDecimal value){this.iddestinatarioin=value;}
    public void setCoddestinatarioin(java.lang.String value){this.coddestinatarioin=value;}
    public void setRagsocialedestinatarioin(java.lang.String value){this.ragsocialedestinatarioin=value;}
    public void setCfpivadestinatarioin(java.lang.String value){this.cfpivadestinatarioin=value;}
    public void setEmaildestinatarioin(java.lang.String value){this.emaildestinatarioin=value;}
    public void setTelefonodestinatarioin(java.lang.String value){this.telefonodestinatarioin=value;}
    public void setNomerifdestinatarioin(java.lang.String value){this.nomerifdestinatarioin=value;}
    public void setTsrichiestain(java.lang.String value){this.tsrichiestain=value;}
    public void setEsitorichiestin(java.lang.String value){this.esitorichiestin=value;}
    public void setAttributiaddin(java.lang.String value){this.attributiaddin=value;}
    public void setFlgannullatoin(java.lang.Integer value){this.flgannullatoin=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
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