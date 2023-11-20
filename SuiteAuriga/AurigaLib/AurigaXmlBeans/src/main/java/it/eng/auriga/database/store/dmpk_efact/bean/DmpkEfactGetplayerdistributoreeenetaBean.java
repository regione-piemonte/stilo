/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkEfactGetplayerdistributoreeenetaBean")
public class DmpkEfactGetplayerdistributoreeenetaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_EFACT_GETPLAYERDISTRIBUTOREEENETA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codice_distributore_in;
	private java.lang.String codiceplayernetaout;
	private java.lang.String ragsocialeplayernetaout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodice_distributore_in(){return codice_distributore_in;}
    public java.lang.String getCodiceplayernetaout(){return codiceplayernetaout;}
    public java.lang.String getRagsocialeplayernetaout(){return ragsocialeplayernetaout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodice_distributore_in(java.lang.String value){this.codice_distributore_in=value;}
    public void setCodiceplayernetaout(java.lang.String value){this.codiceplayernetaout=value;}
    public void setRagsocialeplayernetaout(java.lang.String value){this.ragsocialeplayernetaout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    