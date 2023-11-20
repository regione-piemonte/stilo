/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAmmTraspGetdatireportBean")
public class DmpkAmmTraspGetdatireportBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_AMM_TRASP_GETDATIREPORT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String tiporeportin;
	private java.lang.String parametrireportin;
	private java.math.BigDecimal nrocolonneout;
	private java.math.BigDecimal nrorecordout;
	private java.lang.String intestazionireportout;
	private java.lang.String valorireportout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getTiporeportin(){return tiporeportin;}
    public java.lang.String getParametrireportin(){return parametrireportin;}
    public java.math.BigDecimal getNrocolonneout(){return nrocolonneout;}
    public java.math.BigDecimal getNrorecordout(){return nrorecordout;}
    public java.lang.String getIntestazionireportout(){return intestazionireportout;}
    public java.lang.String getValorireportout(){return valorireportout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setTiporeportin(java.lang.String value){this.tiporeportin=value;}
    public void setParametrireportin(java.lang.String value){this.parametrireportin=value;}
    public void setNrocolonneout(java.math.BigDecimal value){this.nrocolonneout=value;}
    public void setNrorecordout(java.math.BigDecimal value){this.nrorecordout=value;}
    public void setIntestazionireportout(java.lang.String value){this.intestazionireportout=value;}
    public void setValorireportout(java.lang.String value){this.valorireportout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    