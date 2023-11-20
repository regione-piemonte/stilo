/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCore2VerifyforlockBean")
public class DmpkCore2VerifyforlockBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_2_VERIFYFORLOCK";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String objtypetoverifyin;
	private java.math.BigDecimal idobjtoverifyin;
	private java.math.BigDecimal iduserlockout;
	private java.util.Date tslockout;
	private java.lang.String objtypelockonout;
	private java.math.BigDecimal idobjlockout;
	private java.lang.String nomeobjlockout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String codidconnectiontokenin;
	private java.lang.String desuserlockout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getObjtypetoverifyin(){return objtypetoverifyin;}
    public java.math.BigDecimal getIdobjtoverifyin(){return idobjtoverifyin;}
    public java.math.BigDecimal getIduserlockout(){return iduserlockout;}
    public java.util.Date getTslockout(){return tslockout;}
    public java.lang.String getObjtypelockonout(){return objtypelockonout;}
    public java.math.BigDecimal getIdobjlockout(){return idobjlockout;}
    public java.lang.String getNomeobjlockout(){return nomeobjlockout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getDesuserlockout(){return desuserlockout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setObjtypetoverifyin(java.lang.String value){this.objtypetoverifyin=value;}
    public void setIdobjtoverifyin(java.math.BigDecimal value){this.idobjtoverifyin=value;}
    public void setIduserlockout(java.math.BigDecimal value){this.iduserlockout=value;}
    public void setTslockout(java.util.Date value){this.tslockout=value;}
    public void setObjtypelockonout(java.lang.String value){this.objtypelockonout=value;}
    public void setIdobjlockout(java.math.BigDecimal value){this.idobjlockout=value;}
    public void setNomeobjlockout(java.lang.String value){this.nomeobjlockout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setDesuserlockout(java.lang.String value){this.desuserlockout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    