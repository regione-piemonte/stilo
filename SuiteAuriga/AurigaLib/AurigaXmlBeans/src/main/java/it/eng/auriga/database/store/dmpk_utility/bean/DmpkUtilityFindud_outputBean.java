/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFindud_outputBean")
public class DmpkUtilityFindud_outputBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDUD_OUTPUT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String codcategoriaregin;
	private java.lang.String siglaregin;
	private java.lang.Integer annoregin;
	private java.math.BigDecimal numregin;
	private java.lang.String subnumregin;
	private java.math.BigDecimal idudout;
	private java.math.BigDecimal iddocprimarioout;
	private java.lang.String datiudout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getCodcategoriaregin(){return codcategoriaregin;}
    public java.lang.String getSiglaregin(){return siglaregin;}
    public java.lang.Integer getAnnoregin(){return annoregin;}
    public java.math.BigDecimal getNumregin(){return numregin;}
    public java.lang.String getSubnumregin(){return subnumregin;}
    public java.math.BigDecimal getIdudout(){return idudout;}
    public java.math.BigDecimal getIddocprimarioout(){return iddocprimarioout;}
    public java.lang.String getDatiudout(){return datiudout;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setCodcategoriaregin(java.lang.String value){this.codcategoriaregin=value;}
    public void setSiglaregin(java.lang.String value){this.siglaregin=value;}
    public void setAnnoregin(java.lang.Integer value){this.annoregin=value;}
    public void setNumregin(java.math.BigDecimal value){this.numregin=value;}
    public void setSubnumregin(java.lang.String value){this.subnumregin=value;}
    public void setIdudout(java.math.BigDecimal value){this.idudout=value;}
    public void setIddocprimarioout(java.math.BigDecimal value){this.iddocprimarioout=value;}
    public void setDatiudout(java.lang.String value){this.datiudout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    