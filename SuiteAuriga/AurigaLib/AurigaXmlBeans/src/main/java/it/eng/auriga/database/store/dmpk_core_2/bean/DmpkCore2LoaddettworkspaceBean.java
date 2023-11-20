/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCore2LoaddettworkspaceBean")
public class DmpkCore2LoaddettworkspaceBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_2_LOADDETTWORKSPACE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idworkspaceio;
	private java.lang.String nomeworkspaceio;
	private java.lang.String desworkspaceout;
	private java.lang.String annotazioniout;
	private java.lang.Integer flgconspropagazpermessiout;
	private java.lang.String estremilockout;
	private java.lang.Integer flgannout;
	private java.lang.String ciprovworkspaceout;
	private java.lang.String rowidout;
	private java.lang.String attributiaddout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String dettaglioperazioniout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdworkspaceio(){return idworkspaceio;}
    public java.lang.String getNomeworkspaceio(){return nomeworkspaceio;}
    public java.lang.String getDesworkspaceout(){return desworkspaceout;}
    public java.lang.String getAnnotazioniout(){return annotazioniout;}
    public java.lang.Integer getFlgconspropagazpermessiout(){return flgconspropagazpermessiout;}
    public java.lang.String getEstremilockout(){return estremilockout;}
    public java.lang.Integer getFlgannout(){return flgannout;}
    public java.lang.String getCiprovworkspaceout(){return ciprovworkspaceout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getDettaglioperazioniout(){return dettaglioperazioniout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdworkspaceio(java.math.BigDecimal value){this.idworkspaceio=value;}
    public void setNomeworkspaceio(java.lang.String value){this.nomeworkspaceio=value;}
    public void setDesworkspaceout(java.lang.String value){this.desworkspaceout=value;}
    public void setAnnotazioniout(java.lang.String value){this.annotazioniout=value;}
    public void setFlgconspropagazpermessiout(java.lang.Integer value){this.flgconspropagazpermessiout=value;}
    public void setEstremilockout(java.lang.String value){this.estremilockout=value;}
    public void setFlgannout(java.lang.Integer value){this.flgannout=value;}
    public void setCiprovworkspaceout(java.lang.String value){this.ciprovworkspaceout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setDettaglioperazioniout(java.lang.String value){this.dettaglioperazioniout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    