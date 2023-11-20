/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntCsAggdatielabitemxpdvBean")
public class DmpkIntCsAggdatielabitemxpdvBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_CS_AGGDATIELABITEMXPDV";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String tipoitemin;
	private java.math.BigDecimal iditemin;
	private java.lang.String idpdvin;
	private java.lang.String iditeminviatoin;
	private java.lang.String iditemconservatorein;
	private java.lang.String errwarncodein;
	private java.lang.String errwarnmsgin;
	private java.lang.String esitoconsin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String providitemin;
	private java.lang.String labelpdvin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getTipoitemin(){return tipoitemin;}
    public java.math.BigDecimal getIditemin(){return iditemin;}
    public java.lang.String getIdpdvin(){return idpdvin;}
    public java.lang.String getIditeminviatoin(){return iditeminviatoin;}
    public java.lang.String getIditemconservatorein(){return iditemconservatorein;}
    public java.lang.String getErrwarncodein(){return errwarncodein;}
    public java.lang.String getErrwarnmsgin(){return errwarnmsgin;}
    public java.lang.String getEsitoconsin(){return esitoconsin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getProviditemin(){return providitemin;}
    public java.lang.String getLabelpdvin(){return labelpdvin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setTipoitemin(java.lang.String value){this.tipoitemin=value;}
    public void setIditemin(java.math.BigDecimal value){this.iditemin=value;}
    public void setIdpdvin(java.lang.String value){this.idpdvin=value;}
    public void setIditeminviatoin(java.lang.String value){this.iditeminviatoin=value;}
    public void setIditemconservatorein(java.lang.String value){this.iditemconservatorein=value;}
    public void setErrwarncodein(java.lang.String value){this.errwarncodein=value;}
    public void setErrwarnmsgin(java.lang.String value){this.errwarnmsgin=value;}
    public void setEsitoconsin(java.lang.String value){this.esitoconsin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setProviditemin(java.lang.String value){this.providitemin=value;}
    public void setLabelpdvin(java.lang.String value){this.labelpdvin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    