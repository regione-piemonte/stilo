/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.database.store.bean.StorageStoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkBlobFinddocelettronicoBean")
public class DmpkBlobFinddocelettronicoBean extends StorageStoreBean implements Serializable{

	private static final String storeName = "DMPK_BLOB_FINDDOCELETTRONICO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String idstoragein;
	private java.lang.String iddocelettronicoio;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getIdstoragein(){return idstoragein;}
    public java.lang.String getIddocelettronicoio(){return iddocelettronicoio;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdstoragein(java.lang.String value){this.idstoragein=value;}
    public void setIddocelettronicoio(java.lang.String value){this.iddocelettronicoio=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    