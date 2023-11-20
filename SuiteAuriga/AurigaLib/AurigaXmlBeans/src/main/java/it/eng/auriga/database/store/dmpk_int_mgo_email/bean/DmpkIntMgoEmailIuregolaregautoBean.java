/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntMgoEmailIuregolaregautoBean")
public class DmpkIntMgoEmailIuregolaregautoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_MGO_EMAIL_IUREGOLAREGAUTO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idregolaio;
	private java.lang.String nomeregolain;
	private java.lang.String desregolain;
	private java.lang.String xmlfiltroin;
	private java.lang.String xmldatiregin;
	private java.lang.String dtattivazionein;
	private java.lang.String dtcessazionein;
	private java.lang.String dtiniziosospensionein;
	private java.lang.String dtfinesospensionein;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdregolaio(){return idregolaio;}
    public java.lang.String getNomeregolain(){return nomeregolain;}
    public java.lang.String getDesregolain(){return desregolain;}
    public java.lang.String getXmlfiltroin(){return xmlfiltroin;}
    public java.lang.String getXmldatiregin(){return xmldatiregin;}
    public java.lang.String getDtattivazionein(){return dtattivazionein;}
    public java.lang.String getDtcessazionein(){return dtcessazionein;}
    public java.lang.String getDtiniziosospensionein(){return dtiniziosospensionein;}
    public java.lang.String getDtfinesospensionein(){return dtfinesospensionein;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdregolaio(java.math.BigDecimal value){this.idregolaio=value;}
    public void setNomeregolain(java.lang.String value){this.nomeregolain=value;}
    public void setDesregolain(java.lang.String value){this.desregolain=value;}
    public void setXmlfiltroin(java.lang.String value){this.xmlfiltroin=value;}
    public void setXmldatiregin(java.lang.String value){this.xmldatiregin=value;}
    public void setDtattivazionein(java.lang.String value){this.dtattivazionein=value;}
    public void setDtcessazionein(java.lang.String value){this.dtcessazionein=value;}
    public void setDtiniziosospensionein(java.lang.String value){this.dtiniziosospensionein=value;}
    public void setDtfinesospensionein(java.lang.String value){this.dtfinesospensionein=value;}
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