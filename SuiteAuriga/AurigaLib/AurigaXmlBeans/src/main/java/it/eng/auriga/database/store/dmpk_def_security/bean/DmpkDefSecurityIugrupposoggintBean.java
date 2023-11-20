/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityIugrupposoggintBean")
public class DmpkDefSecurityIugrupposoggintBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_IUGRUPPOSOGGINT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idgruppoio;
	private java.lang.String nomegruppoin;
	private java.lang.Integer flgdidefaultinaclin;
	private java.lang.String dtiniziovldin;
	private java.lang.String dtfinevldin;
	private java.lang.String ciprovgruppoin;
	private java.lang.Integer flglockedin;
	private java.lang.String rowidout;
	private java.lang.String flgmodmembriin;
	private java.lang.String xmlmembriin;
	private java.lang.String attributiaddin;
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
    public java.math.BigDecimal getIdgruppoio(){return idgruppoio;}
    public java.lang.String getNomegruppoin(){return nomegruppoin;}
    public java.lang.Integer getFlgdidefaultinaclin(){return flgdidefaultinaclin;}
    public java.lang.String getDtiniziovldin(){return dtiniziovldin;}
    public java.lang.String getDtfinevldin(){return dtfinevldin;}
    public java.lang.String getCiprovgruppoin(){return ciprovgruppoin;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getFlgmodmembriin(){return flgmodmembriin;}
    public java.lang.String getXmlmembriin(){return xmlmembriin;}
    public java.lang.String getAttributiaddin(){return attributiaddin;}
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
    public void setIdgruppoio(java.math.BigDecimal value){this.idgruppoio=value;}
    public void setNomegruppoin(java.lang.String value){this.nomegruppoin=value;}
    public void setFlgdidefaultinaclin(java.lang.Integer value){this.flgdidefaultinaclin=value;}
    public void setDtiniziovldin(java.lang.String value){this.dtiniziovldin=value;}
    public void setDtfinevldin(java.lang.String value){this.dtfinevldin=value;}
    public void setCiprovgruppoin(java.lang.String value){this.ciprovgruppoin=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setFlgmodmembriin(java.lang.String value){this.flgmodmembriin=value;}
    public void setXmlmembriin(java.lang.String value){this.xmlmembriin=value;}
    public void setAttributiaddin(java.lang.String value){this.attributiaddin=value;}
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