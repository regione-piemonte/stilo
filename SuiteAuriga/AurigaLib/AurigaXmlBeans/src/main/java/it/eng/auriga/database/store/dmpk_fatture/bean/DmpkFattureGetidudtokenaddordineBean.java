/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkFattureGetidudtokenaddordineBean")
public class DmpkFattureGetidudtokenaddordineBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_FATTURE_GETIDUDTOKENADDORDINE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String progrtrasmsdiin;
	private java.lang.String idordinein;
	private java.lang.String idtrasmittentein;
	private java.util.Date dataordinein;
	private java.math.BigDecimal idudordineout;
	private java.lang.String codidconnectiontokenout;
	private java.math.BigDecimal iddominioout;
	private java.lang.String nomefileorigout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getProgrtrasmsdiin(){return progrtrasmsdiin;}
    public java.lang.String getIdordinein(){return idordinein;}
    public java.lang.String getIdtrasmittentein(){return idtrasmittentein;}
    public java.util.Date getDataordinein(){return dataordinein;}
    public java.math.BigDecimal getIdudordineout(){return idudordineout;}
    public java.lang.String getCodidconnectiontokenout(){return codidconnectiontokenout;}
    public java.math.BigDecimal getIddominioout(){return iddominioout;}
    public java.lang.String getNomefileorigout(){return nomefileorigout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setProgrtrasmsdiin(java.lang.String value){this.progrtrasmsdiin=value;}
    public void setIdordinein(java.lang.String value){this.idordinein=value;}
    public void setIdtrasmittentein(java.lang.String value){this.idtrasmittentein=value;}
    public void setDataordinein(java.util.Date value){this.dataordinein=value;}
    public void setIdudordineout(java.math.BigDecimal value){this.idudordineout=value;}
    public void setCodidconnectiontokenout(java.lang.String value){this.codidconnectiontokenout=value;}
    public void setIddominioout(java.math.BigDecimal value){this.iddominioout=value;}
    public void setNomefileorigout(java.lang.String value){this.nomefileorigout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    