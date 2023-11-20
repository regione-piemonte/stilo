/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkTitolarioAggiornaclassifinibitaBean")
public class DmpkTitolarioAggiornaclassifinibitaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_TITOLARIO_AGGIORNACLASSIFINIBITA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idpianoclassifin;
	public Integer getParametro_1() { return 1; }
    public java.math.BigDecimal getIdpianoclassifin(){return idpianoclassifin;}
    
    public void setIdpianoclassifin(java.math.BigDecimal value){this.idpianoclassifin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
    public StoreType getType() { return StoreType.FUNCTION; }

}    