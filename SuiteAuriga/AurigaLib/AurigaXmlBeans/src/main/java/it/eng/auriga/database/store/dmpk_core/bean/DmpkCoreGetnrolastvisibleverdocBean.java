/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreGetnrolastvisibleverdocBean")
public class DmpkCoreGetnrolastvisibleverdocBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_GETNROLASTVISIBLEVERDOC";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal iddocin;
	private java.lang.Integer flgignoredellogin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIddocin(){return iddocin;}
    public java.lang.Integer getFlgignoredellogin(){return flgignoredellogin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIddocin(java.math.BigDecimal value){this.iddocin=value;}
    public void setFlgignoredellogin(java.lang.Integer value){this.flgignoredellogin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    