/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreGetversioncontainerBean")
public class DmpkCoreGetversioncontainerBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_GETVERSIONCONTAINER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddocin;
	private java.math.BigDecimal nroprogrverio;
	private java.lang.String containertypeout;
	private java.lang.String containeraliasout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddocin(){return iddocin;}
    public java.math.BigDecimal getNroprogrverio(){return nroprogrverio;}
    public java.lang.String getContainertypeout(){return containertypeout;}
    public java.lang.String getContaineraliasout(){return containeraliasout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIddocin(java.math.BigDecimal value){this.iddocin=value;}
    public void setNroprogrverio(java.math.BigDecimal value){this.nroprogrverio=value;}
    public void setContainertypeout(java.lang.String value){this.containertypeout=value;}
    public void setContaineraliasout(java.lang.String value){this.containeraliasout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    