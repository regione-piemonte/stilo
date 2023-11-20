/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkEfactGetlivellistrutturaorgfromudBean")
public class DmpkEfactGetlivellistrutturaorgfromudBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_EFACT_GETLIVELLISTRUTTURAORGFROMUD";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idudin;
	private java.lang.String livellisalesorganizationout;
	private java.lang.String livellidistributionchannelout;
	private java.lang.String centrodicostoout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdudin(){return idudin;}
    public java.lang.String getLivellisalesorganizationout(){return livellisalesorganizationout;}
    public java.lang.String getLivellidistributionchannelout(){return livellidistributionchannelout;}
    public java.lang.String getCentrodicostoout(){return centrodicostoout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    public void setLivellisalesorganizationout(java.lang.String value){this.livellisalesorganizationout=value;}
    public void setLivellidistributionchannelout(java.lang.String value){this.livellidistributionchannelout=value;}
    public void setCentrodicostoout(java.lang.String value){this.centrodicostoout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    