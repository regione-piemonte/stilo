/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessesAggscaddopoopersudocBean")
public class DmpkProcessesAggscaddopoopersudocBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESSES_AGGSCADDOPOOPERSUDOC";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String flgoperazionesudocin;
	private java.math.BigDecimal idprocessin;
	private java.lang.String codtipoeventoin;
	private java.lang.Object udrecoldin;
	private java.lang.Object docrecoldin;
	private java.lang.Object udrecin;
	private java.lang.Object docrecin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getFlgoperazionesudocin(){return flgoperazionesudocin;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.lang.String getCodtipoeventoin(){return codtipoeventoin;}
    public java.lang.Object getUdrecoldin(){return udrecoldin;}
    public java.lang.Object getDocrecoldin(){return docrecoldin;}
    public java.lang.Object getUdrecin(){return udrecin;}
    public java.lang.Object getDocrecin(){return docrecin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setFlgoperazionesudocin(java.lang.String value){this.flgoperazionesudocin=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setCodtipoeventoin(java.lang.String value){this.codtipoeventoin=value;}
    public void setUdrecoldin(java.lang.Object value){this.udrecoldin=value;}
    public void setDocrecoldin(java.lang.Object value){this.docrecoldin=value;}
    public void setUdrecin(java.lang.Object value){this.udrecin=value;}
    public void setDocrecin(java.lang.Object value){this.docrecin=value;}
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