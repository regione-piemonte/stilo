/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreVistadocumentoBean")
public class DmpkCoreVistadocumentoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_VISTADOCUMENTO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idudin;
	private java.lang.String flgappostorifiutatoin;
	private java.lang.String messaggioin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String tipomittintudout;
	private java.math.BigDecimal idmittudout;
	private java.lang.String tipomittultimoinvioudout;
	private java.math.BigDecimal idmittultimoinvioudout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdudin(){return idudin;}
    public java.lang.String getFlgappostorifiutatoin(){return flgappostorifiutatoin;}
    public java.lang.String getMessaggioin(){return messaggioin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getTipomittintudout(){return tipomittintudout;}
    public java.math.BigDecimal getIdmittudout(){return idmittudout;}
    public java.lang.String getTipomittultimoinvioudout(){return tipomittultimoinvioudout;}
    public java.math.BigDecimal getIdmittultimoinvioudout(){return idmittultimoinvioudout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    public void setFlgappostorifiutatoin(java.lang.String value){this.flgappostorifiutatoin=value;}
    public void setMessaggioin(java.lang.String value){this.messaggioin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setTipomittintudout(java.lang.String value){this.tipomittintudout=value;}
    public void setIdmittudout(java.math.BigDecimal value){this.idmittudout=value;}
    public void setTipomittultimoinvioudout(java.lang.String value){this.tipomittultimoinvioudout=value;}
    public void setIdmittultimoinvioudout(java.math.BigDecimal value){this.idmittultimoinvioudout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    