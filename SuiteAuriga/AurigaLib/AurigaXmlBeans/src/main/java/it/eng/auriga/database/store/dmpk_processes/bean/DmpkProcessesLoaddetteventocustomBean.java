/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessesLoaddetteventocustomBean")
public class DmpkProcessesLoaddetteventocustomBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESSES_LOADDETTEVENTOCUSTOM";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal ideventoio;
	private java.math.BigDecimal idprocessout;
	private java.lang.String desprocesstypeout;
	private java.lang.String ciprovprocessout;
	private java.math.BigDecimal idtipoeventoout;
	private java.lang.String destipoeventoout;
	private java.lang.String deseventoout;
	private java.lang.String tsinizioeventoout;
	private java.math.BigDecimal iduserinianomediout;
	private java.lang.String desuserinianomediout;
	private java.math.BigDecimal iduserinidaout;
	private java.lang.String desuserinidaout;
	private java.math.BigDecimal ideventoinidaout;
	private java.lang.String estremieventoinidaout;
	private java.lang.String tscompleventoout;
	private java.math.BigDecimal idusercomplanomediout;
	private java.lang.String desusercomplanomediout;
	private java.math.BigDecimal idusercompldaout;
	private java.lang.String desusercompldaout;
	private java.math.BigDecimal ideventoenddaout;
	private java.lang.String estremieventoenddaout;
	private java.lang.String eventiderivatiout;
	private java.lang.String desesitoout;
	private java.lang.String messaggioout;
	private java.math.BigDecimal idudassociataout;
	private java.math.BigDecimal iddocassociatoout;
	private java.lang.Integer nrolastverdocassout;
	private java.lang.String displayfilenameout;
	private java.lang.String estremiudassociataout;
	private java.math.BigDecimal iddoctypeassociatoout;
	private java.math.BigDecimal duratamaxeventoout;
	private java.lang.String rowidout;
	private java.lang.String attributiaddout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.Integer bachsizeout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdeventoio(){return ideventoio;}
    public java.math.BigDecimal getIdprocessout(){return idprocessout;}
    public java.lang.String getDesprocesstypeout(){return desprocesstypeout;}
    public java.lang.String getCiprovprocessout(){return ciprovprocessout;}
    public java.math.BigDecimal getIdtipoeventoout(){return idtipoeventoout;}
    public java.lang.String getDestipoeventoout(){return destipoeventoout;}
    public java.lang.String getDeseventoout(){return deseventoout;}
    public java.lang.String getTsinizioeventoout(){return tsinizioeventoout;}
    public java.math.BigDecimal getIduserinianomediout(){return iduserinianomediout;}
    public java.lang.String getDesuserinianomediout(){return desuserinianomediout;}
    public java.math.BigDecimal getIduserinidaout(){return iduserinidaout;}
    public java.lang.String getDesuserinidaout(){return desuserinidaout;}
    public java.math.BigDecimal getIdeventoinidaout(){return ideventoinidaout;}
    public java.lang.String getEstremieventoinidaout(){return estremieventoinidaout;}
    public java.lang.String getTscompleventoout(){return tscompleventoout;}
    public java.math.BigDecimal getIdusercomplanomediout(){return idusercomplanomediout;}
    public java.lang.String getDesusercomplanomediout(){return desusercomplanomediout;}
    public java.math.BigDecimal getIdusercompldaout(){return idusercompldaout;}
    public java.lang.String getDesusercompldaout(){return desusercompldaout;}
    public java.math.BigDecimal getIdeventoenddaout(){return ideventoenddaout;}
    public java.lang.String getEstremieventoenddaout(){return estremieventoenddaout;}
    public java.lang.String getEventiderivatiout(){return eventiderivatiout;}
    public java.lang.String getDesesitoout(){return desesitoout;}
    public java.lang.String getMessaggioout(){return messaggioout;}
    public java.math.BigDecimal getIdudassociataout(){return idudassociataout;}
    public java.math.BigDecimal getIddocassociatoout(){return iddocassociatoout;}
    public java.lang.Integer getNrolastverdocassout(){return nrolastverdocassout;}
    public java.lang.String getDisplayfilenameout(){return displayfilenameout;}
    public java.lang.String getEstremiudassociataout(){return estremiudassociataout;}
    public java.math.BigDecimal getIddoctypeassociatoout(){return iddoctypeassociatoout;}
    public java.math.BigDecimal getDuratamaxeventoout(){return duratamaxeventoout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.Integer getBachsizeout(){return bachsizeout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdeventoio(java.math.BigDecimal value){this.ideventoio=value;}
    public void setIdprocessout(java.math.BigDecimal value){this.idprocessout=value;}
    public void setDesprocesstypeout(java.lang.String value){this.desprocesstypeout=value;}
    public void setCiprovprocessout(java.lang.String value){this.ciprovprocessout=value;}
    public void setIdtipoeventoout(java.math.BigDecimal value){this.idtipoeventoout=value;}
    public void setDestipoeventoout(java.lang.String value){this.destipoeventoout=value;}
    public void setDeseventoout(java.lang.String value){this.deseventoout=value;}
    public void setTsinizioeventoout(java.lang.String value){this.tsinizioeventoout=value;}
    public void setIduserinianomediout(java.math.BigDecimal value){this.iduserinianomediout=value;}
    public void setDesuserinianomediout(java.lang.String value){this.desuserinianomediout=value;}
    public void setIduserinidaout(java.math.BigDecimal value){this.iduserinidaout=value;}
    public void setDesuserinidaout(java.lang.String value){this.desuserinidaout=value;}
    public void setIdeventoinidaout(java.math.BigDecimal value){this.ideventoinidaout=value;}
    public void setEstremieventoinidaout(java.lang.String value){this.estremieventoinidaout=value;}
    public void setTscompleventoout(java.lang.String value){this.tscompleventoout=value;}
    public void setIdusercomplanomediout(java.math.BigDecimal value){this.idusercomplanomediout=value;}
    public void setDesusercomplanomediout(java.lang.String value){this.desusercomplanomediout=value;}
    public void setIdusercompldaout(java.math.BigDecimal value){this.idusercompldaout=value;}
    public void setDesusercompldaout(java.lang.String value){this.desusercompldaout=value;}
    public void setIdeventoenddaout(java.math.BigDecimal value){this.ideventoenddaout=value;}
    public void setEstremieventoenddaout(java.lang.String value){this.estremieventoenddaout=value;}
    public void setEventiderivatiout(java.lang.String value){this.eventiderivatiout=value;}
    public void setDesesitoout(java.lang.String value){this.desesitoout=value;}
    public void setMessaggioout(java.lang.String value){this.messaggioout=value;}
    public void setIdudassociataout(java.math.BigDecimal value){this.idudassociataout=value;}
    public void setIddocassociatoout(java.math.BigDecimal value){this.iddocassociatoout=value;}
    public void setNrolastverdocassout(java.lang.Integer value){this.nrolastverdocassout=value;}
    public void setDisplayfilenameout(java.lang.String value){this.displayfilenameout=value;}
    public void setEstremiudassociataout(java.lang.String value){this.estremiudassociataout=value;}
    public void setIddoctypeassociatoout(java.math.BigDecimal value){this.iddoctypeassociatoout=value;}
    public void setDuratamaxeventoout(java.math.BigDecimal value){this.duratamaxeventoout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setBachsizeout(java.lang.Integer value){this.bachsizeout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    