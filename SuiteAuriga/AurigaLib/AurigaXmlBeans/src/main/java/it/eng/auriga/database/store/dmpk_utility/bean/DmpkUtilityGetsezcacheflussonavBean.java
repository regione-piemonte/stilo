/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGetsezcacheflussonavBean")
public class DmpkUtilityGetsezcacheflussonavBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETSEZCACHEFLUSSONAV";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String ciflussonavin;
	private java.lang.String sezioneflussoin;
	private java.lang.String flgtipovarin;
	private java.lang.String variabiliout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCiflussonavin(){return ciflussonavin;}
    public java.lang.String getSezioneflussoin(){return sezioneflussoin;}
    public java.lang.String getFlgtipovarin(){return flgtipovarin;}
    public java.lang.String getVariabiliout(){return variabiliout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCiflussonavin(java.lang.String value){this.ciflussonavin=value;}
    public void setSezioneflussoin(java.lang.String value){this.sezioneflussoin=value;}
    public void setFlgtipovarin(java.lang.String value){this.flgtipovarin=value;}
    public void setVariabiliout(java.lang.String value){this.variabiliout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    