/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntCsInspdvBean")
public class DmpkIntCsInspdvBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_CS_INSPDV";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String iditemgroupin;
	private java.lang.String labelpdvin;
	private java.lang.String xmlpdvin;
	private java.math.BigDecimal dimensionepdvin;
	private java.lang.String improntapdvin;
	private java.lang.String algoritmoimprontain;
	private java.lang.String encodingimprontain;
	private java.lang.String idpdvout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String inffilepdvin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getIditemgroupin(){return iditemgroupin;}
    public java.lang.String getLabelpdvin(){return labelpdvin;}
    public java.lang.String getXmlpdvin(){return xmlpdvin;}
    public java.math.BigDecimal getDimensionepdvin(){return dimensionepdvin;}
    public java.lang.String getImprontapdvin(){return improntapdvin;}
    public java.lang.String getAlgoritmoimprontain(){return algoritmoimprontain;}
    public java.lang.String getEncodingimprontain(){return encodingimprontain;}
    public java.lang.String getIdpdvout(){return idpdvout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getInffilepdvin(){return inffilepdvin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIditemgroupin(java.lang.String value){this.iditemgroupin=value;}
    public void setLabelpdvin(java.lang.String value){this.labelpdvin=value;}
    public void setXmlpdvin(java.lang.String value){this.xmlpdvin=value;}
    public void setDimensionepdvin(java.math.BigDecimal value){this.dimensionepdvin=value;}
    public void setImprontapdvin(java.lang.String value){this.improntapdvin=value;}
    public void setAlgoritmoimprontain(java.lang.String value){this.algoritmoimprontain=value;}
    public void setEncodingimprontain(java.lang.String value){this.encodingimprontain=value;}
    public void setIdpdvout(java.lang.String value){this.idpdvout=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setInffilepdvin(java.lang.String value){this.inffilepdvin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    