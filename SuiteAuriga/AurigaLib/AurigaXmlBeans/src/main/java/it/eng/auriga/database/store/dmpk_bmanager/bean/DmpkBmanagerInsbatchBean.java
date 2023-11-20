/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkBmanagerInsbatchBean")
public class DmpkBmanagerInsbatchBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_BMANAGER_INSBATCH";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String useridin;
	private java.math.BigDecimal iddominioin;
	private java.lang.String codapplicazionein;
	private java.lang.String codistapplicazionein;
	private java.lang.String tipojobin;
	private java.lang.String scheduletimein;
	private java.lang.Integer prioritain;
	private java.lang.Integer flgsoloxbassaoperin;
	private java.lang.String formatofileoutputin;
	private java.lang.String nomefileoutputin;
	private java.lang.String parametriin;
	private java.lang.String connstringin;
	private java.lang.String conndriverin;
	private java.lang.Integer idjobout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String parametrixmlin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getUseridin(){return useridin;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.lang.String getCodapplicazionein(){return codapplicazionein;}
    public java.lang.String getCodistapplicazionein(){return codistapplicazionein;}
    public java.lang.String getTipojobin(){return tipojobin;}
    public java.lang.String getScheduletimein(){return scheduletimein;}
    public java.lang.Integer getPrioritain(){return prioritain;}
    public java.lang.Integer getFlgsoloxbassaoperin(){return flgsoloxbassaoperin;}
    public java.lang.String getFormatofileoutputin(){return formatofileoutputin;}
    public java.lang.String getNomefileoutputin(){return nomefileoutputin;}
    public java.lang.String getParametriin(){return parametriin;}
    public java.lang.String getConnstringin(){return connstringin;}
    public java.lang.String getConndriverin(){return conndriverin;}
    public java.lang.Integer getIdjobout(){return idjobout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getParametrixmlin(){return parametrixmlin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setUseridin(java.lang.String value){this.useridin=value;}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setCodapplicazionein(java.lang.String value){this.codapplicazionein=value;}
    public void setCodistapplicazionein(java.lang.String value){this.codistapplicazionein=value;}
    public void setTipojobin(java.lang.String value){this.tipojobin=value;}
    public void setScheduletimein(java.lang.String value){this.scheduletimein=value;}
    public void setPrioritain(java.lang.Integer value){this.prioritain=value;}
    public void setFlgsoloxbassaoperin(java.lang.Integer value){this.flgsoloxbassaoperin=value;}
    public void setFormatofileoutputin(java.lang.String value){this.formatofileoutputin=value;}
    public void setNomefileoutputin(java.lang.String value){this.nomefileoutputin=value;}
    public void setParametriin(java.lang.String value){this.parametriin=value;}
    public void setConnstringin(java.lang.String value){this.connstringin=value;}
    public void setConndriverin(java.lang.String value){this.conndriverin=value;}
    public void setIdjobout(java.lang.Integer value){this.idjobout=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setParametrixmlin(java.lang.String value){this.parametrixmlin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    