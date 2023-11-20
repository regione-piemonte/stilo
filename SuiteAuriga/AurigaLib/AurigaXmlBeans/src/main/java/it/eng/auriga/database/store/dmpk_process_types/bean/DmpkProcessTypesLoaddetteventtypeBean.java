/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessTypesLoaddetteventtypeBean")
public class DmpkProcessTypesLoaddetteventtypeBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESS_TYPES_LOADDETTEVENTTYPE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal ideventtypeio;
	private java.lang.String deseventout;
	private java.lang.String categoriaout;
	private java.lang.Integer flgdurativoout;
	private java.math.BigDecimal duratamaxout;
	private java.lang.Integer flgvldxtuttiprocammout;
	private java.math.BigDecimal iddoctypeout;
	private java.lang.String nomedoctypeout;
	private java.lang.String codtipodatareldocout;
	private java.lang.String annotazioniout;
	private java.lang.String ciproveventtypeout;
	private java.lang.Integer flglockedout;
	private java.lang.String rowidout;
	private java.lang.String xmlstoredfuncout;
	private java.lang.String xmlattraddxevtdeltipoout;
	private java.lang.String attributiaddout;
	private java.lang.Integer bachsizeout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdeventtypeio(){return ideventtypeio;}
    public java.lang.String getDeseventout(){return deseventout;}
    public java.lang.String getCategoriaout(){return categoriaout;}
    public java.lang.Integer getFlgdurativoout(){return flgdurativoout;}
    public java.math.BigDecimal getDuratamaxout(){return duratamaxout;}
    public java.lang.Integer getFlgvldxtuttiprocammout(){return flgvldxtuttiprocammout;}
    public java.math.BigDecimal getIddoctypeout(){return iddoctypeout;}
    public java.lang.String getNomedoctypeout(){return nomedoctypeout;}
    public java.lang.String getCodtipodatareldocout(){return codtipodatareldocout;}
    public java.lang.String getAnnotazioniout(){return annotazioniout;}
    public java.lang.String getCiproveventtypeout(){return ciproveventtypeout;}
    public java.lang.Integer getFlglockedout(){return flglockedout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getXmlstoredfuncout(){return xmlstoredfuncout;}
    public java.lang.String getXmlattraddxevtdeltipoout(){return xmlattraddxevtdeltipoout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getBachsizeout(){return bachsizeout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdeventtypeio(java.math.BigDecimal value){this.ideventtypeio=value;}
    public void setDeseventout(java.lang.String value){this.deseventout=value;}
    public void setCategoriaout(java.lang.String value){this.categoriaout=value;}
    public void setFlgdurativoout(java.lang.Integer value){this.flgdurativoout=value;}
    public void setDuratamaxout(java.math.BigDecimal value){this.duratamaxout=value;}
    public void setFlgvldxtuttiprocammout(java.lang.Integer value){this.flgvldxtuttiprocammout=value;}
    public void setIddoctypeout(java.math.BigDecimal value){this.iddoctypeout=value;}
    public void setNomedoctypeout(java.lang.String value){this.nomedoctypeout=value;}
    public void setCodtipodatareldocout(java.lang.String value){this.codtipodatareldocout=value;}
    public void setAnnotazioniout(java.lang.String value){this.annotazioniout=value;}
    public void setCiproveventtypeout(java.lang.String value){this.ciproveventtypeout=value;}
    public void setFlglockedout(java.lang.Integer value){this.flglockedout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setXmlstoredfuncout(java.lang.String value){this.xmlstoredfuncout=value;}
    public void setXmlattraddxevtdeltipoout(java.lang.String value){this.xmlattraddxevtdeltipoout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setBachsizeout(java.lang.Integer value){this.bachsizeout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    