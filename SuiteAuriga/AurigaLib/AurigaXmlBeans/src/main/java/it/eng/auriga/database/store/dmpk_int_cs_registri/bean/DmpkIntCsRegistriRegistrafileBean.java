/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntCsRegistriRegistrafileBean")
public class DmpkIntCsRegistriRegistrafileBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_CS_REGISTRI_REGISTRAFILE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String nomecartellaftpin;
	private java.lang.String nomefileregistroin;
	private java.lang.String tsricezionesuftpin;
	private java.lang.String esitoctrlfilein;
	private java.lang.String errtypectrlfilein;
	private java.lang.String errmsgctrlfilein;
	private java.lang.String improntafilein;
	private java.lang.String encodingimprontain;
	private java.lang.String algoritmoimprontain;
	private java.lang.String uriarchiviazionein;
	private java.lang.String idregistroout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String xmlprofiloregistroin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getNomecartellaftpin(){return nomecartellaftpin;}
    public java.lang.String getNomefileregistroin(){return nomefileregistroin;}
    public java.lang.String getTsricezionesuftpin(){return tsricezionesuftpin;}
    public java.lang.String getEsitoctrlfilein(){return esitoctrlfilein;}
    public java.lang.String getErrtypectrlfilein(){return errtypectrlfilein;}
    public java.lang.String getErrmsgctrlfilein(){return errmsgctrlfilein;}
    public java.lang.String getImprontafilein(){return improntafilein;}
    public java.lang.String getEncodingimprontain(){return encodingimprontain;}
    public java.lang.String getAlgoritmoimprontain(){return algoritmoimprontain;}
    public java.lang.String getUriarchiviazionein(){return uriarchiviazionein;}
    public java.lang.String getIdregistroout(){return idregistroout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getXmlprofiloregistroin(){return xmlprofiloregistroin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setNomecartellaftpin(java.lang.String value){this.nomecartellaftpin=value;}
    public void setNomefileregistroin(java.lang.String value){this.nomefileregistroin=value;}
    public void setTsricezionesuftpin(java.lang.String value){this.tsricezionesuftpin=value;}
    public void setEsitoctrlfilein(java.lang.String value){this.esitoctrlfilein=value;}
    public void setErrtypectrlfilein(java.lang.String value){this.errtypectrlfilein=value;}
    public void setErrmsgctrlfilein(java.lang.String value){this.errmsgctrlfilein=value;}
    public void setImprontafilein(java.lang.String value){this.improntafilein=value;}
    public void setEncodingimprontain(java.lang.String value){this.encodingimprontain=value;}
    public void setAlgoritmoimprontain(java.lang.String value){this.algoritmoimprontain=value;}
    public void setUriarchiviazionein(java.lang.String value){this.uriarchiviazionein=value;}
    public void setIdregistroout(java.lang.String value){this.idregistroout=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setXmlprofiloregistroin(java.lang.String value){this.xmlprofiloregistroin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    