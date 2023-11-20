/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCtrlCfComponicfBean")
public class DmpkCtrlCfComponicfBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CTRL_CF_COMPONICF";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String v_nome;
	private java.lang.String v_cognome;
	private java.lang.String v_data_di_nascita;
	private java.lang.String v_sesso;
	private java.lang.String v_istat_comune_di_nascita;
	private java.lang.String v_cod_stato_di_nascita;
	private java.lang.String v_codice_fiscale;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getV_nome(){return v_nome;}
    public java.lang.String getV_cognome(){return v_cognome;}
    public java.lang.String getV_data_di_nascita(){return v_data_di_nascita;}
    public java.lang.String getV_sesso(){return v_sesso;}
    public java.lang.String getV_istat_comune_di_nascita(){return v_istat_comune_di_nascita;}
    public java.lang.String getV_cod_stato_di_nascita(){return v_cod_stato_di_nascita;}
    public java.lang.String getV_codice_fiscale(){return v_codice_fiscale;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setV_nome(java.lang.String value){this.v_nome=value;}
    public void setV_cognome(java.lang.String value){this.v_cognome=value;}
    public void setV_data_di_nascita(java.lang.String value){this.v_data_di_nascita=value;}
    public void setV_sesso(java.lang.String value){this.v_sesso=value;}
    public void setV_istat_comune_di_nascita(java.lang.String value){this.v_istat_comune_di_nascita=value;}
    public void setV_cod_stato_di_nascita(java.lang.String value){this.v_cod_stato_di_nascita=value;}
    public void setV_codice_fiscale(java.lang.String value){this.v_codice_fiscale=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    