/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCore2FindworkspaceBean")
public class DmpkCore2FindworkspaceBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_2_FINDWORKSPACE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.lang.String nomeworkspacein;
	private java.math.BigDecimal idworkspacein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.lang.String getNomeworkspacein(){return nomeworkspacein;}
    public java.math.BigDecimal getIdworkspacein(){return idworkspacein;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setNomeworkspacein(java.lang.String value){this.nomeworkspacein=value;}
    public void setIdworkspacein(java.math.BigDecimal value){this.idworkspacein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    