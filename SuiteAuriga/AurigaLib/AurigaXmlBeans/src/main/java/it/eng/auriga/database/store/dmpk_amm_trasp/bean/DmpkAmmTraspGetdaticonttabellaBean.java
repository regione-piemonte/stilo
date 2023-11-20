/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAmmTraspGetdaticonttabellaBean")
public class DmpkAmmTraspGetdaticonttabellaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_AMM_TRASP_GETDATICONTTABELLA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal idcontenutosezin;
	private java.lang.String titoloout;
	private java.lang.Integer flgpresentiinfodettout;
	private java.lang.String infodettindettaglioout;
	private java.lang.String infodettinsezout;
	private java.lang.Integer flginfodettugualiout;
	private java.lang.String datiaggout;
	private java.lang.Integer nrorecxpaginain;
	private java.lang.Integer nropaginaio;
	private java.lang.String infostrutturatabout;
	private java.lang.Integer nrorectotaliout;
	private java.lang.Integer nropaginetotout;
	private java.lang.Integer nrorecinpaginaout;
	private java.lang.String valorirectabout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgrecordfuoripubblin;
	private java.lang.String filtriin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIdcontenutosezin(){return idcontenutosezin;}
    public java.lang.String getTitoloout(){return titoloout;}
    public java.lang.Integer getFlgpresentiinfodettout(){return flgpresentiinfodettout;}
    public java.lang.String getInfodettindettaglioout(){return infodettindettaglioout;}
    public java.lang.String getInfodettinsezout(){return infodettinsezout;}
    public java.lang.Integer getFlginfodettugualiout(){return flginfodettugualiout;}
    public java.lang.String getDatiaggout(){return datiaggout;}
    public java.lang.Integer getNrorecxpaginain(){return nrorecxpaginain;}
    public java.lang.Integer getNropaginaio(){return nropaginaio;}
    public java.lang.String getInfostrutturatabout(){return infostrutturatabout;}
    public java.lang.Integer getNrorectotaliout(){return nrorectotaliout;}
    public java.lang.Integer getNropaginetotout(){return nropaginetotout;}
    public java.lang.Integer getNrorecinpaginaout(){return nrorecinpaginaout;}
    public java.lang.String getValorirectabout(){return valorirectabout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgrecordfuoripubblin(){return flgrecordfuoripubblin;}
    public java.lang.String getFiltriin(){return filtriin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIdcontenutosezin(java.math.BigDecimal value){this.idcontenutosezin=value;}
    public void setTitoloout(java.lang.String value){this.titoloout=value;}
    public void setFlgpresentiinfodettout(java.lang.Integer value){this.flgpresentiinfodettout=value;}
    public void setInfodettindettaglioout(java.lang.String value){this.infodettindettaglioout=value;}
    public void setInfodettinsezout(java.lang.String value){this.infodettinsezout=value;}
    public void setFlginfodettugualiout(java.lang.Integer value){this.flginfodettugualiout=value;}
    public void setDatiaggout(java.lang.String value){this.datiaggout=value;}
    public void setNrorecxpaginain(java.lang.Integer value){this.nrorecxpaginain=value;}
    public void setNropaginaio(java.lang.Integer value){this.nropaginaio=value;}
    public void setInfostrutturatabout(java.lang.String value){this.infostrutturatabout=value;}
    public void setNrorectotaliout(java.lang.Integer value){this.nrorectotaliout=value;}
    public void setNropaginetotout(java.lang.Integer value){this.nropaginetotout=value;}
    public void setNrorecinpaginaout(java.lang.Integer value){this.nrorecinpaginaout=value;}
    public void setValorirectabout(java.lang.String value){this.valorirectabout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgrecordfuoripubblin(java.lang.Integer value){this.flgrecordfuoripubblin=value;}
    public void setFiltriin(java.lang.String value){this.filtriin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    