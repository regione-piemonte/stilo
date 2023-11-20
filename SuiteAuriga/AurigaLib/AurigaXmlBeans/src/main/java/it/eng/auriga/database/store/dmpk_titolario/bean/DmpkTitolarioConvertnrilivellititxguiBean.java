/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkTitolarioConvertnrilivellititxguiBean")
public class DmpkTitolarioConvertnrilivellititxguiBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_TITOLARIO_CONVERTNRILIVELLITITXGUI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String nrilivellititintablein;
	private java.lang.Object deflivellitittabin;
	private java.lang.String seplivelliin;
	private java.lang.Integer flgpaddinglivtitin;
	private java.lang.Integer lengthnrolivelloin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getNrilivellititintablein(){return nrilivellititintablein;}
    public java.lang.Object getDeflivellitittabin(){return deflivellitittabin;}
    public java.lang.String getSeplivelliin(){return seplivelliin;}
    public java.lang.Integer getFlgpaddinglivtitin(){return flgpaddinglivtitin;}
    public java.lang.Integer getLengthnrolivelloin(){return lengthnrolivelloin;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setNrilivellititintablein(java.lang.String value){this.nrilivellititintablein=value;}
    public void setDeflivellitittabin(java.lang.Object value){this.deflivellitittabin=value;}
    public void setSeplivelliin(java.lang.String value){this.seplivelliin=value;}
    public void setFlgpaddinglivtitin(java.lang.Integer value){this.flgpaddinglivtitin=value;}
    public void setLengthnrolivelloin(java.lang.Integer value){this.lengthnrolivelloin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    