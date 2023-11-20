/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkOrderSetattributedettordiniBean")
public class DmpkOrderSetattributedettordiniBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ORDER_SETATTRIBUTEDETTORDINI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal iddoc;
	private java.lang.String nomeattrio;
	private java.lang.String valoreattributo;
	private java.lang.String tipoattrio;
	private java.lang.Integer numlinea;
	private java.lang.Integer numoccvalue;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIddoc(){return iddoc;}
    public java.lang.String getNomeattrio(){return nomeattrio;}
    public java.lang.String getValoreattributo(){return valoreattributo;}
    public java.lang.String getTipoattrio(){return tipoattrio;}
    public java.lang.Integer getNumlinea(){return numlinea;}
    public java.lang.Integer getNumoccvalue(){return numoccvalue;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIddoc(java.math.BigDecimal value){this.iddoc=value;}
    public void setNomeattrio(java.lang.String value){this.nomeattrio=value;}
    public void setValoreattributo(java.lang.String value){this.valoreattributo=value;}
    public void setTipoattrio(java.lang.String value){this.tipoattrio=value;}
    public void setNumlinea(java.lang.Integer value){this.numlinea=value;}
    public void setNumoccvalue(java.lang.Integer value){this.numoccvalue=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    