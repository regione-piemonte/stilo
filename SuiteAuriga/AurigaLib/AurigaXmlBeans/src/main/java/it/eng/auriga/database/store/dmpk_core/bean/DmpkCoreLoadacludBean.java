/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreLoadacludBean")
public class DmpkCoreLoadacludBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_LOADACLUD";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idudin;
	private java.lang.Integer flgereditarietapossibileout;
	private java.lang.Integer flgereditarietaconsentitaout;
	private java.lang.String flgtipooggereditadaout;
	private java.math.BigDecimal idoggereditadaout;
	private java.lang.String desoggereditadaout;
	private java.lang.String flgtipooggpossibileereddaout;
	private java.math.BigDecimal idoggpossibileereddaout;
	private java.lang.String desoggpossibileereddaout;
	private java.lang.String aclxmlout;
	private java.lang.String listadocudxmlout;
	private java.lang.Integer flgabilupdout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdudin(){return idudin;}
    public java.lang.Integer getFlgereditarietapossibileout(){return flgereditarietapossibileout;}
    public java.lang.Integer getFlgereditarietaconsentitaout(){return flgereditarietaconsentitaout;}
    public java.lang.String getFlgtipooggereditadaout(){return flgtipooggereditadaout;}
    public java.math.BigDecimal getIdoggereditadaout(){return idoggereditadaout;}
    public java.lang.String getDesoggereditadaout(){return desoggereditadaout;}
    public java.lang.String getFlgtipooggpossibileereddaout(){return flgtipooggpossibileereddaout;}
    public java.math.BigDecimal getIdoggpossibileereddaout(){return idoggpossibileereddaout;}
    public java.lang.String getDesoggpossibileereddaout(){return desoggpossibileereddaout;}
    public java.lang.String getAclxmlout(){return aclxmlout;}
    public java.lang.String getListadocudxmlout(){return listadocudxmlout;}
    public java.lang.Integer getFlgabilupdout(){return flgabilupdout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    public void setFlgereditarietapossibileout(java.lang.Integer value){this.flgereditarietapossibileout=value;}
    public void setFlgereditarietaconsentitaout(java.lang.Integer value){this.flgereditarietaconsentitaout=value;}
    public void setFlgtipooggereditadaout(java.lang.String value){this.flgtipooggereditadaout=value;}
    public void setIdoggereditadaout(java.math.BigDecimal value){this.idoggereditadaout=value;}
    public void setDesoggereditadaout(java.lang.String value){this.desoggereditadaout=value;}
    public void setFlgtipooggpossibileereddaout(java.lang.String value){this.flgtipooggpossibileereddaout=value;}
    public void setIdoggpossibileereddaout(java.math.BigDecimal value){this.idoggpossibileereddaout=value;}
    public void setDesoggpossibileereddaout(java.lang.String value){this.desoggpossibileereddaout=value;}
    public void setAclxmlout(java.lang.String value){this.aclxmlout=value;}
    public void setListadocudxmlout(java.lang.String value){this.listadocudxmlout=value;}
    public void setFlgabilupdout(java.lang.Integer value){this.flgabilupdout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    