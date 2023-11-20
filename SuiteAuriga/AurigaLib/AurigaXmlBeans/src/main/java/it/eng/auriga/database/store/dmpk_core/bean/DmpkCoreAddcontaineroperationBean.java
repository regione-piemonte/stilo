/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreAddcontaineroperationBean")
public class DmpkCoreAddcontaineroperationBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_ADDCONTAINEROPERATION";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String containertypein;
	private java.lang.String containeraliasin;
	private java.lang.String flgtipotargetin;
	private java.math.BigDecimal idtargetin;
	private java.lang.Integer nroprogrverin;
	private java.lang.String codoperationtyin;
	private java.math.BigDecimal flgsuccessin;
	private java.lang.String messaggioerrin;
	private java.lang.String notein;
	private java.math.BigDecimal flgoperdanonriprovarein;
	private java.math.BigDecimal idoperationout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getContainertypein(){return containertypein;}
    public java.lang.String getContaineraliasin(){return containeraliasin;}
    public java.lang.String getFlgtipotargetin(){return flgtipotargetin;}
    public java.math.BigDecimal getIdtargetin(){return idtargetin;}
    public java.lang.Integer getNroprogrverin(){return nroprogrverin;}
    public java.lang.String getCodoperationtyin(){return codoperationtyin;}
    public java.math.BigDecimal getFlgsuccessin(){return flgsuccessin;}
    public java.lang.String getMessaggioerrin(){return messaggioerrin;}
    public java.lang.String getNotein(){return notein;}
    public java.math.BigDecimal getFlgoperdanonriprovarein(){return flgoperdanonriprovarein;}
    public java.math.BigDecimal getIdoperationout(){return idoperationout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setContainertypein(java.lang.String value){this.containertypein=value;}
    public void setContaineraliasin(java.lang.String value){this.containeraliasin=value;}
    public void setFlgtipotargetin(java.lang.String value){this.flgtipotargetin=value;}
    public void setIdtargetin(java.math.BigDecimal value){this.idtargetin=value;}
    public void setNroprogrverin(java.lang.Integer value){this.nroprogrverin=value;}
    public void setCodoperationtyin(java.lang.String value){this.codoperationtyin=value;}
    public void setFlgsuccessin(java.math.BigDecimal value){this.flgsuccessin=value;}
    public void setMessaggioerrin(java.lang.String value){this.messaggioerrin=value;}
    public void setNotein(java.lang.String value){this.notein=value;}
    public void setFlgoperdanonriprovarein(java.math.BigDecimal value){this.flgoperdanonriprovarein=value;}
    public void setIdoperationout(java.math.BigDecimal value){this.idoperationout=value;}
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