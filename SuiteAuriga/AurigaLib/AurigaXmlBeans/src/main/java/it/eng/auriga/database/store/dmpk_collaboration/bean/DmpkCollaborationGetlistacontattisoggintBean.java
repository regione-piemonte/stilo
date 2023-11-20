/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCollaborationGetlistacontattisoggintBean")
public class DmpkCollaborationGetlistacontattisoggintBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_COLLABORATION_GETLISTACONTATTISOGGINT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String recipientsxmlin;
	private java.lang.String tipocontattoin;
	private java.lang.String listacontattiout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgcallbyguiin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getRecipientsxmlin(){return recipientsxmlin;}
    public java.lang.String getTipocontattoin(){return tipocontattoin;}
    public java.lang.String getListacontattiout(){return listacontattiout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgcallbyguiin(){return flgcallbyguiin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setRecipientsxmlin(java.lang.String value){this.recipientsxmlin=value;}
    public void setTipocontattoin(java.lang.String value){this.tipocontattoin=value;}
    public void setListacontattiout(java.lang.String value){this.listacontattiout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgcallbyguiin(java.lang.Integer value){this.flgcallbyguiin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    