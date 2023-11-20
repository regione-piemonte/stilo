/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreGetmaindataverdocBean")
public class DmpkCoreGetmaindataverdocBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_GETMAINDATAVERDOC";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddocin;
	private java.math.BigDecimal nroprogrverio;
	private java.lang.String codverout;
	private java.lang.Integer flgvldout;
	private java.lang.String displayfilenameverout;
	private java.lang.String uriverout;
	private java.lang.String improntaverout;
	private java.lang.String algoritmoimprontaverout;
	private java.lang.String encodingimprontaverout;
	private java.math.BigDecimal dimensioneverout;
	private java.math.BigDecimal mimetypeverout;
	private java.lang.Integer flgfirmataverout;
	private java.lang.String noteverout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddocin(){return iddocin;}
    public java.math.BigDecimal getNroprogrverio(){return nroprogrverio;}
    public java.lang.String getCodverout(){return codverout;}
    public java.lang.Integer getFlgvldout(){return flgvldout;}
    public java.lang.String getDisplayfilenameverout(){return displayfilenameverout;}
    public java.lang.String getUriverout(){return uriverout;}
    public java.lang.String getImprontaverout(){return improntaverout;}
    public java.lang.String getAlgoritmoimprontaverout(){return algoritmoimprontaverout;}
    public java.lang.String getEncodingimprontaverout(){return encodingimprontaverout;}
    public java.math.BigDecimal getDimensioneverout(){return dimensioneverout;}
    public java.math.BigDecimal getMimetypeverout(){return mimetypeverout;}
    public java.lang.Integer getFlgfirmataverout(){return flgfirmataverout;}
    public java.lang.String getNoteverout(){return noteverout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIddocin(java.math.BigDecimal value){this.iddocin=value;}
    public void setNroprogrverio(java.math.BigDecimal value){this.nroprogrverio=value;}
    public void setCodverout(java.lang.String value){this.codverout=value;}
    public void setFlgvldout(java.lang.Integer value){this.flgvldout=value;}
    public void setDisplayfilenameverout(java.lang.String value){this.displayfilenameverout=value;}
    public void setUriverout(java.lang.String value){this.uriverout=value;}
    public void setImprontaverout(java.lang.String value){this.improntaverout=value;}
    public void setAlgoritmoimprontaverout(java.lang.String value){this.algoritmoimprontaverout=value;}
    public void setEncodingimprontaverout(java.lang.String value){this.encodingimprontaverout=value;}
    public void setDimensioneverout(java.math.BigDecimal value){this.dimensioneverout=value;}
    public void setMimetypeverout(java.math.BigDecimal value){this.mimetypeverout=value;}
    public void setFlgfirmataverout(java.lang.Integer value){this.flgfirmataverout=value;}
    public void setNoteverout(java.lang.String value){this.noteverout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    