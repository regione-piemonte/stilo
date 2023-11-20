/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkSeduteOrgCollGetfileargsedutaxdownloadBean")
public class DmpkSeduteOrgCollGetfileargsedutaxdownloadBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_SEDUTE_ORG_COLL_GETFILEARGSEDUTAXDOWNLOAD";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String idsedutain;
	private java.lang.String listaidudargselin;
	private java.math.BigDecimal flginclusipareriin;
	private java.lang.String flginclallegatipiin;
	private java.lang.String flgintxpubblin;
	private java.lang.String listafilexdownloadout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgtrasmissioneattiin;
	private java.lang.String infotrasmissionexmlin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getIdsedutain(){return idsedutain;}
    public java.lang.String getListaidudargselin(){return listaidudargselin;}
    public java.math.BigDecimal getFlginclusipareriin(){return flginclusipareriin;}
    public java.lang.String getFlginclallegatipiin(){return flginclallegatipiin;}
    public java.lang.String getFlgintxpubblin(){return flgintxpubblin;}
    public java.lang.String getListafilexdownloadout(){return listafilexdownloadout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgtrasmissioneattiin(){return flgtrasmissioneattiin;}
    public java.lang.String getInfotrasmissionexmlin(){return infotrasmissionexmlin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdsedutain(java.lang.String value){this.idsedutain=value;}
    public void setListaidudargselin(java.lang.String value){this.listaidudargselin=value;}
    public void setFlginclusipareriin(java.math.BigDecimal value){this.flginclusipareriin=value;}
    public void setFlginclallegatipiin(java.lang.String value){this.flginclallegatipiin=value;}
    public void setFlgintxpubblin(java.lang.String value){this.flgintxpubblin=value;}
    public void setListafilexdownloadout(java.lang.String value){this.listafilexdownloadout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgtrasmissioneattiin(java.lang.Integer value){this.flgtrasmissioneattiin=value;}
    public void setInfotrasmissionexmlin(java.lang.String value){this.infotrasmissionexmlin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    