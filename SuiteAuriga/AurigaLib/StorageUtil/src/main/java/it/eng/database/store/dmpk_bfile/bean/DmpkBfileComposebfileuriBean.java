/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.database.store.bean.StorageStoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkBfileComposebfileuriBean")
public class DmpkBfileComposebfileuriBean extends StorageStoreBean implements Serializable{

	private static final String storeName = "DMPK_BFILE_COMPOSEBFILEURI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String rootdiraliasin;
	private java.lang.String fspathfilenamein;
	private java.lang.String fileuriout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getRootdiraliasin(){return rootdiraliasin;}
    public java.lang.String getFspathfilenamein(){return fspathfilenamein;}
    public java.lang.String getFileuriout(){return fileuriout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setRootdiraliasin(java.lang.String value){this.rootdiraliasin=value;}
    public void setFspathfilenamein(java.lang.String value){this.fspathfilenamein=value;}
    public void setFileuriout(java.lang.String value){this.fileuriout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    