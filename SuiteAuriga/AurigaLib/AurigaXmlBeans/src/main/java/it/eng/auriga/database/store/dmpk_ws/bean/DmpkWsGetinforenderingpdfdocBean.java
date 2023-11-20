/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWsGetinforenderingpdfdocBean")
public class DmpkWsGetinforenderingpdfdocBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WS_GETINFORENDERINGPDFDOC";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iddocin;
	private java.math.BigDecimal flgfatturaelettronicaout;
	private java.lang.String nomemodelloout;
	private java.math.BigDecimal idmodelloout;
	private java.lang.String urimodelloout;
	private java.lang.String nomedoctypeout;
	private java.math.BigDecimal iddoctypeout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIddocin(){return iddocin;}
    public java.math.BigDecimal getFlgfatturaelettronicaout(){return flgfatturaelettronicaout;}
    public java.lang.String getNomemodelloout(){return nomemodelloout;}
    public java.math.BigDecimal getIdmodelloout(){return idmodelloout;}
    public java.lang.String getUrimodelloout(){return urimodelloout;}
    public java.lang.String getNomedoctypeout(){return nomedoctypeout;}
    public java.math.BigDecimal getIddoctypeout(){return iddoctypeout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIddocin(java.math.BigDecimal value){this.iddocin=value;}
    public void setFlgfatturaelettronicaout(java.math.BigDecimal value){this.flgfatturaelettronicaout=value;}
    public void setNomemodelloout(java.lang.String value){this.nomemodelloout=value;}
    public void setIdmodelloout(java.math.BigDecimal value){this.idmodelloout=value;}
    public void setUrimodelloout(java.lang.String value){this.urimodelloout=value;}
    public void setNomedoctypeout(java.lang.String value){this.nomedoctypeout=value;}
    public void setIddoctypeout(java.math.BigDecimal value){this.iddoctypeout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    