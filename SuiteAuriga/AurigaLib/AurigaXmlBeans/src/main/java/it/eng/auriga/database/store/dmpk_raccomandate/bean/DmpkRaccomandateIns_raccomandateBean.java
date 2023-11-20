/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRaccomandateIns_raccomandateBean")
public class DmpkRaccomandateIns_raccomandateBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_RACCOMANDATE_INS_RACCOMANDATE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal v_id_ud;
	private java.lang.String v_tipo;
	private java.lang.String v_idposte;
	private java.math.BigDecimal v_numero_protocollo;
	private java.lang.String v_data_protocollo;
	private java.math.BigDecimal v_anno_protocollo;
	private java.lang.String v_data_invio;
	private java.lang.String v_stato_della_lavorazione;
	private java.lang.String v_data_aggiornamento_stato;
	private java.lang.String v_motivo_eccezione;
	private java.lang.String v_dati_mittente;
	private java.lang.String v_destinatari;
	private java.lang.String v_stato_verifica;
	private java.math.BigDecimal v_importo_totale;
	private java.math.BigDecimal v_imponibile;
	private java.math.BigDecimal v_iva;
	private java.math.BigDecimal v_nro_raccomandata;
	private java.math.BigDecimal v_nro_lettera;
	private java.util.Date v_data_raccomandata;
	private java.math.BigDecimal v_idricevuta;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getV_id_ud(){return v_id_ud;}
    public java.lang.String getV_tipo(){return v_tipo;}
    public java.lang.String getV_idposte(){return v_idposte;}
    public java.math.BigDecimal getV_numero_protocollo(){return v_numero_protocollo;}
    public java.lang.String getV_data_protocollo(){return v_data_protocollo;}
    public java.math.BigDecimal getV_anno_protocollo(){return v_anno_protocollo;}
    public java.lang.String getV_data_invio(){return v_data_invio;}
    public java.lang.String getV_stato_della_lavorazione(){return v_stato_della_lavorazione;}
    public java.lang.String getV_data_aggiornamento_stato(){return v_data_aggiornamento_stato;}
    public java.lang.String getV_motivo_eccezione(){return v_motivo_eccezione;}
    public java.lang.String getV_dati_mittente(){return v_dati_mittente;}
    public java.lang.String getV_destinatari(){return v_destinatari;}
    public java.lang.String getV_stato_verifica(){return v_stato_verifica;}
    public java.math.BigDecimal getV_importo_totale(){return v_importo_totale;}
    public java.math.BigDecimal getV_imponibile(){return v_imponibile;}
    public java.math.BigDecimal getV_iva(){return v_iva;}
    public java.math.BigDecimal getV_nro_raccomandata(){return v_nro_raccomandata;}
    public java.math.BigDecimal getV_nro_lettera(){return v_nro_lettera;}
    public java.util.Date getV_data_raccomandata(){return v_data_raccomandata;}
    public java.math.BigDecimal getV_idricevuta(){return v_idricevuta;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setV_id_ud(java.math.BigDecimal value){this.v_id_ud=value;}
    public void setV_tipo(java.lang.String value){this.v_tipo=value;}
    public void setV_idposte(java.lang.String value){this.v_idposte=value;}
    public void setV_numero_protocollo(java.math.BigDecimal value){this.v_numero_protocollo=value;}
    public void setV_data_protocollo(java.lang.String value){this.v_data_protocollo=value;}
    public void setV_anno_protocollo(java.math.BigDecimal value){this.v_anno_protocollo=value;}
    public void setV_data_invio(java.lang.String value){this.v_data_invio=value;}
    public void setV_stato_della_lavorazione(java.lang.String value){this.v_stato_della_lavorazione=value;}
    public void setV_data_aggiornamento_stato(java.lang.String value){this.v_data_aggiornamento_stato=value;}
    public void setV_motivo_eccezione(java.lang.String value){this.v_motivo_eccezione=value;}
    public void setV_dati_mittente(java.lang.String value){this.v_dati_mittente=value;}
    public void setV_destinatari(java.lang.String value){this.v_destinatari=value;}
    public void setV_stato_verifica(java.lang.String value){this.v_stato_verifica=value;}
    public void setV_importo_totale(java.math.BigDecimal value){this.v_importo_totale=value;}
    public void setV_imponibile(java.math.BigDecimal value){this.v_imponibile=value;}
    public void setV_iva(java.math.BigDecimal value){this.v_iva=value;}
    public void setV_nro_raccomandata(java.math.BigDecimal value){this.v_nro_raccomandata=value;}
    public void setV_nro_lettera(java.math.BigDecimal value){this.v_nro_lettera=value;}
    public void setV_data_raccomandata(java.util.Date value){this.v_data_raccomandata=value;}
    public void setV_idricevuta(java.math.BigDecimal value){this.v_idricevuta=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    