/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAmmTraspGetdettcontdoccomplessoBean")
public class DmpkAmmTraspGetdettcontdoccomplessoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_AMM_TRASP_GETDETTCONTDOCCOMPLESSO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal idcontenutosezin;
	private java.lang.String titoloout;
	private java.lang.Integer flgfileprimarioout;
	private java.lang.Integer nrofileallegatiout;
	private java.lang.Integer flgpresentiinfodettout;
	private java.lang.String infodettindettaglioout;
	private java.lang.String infodettinsezout;
	private java.lang.Integer flginfodettugualiout;
	private java.lang.String datiaggout;
	private java.lang.String datifileprimarioout;
	private java.lang.String datifileallegatiout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIdcontenutosezin(){return idcontenutosezin;}
    public java.lang.String getTitoloout(){return titoloout;}
    public java.lang.Integer getFlgfileprimarioout(){return flgfileprimarioout;}
    public java.lang.Integer getNrofileallegatiout(){return nrofileallegatiout;}
    public java.lang.Integer getFlgpresentiinfodettout(){return flgpresentiinfodettout;}
    public java.lang.String getInfodettindettaglioout(){return infodettindettaglioout;}
    public java.lang.String getInfodettinsezout(){return infodettinsezout;}
    public java.lang.Integer getFlginfodettugualiout(){return flginfodettugualiout;}
    public java.lang.String getDatiaggout(){return datiaggout;}
    public java.lang.String getDatifileprimarioout(){return datifileprimarioout;}
    public java.lang.String getDatifileallegatiout(){return datifileallegatiout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIdcontenutosezin(java.math.BigDecimal value){this.idcontenutosezin=value;}
    public void setTitoloout(java.lang.String value){this.titoloout=value;}
    public void setFlgfileprimarioout(java.lang.Integer value){this.flgfileprimarioout=value;}
    public void setNrofileallegatiout(java.lang.Integer value){this.nrofileallegatiout=value;}
    public void setFlgpresentiinfodettout(java.lang.Integer value){this.flgpresentiinfodettout=value;}
    public void setInfodettindettaglioout(java.lang.String value){this.infodettindettaglioout=value;}
    public void setInfodettinsezout(java.lang.String value){this.infodettinsezout=value;}
    public void setFlginfodettugualiout(java.lang.Integer value){this.flginfodettugualiout=value;}
    public void setDatiaggout(java.lang.String value){this.datiaggout=value;}
    public void setDatifileprimarioout(java.lang.String value){this.datifileprimarioout=value;}
    public void setDatifileallegatiout(java.lang.String value){this.datifileallegatiout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    