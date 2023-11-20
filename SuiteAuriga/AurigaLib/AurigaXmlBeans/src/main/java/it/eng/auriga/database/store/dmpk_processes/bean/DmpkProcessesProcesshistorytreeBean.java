/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessesProcesshistorytreeBean")
public class DmpkProcessesProcesshistorytreeBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESSES_PROCESSHISTORYTREE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idprocessin;
	private java.lang.String codtipoeventoin;
	private java.lang.String deseventoin;
	private java.lang.String desuserin;
	private java.lang.String desesitoin;
	private java.lang.String dettaglieventoin;
	private java.lang.String idselectednodeio;
	private java.lang.Integer flgexplselnodein;
	private java.lang.String flgnextprevnodein;
	private java.lang.String treexmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.lang.String getCodtipoeventoin(){return codtipoeventoin;}
    public java.lang.String getDeseventoin(){return deseventoin;}
    public java.lang.String getDesuserin(){return desuserin;}
    public java.lang.String getDesesitoin(){return desesitoin;}
    public java.lang.String getDettaglieventoin(){return dettaglieventoin;}
    public java.lang.String getIdselectednodeio(){return idselectednodeio;}
    public java.lang.Integer getFlgexplselnodein(){return flgexplselnodein;}
    public java.lang.String getFlgnextprevnodein(){return flgnextprevnodein;}
    public java.lang.String getTreexmlout(){return treexmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setCodtipoeventoin(java.lang.String value){this.codtipoeventoin=value;}
    public void setDeseventoin(java.lang.String value){this.deseventoin=value;}
    public void setDesuserin(java.lang.String value){this.desuserin=value;}
    public void setDesesitoin(java.lang.String value){this.desesitoin=value;}
    public void setDettaglieventoin(java.lang.String value){this.dettaglieventoin=value;}
    public void setIdselectednodeio(java.lang.String value){this.idselectednodeio=value;}
    public void setFlgexplselnodein(java.lang.Integer value){this.flgexplselnodein=value;}
    public void setFlgnextprevnodein(java.lang.String value){this.flgnextprevnodein=value;}
    public void setTreexmlout(java.lang.String value){this.treexmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    