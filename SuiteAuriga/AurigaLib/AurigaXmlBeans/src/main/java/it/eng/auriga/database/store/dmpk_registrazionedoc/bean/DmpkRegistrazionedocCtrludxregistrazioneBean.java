/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRegistrazionedocCtrludxregistrazioneBean")
public class DmpkRegistrazionedocCtrludxregistrazioneBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_REGISTRAZIONEDOC_CTRLUDXREGISTRAZIONE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idudin;
	private java.lang.String codcategoriaregin;
	private java.lang.String siglaregin;
	private java.lang.Integer annoregin;
	private java.lang.String cisistemaprotocolloin;
	private java.lang.String cisistemaaltreregnumin;
	private java.lang.String inputxmlxextapiout;
	private java.math.BigDecimal iduserregout;
	private java.lang.String usernameuserregout;
	private java.lang.String passworduserregout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdudin(){return idudin;}
    public java.lang.String getCodcategoriaregin(){return codcategoriaregin;}
    public java.lang.String getSiglaregin(){return siglaregin;}
    public java.lang.Integer getAnnoregin(){return annoregin;}
    public java.lang.String getCisistemaprotocolloin(){return cisistemaprotocolloin;}
    public java.lang.String getCisistemaaltreregnumin(){return cisistemaaltreregnumin;}
    public java.lang.String getInputxmlxextapiout(){return inputxmlxextapiout;}
    public java.math.BigDecimal getIduserregout(){return iduserregout;}
    public java.lang.String getUsernameuserregout(){return usernameuserregout;}
    public java.lang.String getPassworduserregout(){return passworduserregout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    public void setCodcategoriaregin(java.lang.String value){this.codcategoriaregin=value;}
    public void setSiglaregin(java.lang.String value){this.siglaregin=value;}
    public void setAnnoregin(java.lang.Integer value){this.annoregin=value;}
    public void setCisistemaprotocolloin(java.lang.String value){this.cisistemaprotocolloin=value;}
    public void setCisistemaaltreregnumin(java.lang.String value){this.cisistemaaltreregnumin=value;}
    public void setInputxmlxextapiout(java.lang.String value){this.inputxmlxextapiout=value;}
    public void setIduserregout(java.math.BigDecimal value){this.iduserregout=value;}
    public void setUsernameuserregout(java.lang.String value){this.usernameuserregout=value;}
    public void setPassworduserregout(java.lang.String value){this.passworduserregout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    