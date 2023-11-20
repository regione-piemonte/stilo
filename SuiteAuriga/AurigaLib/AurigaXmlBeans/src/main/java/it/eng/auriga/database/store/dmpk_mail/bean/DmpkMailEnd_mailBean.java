/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkMailEnd_mailBean")
public class DmpkMailEnd_mailBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_MAIL_END_MAIL";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Object conn;
	public Integer getParametro_1() { return 1; }
    public java.lang.Object getConn(){return conn;}
    
    public void setConn(java.lang.Object value){this.conn=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
    public StoreType getType() { return StoreType.FUNCTION; }

}    