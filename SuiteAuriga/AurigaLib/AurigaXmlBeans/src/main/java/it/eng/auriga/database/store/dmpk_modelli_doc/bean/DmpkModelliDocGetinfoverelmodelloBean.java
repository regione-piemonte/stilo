/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkModelliDocGetinfoverelmodelloBean")
public class DmpkModelliDocGetinfoverelmodelloBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_MODELLI_DOC_GETINFOVERELMODELLO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idmodelloin;
	private java.math.BigDecimal iddocout;
	private java.lang.Integer nroverout;
	private java.math.BigDecimal idudout;
	private java.lang.String displayfilenameverout;
	private java.math.BigDecimal idformatoelout;
	private java.lang.String nomeformatoelout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdmodelloin(){return idmodelloin;}
    public java.math.BigDecimal getIddocout(){return iddocout;}
    public java.lang.Integer getNroverout(){return nroverout;}
    public java.math.BigDecimal getIdudout(){return idudout;}
    public java.lang.String getDisplayfilenameverout(){return displayfilenameverout;}
    public java.math.BigDecimal getIdformatoelout(){return idformatoelout;}
    public java.lang.String getNomeformatoelout(){return nomeformatoelout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdmodelloin(java.math.BigDecimal value){this.idmodelloin=value;}
    public void setIddocout(java.math.BigDecimal value){this.iddocout=value;}
    public void setNroverout(java.lang.Integer value){this.nroverout=value;}
    public void setIdudout(java.math.BigDecimal value){this.idudout=value;}
    public void setDisplayfilenameverout(java.lang.String value){this.displayfilenameverout=value;}
    public void setIdformatoelout(java.math.BigDecimal value){this.idformatoelout=value;}
    public void setNomeformatoelout(java.lang.String value){this.nomeformatoelout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    