/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreGetpath_lBean")
public class DmpkCoreGetpath_lBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_GETPATH_L";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal flgtpdominioautin;
	private java.math.BigDecimal iddominioautin;
	private java.lang.String flgvisibindipaclin;
	private java.lang.String flgvisibindipuserabilin;
	private java.math.BigDecimal maxlivriservuserin;
	private java.lang.Object idfoldertypeabiltabin;
	private java.lang.Object idclassifabiltabin;
	private java.lang.String par_ctrl_folder_type_x_visin;
	private java.lang.String par_ctrl_classif_x_visin;
	private java.lang.String objtypetoverifyin;
	private java.math.BigDecimal idobjtoverifyin;
	private java.math.BigDecimal viaidfolderin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getFlgtpdominioautin(){return flgtpdominioautin;}
    public java.math.BigDecimal getIddominioautin(){return iddominioautin;}
    public java.lang.String getFlgvisibindipaclin(){return flgvisibindipaclin;}
    public java.lang.String getFlgvisibindipuserabilin(){return flgvisibindipuserabilin;}
    public java.math.BigDecimal getMaxlivriservuserin(){return maxlivriservuserin;}
    public java.lang.Object getIdfoldertypeabiltabin(){return idfoldertypeabiltabin;}
    public java.lang.Object getIdclassifabiltabin(){return idclassifabiltabin;}
    public java.lang.String getPar_ctrl_folder_type_x_visin(){return par_ctrl_folder_type_x_visin;}
    public java.lang.String getPar_ctrl_classif_x_visin(){return par_ctrl_classif_x_visin;}
    public java.lang.String getObjtypetoverifyin(){return objtypetoverifyin;}
    public java.math.BigDecimal getIdobjtoverifyin(){return idobjtoverifyin;}
    public java.math.BigDecimal getViaidfolderin(){return viaidfolderin;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgtpdominioautin(java.math.BigDecimal value){this.flgtpdominioautin=value;}
    public void setIddominioautin(java.math.BigDecimal value){this.iddominioautin=value;}
    public void setFlgvisibindipaclin(java.lang.String value){this.flgvisibindipaclin=value;}
    public void setFlgvisibindipuserabilin(java.lang.String value){this.flgvisibindipuserabilin=value;}
    public void setMaxlivriservuserin(java.math.BigDecimal value){this.maxlivriservuserin=value;}
    public void setIdfoldertypeabiltabin(java.lang.Object value){this.idfoldertypeabiltabin=value;}
    public void setIdclassifabiltabin(java.lang.Object value){this.idclassifabiltabin=value;}
    public void setPar_ctrl_folder_type_x_visin(java.lang.String value){this.par_ctrl_folder_type_x_visin=value;}
    public void setPar_ctrl_classif_x_visin(java.lang.String value){this.par_ctrl_classif_x_visin=value;}
    public void setObjtypetoverifyin(java.lang.String value){this.objtypetoverifyin=value;}
    public void setIdobjtoverifyin(java.math.BigDecimal value){this.idobjtoverifyin=value;}
    public void setViaidfolderin(java.math.BigDecimal value){this.viaidfolderin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    