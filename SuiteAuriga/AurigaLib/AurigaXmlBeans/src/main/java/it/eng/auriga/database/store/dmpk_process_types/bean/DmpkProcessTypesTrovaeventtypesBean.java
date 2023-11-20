/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessTypesTrovaeventtypesBean")
public class DmpkProcessTypesTrovaeventtypesBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESS_TYPES_TROVAEVENTTYPES";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer flgpreimpostafiltroin;
	private java.math.BigDecimal ideventtypeio;
	private java.lang.String deseventio;
	private java.lang.String categoriaio;
	private java.lang.Integer flgdurativoio;
	private java.lang.Integer flgvldxtuttiprocammio;
	private java.math.BigDecimal idprocesstypeio;
	private java.lang.String nomeprocesstypeio;
	private java.math.BigDecimal idprocessio;
	private java.lang.String flgmodterminiprocio;
	private java.lang.Integer flgassociatotipodocio;
	private java.math.BigDecimal iddoctypeio;
	private java.lang.String nomedoctypeio;
	private java.lang.String ciproveventtypeio;
	private java.lang.String codapplicazioneio;
	private java.lang.String codistapplicazioneio;
	private java.math.BigDecimal flginclannullatiio;
	private java.lang.String altricriteriio;
	private java.lang.String colorderbyio;
	private java.lang.String flgdescorderbyio;
	private java.lang.Integer flgsenzapaginazionein;
	private java.lang.Integer nropaginaio;
	private java.lang.Integer bachsizeio;
	private java.lang.Integer overflowlimitin;
	private java.lang.Integer flgsenzatotin;
	private java.lang.Integer nrototrecout;
	private java.lang.Integer nrorecinpaginaout;
	private java.lang.String listaxmlout;
	private java.lang.Integer flgabilinsout;
	private java.lang.Integer flgabilupdout;
	private java.lang.Integer flgabildelout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgesclevtattprocin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getFlgpreimpostafiltroin(){return flgpreimpostafiltroin;}
    public java.math.BigDecimal getIdeventtypeio(){return ideventtypeio;}
    public java.lang.String getDeseventio(){return deseventio;}
    public java.lang.String getCategoriaio(){return categoriaio;}
    public java.lang.Integer getFlgdurativoio(){return flgdurativoio;}
    public java.lang.Integer getFlgvldxtuttiprocammio(){return flgvldxtuttiprocammio;}
    public java.math.BigDecimal getIdprocesstypeio(){return idprocesstypeio;}
    public java.lang.String getNomeprocesstypeio(){return nomeprocesstypeio;}
    public java.math.BigDecimal getIdprocessio(){return idprocessio;}
    public java.lang.String getFlgmodterminiprocio(){return flgmodterminiprocio;}
    public java.lang.Integer getFlgassociatotipodocio(){return flgassociatotipodocio;}
    public java.math.BigDecimal getIddoctypeio(){return iddoctypeio;}
    public java.lang.String getNomedoctypeio(){return nomedoctypeio;}
    public java.lang.String getCiproveventtypeio(){return ciproveventtypeio;}
    public java.lang.String getCodapplicazioneio(){return codapplicazioneio;}
    public java.lang.String getCodistapplicazioneio(){return codistapplicazioneio;}
    public java.math.BigDecimal getFlginclannullatiio(){return flginclannullatiio;}
    public java.lang.String getAltricriteriio(){return altricriteriio;}
    public java.lang.String getColorderbyio(){return colorderbyio;}
    public java.lang.String getFlgdescorderbyio(){return flgdescorderbyio;}
    public java.lang.Integer getFlgsenzapaginazionein(){return flgsenzapaginazionein;}
    public java.lang.Integer getNropaginaio(){return nropaginaio;}
    public java.lang.Integer getBachsizeio(){return bachsizeio;}
    public java.lang.Integer getOverflowlimitin(){return overflowlimitin;}
    public java.lang.Integer getFlgsenzatotin(){return flgsenzatotin;}
    public java.lang.Integer getNrototrecout(){return nrototrecout;}
    public java.lang.Integer getNrorecinpaginaout(){return nrorecinpaginaout;}
    public java.lang.String getListaxmlout(){return listaxmlout;}
    public java.lang.Integer getFlgabilinsout(){return flgabilinsout;}
    public java.lang.Integer getFlgabilupdout(){return flgabilupdout;}
    public java.lang.Integer getFlgabildelout(){return flgabildelout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgesclevtattprocin(){return flgesclevtattprocin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgpreimpostafiltroin(java.lang.Integer value){this.flgpreimpostafiltroin=value;}
    public void setIdeventtypeio(java.math.BigDecimal value){this.ideventtypeio=value;}
    public void setDeseventio(java.lang.String value){this.deseventio=value;}
    public void setCategoriaio(java.lang.String value){this.categoriaio=value;}
    public void setFlgdurativoio(java.lang.Integer value){this.flgdurativoio=value;}
    public void setFlgvldxtuttiprocammio(java.lang.Integer value){this.flgvldxtuttiprocammio=value;}
    public void setIdprocesstypeio(java.math.BigDecimal value){this.idprocesstypeio=value;}
    public void setNomeprocesstypeio(java.lang.String value){this.nomeprocesstypeio=value;}
    public void setIdprocessio(java.math.BigDecimal value){this.idprocessio=value;}
    public void setFlgmodterminiprocio(java.lang.String value){this.flgmodterminiprocio=value;}
    public void setFlgassociatotipodocio(java.lang.Integer value){this.flgassociatotipodocio=value;}
    public void setIddoctypeio(java.math.BigDecimal value){this.iddoctypeio=value;}
    public void setNomedoctypeio(java.lang.String value){this.nomedoctypeio=value;}
    public void setCiproveventtypeio(java.lang.String value){this.ciproveventtypeio=value;}
    public void setCodapplicazioneio(java.lang.String value){this.codapplicazioneio=value;}
    public void setCodistapplicazioneio(java.lang.String value){this.codistapplicazioneio=value;}
    public void setFlginclannullatiio(java.math.BigDecimal value){this.flginclannullatiio=value;}
    public void setAltricriteriio(java.lang.String value){this.altricriteriio=value;}
    public void setColorderbyio(java.lang.String value){this.colorderbyio=value;}
    public void setFlgdescorderbyio(java.lang.String value){this.flgdescorderbyio=value;}
    public void setFlgsenzapaginazionein(java.lang.Integer value){this.flgsenzapaginazionein=value;}
    public void setNropaginaio(java.lang.Integer value){this.nropaginaio=value;}
    public void setBachsizeio(java.lang.Integer value){this.bachsizeio=value;}
    public void setOverflowlimitin(java.lang.Integer value){this.overflowlimitin=value;}
    public void setFlgsenzatotin(java.lang.Integer value){this.flgsenzatotin=value;}
    public void setNrototrecout(java.lang.Integer value){this.nrototrecout=value;}
    public void setNrorecinpaginaout(java.lang.Integer value){this.nrorecinpaginaout=value;}
    public void setListaxmlout(java.lang.String value){this.listaxmlout=value;}
    public void setFlgabilinsout(java.lang.Integer value){this.flgabilinsout=value;}
    public void setFlgabilupdout(java.lang.Integer value){this.flgabilupdout=value;}
    public void setFlgabildelout(java.lang.Integer value){this.flgabildelout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgesclevtattprocin(java.lang.Integer value){this.flgesclevtattprocin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    