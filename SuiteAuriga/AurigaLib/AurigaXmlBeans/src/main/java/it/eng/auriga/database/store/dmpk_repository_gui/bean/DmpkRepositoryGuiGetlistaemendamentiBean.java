/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRepositoryGuiGetlistaemendamentiBean")
public class DmpkRepositoryGuiGetlistaemendamentiBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_REPOSITORY_GUI_GETLISTAEMENDAMENTI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idudin;
	private java.lang.Integer flginclsubin;
	private java.lang.String emendamentiout;
	private java.lang.Integer nroemendamentiout;
	private java.lang.Integer nrosubemendamentiout;
	private java.lang.Integer flgbloccoriordautoout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdudin(){return idudin;}
    public java.lang.Integer getFlginclsubin(){return flginclsubin;}
    public java.lang.String getEmendamentiout(){return emendamentiout;}
    public java.lang.Integer getNroemendamentiout(){return nroemendamentiout;}
    public java.lang.Integer getNrosubemendamentiout(){return nrosubemendamentiout;}
    public java.lang.Integer getFlgbloccoriordautoout(){return flgbloccoriordautoout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    public void setFlginclsubin(java.lang.Integer value){this.flginclsubin=value;}
    public void setEmendamentiout(java.lang.String value){this.emendamentiout=value;}
    public void setNroemendamentiout(java.lang.Integer value){this.nroemendamentiout=value;}
    public void setNrosubemendamentiout(java.lang.Integer value){this.nrosubemendamentiout=value;}
    public void setFlgbloccoriordautoout(java.lang.Integer value){this.flgbloccoriordautoout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    