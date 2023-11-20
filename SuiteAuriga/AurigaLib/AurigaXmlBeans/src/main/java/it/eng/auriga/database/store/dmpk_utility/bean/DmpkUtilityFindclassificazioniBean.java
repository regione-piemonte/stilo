/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFindclassificazioniBean")
public class DmpkUtilityFindclassificazioniBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDCLASSIFICAZIONI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.math.BigDecimal idclassificazionein;
	private java.lang.String livellistrin;
	private java.lang.String desclassificazionein;
	private java.lang.Integer flgsolovldin;
	private java.lang.String tsrifin;
	private java.math.BigDecimal livellogerarchicoin;
	private java.lang.Integer flglivelliparzin;
	private java.math.BigDecimal idpianoclassifin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.math.BigDecimal getIdclassificazionein(){return idclassificazionein;}
    public java.lang.String getLivellistrin(){return livellistrin;}
    public java.lang.String getDesclassificazionein(){return desclassificazionein;}
    public java.lang.Integer getFlgsolovldin(){return flgsolovldin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    public java.math.BigDecimal getLivellogerarchicoin(){return livellogerarchicoin;}
    public java.lang.Integer getFlglivelliparzin(){return flglivelliparzin;}
    public java.math.BigDecimal getIdpianoclassifin(){return idpianoclassifin;}
    
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setIdclassificazionein(java.math.BigDecimal value){this.idclassificazionein=value;}
    public void setLivellistrin(java.lang.String value){this.livellistrin=value;}
    public void setDesclassificazionein(java.lang.String value){this.desclassificazionein=value;}
    public void setFlgsolovldin(java.lang.Integer value){this.flgsolovldin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    public void setLivellogerarchicoin(java.math.BigDecimal value){this.livellogerarchicoin=value;}
    public void setFlglivelliparzin(java.lang.Integer value){this.flglivelliparzin=value;}
    public void setIdpianoclassifin(java.math.BigDecimal value){this.idpianoclassifin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    