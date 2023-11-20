/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGetiddocpallfromud_byposBean")
public class DmpkUtilityGetiddocpallfromud_byposBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETIDDOCPALLFROMUD_BYPOS";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.math.BigDecimal idudin;
	private java.lang.String codcategoriaregin;
	private java.lang.String siglaregin;
	private java.lang.Integer annoregin;
	private java.lang.Integer numregin;
	private java.lang.Integer nroposizioneallin;
	private java.lang.Integer nroultimoallegatoout;
	private java.math.BigDecimal iddocout;
	private java.lang.String subnumregin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.math.BigDecimal getIdudin(){return idudin;}
    public java.lang.String getCodcategoriaregin(){return codcategoriaregin;}
    public java.lang.String getSiglaregin(){return siglaregin;}
    public java.lang.Integer getAnnoregin(){return annoregin;}
    public java.lang.Integer getNumregin(){return numregin;}
    public java.lang.Integer getNroposizioneallin(){return nroposizioneallin;}
    public java.lang.Integer getNroultimoallegatoout(){return nroultimoallegatoout;}
    public java.math.BigDecimal getIddocout(){return iddocout;}
    public java.lang.String getSubnumregin(){return subnumregin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    public void setCodcategoriaregin(java.lang.String value){this.codcategoriaregin=value;}
    public void setSiglaregin(java.lang.String value){this.siglaregin=value;}
    public void setAnnoregin(java.lang.Integer value){this.annoregin=value;}
    public void setNumregin(java.lang.Integer value){this.numregin=value;}
    public void setNroposizioneallin(java.lang.Integer value){this.nroposizioneallin=value;}
    public void setNroultimoallegatoout(java.lang.Integer value){this.nroultimoallegatoout=value;}
    public void setIddocout(java.math.BigDecimal value){this.iddocout=value;}
    public void setSubnumregin(java.lang.String value){this.subnumregin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    