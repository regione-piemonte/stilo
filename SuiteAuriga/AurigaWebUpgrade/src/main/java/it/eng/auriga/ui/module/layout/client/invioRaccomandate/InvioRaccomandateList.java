/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class InvioRaccomandateList extends CustomList {
	
	private ListGridField idBusta;
	private ListGridField idPoste;
	private ListGridField tipo;
	private ListGridField numeroProtocollo;
	private ListGridField dataProtocollo;
	private ListGridField annoProtocollo;
	private ListGridField dataInvio;
	private ListGridField statoLavorazioneRacc;
	private ListGridField dataAggiornamentoStato;
	private ListGridField datiMittente;
	private ListGridField destinatario;
	private ListGridField importoTotale;
	private ListGridField imponibile;
	private ListGridField iva;
	private ListGridField nroRaccomandata;
	private ListGridField nroLettera;
	private ListGridField dataRaccomandata;
	private ListGridField idRicevuta;
	private ListGridField epm_ts;
	private ListGridField epm_key;

	public InvioRaccomandateList(String nomeEntita) {
		
		super(nomeEntita, false);
		setAutoFetchData(true);

		idBusta = new ListGridField("id_busta");
		idBusta.setType(ListGridFieldType.INTEGER);		
		idBusta.setHidden(true);
		idBusta.setCanHide(false);

		idPoste = new ListGridField("idPoste", I18NUtil.getMessages().invio_raccomandate_idPoste_title());
		
		tipo = new ListGridField("tipo", I18NUtil.getMessages().invio_raccomandate_tipo_title());

		numeroProtocollo = new ListGridField("numeroProtocollo", I18NUtil.getMessages().invio_raccomandate_numeroProtocollo_title());
		numeroProtocollo.setType(ListGridFieldType.INTEGER);
		
		dataProtocollo = new ListGridField("dataProtocollo", I18NUtil.getMessages().invio_raccomandate_dataProtocollo_title());
		dataProtocollo.setType(ListGridFieldType.DATE);
		dataProtocollo.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
		annoProtocollo = new ListGridField("annoProtocollo", I18NUtil.getMessages().invio_raccomandate_anno_title());
		annoProtocollo.setType(ListGridFieldType.INTEGER);
		
		dataInvio = new ListGridField("dataInvio", I18NUtil.getMessages().invio_raccomandate_dataInvio_title());
		dataInvio.setType(ListGridFieldType.DATE);
		dataInvio.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
		statoLavorazioneRacc = new ListGridField("statoLavorazioneRacc", I18NUtil.getMessages().invio_raccomandate_statoLavorazioneRacc_title());

		dataAggiornamentoStato = new ListGridField("dataAggiornamentoStato", I18NUtil.getMessages().invio_raccomandate_dataAggiornamentoStato_title());
		dataAggiornamentoStato.setType(ListGridFieldType.DATE);
		dataAggiornamentoStato.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
		datiMittente = new ListGridField("datiMittente", I18NUtil.getMessages().invio_raccomandate_datiMittente_title());

		destinatario = new ListGridField("destinatario", I18NUtil.getMessages().invio_raccomandate_destinatario_title());

		importoTotale = new ListGridField("importoTotale", I18NUtil.getMessages().invio_raccomandate_importoTotale_title());		
		importoTotale.setType(ListGridFieldType.FLOAT);	
		importoTotale.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Double valueDouble = record.getAttributeAsDouble("importoTotale");			
				if(valueDouble != null) {
					try {
						String groupingSeparator = LocaleInfo.getCurrentLocale().getNumberConstants().groupingSeparator();
						String decimalSeparator = LocaleInfo.getCurrentLocale().getNumberConstants().decimalSeparator();									
						return NumberFormat.getFormat("#,##0.00").format(new Double(valueDouble).doubleValue()).replace(groupingSeparator, ".").replace(decimalSeparator, ",");
					} catch(Exception e) {}
				}	
				return null;
			}
		});	
		
		imponibile = new ListGridField("imponibile", I18NUtil.getMessages().invio_raccomandate_imponibile_title());		
		imponibile.setType(ListGridFieldType.FLOAT);	
		imponibile.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Double valueDouble = record.getAttributeAsDouble("imponibile");
				if(valueDouble != null) {
					try {
						String groupingSeparator = LocaleInfo.getCurrentLocale().getNumberConstants().groupingSeparator();
						String decimalSeparator = LocaleInfo.getCurrentLocale().getNumberConstants().decimalSeparator();									
						return NumberFormat.getFormat("#,##0.00").format(new Double(valueDouble).doubleValue()).replace(groupingSeparator, ".").replace(decimalSeparator, ",");
					} catch(Exception e) {}
				}	
				return null;
			}
		});
		
		iva = new ListGridField("iva", I18NUtil.getMessages().invio_raccomandate_iva_title());
		iva.setType(ListGridFieldType.INTEGER);
		
		nroRaccomandata = new ListGridField("nroRaccomandata", I18NUtil.getMessages().invio_raccomandate_nroRaccomandata_title());
		nroRaccomandata.setType(ListGridFieldType.INTEGER);
		
		nroLettera = new ListGridField("nroLettera", I18NUtil.getMessages().invio_raccomandate_nroLettera_title());
		nroLettera.setType(ListGridFieldType.INTEGER);
		
		dataRaccomandata = new ListGridField("dataLotto", I18NUtil.getMessages().invio_raccomandate_dataLotto_title());
		dataRaccomandata.setType(ListGridFieldType.DATE);
		dataRaccomandata.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
		idRicevuta = new ListGridField("idRicevuta", I18NUtil.getMessages().invio_raccomandate_idRicevuta_title());
		idRicevuta.setType(ListGridFieldType.INTEGER);		
		
		epm_ts = new ListGridField("epm_ts", I18NUtil.getMessages().invio_raccomandate_epm_ts_title());
		epm_ts.setType(ListGridFieldType.DATE);
		epm_ts.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
		epm_key = new ListGridField("epm_key", I18NUtil.getMessages().invio_raccomandate_epm_key_title());

		setFields(idPoste, tipo, numeroProtocollo, dataProtocollo, annoProtocollo, dataInvio, statoLavorazioneRacc, dataAggiornamentoStato, datiMittente, destinatario, importoTotale, imponibile, iva, nroRaccomandata, nroLettera, dataRaccomandata, idRicevuta, epm_ts, epm_key);
	}
	
	@Override
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
		if (layout.isLookup() && record != null) {
			if (isRecordSelezionabileForLookup(record)) {
				return "font-weight:bold; color:#1D66B2";
			} else {
				return "font-weight:normal; color:gray";
			}
		}
		return super.getCellCSSText(record, rowNum, colNum);
	}
	
	@Override
	protected boolean isRecordSelezionabileForLookup(ListGridRecord record) {
		if(layout != null && layout instanceof InvioRaccomandateLayout) {
			return ((InvioRaccomandateLayout)layout).isRecordSelezionabileForLookup(record);
		} else {
			return record.getAttributeAsString("appartenenza") == null || "".equalsIgnoreCase(record.getAttributeAsString("appartenenza"));			
		}			
	}
	
	@Override
	protected boolean showDetailButtonField() {
		return false;
	}
	@Override
	protected boolean showModifyButtonField() {
		return false;
	}
	@Override
	protected boolean showDeleteButtonField() {
		return false;
	}
	@Override
	protected boolean showAltreOpButtonField() {
		return false;
	}
	@Override
	protected boolean isRecordAbilToView(ListGridRecord record) {
		return false;
	}
	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		return false;
	}
	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		return false;
	}	
	@Override
	protected boolean isRecordAbilToAltreOp(ListGridRecord record) {
		return false;
	}
	@Override
	public void manageRecordClick(final Record record, final int recordNum) {
		
	}

}
