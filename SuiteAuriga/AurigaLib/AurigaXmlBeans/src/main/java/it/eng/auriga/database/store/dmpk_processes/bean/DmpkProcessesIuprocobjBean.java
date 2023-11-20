/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessesIuprocobjBean")
public class DmpkProcessesIuprocobjBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESSES_IUPROCOBJ";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idprocessin;
	private java.math.BigDecimal idtipoeventoin;
	private java.math.BigDecimal ideventoio;
	private java.math.BigDecimal idprocobjcario;
	private java.math.BigDecimal idprocobjtyin;
	private java.lang.String provciprocobjtyin;
	private java.lang.String desprocobjcarin;
	private java.lang.String provciprocobjin;
	private java.lang.String provciprocobjcarin;
	private java.lang.String cigeolocalizzazionein;
	private java.math.BigDecimal idprocobjcarprecin;
	private java.lang.String attributiaddin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.math.BigDecimal getIdtipoeventoin(){return idtipoeventoin;}
    public java.math.BigDecimal getIdeventoio(){return ideventoio;}
    public java.math.BigDecimal getIdprocobjcario(){return idprocobjcario;}
    public java.math.BigDecimal getIdprocobjtyin(){return idprocobjtyin;}
    public java.lang.String getProvciprocobjtyin(){return provciprocobjtyin;}
    public java.lang.String getDesprocobjcarin(){return desprocobjcarin;}
    public java.lang.String getProvciprocobjin(){return provciprocobjin;}
    public java.lang.String getProvciprocobjcarin(){return provciprocobjcarin;}
    public java.lang.String getCigeolocalizzazionein(){return cigeolocalizzazionein;}
    public java.math.BigDecimal getIdprocobjcarprecin(){return idprocobjcarprecin;}
    public java.lang.String getAttributiaddin(){return attributiaddin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setIdtipoeventoin(java.math.BigDecimal value){this.idtipoeventoin=value;}
    public void setIdeventoio(java.math.BigDecimal value){this.ideventoio=value;}
    public void setIdprocobjcario(java.math.BigDecimal value){this.idprocobjcario=value;}
    public void setIdprocobjtyin(java.math.BigDecimal value){this.idprocobjtyin=value;}
    public void setProvciprocobjtyin(java.lang.String value){this.provciprocobjtyin=value;}
    public void setDesprocobjcarin(java.lang.String value){this.desprocobjcarin=value;}
    public void setProvciprocobjin(java.lang.String value){this.provciprocobjin=value;}
    public void setProvciprocobjcarin(java.lang.String value){this.provciprocobjcarin=value;}
    public void setCigeolocalizzazionein(java.lang.String value){this.cigeolocalizzazionein=value;}
    public void setIdprocobjcarprecin(java.math.BigDecimal value){this.idprocobjcarprecin=value;}
    public void setAttributiaddin(java.lang.String value){this.attributiaddin=value;}
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