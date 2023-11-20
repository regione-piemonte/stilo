/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkModelliDocExtractvermodelloBean")
public class DmpkModelliDocExtractvermodelloBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_MODELLI_DOC_EXTRACTVERMODELLO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idmodelloin;
	private java.lang.String nomemodelloin;
	private java.math.BigDecimal nroprogrverio;
	private java.math.BigDecimal iddocout;
	private java.lang.String displayfilenameverout;
	private java.lang.String uriverout;
	private java.lang.String improntaverout;
	private java.lang.String algoritmoimprontaverout;
	private java.lang.String encodingimprontaverout;
	private java.math.BigDecimal dimensioneverout;
	private java.lang.String mimetypeverout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdmodelloin(){return idmodelloin;}
    public java.lang.String getNomemodelloin(){return nomemodelloin;}
    public java.math.BigDecimal getNroprogrverio(){return nroprogrverio;}
    public java.math.BigDecimal getIddocout(){return iddocout;}
    public java.lang.String getDisplayfilenameverout(){return displayfilenameverout;}
    public java.lang.String getUriverout(){return uriverout;}
    public java.lang.String getImprontaverout(){return improntaverout;}
    public java.lang.String getAlgoritmoimprontaverout(){return algoritmoimprontaverout;}
    public java.lang.String getEncodingimprontaverout(){return encodingimprontaverout;}
    public java.math.BigDecimal getDimensioneverout(){return dimensioneverout;}
    public java.lang.String getMimetypeverout(){return mimetypeverout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdmodelloin(java.math.BigDecimal value){this.idmodelloin=value;}
    public void setNomemodelloin(java.lang.String value){this.nomemodelloin=value;}
    public void setNroprogrverio(java.math.BigDecimal value){this.nroprogrverio=value;}
    public void setIddocout(java.math.BigDecimal value){this.iddocout=value;}
    public void setDisplayfilenameverout(java.lang.String value){this.displayfilenameverout=value;}
    public void setUriverout(java.lang.String value){this.uriverout=value;}
    public void setImprontaverout(java.lang.String value){this.improntaverout=value;}
    public void setAlgoritmoimprontaverout(java.lang.String value){this.algoritmoimprontaverout=value;}
    public void setEncodingimprontaverout(java.lang.String value){this.encodingimprontaverout=value;}
    public void setDimensioneverout(java.math.BigDecimal value){this.dimensioneverout=value;}
    public void setMimetypeverout(java.lang.String value){this.mimetypeverout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    