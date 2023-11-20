/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGetstatoannregnumudBean")
public class DmpkUtilityGetstatoannregnumudBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETSTATOANNREGNUMUD";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Object estremiregnumtabin;
	private java.lang.String codcategoriaregin;
	private java.lang.String siglaregin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.Object getEstremiregnumtabin(){return estremiregnumtabin;}
    public java.lang.String getCodcategoriaregin(){return codcategoriaregin;}
    public java.lang.String getSiglaregin(){return siglaregin;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setEstremiregnumtabin(java.lang.Object value){this.estremiregnumtabin=value;}
    public void setCodcategoriaregin(java.lang.String value){this.codcategoriaregin=value;}
    public void setSiglaregin(java.lang.String value){this.siglaregin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    