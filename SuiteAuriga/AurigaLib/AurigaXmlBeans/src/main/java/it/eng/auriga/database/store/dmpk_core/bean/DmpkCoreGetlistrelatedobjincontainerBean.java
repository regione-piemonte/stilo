/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreGetlistrelatedobjincontainerBean")
public class DmpkCoreGetlistrelatedobjincontainerBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_GETLISTRELATEDOBJINCONTAINER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String containertypein;
	private java.lang.String containeraliasin;
	private java.lang.String flgobjtypein;
	private java.math.BigDecimal idobjin;
	private java.lang.String relatedobjxmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getContainertypein(){return containertypein;}
    public java.lang.String getContaineraliasin(){return containeraliasin;}
    public java.lang.String getFlgobjtypein(){return flgobjtypein;}
    public java.math.BigDecimal getIdobjin(){return idobjin;}
    public java.lang.String getRelatedobjxmlout(){return relatedobjxmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setContainertypein(java.lang.String value){this.containertypein=value;}
    public void setContaineraliasin(java.lang.String value){this.containeraliasin=value;}
    public void setFlgobjtypein(java.lang.String value){this.flgobjtypein=value;}
    public void setIdobjin(java.math.BigDecimal value){this.idobjin=value;}
    public void setRelatedobjxmlout(java.lang.String value){this.relatedobjxmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    