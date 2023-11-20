/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkBmanagerLoaddettfoglioximportBean")
public class DmpkBmanagerLoaddettfoglioximportBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_BMANAGER_LOADDETTFOGLIOXIMPORT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String idfoglioin;
	private java.lang.String tipocontenutoout;
	private java.math.BigDecimal iduseruploadout;
	private java.lang.String desuseruploadout;
	private java.lang.String tsuploadout;
	private java.lang.String tsinizioelabout;
	private java.lang.String tsfineelabout;
	private java.math.BigDecimal nrorigheout;
	private java.math.BigDecimal nrorigheokout;
	private java.math.BigDecimal nrorighekoout;
	private java.lang.String uriout;
	private java.lang.String statoout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getIdfoglioin(){return idfoglioin;}
    public java.lang.String getTipocontenutoout(){return tipocontenutoout;}
    public java.math.BigDecimal getIduseruploadout(){return iduseruploadout;}
    public java.lang.String getDesuseruploadout(){return desuseruploadout;}
    public java.lang.String getTsuploadout(){return tsuploadout;}
    public java.lang.String getTsinizioelabout(){return tsinizioelabout;}
    public java.lang.String getTsfineelabout(){return tsfineelabout;}
    public java.math.BigDecimal getNrorigheout(){return nrorigheout;}
    public java.math.BigDecimal getNrorigheokout(){return nrorigheokout;}
    public java.math.BigDecimal getNrorighekoout(){return nrorighekoout;}
    public java.lang.String getUriout(){return uriout;}
    public java.lang.String getStatoout(){return statoout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdfoglioin(java.lang.String value){this.idfoglioin=value;}
    public void setTipocontenutoout(java.lang.String value){this.tipocontenutoout=value;}
    public void setIduseruploadout(java.math.BigDecimal value){this.iduseruploadout=value;}
    public void setDesuseruploadout(java.lang.String value){this.desuseruploadout=value;}
    public void setTsuploadout(java.lang.String value){this.tsuploadout=value;}
    public void setTsinizioelabout(java.lang.String value){this.tsinizioelabout=value;}
    public void setTsfineelabout(java.lang.String value){this.tsfineelabout=value;}
    public void setNrorigheout(java.math.BigDecimal value){this.nrorigheout=value;}
    public void setNrorigheokout(java.math.BigDecimal value){this.nrorigheokout=value;}
    public void setNrorighekoout(java.math.BigDecimal value){this.nrorighekoout=value;}
    public void setUriout(java.lang.String value){this.uriout=value;}
    public void setStatoout(java.lang.String value){this.statoout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    