/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessesGetdurataeffprocBean")
public class DmpkProcessesGetdurataeffprocBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESSES_GETDURATAEFFPROC";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.util.Date tsavvioin;
	private java.util.Date dtdecorrenzain;
	private java.util.Date tsfinein;
	private java.util.Date dtchiusuraterminiin;
	private java.lang.String flgstatoin;
	private java.util.Date dtiniziosospinterrin;
	private java.util.Date dtfineinterrin;
	private java.math.BigDecimal ggduratasospconclusein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.util.Date getTsavvioin(){return tsavvioin;}
    public java.util.Date getDtdecorrenzain(){return dtdecorrenzain;}
    public java.util.Date getTsfinein(){return tsfinein;}
    public java.util.Date getDtchiusuraterminiin(){return dtchiusuraterminiin;}
    public java.lang.String getFlgstatoin(){return flgstatoin;}
    public java.util.Date getDtiniziosospinterrin(){return dtiniziosospinterrin;}
    public java.util.Date getDtfineinterrin(){return dtfineinterrin;}
    public java.math.BigDecimal getGgduratasospconclusein(){return ggduratasospconclusein;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setTsavvioin(java.util.Date value){this.tsavvioin=value;}
    public void setDtdecorrenzain(java.util.Date value){this.dtdecorrenzain=value;}
    public void setTsfinein(java.util.Date value){this.tsfinein=value;}
    public void setDtchiusuraterminiin(java.util.Date value){this.dtchiusuraterminiin=value;}
    public void setFlgstatoin(java.lang.String value){this.flgstatoin=value;}
    public void setDtiniziosospinterrin(java.util.Date value){this.dtiniziosospinterrin=value;}
    public void setDtfineinterrin(java.util.Date value){this.dtfineinterrin=value;}
    public void setGgduratasospconclusein(java.math.BigDecimal value){this.ggduratasospconclusein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    