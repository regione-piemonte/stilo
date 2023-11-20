/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessTypesCtrlstartendscadBean")
public class DmpkProcessTypesCtrlstartendscadBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESS_TYPES_CTRLSTARTENDSCAD";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal idprocesstypein;
	private java.lang.String flgscadpuntualeperiodoin;
	private java.lang.String flgstartendin;
	private java.lang.String flgtipoin;
	private java.lang.String valorein;
	private java.lang.String dettaglio1in;
	private java.lang.String dettaglio2in;
	private java.lang.String listaesitiin;
	private java.lang.String desstartendout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIdprocesstypein(){return idprocesstypein;}
    public java.lang.String getFlgscadpuntualeperiodoin(){return flgscadpuntualeperiodoin;}
    public java.lang.String getFlgstartendin(){return flgstartendin;}
    public java.lang.String getFlgtipoin(){return flgtipoin;}
    public java.lang.String getValorein(){return valorein;}
    public java.lang.String getDettaglio1in(){return dettaglio1in;}
    public java.lang.String getDettaglio2in(){return dettaglio2in;}
    public java.lang.String getListaesitiin(){return listaesitiin;}
    public java.lang.String getDesstartendout(){return desstartendout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIdprocesstypein(java.math.BigDecimal value){this.idprocesstypein=value;}
    public void setFlgscadpuntualeperiodoin(java.lang.String value){this.flgscadpuntualeperiodoin=value;}
    public void setFlgstartendin(java.lang.String value){this.flgstartendin=value;}
    public void setFlgtipoin(java.lang.String value){this.flgtipoin=value;}
    public void setValorein(java.lang.String value){this.valorein=value;}
    public void setDettaglio1in(java.lang.String value){this.dettaglio1in=value;}
    public void setDettaglio2in(java.lang.String value){this.dettaglio2in=value;}
    public void setListaesitiin(java.lang.String value){this.listaesitiin=value;}
    public void setDesstartendout(java.lang.String value){this.desstartendout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    