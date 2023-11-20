/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkModelliDocLoaddettmodelloBean")
public class DmpkModelliDocLoaddettmodelloBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_MODELLI_DOC_LOADDETTMODELLO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idmodelloio;
	private java.lang.String nomeout;
	private java.lang.String descrizioneout;
	private java.math.BigDecimal iddocout;
	private java.lang.Integer nrovereldocout;
	private java.lang.String displayfilenamevereldocout;
	private java.lang.String annotazioniout;
	private java.lang.String ciprovmodelloout;
	private java.lang.Integer flglockedout;
	private java.lang.Integer iddocxmlout;
	private java.lang.Integer iddocpdfout;
	private java.lang.Integer iddochtmlout;
	private java.lang.Integer nroordineout;
	private java.lang.Integer flgprofcompletaout;
	private java.lang.String rowidout;
	private java.lang.String attributiaddout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String tipomodelloout;
	private java.lang.String tipoenitaassociataout;
	private java.math.BigDecimal identitaassociataout;
	private java.lang.String nomeentitaassociataout;
	private java.lang.String nometabellaentitaassout;
	private java.lang.Integer flggeneraformatopdfout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdmodelloio(){return idmodelloio;}
    public java.lang.String getNomeout(){return nomeout;}
    public java.lang.String getDescrizioneout(){return descrizioneout;}
    public java.math.BigDecimal getIddocout(){return iddocout;}
    public java.lang.Integer getNrovereldocout(){return nrovereldocout;}
    public java.lang.String getDisplayfilenamevereldocout(){return displayfilenamevereldocout;}
    public java.lang.String getAnnotazioniout(){return annotazioniout;}
    public java.lang.String getCiprovmodelloout(){return ciprovmodelloout;}
    public java.lang.Integer getFlglockedout(){return flglockedout;}
    public java.lang.Integer getIddocxmlout(){return iddocxmlout;}
    public java.lang.Integer getIddocpdfout(){return iddocpdfout;}
    public java.lang.Integer getIddochtmlout(){return iddochtmlout;}
    public java.lang.Integer getNroordineout(){return nroordineout;}
    public java.lang.Integer getFlgprofcompletaout(){return flgprofcompletaout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getTipomodelloout(){return tipomodelloout;}
    public java.lang.String getTipoenitaassociataout(){return tipoenitaassociataout;}
    public java.math.BigDecimal getIdentitaassociataout(){return identitaassociataout;}
    public java.lang.String getNomeentitaassociataout(){return nomeentitaassociataout;}
    public java.lang.String getNometabellaentitaassout(){return nometabellaentitaassout;}
    public java.lang.Integer getFlggeneraformatopdfout(){return flggeneraformatopdfout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdmodelloio(java.math.BigDecimal value){this.idmodelloio=value;}
    public void setNomeout(java.lang.String value){this.nomeout=value;}
    public void setDescrizioneout(java.lang.String value){this.descrizioneout=value;}
    public void setIddocout(java.math.BigDecimal value){this.iddocout=value;}
    public void setNrovereldocout(java.lang.Integer value){this.nrovereldocout=value;}
    public void setDisplayfilenamevereldocout(java.lang.String value){this.displayfilenamevereldocout=value;}
    public void setAnnotazioniout(java.lang.String value){this.annotazioniout=value;}
    public void setCiprovmodelloout(java.lang.String value){this.ciprovmodelloout=value;}
    public void setFlglockedout(java.lang.Integer value){this.flglockedout=value;}
    public void setIddocxmlout(java.lang.Integer value){this.iddocxmlout=value;}
    public void setIddocpdfout(java.lang.Integer value){this.iddocpdfout=value;}
    public void setIddochtmlout(java.lang.Integer value){this.iddochtmlout=value;}
    public void setNroordineout(java.lang.Integer value){this.nroordineout=value;}
    public void setFlgprofcompletaout(java.lang.Integer value){this.flgprofcompletaout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setTipomodelloout(java.lang.String value){this.tipomodelloout=value;}
    public void setTipoenitaassociataout(java.lang.String value){this.tipoenitaassociataout=value;}
    public void setIdentitaassociataout(java.math.BigDecimal value){this.identitaassociataout=value;}
    public void setNomeentitaassociataout(java.lang.String value){this.nomeentitaassociataout=value;}
    public void setNometabellaentitaassout(java.lang.String value){this.nometabellaentitaassout=value;}
    public void setFlggeneraformatopdfout(java.lang.Integer value){this.flggeneraformatopdfout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    