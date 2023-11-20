/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWfGeturlfromnameattBean")
public class DmpkWfGeturlfromnameattBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WF_GETURLFROMNAMEATT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idprocessin;
	private java.lang.String activitynamein;
	private java.lang.String urlfromnomeattout;
	private java.math.BigDecimal idtyevtout;
	private java.lang.String nometyevtout;
	private java.lang.Integer flgevtdurativoout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.lang.String getActivitynamein(){return activitynamein;}
    public java.lang.String getUrlfromnomeattout(){return urlfromnomeattout;}
    public java.math.BigDecimal getIdtyevtout(){return idtyevtout;}
    public java.lang.String getNometyevtout(){return nometyevtout;}
    public java.lang.Integer getFlgevtdurativoout(){return flgevtdurativoout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setActivitynamein(java.lang.String value){this.activitynamein=value;}
    public void setUrlfromnomeattout(java.lang.String value){this.urlfromnomeattout=value;}
    public void setIdtyevtout(java.math.BigDecimal value){this.idtyevtout=value;}
    public void setNometyevtout(java.lang.String value){this.nometyevtout=value;}
    public void setFlgevtdurativoout(java.lang.Integer value){this.flgevtdurativoout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    