/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreGetsegnaturafolderBean")
public class DmpkCoreGetsegnaturafolderBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_GETSEGNATURAFOLDER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.lang.String orderbyvaluein;
	private java.math.BigDecimal idclassificazionein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.lang.String getOrderbyvaluein(){return orderbyvaluein;}
    public java.math.BigDecimal getIdclassificazionein(){return idclassificazionein;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setOrderbyvaluein(java.lang.String value){this.orderbyvaluein=value;}
    public void setIdclassificazionein(java.math.BigDecimal value){this.idclassificazionein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    