/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkMailBegin_mail_in_sessionBean")
public class DmpkMailBegin_mail_in_sessionBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_MAIL_BEGIN_MAIL_IN_SESSION";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Object conn;
	private java.lang.String sender;
	private java.lang.String recipients;
	private java.lang.String subject;
	private java.lang.String mime_type;
	private java.lang.Integer priority;
	public Integer getParametro_1() { return 1; }
    public java.lang.Object getConn(){return conn;}
    public java.lang.String getSender(){return sender;}
    public java.lang.String getRecipients(){return recipients;}
    public java.lang.String getSubject(){return subject;}
    public java.lang.String getMime_type(){return mime_type;}
    public java.lang.Integer getPriority(){return priority;}
    
    public void setConn(java.lang.Object value){this.conn=value;}
    public void setSender(java.lang.String value){this.sender=value;}
    public void setRecipients(java.lang.String value){this.recipients=value;}
    public void setSubject(java.lang.String value){this.subject=value;}
    public void setMime_type(java.lang.String value){this.mime_type=value;}
    public void setPriority(java.lang.Integer value){this.priority=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
    public StoreType getType() { return StoreType.FUNCTION; }

}    