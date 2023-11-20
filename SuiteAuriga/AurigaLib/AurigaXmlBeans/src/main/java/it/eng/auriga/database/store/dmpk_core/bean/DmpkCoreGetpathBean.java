/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreGetpathBean")
public class DmpkCoreGetpathBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_GETPATH";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String objtypetoverifyin;
	private java.math.BigDecimal idobjtoverifyin;
	private java.math.BigDecimal viaidfolderin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getObjtypetoverifyin(){return objtypetoverifyin;}
    public java.math.BigDecimal getIdobjtoverifyin(){return idobjtoverifyin;}
    public java.math.BigDecimal getViaidfolderin(){return viaidfolderin;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setObjtypetoverifyin(java.lang.String value){this.objtypetoverifyin=value;}
    public void setIdobjtoverifyin(java.math.BigDecimal value){this.idobjtoverifyin=value;}
    public void setViaidfolderin(java.math.BigDecimal value){this.viaidfolderin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    