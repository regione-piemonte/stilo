/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkGestoreLoad_warningBean")
public class DmpkGestoreLoad_warningBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_GESTORE_LOAD_WARNING";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String warningmsgout;
	private java.lang.String warningurlprocediout;
	private java.math.BigDecimal idtyevtout;
	private java.lang.String nometyevtout;
	private java.lang.Integer flgevtdurativoout;
	private java.lang.Integer flgevtconesitoout;
	private java.lang.Integer evtduratamaxout;
	private java.math.BigDecimal idtydocevtout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.String getWarningurlprocediout(){return warningurlprocediout;}
    public java.math.BigDecimal getIdtyevtout(){return idtyevtout;}
    public java.lang.String getNometyevtout(){return nometyevtout;}
    public java.lang.Integer getFlgevtdurativoout(){return flgevtdurativoout;}
    public java.lang.Integer getFlgevtconesitoout(){return flgevtconesitoout;}
    public java.lang.Integer getEvtduratamaxout(){return evtduratamaxout;}
    public java.math.BigDecimal getIdtydocevtout(){return idtydocevtout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setWarningurlprocediout(java.lang.String value){this.warningurlprocediout=value;}
    public void setIdtyevtout(java.math.BigDecimal value){this.idtyevtout=value;}
    public void setNometyevtout(java.lang.String value){this.nometyevtout=value;}
    public void setFlgevtdurativoout(java.lang.Integer value){this.flgevtdurativoout=value;}
    public void setFlgevtconesitoout(java.lang.Integer value){this.flgevtconesitoout=value;}
    public void setEvtduratamaxout(java.lang.Integer value){this.evtduratamaxout=value;}
    public void setIdtydocevtout(java.math.BigDecimal value){this.idtydocevtout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    