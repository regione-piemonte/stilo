/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreGetcontainerfornewfolderBean")
public class DmpkCoreGetcontainerfornewfolderBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_GETCONTAINERFORNEWFOLDER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idfolderin;
	private java.lang.String containertypeio;
	private java.lang.String containeraliasio;
	private java.lang.String containertypetologout;
	private java.lang.String containeraliastologout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdfolderin(){return idfolderin;}
    public java.lang.String getContainertypeio(){return containertypeio;}
    public java.lang.String getContaineraliasio(){return containeraliasio;}
    public java.lang.String getContainertypetologout(){return containertypetologout;}
    public java.lang.String getContaineraliastologout(){return containeraliastologout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdfolderin(java.math.BigDecimal value){this.idfolderin=value;}
    public void setContainertypeio(java.lang.String value){this.containertypeio=value;}
    public void setContaineraliasio(java.lang.String value){this.containeraliasio=value;}
    public void setContainertypetologout(java.lang.String value){this.containertypetologout=value;}
    public void setContaineraliastologout(java.lang.String value){this.containeraliastologout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    