/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAnagraficaLoaddettindluogosoggBean")
public class DmpkAnagraficaLoaddettindluogosoggBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ANAGRAFICA_LOADDETTINDLUOGOSOGG";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String ciindluogoio;
	private java.lang.String codtipoindluogoout;
	private java.lang.String dettaglitipoindluogoout;
	private java.lang.String dtiniziovldindluogoout;
	private java.lang.String dtfinevldindluogoout;
	private java.lang.String citoponindirizzoout;
	private java.lang.String indirizzoout;
	private java.lang.String civicoout;
	private java.lang.String internocivout;
	private java.lang.String scalacivout;
	private java.lang.Integer pianoout;
	private java.lang.String capout;
	private java.lang.String localitaout;
	private java.lang.String codistatcomuneout;
	private java.lang.String nomecomunecittaout;
	private java.lang.String targaprovout;
	private java.lang.String codistatstatoout;
	private java.lang.String nomestatoout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getCiindluogoio(){return ciindluogoio;}
    public java.lang.String getCodtipoindluogoout(){return codtipoindluogoout;}
    public java.lang.String getDettaglitipoindluogoout(){return dettaglitipoindluogoout;}
    public java.lang.String getDtiniziovldindluogoout(){return dtiniziovldindluogoout;}
    public java.lang.String getDtfinevldindluogoout(){return dtfinevldindluogoout;}
    public java.lang.String getCitoponindirizzoout(){return citoponindirizzoout;}
    public java.lang.String getIndirizzoout(){return indirizzoout;}
    public java.lang.String getCivicoout(){return civicoout;}
    public java.lang.String getInternocivout(){return internocivout;}
    public java.lang.String getScalacivout(){return scalacivout;}
    public java.lang.Integer getPianoout(){return pianoout;}
    public java.lang.String getCapout(){return capout;}
    public java.lang.String getLocalitaout(){return localitaout;}
    public java.lang.String getCodistatcomuneout(){return codistatcomuneout;}
    public java.lang.String getNomecomunecittaout(){return nomecomunecittaout;}
    public java.lang.String getTargaprovout(){return targaprovout;}
    public java.lang.String getCodistatstatoout(){return codistatstatoout;}
    public java.lang.String getNomestatoout(){return nomestatoout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setCiindluogoio(java.lang.String value){this.ciindluogoio=value;}
    public void setCodtipoindluogoout(java.lang.String value){this.codtipoindluogoout=value;}
    public void setDettaglitipoindluogoout(java.lang.String value){this.dettaglitipoindluogoout=value;}
    public void setDtiniziovldindluogoout(java.lang.String value){this.dtiniziovldindluogoout=value;}
    public void setDtfinevldindluogoout(java.lang.String value){this.dtfinevldindluogoout=value;}
    public void setCitoponindirizzoout(java.lang.String value){this.citoponindirizzoout=value;}
    public void setIndirizzoout(java.lang.String value){this.indirizzoout=value;}
    public void setCivicoout(java.lang.String value){this.civicoout=value;}
    public void setInternocivout(java.lang.String value){this.internocivout=value;}
    public void setScalacivout(java.lang.String value){this.scalacivout=value;}
    public void setPianoout(java.lang.Integer value){this.pianoout=value;}
    public void setCapout(java.lang.String value){this.capout=value;}
    public void setLocalitaout(java.lang.String value){this.localitaout=value;}
    public void setCodistatcomuneout(java.lang.String value){this.codistatcomuneout=value;}
    public void setNomecomunecittaout(java.lang.String value){this.nomecomunecittaout=value;}
    public void setTargaprovout(java.lang.String value){this.targaprovout=value;}
    public void setCodistatstatoout(java.lang.String value){this.codistatstatoout=value;}
    public void setNomestatoout(java.lang.String value){this.nomestatoout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    