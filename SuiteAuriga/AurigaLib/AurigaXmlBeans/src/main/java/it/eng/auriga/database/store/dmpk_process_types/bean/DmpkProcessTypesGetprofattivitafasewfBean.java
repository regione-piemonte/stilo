/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessTypesGetprofattivitafasewfBean")
public class DmpkProcessTypesGetprofattivitafasewfBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESS_TYPES_GETPROFATTIVITAFASEWF";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String citypeflussowfin;
	private java.lang.String cifasein;
	private java.lang.String ciattivitain;
	private java.lang.Integer nroordinevisout;
	private java.lang.String xmlaclout;
	private java.lang.Integer flgassabilout;
	private java.lang.String controlliobblvsfaseout;
	private java.lang.String xmldettxesitoout;
	private java.lang.String xmldesttrasmout;
	private java.lang.String nomiattraddeditabiliout;
	private java.lang.Integer bachsizeout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getCitypeflussowfin(){return citypeflussowfin;}
    public java.lang.String getCifasein(){return cifasein;}
    public java.lang.String getCiattivitain(){return ciattivitain;}
    public java.lang.Integer getNroordinevisout(){return nroordinevisout;}
    public java.lang.String getXmlaclout(){return xmlaclout;}
    public java.lang.Integer getFlgassabilout(){return flgassabilout;}
    public java.lang.String getControlliobblvsfaseout(){return controlliobblvsfaseout;}
    public java.lang.String getXmldettxesitoout(){return xmldettxesitoout;}
    public java.lang.String getXmldesttrasmout(){return xmldesttrasmout;}
    public java.lang.String getNomiattraddeditabiliout(){return nomiattraddeditabiliout;}
    public java.lang.Integer getBachsizeout(){return bachsizeout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setCitypeflussowfin(java.lang.String value){this.citypeflussowfin=value;}
    public void setCifasein(java.lang.String value){this.cifasein=value;}
    public void setCiattivitain(java.lang.String value){this.ciattivitain=value;}
    public void setNroordinevisout(java.lang.Integer value){this.nroordinevisout=value;}
    public void setXmlaclout(java.lang.String value){this.xmlaclout=value;}
    public void setFlgassabilout(java.lang.Integer value){this.flgassabilout=value;}
    public void setControlliobblvsfaseout(java.lang.String value){this.controlliobblvsfaseout=value;}
    public void setXmldettxesitoout(java.lang.String value){this.xmldettxesitoout=value;}
    public void setXmldesttrasmout(java.lang.String value){this.xmldesttrasmout=value;}
    public void setNomiattraddeditabiliout(java.lang.String value){this.nomiattraddeditabiliout=value;}
    public void setBachsizeout(java.lang.Integer value){this.bachsizeout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    