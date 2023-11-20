/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class RichiesteAutotutelaCedList extends CustomList {

	private ListGridField idRichiestaField;
	private ListGridField descrizioneTipoRichiestaField;
	private ListGridField codiceTipoRichiestaField;
	private ListGridField descrizioneStatoRichiestaField;
	private ListGridField codiceStatoRichiestaField;
	private ListGridField codicefiscaleUtenteField;
	private ListGridField numeroDocumentoField;
	private ListGridField numeroProtocolloField;
	private ListGridField dataRichiestaField;
	private ListGridField dataCambioStatoField;
	private ListGridField flagLettoField;
	private ListGridField idEnteField;
	
	private ListGridField esitoField;
	private ListGridField motivazioneField;
	//private ListGridField ricevutaField;
	private ListGridField codiceAcsField;
	private ListGridField intestatarioDenominazioneField;
	private ListGridField intestatarioIndirizzoField;
	private ListGridField intestatarioComuneField;
	private ListGridField intestatarioProvinciaField;
	private ListGridField intestatarioCapField;
	private ListGridField annoDocumentoField;
	private ListGridField dataDocumentoField;
	private ListGridField idEntrataField;
	private ListGridField entrataField;
	private ListGridField entrataAliasField;
	private ListGridField noteField;		

	public RichiesteAutotutelaCedList(String nomeEntita) {

		super(nomeEntita);

		idRichiestaField = new ListGridField("idRichiesta");
		idRichiestaField.setCanHide(true);
		idRichiestaField.setHidden(true);

		descrizioneTipoRichiestaField = new ListGridField("descrizioneTipoRichiesta", I18NUtil.getMessages().richiesta_autotutela_list_descrizioneTipoRichiesta());

		codiceTipoRichiestaField = new ListGridField("codiceTipoRichiesta", I18NUtil.getMessages().richiesta_autotutela_list_codiceTipoRichiesta());

		descrizioneStatoRichiestaField = new ListGridField("descrizioneStatoRichiesta", I18NUtil.getMessages().richiesta_autotutela_list_descrizioneStatoRichiesta());

		codiceStatoRichiestaField = new ListGridField("codiceStatoRichiesta", I18NUtil.getMessages().richiesta_autotutela_list_codiceStatoRichiesta());

		codicefiscaleUtenteField = new ListGridField("codicefiscaleUtente", I18NUtil.getMessages().richiesta_autotutela_list_codicefiscaleUtente());

		numeroDocumentoField = new ListGridField("numeroDocumento", I18NUtil.getMessages().richiesta_autotutela_list_numeroDocumento());

		numeroProtocolloField = new ListGridField("numeroProtocollo", I18NUtil.getMessages().richiesta_autotutela_list_numeroProtocollo());

		dataRichiestaField = new ListGridField("dataRichiesta", I18NUtil.getMessages().richiesta_autotutela_list_dataRichiesta());
		dataRichiestaField.setType(ListGridFieldType.DATE);
		dataRichiestaField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);

		dataCambioStatoField = new ListGridField("dataCambioStato", I18NUtil.getMessages().richiesta_autotutela_list_dataCambioStato());
		dataCambioStatoField.setType(ListGridFieldType.DATE);
		dataCambioStatoField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);

		flagLettoField = new ListGridField("flagLetto", I18NUtil.getMessages().richiesta_autotutela_list_flagLetto());
		flagLettoField.setType(ListGridFieldType.ICON);
		flagLettoField.setWidth(30);
		flagLettoField.setIconWidth(16);
		flagLettoField.setIconHeight(16);
		Map<String, String> flgTipoIcons = new HashMap<String, String>();
		flgTipoIcons.put("true", "ok.png");
		flgTipoIcons.put("false", "ko.png");
		flgTipoIcons.put("", "blank.png");
		flagLettoField.setValueIcons(flgTipoIcons);
		flagLettoField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("flagLetto") != null && "true".equals(record.getAttribute("flagLetto"))) {
					return "Letto";
				}
				return null;
			}
		});

		idEnteField = new ListGridField("idEnte", I18NUtil.getMessages().richiesta_autotutela_list_idEnte());
		idEnteField.setCanHide(true);
		idEnteField.setHidden(true);
		
		esitoField = new ListGridField("esito", I18NUtil.getMessages().richiesta_autotutela_list_Esito());
		esitoField.setType(ListGridFieldType.ICON);
		esitoField.setWidth(30);
		esitoField.setIconWidth(16);
		esitoField.setIconHeight(16);
		Map<String, String> flgEsitoIcons = new HashMap<String, String>();
		flgEsitoIcons.put("true", "ok.png");
		flgEsitoIcons.put("false", "ko.png");
		flgEsitoIcons.put("", "blank.png");
		esitoField.setValueIcons(flgEsitoIcons);
		esitoField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("esito") != null && "true".equals(record.getAttribute("esito"))) {
					return "Valido";
				}
				return null;
			}
		});
		
		motivazioneField = new ListGridField("motivazione", I18NUtil.getMessages().richiesta_autotutela_list_Motivazione());
		
		//ricevutaField = new ListGridField("ricevuta", "Ricevuta");
		
		codiceAcsField = new ListGridField("codiceAcs", I18NUtil.getMessages().richiesta_autotutela_list_Codice_Acs());
		
		intestatarioDenominazioneField = new ListGridField("intestatarioDenominazione", I18NUtil.getMessages().richiesta_autotutela_list_Denominazione_Intestatario());
		
		intestatarioIndirizzoField = new ListGridField("intestatarioIndirizzo", I18NUtil.getMessages().richiesta_autotutela_list_Indirizzo_Intestatario());
		
		intestatarioComuneField = new ListGridField("intestatarioComune", I18NUtil.getMessages().richiesta_autotutela_list_Comune_Intestatario());
		
		intestatarioProvinciaField = new ListGridField("intestatarioProvincia", I18NUtil.getMessages().richiesta_autotutela_list_Provincia_Intestatario());
		
		intestatarioCapField = new ListGridField("intestatarioCap", I18NUtil.getMessages().richiesta_autotutela_list_Cap_Intestatario());
		
		annoDocumentoField = new ListGridField("annoDocumento", I18NUtil.getMessages().richiesta_autotutela_list_Anno_Documento());
		annoDocumentoField.setType(ListGridFieldType.INTEGER);
		
		dataDocumentoField = new ListGridField("dataDocumento", I18NUtil.getMessages().richiesta_autotutela_list_Data_Documento());
		dataDocumentoField.setType(ListGridFieldType.DATE);
		dataDocumentoField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
		idEntrataField = new ListGridField("idEntrata", I18NUtil.getMessages().richiesta_autotutela_list_Identrata());
		idEntrataField.setCanHide(true);
		idEntrataField.setHidden(true);
		
		entrataField = new ListGridField("entrata", I18NUtil.getMessages().richiesta_autotutela_list_Entrata());
		
		entrataAliasField = new ListGridField("entrataAlias", I18NUtil.getMessages().richiesta_autotutela_list_Aliasentrata());
		
		noteField = new ListGridField("note", I18NUtil.getMessages().richiesta_autotutela_list_Note());
		
		setFields(idRichiestaField, 
				  codiceTipoRichiestaField, 
				  descrizioneTipoRichiestaField, 
				  codiceStatoRichiestaField, 
				  descrizioneStatoRichiestaField,
				  codicefiscaleUtenteField,
				  numeroDocumentoField, 
				  numeroProtocolloField, 
				  dataRichiestaField,
				  dataCambioStatoField, 
				  flagLettoField, 
				  idEnteField,
				  esitoField,
				  motivazioneField,
			 //   ricevutaField,
				  codiceAcsField,
				  intestatarioDenominazioneField,
				  intestatarioIndirizzoField,
				  intestatarioComuneField,
				  intestatarioProvinciaField,
				  intestatarioCapField,
				  annoDocumentoField,
				  dataDocumentoField,
				  idEntrataField,
				  entrataField,
				  entrataAliasField,
				  noteField
		);

	}

	@Override
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		getDataSource().performCustomOperation("get", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					layout.getDetail().editRecord(record, recordNum);
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				}
			}
		}, new DSRequest());
	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		
		return true;
	};

	@Override
	protected boolean showDetailButtonField() {
		
		return true;
	}

	@Override
	protected boolean showModifyButtonField() {
		
		return false;
	}

	@Override
	protected boolean showDeleteButtonField() {
		return false;
	}
	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
}