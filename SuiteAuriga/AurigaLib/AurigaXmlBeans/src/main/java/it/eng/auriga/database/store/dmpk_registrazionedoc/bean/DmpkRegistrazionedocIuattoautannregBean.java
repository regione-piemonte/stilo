/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRegistrazionedocIuattoautannregBean")
public class DmpkRegistrazionedocIuattoautannregBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_REGISTRAZIONEDOC_IUATTOAUTANNREG";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idudattoio;
	private java.lang.String oggettoin;
	private java.lang.Integer flgattochiusoin;
	private java.lang.String flgregdaannullarein;
	private java.lang.String xmlregdaannullarein;
	private java.lang.String segnaturaattoout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdudattoio(){return idudattoio;}
    public java.lang.String getOggettoin(){return oggettoin;}
    public java.lang.Integer getFlgattochiusoin(){return flgattochiusoin;}
    public java.lang.String getFlgregdaannullarein(){return flgregdaannullarein;}
    public java.lang.String getXmlregdaannullarein(){return xmlregdaannullarein;}
    public java.lang.String getSegnaturaattoout(){return segnaturaattoout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdudattoio(java.math.BigDecimal value){this.idudattoio=value;}
    public void setOggettoin(java.lang.String value){this.oggettoin=value;}
    public void setFlgattochiusoin(java.lang.Integer value){this.flgattochiusoin=value;}
    public void setFlgregdaannullarein(java.lang.String value){this.flgregdaannullarein=value;}
    public void setXmlregdaannullarein(java.lang.String value){this.xmlregdaannullarein=value;}
    public void setSegnaturaattoout(java.lang.String value){this.segnaturaattoout=value;}
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