/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCore2CheckpathxaddricorsivaBean")
public class DmpkCore2CheckpathxaddricorsivaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_2_CHECKPATHXADDRICORSIVA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idlibraryin;
	private java.lang.String nomelibraryin;
	private java.lang.String pathin;
	private java.math.BigDecimal idnodoout;
	private java.lang.String nodipathdacreareout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdlibraryin(){return idlibraryin;}
    public java.lang.String getNomelibraryin(){return nomelibraryin;}
    public java.lang.String getPathin(){return pathin;}
    public java.math.BigDecimal getIdnodoout(){return idnodoout;}
    public java.lang.String getNodipathdacreareout(){return nodipathdacreareout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdlibraryin(java.math.BigDecimal value){this.idlibraryin=value;}
    public void setNomelibraryin(java.lang.String value){this.nomelibraryin=value;}
    public void setPathin(java.lang.String value){this.pathin=value;}
    public void setIdnodoout(java.math.BigDecimal value){this.idnodoout=value;}
    public void setNodipathdacreareout(java.lang.String value){this.nodipathdacreareout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    