/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWfGetsceltevariabileesitoBean")
public class DmpkWfGetsceltevariabileesitoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WF_GETSCELTEVARIABILEESITO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String bpmnxml;
	private java.lang.String variabileesitoin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getBpmnxml(){return bpmnxml;}
    public java.lang.String getVariabileesitoin(){return variabileesitoin;}
    
    public void setBpmnxml(java.lang.String value){this.bpmnxml=value;}
    public void setVariabileesitoin(java.lang.String value){this.variabileesitoin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    