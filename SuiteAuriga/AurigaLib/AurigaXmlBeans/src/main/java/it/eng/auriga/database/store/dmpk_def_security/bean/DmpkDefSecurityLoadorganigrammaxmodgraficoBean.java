/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityLoadorganigrammaxmodgraficoBean")
public class DmpkDefSecurityLoadorganigrammaxmodgraficoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_LOADORGANIGRAMMAXMODGRAFICO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer flgincludiutentiin;
	private java.lang.String tiporelutenticonuoin;
	private java.lang.String iduorootin;
	private java.lang.Integer livellomaxin;
	private java.lang.String codtipiuoin;
	private java.lang.String organigrammaxmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getFlgincludiutentiin(){return flgincludiutentiin;}
    public java.lang.String getTiporelutenticonuoin(){return tiporelutenticonuoin;}
    public java.lang.String getIduorootin(){return iduorootin;}
    public java.lang.Integer getLivellomaxin(){return livellomaxin;}
    public java.lang.String getCodtipiuoin(){return codtipiuoin;}
    public java.lang.String getOrganigrammaxmlout(){return organigrammaxmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgincludiutentiin(java.lang.Integer value){this.flgincludiutentiin=value;}
    public void setTiporelutenticonuoin(java.lang.String value){this.tiporelutenticonuoin=value;}
    public void setIduorootin(java.lang.String value){this.iduorootin=value;}
    public void setLivellomaxin(java.lang.Integer value){this.livellomaxin=value;}
    public void setCodtipiuoin(java.lang.String value){this.codtipiuoin=value;}
    public void setOrganigrammaxmlout(java.lang.String value){this.organigrammaxmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    