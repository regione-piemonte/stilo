/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGetlistdefprefsBean")
public class DmpkUtilityGetlistdefprefsBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETLISTDEFPREFS";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String usernamein;
	private java.lang.String listidin;
	private java.lang.String filterprefout;
	private java.lang.String filterlayoutprefout;
	private java.lang.String gridprefout;
	private java.lang.Integer autosearchprefout;
	private java.lang.Integer flgfilterprefdefout;
	private java.lang.Integer flgfilterlayoutprefdefout;
	private java.lang.Integer flggridprefdefout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String idtreenodein;
	private java.lang.String finalitain;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getUsernamein(){return usernamein;}
    public java.lang.String getListidin(){return listidin;}
    public java.lang.String getFilterprefout(){return filterprefout;}
    public java.lang.String getFilterlayoutprefout(){return filterlayoutprefout;}
    public java.lang.String getGridprefout(){return gridprefout;}
    public java.lang.Integer getAutosearchprefout(){return autosearchprefout;}
    public java.lang.Integer getFlgfilterprefdefout(){return flgfilterprefdefout;}
    public java.lang.Integer getFlgfilterlayoutprefdefout(){return flgfilterlayoutprefdefout;}
    public java.lang.Integer getFlggridprefdefout(){return flggridprefdefout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getIdtreenodein(){return idtreenodein;}
    public java.lang.String getFinalitain(){return finalitain;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setUsernamein(java.lang.String value){this.usernamein=value;}
    public void setListidin(java.lang.String value){this.listidin=value;}
    public void setFilterprefout(java.lang.String value){this.filterprefout=value;}
    public void setFilterlayoutprefout(java.lang.String value){this.filterlayoutprefout=value;}
    public void setGridprefout(java.lang.String value){this.gridprefout=value;}
    public void setAutosearchprefout(java.lang.Integer value){this.autosearchprefout=value;}
    public void setFlgfilterprefdefout(java.lang.Integer value){this.flgfilterprefdefout=value;}
    public void setFlgfilterlayoutprefdefout(java.lang.Integer value){this.flgfilterlayoutprefdefout=value;}
    public void setFlggridprefdefout(java.lang.Integer value){this.flggridprefdefout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setIdtreenodein(java.lang.String value){this.idtreenodein=value;}
    public void setFinalitain(java.lang.String value){this.finalitain=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    