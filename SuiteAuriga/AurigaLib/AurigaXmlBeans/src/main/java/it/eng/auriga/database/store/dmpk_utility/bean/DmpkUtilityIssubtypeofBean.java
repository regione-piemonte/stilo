/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityIssubtypeofBean")
public class DmpkUtilityIssubtypeofBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_ISSUBTYPEOF";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String flgtypetoverifyin;
	private java.math.BigDecimal idtypetotestin;
	private java.math.BigDecimal idtypesubtypeofin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getFlgtypetoverifyin(){return flgtypetoverifyin;}
    public java.math.BigDecimal getIdtypetotestin(){return idtypetotestin;}
    public java.math.BigDecimal getIdtypesubtypeofin(){return idtypesubtypeofin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setFlgtypetoverifyin(java.lang.String value){this.flgtypetoverifyin=value;}
    public void setIdtypetotestin(java.math.BigDecimal value){this.idtypetotestin=value;}
    public void setIdtypesubtypeofin(java.math.BigDecimal value){this.idtypesubtypeofin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    