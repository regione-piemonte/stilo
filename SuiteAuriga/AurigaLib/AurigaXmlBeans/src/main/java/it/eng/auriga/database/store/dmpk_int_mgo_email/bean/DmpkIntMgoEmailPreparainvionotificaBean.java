/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntMgoEmailPreparainvionotificaBean")
public class DmpkIntMgoEmailPreparainvionotificaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_MGO_EMAIL_PREPARAINVIONOTIFICA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String tiponotificain;
	private java.lang.String idemailin;
	private java.math.BigDecimal idudin;
	private java.lang.String xmlsegnaturain;
	private java.lang.Integer flgnoattachxmlin;
	private java.lang.String versionedtdin;
	private java.lang.String xmldatiinviomailout;
	private java.lang.String xmlattachout;
	private java.lang.String nomexmlattachout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getTiponotificain(){return tiponotificain;}
    public java.lang.String getIdemailin(){return idemailin;}
    public java.math.BigDecimal getIdudin(){return idudin;}
    public java.lang.String getXmlsegnaturain(){return xmlsegnaturain;}
    public java.lang.Integer getFlgnoattachxmlin(){return flgnoattachxmlin;}
    public java.lang.String getVersionedtdin(){return versionedtdin;}
    public java.lang.String getXmldatiinviomailout(){return xmldatiinviomailout;}
    public java.lang.String getXmlattachout(){return xmlattachout;}
    public java.lang.String getNomexmlattachout(){return nomexmlattachout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setTiponotificain(java.lang.String value){this.tiponotificain=value;}
    public void setIdemailin(java.lang.String value){this.idemailin=value;}
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    public void setXmlsegnaturain(java.lang.String value){this.xmlsegnaturain=value;}
    public void setFlgnoattachxmlin(java.lang.Integer value){this.flgnoattachxmlin=value;}
    public void setVersionedtdin(java.lang.String value){this.versionedtdin=value;}
    public void setXmldatiinviomailout(java.lang.String value){this.xmldatiinviomailout=value;}
    public void setXmlattachout(java.lang.String value){this.xmlattachout=value;}
    public void setNomexmlattachout(java.lang.String value){this.nomexmlattachout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    