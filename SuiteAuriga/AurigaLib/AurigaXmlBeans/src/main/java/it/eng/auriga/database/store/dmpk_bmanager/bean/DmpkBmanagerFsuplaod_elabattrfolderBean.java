/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkBmanagerFsuplaod_elabattrfolderBean")
public class DmpkBmanagerFsuplaod_elabattrfolderBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_BMANAGER_FSUPLAOD_ELABATTRFOLDER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal idjobin;
	private java.lang.String xmlmetadatispecin;
	private java.lang.String xmlattrtosetout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIdjobin(){return idjobin;}
    public java.lang.String getXmlmetadatispecin(){return xmlmetadatispecin;}
    public java.lang.String getXmlattrtosetout(){return xmlattrtosetout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIdjobin(java.math.BigDecimal value){this.idjobin=value;}
    public void setXmlmetadatispecin(java.lang.String value){this.xmlmetadatispecin=value;}
    public void setXmlattrtosetout(java.lang.String value){this.xmlattrtosetout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    