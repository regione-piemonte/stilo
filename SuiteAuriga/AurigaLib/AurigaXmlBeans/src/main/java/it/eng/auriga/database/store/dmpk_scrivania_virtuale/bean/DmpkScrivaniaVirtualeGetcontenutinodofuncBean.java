/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkScrivaniaVirtualeGetcontenutinodofuncBean")
public class DmpkScrivaniaVirtualeGetcontenutinodofuncBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_SCRIVANIA_VIRTUALE_GETCONTENUTINODOFUNC";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iddominioin;
	private java.math.BigDecimal iduserin;
	private java.lang.String idutentemgoin;
	private java.lang.String cinodoin;
	private java.lang.Integer flgpresenticontenutiout;
	private java.lang.String nrocontenutiout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.lang.String getIdutentemgoin(){return idutentemgoin;}
    public java.lang.String getCinodoin(){return cinodoin;}
    public java.lang.Integer getFlgpresenticontenutiout(){return flgpresenticontenutiout;}
    public java.lang.String getNrocontenutiout(){return nrocontenutiout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setIdutentemgoin(java.lang.String value){this.idutentemgoin=value;}
    public void setCinodoin(java.lang.String value){this.cinodoin=value;}
    public void setFlgpresenticontenutiout(java.lang.Integer value){this.flgpresenticontenutiout=value;}
    public void setNrocontenutiout(java.lang.String value){this.nrocontenutiout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    