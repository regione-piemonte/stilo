/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGetcodfunzBean")
public class DmpkUtilityGetcodfunzBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETCODFUNZ";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codfunzp1in;
	private java.lang.String codfunzp2in;
	private java.lang.String codfunzp3in;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodfunzp1in(){return codfunzp1in;}
    public java.lang.String getCodfunzp2in(){return codfunzp2in;}
    public java.lang.String getCodfunzp3in(){return codfunzp3in;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setCodfunzp1in(java.lang.String value){this.codfunzp1in=value;}
    public void setCodfunzp2in(java.lang.String value){this.codfunzp2in=value;}
    public void setCodfunzp3in(java.lang.String value){this.codfunzp3in=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    