/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkPolicyTestabilvsudregistrataaBean")
public class DmpkPolicyTestabilvsudregistrataaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_POLICY_TESTABILVSUDREGISTRATAA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Integer flgtpdominioautin;
	private java.math.BigDecimal iddominioautin;
	private java.math.BigDecimal iduserin;
	private java.lang.String codcategoriain;
	private java.lang.String siglaregin;
	private java.lang.String accesstypein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.Integer getFlgtpdominioautin(){return flgtpdominioautin;}
    public java.math.BigDecimal getIddominioautin(){return iddominioautin;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.lang.String getCodcategoriain(){return codcategoriain;}
    public java.lang.String getSiglaregin(){return siglaregin;}
    public java.lang.String getAccesstypein(){return accesstypein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setFlgtpdominioautin(java.lang.Integer value){this.flgtpdominioautin=value;}
    public void setIddominioautin(java.math.BigDecimal value){this.iddominioautin=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setCodcategoriain(java.lang.String value){this.codcategoriain=value;}
    public void setSiglaregin(java.lang.String value){this.siglaregin=value;}
    public void setAccesstypein(java.lang.String value){this.accesstypein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    