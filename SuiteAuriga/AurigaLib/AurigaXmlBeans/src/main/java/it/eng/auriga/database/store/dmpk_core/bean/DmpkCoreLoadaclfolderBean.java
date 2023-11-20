/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreLoadaclfolderBean")
public class DmpkCoreLoadaclfolderBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_LOADACLFOLDER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idfolderin;
	private java.lang.Integer flgereditarietapossibileout;
	private java.lang.Integer flgereditarietaconsentitaout;
	private java.math.BigDecimal idfolderereditadaout;
	private java.lang.String desfolderereditadaout;
	private java.math.BigDecimal idfolderpossibileereddaout;
	private java.lang.String desfolderpossibileereddaout;
	private java.lang.Integer flgpropagazioneconsentitaout;
	private java.lang.String aclxmlout;
	private java.lang.Integer flgabilupdout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdfolderin(){return idfolderin;}
    public java.lang.Integer getFlgereditarietapossibileout(){return flgereditarietapossibileout;}
    public java.lang.Integer getFlgereditarietaconsentitaout(){return flgereditarietaconsentitaout;}
    public java.math.BigDecimal getIdfolderereditadaout(){return idfolderereditadaout;}
    public java.lang.String getDesfolderereditadaout(){return desfolderereditadaout;}
    public java.math.BigDecimal getIdfolderpossibileereddaout(){return idfolderpossibileereddaout;}
    public java.lang.String getDesfolderpossibileereddaout(){return desfolderpossibileereddaout;}
    public java.lang.Integer getFlgpropagazioneconsentitaout(){return flgpropagazioneconsentitaout;}
    public java.lang.String getAclxmlout(){return aclxmlout;}
    public java.lang.Integer getFlgabilupdout(){return flgabilupdout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdfolderin(java.math.BigDecimal value){this.idfolderin=value;}
    public void setFlgereditarietapossibileout(java.lang.Integer value){this.flgereditarietapossibileout=value;}
    public void setFlgereditarietaconsentitaout(java.lang.Integer value){this.flgereditarietaconsentitaout=value;}
    public void setIdfolderereditadaout(java.math.BigDecimal value){this.idfolderereditadaout=value;}
    public void setDesfolderereditadaout(java.lang.String value){this.desfolderereditadaout=value;}
    public void setIdfolderpossibileereddaout(java.math.BigDecimal value){this.idfolderpossibileereddaout=value;}
    public void setDesfolderpossibileereddaout(java.lang.String value){this.desfolderpossibileereddaout=value;}
    public void setFlgpropagazioneconsentitaout(java.lang.Integer value){this.flgpropagazioneconsentitaout=value;}
    public void setAclxmlout(java.lang.String value){this.aclxmlout=value;}
    public void setFlgabilupdout(java.lang.Integer value){this.flgabilupdout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    