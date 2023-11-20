/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreGetparamforstoreinsharepointBean")
public class DmpkCoreGetparamforstoreinsharepointBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_GETPARAMFORSTOREINSHAREPOINT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddocin;
	private java.lang.String displayfilenamein;
	private java.lang.String mimetypein;
	private java.lang.String parametrixmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddocin(){return iddocin;}
    public java.lang.String getDisplayfilenamein(){return displayfilenamein;}
    public java.lang.String getMimetypein(){return mimetypein;}
    public java.lang.String getParametrixmlout(){return parametrixmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIddocin(java.math.BigDecimal value){this.iddocin=value;}
    public void setDisplayfilenamein(java.lang.String value){this.displayfilenamein=value;}
    public void setMimetypein(java.lang.String value){this.mimetypein=value;}
    public void setParametrixmlout(java.lang.String value){this.parametrixmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    