/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntMgoEmailCtrlregautomailarrivoBean")
public class DmpkIntMgoEmailCtrlregautomailarrivoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_MGO_EMAIL_CTRLREGAUTOMAILARRIVO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String idmailboxin;
	private java.lang.String messageidin;
	private java.lang.String datimailin;
	private java.lang.String allegatointeropxmlin;
	private java.lang.Integer flgdaregistrareout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getIdmailboxin(){return idmailboxin;}
    public java.lang.String getMessageidin(){return messageidin;}
    public java.lang.String getDatimailin(){return datimailin;}
    public java.lang.String getAllegatointeropxmlin(){return allegatointeropxmlin;}
    public java.lang.Integer getFlgdaregistrareout(){return flgdaregistrareout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdmailboxin(java.lang.String value){this.idmailboxin=value;}
    public void setMessageidin(java.lang.String value){this.messageidin=value;}
    public void setDatimailin(java.lang.String value){this.datimailin=value;}
    public void setAllegatointeropxmlin(java.lang.String value){this.allegatointeropxmlin=value;}
    public void setFlgdaregistrareout(java.lang.Integer value){this.flgdaregistrareout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    