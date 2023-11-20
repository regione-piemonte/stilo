/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityLoaddettscrivaniaBean")
public class DmpkDefSecurityLoaddettscrivaniaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_LOADDETTSCRIVANIA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idscrivaniaio;
	private java.lang.String intestazionescrivaniaio;
	private java.math.BigDecimal idprofiloout;
	private java.lang.String nomeprofiloout;
	private java.lang.String xmldatiprofiloout;
	private java.lang.String xmlgruppiprivout;
	private java.lang.String xmlgruppiappout;
	private java.lang.String rowidout;
	private java.lang.String attributiaddout;
	private java.lang.Integer bachsizeout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdscrivaniaio(){return idscrivaniaio;}
    public java.lang.String getIntestazionescrivaniaio(){return intestazionescrivaniaio;}
    public java.math.BigDecimal getIdprofiloout(){return idprofiloout;}
    public java.lang.String getNomeprofiloout(){return nomeprofiloout;}
    public java.lang.String getXmldatiprofiloout(){return xmldatiprofiloout;}
    public java.lang.String getXmlgruppiprivout(){return xmlgruppiprivout;}
    public java.lang.String getXmlgruppiappout(){return xmlgruppiappout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getBachsizeout(){return bachsizeout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdscrivaniaio(java.math.BigDecimal value){this.idscrivaniaio=value;}
    public void setIntestazionescrivaniaio(java.lang.String value){this.intestazionescrivaniaio=value;}
    public void setIdprofiloout(java.math.BigDecimal value){this.idprofiloout=value;}
    public void setNomeprofiloout(java.lang.String value){this.nomeprofiloout=value;}
    public void setXmldatiprofiloout(java.lang.String value){this.xmldatiprofiloout=value;}
    public void setXmlgruppiprivout(java.lang.String value){this.xmlgruppiprivout=value;}
    public void setXmlgruppiappout(java.lang.String value){this.xmlgruppiappout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setBachsizeout(java.lang.Integer value){this.bachsizeout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    