/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRepositoryGuiLoadsoggestdocBean")
public class DmpkRepositoryGuiLoadsoggestdocBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_REPOSITORY_GUI_LOADSOGGESTDOC";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iddocin;
	private java.lang.String naturareldocvssoggin;
	private java.lang.String soggettiesterniout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgescludiasssenzainvioin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIddocin(){return iddocin;}
    public java.lang.String getNaturareldocvssoggin(){return naturareldocvssoggin;}
    public java.lang.String getSoggettiesterniout(){return soggettiesterniout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgescludiasssenzainvioin(){return flgescludiasssenzainvioin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIddocin(java.math.BigDecimal value){this.iddocin=value;}
    public void setNaturareldocvssoggin(java.lang.String value){this.naturareldocvssoggin=value;}
    public void setSoggettiesterniout(java.lang.String value){this.soggettiesterniout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgescludiasssenzainvioin(java.lang.Integer value){this.flgescludiasssenzainvioin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    