/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWfGetlistaattflussoprocBean")
public class DmpkWfGetlistaattflussoprocBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WF_GETLISTAATTFLUSSOPROC";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idprocessin;
	private java.lang.String cidgruppoattin;
	private java.math.BigDecimal idtipoeventoappin;
	private java.lang.String listaxmlout;
	private java.lang.String datiprocxattout;
	private java.lang.String desfasecorrenteout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgnodatiprocin;
	private java.math.BigDecimal idprocesstypein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.lang.String getCidgruppoattin(){return cidgruppoattin;}
    public java.math.BigDecimal getIdtipoeventoappin(){return idtipoeventoappin;}
    public java.lang.String getListaxmlout(){return listaxmlout;}
    public java.lang.String getDatiprocxattout(){return datiprocxattout;}
    public java.lang.String getDesfasecorrenteout(){return desfasecorrenteout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgnodatiprocin(){return flgnodatiprocin;}
    public java.math.BigDecimal getIdprocesstypein(){return idprocesstypein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setCidgruppoattin(java.lang.String value){this.cidgruppoattin=value;}
    public void setIdtipoeventoappin(java.math.BigDecimal value){this.idtipoeventoappin=value;}
    public void setListaxmlout(java.lang.String value){this.listaxmlout=value;}
    public void setDatiprocxattout(java.lang.String value){this.datiprocxattout=value;}
    public void setDesfasecorrenteout(java.lang.String value){this.desfasecorrenteout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgnodatiprocin(java.lang.Integer value){this.flgnodatiprocin=value;}
    public void setIdprocesstypein(java.math.BigDecimal value){this.idprocesstypein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    