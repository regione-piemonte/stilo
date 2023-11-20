/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkMailWrite_textBean")
public class DmpkMailWrite_textBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_MAIL_WRITE_TEXT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Object conn;
	private java.lang.String message;
	public Integer getParametro_1() { return 1; }
    public java.lang.Object getConn(){return conn;}
    public java.lang.String getMessage(){return message;}
    
    public void setConn(java.lang.Object value){this.conn=value;}
    public void setMessage(java.lang.String value){this.message=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
    public StoreType getType() { return StoreType.FUNCTION; }

}    