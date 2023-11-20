/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGetestremiregnumudBean")
public class DmpkUtilityGetestremiregnumudBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETESTREMIREGNUMUD";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idudin;
	private java.lang.String codcategoriaregio;
	private java.lang.String siglaregio;
	private java.lang.Integer annoregout;
	private java.math.BigDecimal numregout;
	private java.util.Date tsregout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdudin(){return idudin;}
    public java.lang.String getCodcategoriaregio(){return codcategoriaregio;}
    public java.lang.String getSiglaregio(){return siglaregio;}
    public java.lang.Integer getAnnoregout(){return annoregout;}
    public java.math.BigDecimal getNumregout(){return numregout;}
    public java.util.Date getTsregout(){return tsregout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    public void setCodcategoriaregio(java.lang.String value){this.codcategoriaregio=value;}
    public void setSiglaregio(java.lang.String value){this.siglaregio=value;}
    public void setAnnoregout(java.lang.Integer value){this.annoregout=value;}
    public void setNumregout(java.math.BigDecimal value){this.numregout=value;}
    public void setTsregout(java.util.Date value){this.tsregout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    