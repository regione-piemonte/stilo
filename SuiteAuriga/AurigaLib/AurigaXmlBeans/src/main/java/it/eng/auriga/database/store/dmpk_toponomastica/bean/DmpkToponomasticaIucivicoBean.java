/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkToponomasticaIucivicoBean")
public class DmpkToponomasticaIucivicoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_TOPONOMASTICA_IUCIVICO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String flgtpoperin;
	private java.lang.String citoponomasticoin;
	private java.lang.String cicivicoin;
	private java.lang.String civicoin;
	private java.lang.String dtinivldin;
	private java.lang.String dtfinvldin;
	private java.lang.String localitain;
	private java.lang.String capcivin;
	private java.lang.String ciquartierein;
	private java.lang.String cizonain;
	private java.lang.String barratoin;
	private java.math.BigDecimal coordinatexin;
	private java.math.BigDecimal coordinateyin;
	private java.math.BigDecimal coordinatezin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getFlgtpoperin(){return flgtpoperin;}
    public java.lang.String getCitoponomasticoin(){return citoponomasticoin;}
    public java.lang.String getCicivicoin(){return cicivicoin;}
    public java.lang.String getCivicoin(){return civicoin;}
    public java.lang.String getDtinivldin(){return dtinivldin;}
    public java.lang.String getDtfinvldin(){return dtfinvldin;}
    public java.lang.String getLocalitain(){return localitain;}
    public java.lang.String getCapcivin(){return capcivin;}
    public java.lang.String getCiquartierein(){return ciquartierein;}
    public java.lang.String getCizonain(){return cizonain;}
    public java.lang.String getBarratoin(){return barratoin;}
    public java.math.BigDecimal getCoordinatexin(){return coordinatexin;}
    public java.math.BigDecimal getCoordinateyin(){return coordinateyin;}
    public java.math.BigDecimal getCoordinatezin(){return coordinatezin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setFlgtpoperin(java.lang.String value){this.flgtpoperin=value;}
    public void setCitoponomasticoin(java.lang.String value){this.citoponomasticoin=value;}
    public void setCicivicoin(java.lang.String value){this.cicivicoin=value;}
    public void setCivicoin(java.lang.String value){this.civicoin=value;}
    public void setDtinivldin(java.lang.String value){this.dtinivldin=value;}
    public void setDtfinvldin(java.lang.String value){this.dtfinvldin=value;}
    public void setLocalitain(java.lang.String value){this.localitain=value;}
    public void setCapcivin(java.lang.String value){this.capcivin=value;}
    public void setCiquartierein(java.lang.String value){this.ciquartierein=value;}
    public void setCizonain(java.lang.String value){this.cizonain=value;}
    public void setBarratoin(java.lang.String value){this.barratoin=value;}
    public void setCoordinatexin(java.math.BigDecimal value){this.coordinatexin=value;}
    public void setCoordinateyin(java.math.BigDecimal value){this.coordinateyin=value;}
    public void setCoordinatezin(java.math.BigDecimal value){this.coordinatezin=value;}
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