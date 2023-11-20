/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDmlsuoggettiAggiornacoluserdefinedBean")
public class DmpkDmlsuoggettiAggiornacoluserdefinedBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DMLSUOGGETTI_AGGIORNACOLUSERDEFINED";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String tablenamein;
	private java.lang.String columnnamein;
	private java.lang.String pkrecordin;
	private java.lang.String rowidrecordin;
	private java.lang.Object valorixobjcolumnin;
	private java.lang.Integer flgappendvaluesin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getTablenamein(){return tablenamein;}
    public java.lang.String getColumnnamein(){return columnnamein;}
    public java.lang.String getPkrecordin(){return pkrecordin;}
    public java.lang.String getRowidrecordin(){return rowidrecordin;}
    public java.lang.Object getValorixobjcolumnin(){return valorixobjcolumnin;}
    public java.lang.Integer getFlgappendvaluesin(){return flgappendvaluesin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setTablenamein(java.lang.String value){this.tablenamein=value;}
    public void setColumnnamein(java.lang.String value){this.columnnamein=value;}
    public void setPkrecordin(java.lang.String value){this.pkrecordin=value;}
    public void setRowidrecordin(java.lang.String value){this.rowidrecordin=value;}
    public void setValorixobjcolumnin(java.lang.Object value){this.valorixobjcolumnin=value;}
    public void setFlgappendvaluesin(java.lang.Integer value){this.flgappendvaluesin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    