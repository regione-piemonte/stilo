/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityAssegnaagrupposoggintBean")
public class DmpkDefSecurityAssegnaagrupposoggintBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_ASSEGNAAGRUPPOSOGGINT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idgruppoio;
	private java.lang.String nomegruppoin;
	private java.lang.String flgtipomembridaaggiungerein;
	private java.lang.String membridaaggiungerexmlin;
	private java.lang.Integer flginclsottouoin;
	private java.lang.Integer flginclscrivaniein;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdgruppoio(){return idgruppoio;}
    public java.lang.String getNomegruppoin(){return nomegruppoin;}
    public java.lang.String getFlgtipomembridaaggiungerein(){return flgtipomembridaaggiungerein;}
    public java.lang.String getMembridaaggiungerexmlin(){return membridaaggiungerexmlin;}
    public java.lang.Integer getFlginclsottouoin(){return flginclsottouoin;}
    public java.lang.Integer getFlginclscrivaniein(){return flginclscrivaniein;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdgruppoio(java.math.BigDecimal value){this.idgruppoio=value;}
    public void setNomegruppoin(java.lang.String value){this.nomegruppoin=value;}
    public void setFlgtipomembridaaggiungerein(java.lang.String value){this.flgtipomembridaaggiungerein=value;}
    public void setMembridaaggiungerexmlin(java.lang.String value){this.membridaaggiungerexmlin=value;}
    public void setFlginclsottouoin(java.lang.Integer value){this.flginclsottouoin=value;}
    public void setFlginclscrivaniein(java.lang.Integer value){this.flginclscrivaniein=value;}
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