/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessesAggscaddopoattfasewfBean")
public class DmpkProcessesAggscaddopoattfasewfBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESSES_AGGSCADDOPOATTFASEWF";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idprocessin;
	private java.lang.String flgattfasein;
	private java.lang.String ciattfasein;
	private java.lang.String flginiziofinefasein;
	private java.util.Date tscomplatt_iffasein;
	private java.lang.String desesitoattfasein;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.lang.String getFlgattfasein(){return flgattfasein;}
    public java.lang.String getCiattfasein(){return ciattfasein;}
    public java.lang.String getFlginiziofinefasein(){return flginiziofinefasein;}
    public java.util.Date getTscomplatt_iffasein(){return tscomplatt_iffasein;}
    public java.lang.String getDesesitoattfasein(){return desesitoattfasein;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setFlgattfasein(java.lang.String value){this.flgattfasein=value;}
    public void setCiattfasein(java.lang.String value){this.ciattfasein=value;}
    public void setFlginiziofinefasein(java.lang.String value){this.flginiziofinefasein=value;}
    public void setTscomplatt_iffasein(java.util.Date value){this.tscomplatt_iffasein=value;}
    public void setDesesitoattfasein(java.lang.String value){this.desesitoattfasein=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    