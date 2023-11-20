/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkModelliDocGetlistacategoriebookmarkBean")
public class DmpkModelliDocGetlistacategoriebookmarkBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_MODELLI_DOC_GETLISTACATEGORIEBOOKMARK";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioin;
	private java.lang.String codapplownerin;
	private java.lang.String codistapplownerin;
	private java.math.BigDecimal flgrestrapplownerin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.lang.String getCodapplownerin(){return codapplownerin;}
    public java.lang.String getCodistapplownerin(){return codistapplownerin;}
    public java.math.BigDecimal getFlgrestrapplownerin(){return flgrestrapplownerin;}
    
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setCodapplownerin(java.lang.String value){this.codapplownerin=value;}
    public void setCodistapplownerin(java.lang.String value){this.codistapplownerin=value;}
    public void setFlgrestrapplownerin(java.math.BigDecimal value){this.flgrestrapplownerin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    