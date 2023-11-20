/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkToponomasticaLoaddettagliotoponBean")
public class DmpkToponomasticaLoaddettagliotoponBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_TOPONOMASTICA_LOADDETTAGLIOTOPON";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String citoponomasticoinout;
	private java.lang.String cicivicoinout;
	private java.lang.String nometoponout;
	private java.lang.String capout;
	private java.lang.String ciquartiereout;
	private java.lang.String nomequartiereout;
	private java.lang.String dtiniziovldout;
	private java.lang.String dtfinevldout;
	private java.math.BigDecimal ciidspaooout;
	private java.lang.String prvcitoponout;
	private java.lang.String civicoout;
	private java.lang.String capcivout;
	private java.lang.String ciquartierecivout;
	private java.lang.String nomequartierecivout;
	private java.lang.String localitacivout;
	private java.math.BigDecimal coordxout;
	private java.math.BigDecimal coordyout;
	private java.math.BigDecimal coordzout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getCitoponomasticoinout(){return citoponomasticoinout;}
    public java.lang.String getCicivicoinout(){return cicivicoinout;}
    public java.lang.String getNometoponout(){return nometoponout;}
    public java.lang.String getCapout(){return capout;}
    public java.lang.String getCiquartiereout(){return ciquartiereout;}
    public java.lang.String getNomequartiereout(){return nomequartiereout;}
    public java.lang.String getDtiniziovldout(){return dtiniziovldout;}
    public java.lang.String getDtfinevldout(){return dtfinevldout;}
    public java.math.BigDecimal getCiidspaooout(){return ciidspaooout;}
    public java.lang.String getPrvcitoponout(){return prvcitoponout;}
    public java.lang.String getCivicoout(){return civicoout;}
    public java.lang.String getCapcivout(){return capcivout;}
    public java.lang.String getCiquartierecivout(){return ciquartierecivout;}
    public java.lang.String getNomequartierecivout(){return nomequartierecivout;}
    public java.lang.String getLocalitacivout(){return localitacivout;}
    public java.math.BigDecimal getCoordxout(){return coordxout;}
    public java.math.BigDecimal getCoordyout(){return coordyout;}
    public java.math.BigDecimal getCoordzout(){return coordzout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setCitoponomasticoinout(java.lang.String value){this.citoponomasticoinout=value;}
    public void setCicivicoinout(java.lang.String value){this.cicivicoinout=value;}
    public void setNometoponout(java.lang.String value){this.nometoponout=value;}
    public void setCapout(java.lang.String value){this.capout=value;}
    public void setCiquartiereout(java.lang.String value){this.ciquartiereout=value;}
    public void setNomequartiereout(java.lang.String value){this.nomequartiereout=value;}
    public void setDtiniziovldout(java.lang.String value){this.dtiniziovldout=value;}
    public void setDtfinevldout(java.lang.String value){this.dtfinevldout=value;}
    public void setCiidspaooout(java.math.BigDecimal value){this.ciidspaooout=value;}
    public void setPrvcitoponout(java.lang.String value){this.prvcitoponout=value;}
    public void setCivicoout(java.lang.String value){this.civicoout=value;}
    public void setCapcivout(java.lang.String value){this.capcivout=value;}
    public void setCiquartierecivout(java.lang.String value){this.ciquartierecivout=value;}
    public void setNomequartierecivout(java.lang.String value){this.nomequartierecivout=value;}
    public void setLocalitacivout(java.lang.String value){this.localitacivout=value;}
    public void setCoordxout(java.math.BigDecimal value){this.coordxout=value;}
    public void setCoordyout(java.math.BigDecimal value){this.coordyout=value;}
    public void setCoordzout(java.math.BigDecimal value){this.coordzout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    