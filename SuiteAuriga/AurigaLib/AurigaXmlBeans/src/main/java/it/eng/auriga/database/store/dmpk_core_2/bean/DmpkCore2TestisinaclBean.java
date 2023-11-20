/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCore2TestisinaclBean")
public class DmpkCore2TestisinaclBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_2_TESTISINACL";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String objtyaclofin;
	private java.math.BigDecimal idobjaclofin;
	private java.lang.String flgtysoggtotestin;
	private java.math.BigDecimal idsoggtotestin;
	private java.math.BigDecimal ruolovsiduoin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getObjtyaclofin(){return objtyaclofin;}
    public java.math.BigDecimal getIdobjaclofin(){return idobjaclofin;}
    public java.lang.String getFlgtysoggtotestin(){return flgtysoggtotestin;}
    public java.math.BigDecimal getIdsoggtotestin(){return idsoggtotestin;}
    public java.math.BigDecimal getRuolovsiduoin(){return ruolovsiduoin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setObjtyaclofin(java.lang.String value){this.objtyaclofin=value;}
    public void setIdobjaclofin(java.math.BigDecimal value){this.idobjaclofin=value;}
    public void setFlgtysoggtotestin(java.lang.String value){this.flgtysoggtotestin=value;}
    public void setIdsoggtotestin(java.math.BigDecimal value){this.idsoggtotestin=value;}
    public void setRuolovsiduoin(java.math.BigDecimal value){this.ruolovsiduoin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    