/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreTestpresenzafileBean")
public class DmpkCoreTestpresenzafileBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_TESTPRESENZAFILE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.lang.String improntafilein;
	private java.lang.String algoritmoimprontain;
	private java.lang.String encodingimprontain;
	private java.lang.Integer flgincludeverannin;
	private java.lang.Integer flgfileesistenteout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.lang.String getImprontafilein(){return improntafilein;}
    public java.lang.String getAlgoritmoimprontain(){return algoritmoimprontain;}
    public java.lang.String getEncodingimprontain(){return encodingimprontain;}
    public java.lang.Integer getFlgincludeverannin(){return flgincludeverannin;}
    public java.lang.Integer getFlgfileesistenteout(){return flgfileesistenteout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setImprontafilein(java.lang.String value){this.improntafilein=value;}
    public void setAlgoritmoimprontain(java.lang.String value){this.algoritmoimprontain=value;}
    public void setEncodingimprontain(java.lang.String value){this.encodingimprontain=value;}
    public void setFlgincludeverannin(java.lang.Integer value){this.flgincludeverannin=value;}
    public void setFlgfileesistenteout(java.lang.Integer value){this.flgfileesistenteout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    