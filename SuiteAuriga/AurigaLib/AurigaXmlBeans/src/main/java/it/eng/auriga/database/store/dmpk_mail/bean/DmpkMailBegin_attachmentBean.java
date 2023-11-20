/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkMailBegin_attachmentBean")
public class DmpkMailBegin_attachmentBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_MAIL_BEGIN_ATTACHMENT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Object conn;
	private java.lang.String mime_type;
	private java.lang.Boolean inline;
	private java.lang.String filename;
	private java.lang.String transfer_enc;
	public Integer getParametro_1() { return 1; }
    public java.lang.Object getConn(){return conn;}
    public java.lang.String getMime_type(){return mime_type;}
    public java.lang.Boolean getInline(){return inline;}
    public java.lang.String getFilename(){return filename;}
    public java.lang.String getTransfer_enc(){return transfer_enc;}
    
    public void setConn(java.lang.Object value){this.conn=value;}
    public void setMime_type(java.lang.String value){this.mime_type=value;}
    public void setInline(java.lang.Boolean value){this.inline=value;}
    public void setFilename(java.lang.String value){this.filename=value;}
    public void setTransfer_enc(java.lang.String value){this.transfer_enc=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
    public StoreType getType() { return StoreType.FUNCTION; }

}    