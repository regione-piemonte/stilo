/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntPortaleCrmRichiestaagibilitaBean")
public class DmpkIntPortaleCrmRichiestaagibilitaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_PORTALE_CRM_RICHIESTAAGIBILITA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String datirichiestaxmlin;
	private java.lang.String fileallegatirichiestain;
	private java.lang.String datirispostaxmlout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getDatirichiestaxmlin(){return datirichiestaxmlin;}
    public java.lang.String getFileallegatirichiestain(){return fileallegatirichiestain;}
    public java.lang.String getDatirispostaxmlout(){return datirispostaxmlout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setDatirichiestaxmlin(java.lang.String value){this.datirichiestaxmlin=value;}
    public void setFileallegatirichiestain(java.lang.String value){this.fileallegatirichiestain=value;}
    public void setDatirispostaxmlout(java.lang.String value){this.datirispostaxmlout=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    