/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntCsRegfinerecrdvBean")
public class DmpkIntCsRegfinerecrdvBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_CS_REGFINERECRDV";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String idpdvin;
	private java.lang.String esitoversamentoin;
	private java.lang.String xmlrdvin;
	private java.lang.String tsgenrdvin;
	private java.lang.String errwarncodein;
	private java.lang.String errwarnmsgin;
	private java.lang.String idpdvconsevatorein;
	private java.lang.String xmlinfoitemexrdvin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String labelpdvin;
	private java.lang.String tsricezionerdvin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getIdpdvin(){return idpdvin;}
    public java.lang.String getEsitoversamentoin(){return esitoversamentoin;}
    public java.lang.String getXmlrdvin(){return xmlrdvin;}
    public java.lang.String getTsgenrdvin(){return tsgenrdvin;}
    public java.lang.String getErrwarncodein(){return errwarncodein;}
    public java.lang.String getErrwarnmsgin(){return errwarnmsgin;}
    public java.lang.String getIdpdvconsevatorein(){return idpdvconsevatorein;}
    public java.lang.String getXmlinfoitemexrdvin(){return xmlinfoitemexrdvin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getLabelpdvin(){return labelpdvin;}
    public java.lang.String getTsricezionerdvin(){return tsricezionerdvin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdpdvin(java.lang.String value){this.idpdvin=value;}
    public void setEsitoversamentoin(java.lang.String value){this.esitoversamentoin=value;}
    public void setXmlrdvin(java.lang.String value){this.xmlrdvin=value;}
    public void setTsgenrdvin(java.lang.String value){this.tsgenrdvin=value;}
    public void setErrwarncodein(java.lang.String value){this.errwarncodein=value;}
    public void setErrwarnmsgin(java.lang.String value){this.errwarnmsgin=value;}
    public void setIdpdvconsevatorein(java.lang.String value){this.idpdvconsevatorein=value;}
    public void setXmlinfoitemexrdvin(java.lang.String value){this.xmlinfoitemexrdvin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setLabelpdvin(java.lang.String value){this.labelpdvin=value;}
    public void setTsricezionerdvin(java.lang.String value){this.tsricezionerdvin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    