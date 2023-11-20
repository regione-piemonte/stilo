/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCtrlCfControllocfBean")
public class DmpkCtrlCfControllocfBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CTRL_CF_CONTROLLOCF";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String cdfiscin;
	private java.lang.String cognomein;
	private java.lang.String nomein;
	private java.lang.String sessoio;
	private java.lang.String dtnascio;
	private java.lang.String codistatcmnnascio;
	private java.lang.String cmnnascio;
	private java.lang.String provnascio;
	private java.lang.String codistatstatonascio;
	private java.lang.String statonascio;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCdfiscin(){return cdfiscin;}
    public java.lang.String getCognomein(){return cognomein;}
    public java.lang.String getNomein(){return nomein;}
    public java.lang.String getSessoio(){return sessoio;}
    public java.lang.String getDtnascio(){return dtnascio;}
    public java.lang.String getCodistatcmnnascio(){return codistatcmnnascio;}
    public java.lang.String getCmnnascio(){return cmnnascio;}
    public java.lang.String getProvnascio(){return provnascio;}
    public java.lang.String getCodistatstatonascio(){return codistatstatonascio;}
    public java.lang.String getStatonascio(){return statonascio;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCdfiscin(java.lang.String value){this.cdfiscin=value;}
    public void setCognomein(java.lang.String value){this.cognomein=value;}
    public void setNomein(java.lang.String value){this.nomein=value;}
    public void setSessoio(java.lang.String value){this.sessoio=value;}
    public void setDtnascio(java.lang.String value){this.dtnascio=value;}
    public void setCodistatcmnnascio(java.lang.String value){this.codistatcmnnascio=value;}
    public void setCmnnascio(java.lang.String value){this.cmnnascio=value;}
    public void setProvnascio(java.lang.String value){this.provnascio=value;}
    public void setCodistatstatonascio(java.lang.String value){this.codistatstatonascio=value;}
    public void setStatonascio(java.lang.String value){this.statonascio=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    