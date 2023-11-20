/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntCsRegistriTestesistenzaregistroBean")
public class DmpkIntCsRegistriTestesistenzaregistroBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_CS_REGISTRI_TESTESISTENZAREGISTRO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String nomecartellaftpin;
	private java.lang.String nomefileregistroin;
	private java.lang.String tsricezionesuftpin;
	private java.lang.Integer flgesisteout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getNomecartellaftpin(){return nomecartellaftpin;}
    public java.lang.String getNomefileregistroin(){return nomefileregistroin;}
    public java.lang.String getTsricezionesuftpin(){return tsricezionesuftpin;}
    public java.lang.Integer getFlgesisteout(){return flgesisteout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setNomecartellaftpin(java.lang.String value){this.nomecartellaftpin=value;}
    public void setNomefileregistroin(java.lang.String value){this.nomefileregistroin=value;}
    public void setTsricezionesuftpin(java.lang.String value){this.tsricezionesuftpin=value;}
    public void setFlgesisteout(java.lang.Integer value){this.flgesisteout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    