/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessTypesAddattore_proctyBean")
public class DmpkProcessTypesAddattore_proctyBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESS_TYPES_ADDATTORE_PROCTY";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String attorein;
	private java.lang.String processtypesxmlin;
	private java.lang.String flgintestin;
	private java.lang.Integer flguserin;
	private java.lang.Integer flguoin;
	private java.lang.Integer flgscrivvirtin;
	private java.lang.Integer flggruppoin;
	private java.lang.Integer flgruoloin;
	private java.math.BigDecimal nroposizionein;
	private java.lang.Integer flgobbligatorioin;
	private java.lang.Integer flgprincipalein;
	private java.math.BigDecimal maxnrooccorrenzein;
	private java.lang.String dtiniziovldin;
	private java.lang.String dtfinevldin;
	private java.lang.Integer flglockedin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getAttorein(){return attorein;}
    public java.lang.String getProcesstypesxmlin(){return processtypesxmlin;}
    public java.lang.String getFlgintestin(){return flgintestin;}
    public java.lang.Integer getFlguserin(){return flguserin;}
    public java.lang.Integer getFlguoin(){return flguoin;}
    public java.lang.Integer getFlgscrivvirtin(){return flgscrivvirtin;}
    public java.lang.Integer getFlggruppoin(){return flggruppoin;}
    public java.lang.Integer getFlgruoloin(){return flgruoloin;}
    public java.math.BigDecimal getNroposizionein(){return nroposizionein;}
    public java.lang.Integer getFlgobbligatorioin(){return flgobbligatorioin;}
    public java.lang.Integer getFlgprincipalein(){return flgprincipalein;}
    public java.math.BigDecimal getMaxnrooccorrenzein(){return maxnrooccorrenzein;}
    public java.lang.String getDtiniziovldin(){return dtiniziovldin;}
    public java.lang.String getDtfinevldin(){return dtfinevldin;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setAttorein(java.lang.String value){this.attorein=value;}
    public void setProcesstypesxmlin(java.lang.String value){this.processtypesxmlin=value;}
    public void setFlgintestin(java.lang.String value){this.flgintestin=value;}
    public void setFlguserin(java.lang.Integer value){this.flguserin=value;}
    public void setFlguoin(java.lang.Integer value){this.flguoin=value;}
    public void setFlgscrivvirtin(java.lang.Integer value){this.flgscrivvirtin=value;}
    public void setFlggruppoin(java.lang.Integer value){this.flggruppoin=value;}
    public void setFlgruoloin(java.lang.Integer value){this.flgruoloin=value;}
    public void setNroposizionein(java.math.BigDecimal value){this.nroposizionein=value;}
    public void setFlgobbligatorioin(java.lang.Integer value){this.flgobbligatorioin=value;}
    public void setFlgprincipalein(java.lang.Integer value){this.flgprincipalein=value;}
    public void setMaxnrooccorrenzein(java.math.BigDecimal value){this.maxnrooccorrenzein=value;}
    public void setDtiniziovldin(java.lang.String value){this.dtiniziovldin=value;}
    public void setDtfinevldin(java.lang.String value){this.dtfinevldin=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
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