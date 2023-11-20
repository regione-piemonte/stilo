/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAttributiDinamiciGetattrdinxricercaBean")
public class DmpkAttributiDinamiciGetattrdinxricercaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ATTRIBUTI_DINAMICI_GETATTRDINXRICERCA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioautin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String nometabellain;
	private java.lang.String citipoentitain;
	private java.lang.Object attribtabout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String tipoattrin;
	private java.lang.Integer flgsoloattrmultivalin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddominioautin(){return iddominioautin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getNometabellain(){return nometabellain;}
    public java.lang.String getCitipoentitain(){return citipoentitain;}
    public java.lang.Object getAttribtabout(){return attribtabout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getTipoattrin(){return tipoattrin;}
    public java.lang.Integer getFlgsoloattrmultivalin(){return flgsoloattrmultivalin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIddominioautin(java.math.BigDecimal value){this.iddominioautin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setNometabellain(java.lang.String value){this.nometabellain=value;}
    public void setCitipoentitain(java.lang.String value){this.citipoentitain=value;}
    public void setAttribtabout(java.lang.Object value){this.attribtabout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setTipoattrin(java.lang.String value){this.tipoattrin=value;}
    public void setFlgsoloattrmultivalin(java.lang.Integer value){this.flgsoloattrmultivalin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    