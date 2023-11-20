/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntCsRegistriRegistraesitoversamentoBean")
public class DmpkIntCsRegistriRegistraesitoversamentoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_CS_REGISTRI_REGISTRAESITOVERSAMENTO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String idsipversamentoin;
	private java.lang.String esitoversamentoin;
	private java.lang.String tsgenerazionerdvin;
	private java.lang.String xmlrdvin;
	private java.lang.String iddocinconservazionein;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getIdsipversamentoin(){return idsipversamentoin;}
    public java.lang.String getEsitoversamentoin(){return esitoversamentoin;}
    public java.lang.String getTsgenerazionerdvin(){return tsgenerazionerdvin;}
    public java.lang.String getXmlrdvin(){return xmlrdvin;}
    public java.lang.String getIddocinconservazionein(){return iddocinconservazionein;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdsipversamentoin(java.lang.String value){this.idsipversamentoin=value;}
    public void setEsitoversamentoin(java.lang.String value){this.esitoversamentoin=value;}
    public void setTsgenerazionerdvin(java.lang.String value){this.tsgenerazionerdvin=value;}
    public void setXmlrdvin(java.lang.String value){this.xmlrdvin=value;}
    public void setIddocinconservazionein(java.lang.String value){this.iddocinconservazionein=value;}
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