/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAnagraficaLoaddettgrupposoggrubricaBean")
public class DmpkAnagraficaLoaddettgrupposoggrubricaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ANAGRAFICA_LOADDETTGRUPPOSOGGRUBRICA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idgruppoio;
	private java.lang.String nomegruppoout;
	private java.lang.String codrapidoout;
	private java.lang.String ciprovgruppoout;
	private java.lang.String dtiniziovldout;
	private java.lang.String dtfinevldout;
	private java.lang.Integer flglockedout;
	private java.math.BigDecimal idgruppointout;
	private java.lang.Integer flggruppointernoout;
	private java.lang.String rowidout;
	private java.lang.String xmlmembriout;
	private java.lang.String attributiaddout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdgruppoio(){return idgruppoio;}
    public java.lang.String getNomegruppoout(){return nomegruppoout;}
    public java.lang.String getCodrapidoout(){return codrapidoout;}
    public java.lang.String getCiprovgruppoout(){return ciprovgruppoout;}
    public java.lang.String getDtiniziovldout(){return dtiniziovldout;}
    public java.lang.String getDtfinevldout(){return dtfinevldout;}
    public java.lang.Integer getFlglockedout(){return flglockedout;}
    public java.math.BigDecimal getIdgruppointout(){return idgruppointout;}
    public java.lang.Integer getFlggruppointernoout(){return flggruppointernoout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getXmlmembriout(){return xmlmembriout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdgruppoio(java.math.BigDecimal value){this.idgruppoio=value;}
    public void setNomegruppoout(java.lang.String value){this.nomegruppoout=value;}
    public void setCodrapidoout(java.lang.String value){this.codrapidoout=value;}
    public void setCiprovgruppoout(java.lang.String value){this.ciprovgruppoout=value;}
    public void setDtiniziovldout(java.lang.String value){this.dtiniziovldout=value;}
    public void setDtfinevldout(java.lang.String value){this.dtfinevldout=value;}
    public void setFlglockedout(java.lang.Integer value){this.flglockedout=value;}
    public void setIdgruppointout(java.math.BigDecimal value){this.idgruppointout=value;}
    public void setFlggruppointernoout(java.lang.Integer value){this.flggruppointernoout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setXmlmembriout(java.lang.String value){this.xmlmembriout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    