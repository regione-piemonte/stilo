/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntCsGetitemdataxpdvBean")
public class DmpkIntCsGetitemdataxpdvBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_CS_GETITEMDATAXPDV";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String tipoitemin;
	private java.math.BigDecimal iditemin;
	private java.lang.String itemdataxmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getTipoitemin(){return tipoitemin;}
    public java.math.BigDecimal getIditemin(){return iditemin;}
    public java.lang.String getItemdataxmlout(){return itemdataxmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setTipoitemin(java.lang.String value){this.tipoitemin=value;}
    public void setIditemin(java.math.BigDecimal value){this.iditemin=value;}
    public void setItemdataxmlout(java.lang.String value){this.itemdataxmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    