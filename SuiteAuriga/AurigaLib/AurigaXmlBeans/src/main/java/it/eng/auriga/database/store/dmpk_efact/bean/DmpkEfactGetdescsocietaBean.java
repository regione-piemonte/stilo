/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkEfactGetdescsocietaBean")
public class DmpkEfactGetdescsocietaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_EFACT_GETDESCSOCIETA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String idspaooin;
	private java.lang.String idciapplicazionein;
	private java.lang.String idciistapplicazionein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getIdspaooin(){return idspaooin;}
    public java.lang.String getIdciapplicazionein(){return idciapplicazionein;}
    public java.lang.String getIdciistapplicazionein(){return idciistapplicazionein;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setIdspaooin(java.lang.String value){this.idspaooin=value;}
    public void setIdciapplicazionein(java.lang.String value){this.idciapplicazionein=value;}
    public void setIdciistapplicazionein(java.lang.String value){this.idciistapplicazionein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    