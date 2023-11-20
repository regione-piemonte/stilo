/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class PubblicazioneAlboConsultazioneRichiesteList extends CustomList {
	
	private ListGridField idUdFolderField;
	private ListGridField idRichPubblField;
	private ListGridField tsRichiestaPubblicazioneField;
	private ListGridField richiedentePubblicazioneField;	
	private ListGridField tipoField;
	private ListGridField segnaturaField;
	private ListGridField segnaturaXOrdField;
	private ListGridField tsRegistrazioneField;
	private ListGridField mittentiField;
	private ListGridField oggettoField;
	private ListGridField dataInizioPubblicazioneField;
	private ListGridField giorniPubblicazioneField;
	private ListGridField dataFinePubblicazioneField;
	
	public PubblicazioneAlboConsultazioneRichiesteList(String nomeEntita) {
		
		super(nomeEntita);
		
		// Campi nascosti
		idUdFolderField = new ListGridField("idUdFolder"); 
		idUdFolderField.setHidden(true);
		idUdFolderField.setCanHide(false);
		
		idRichPubblField = new ListGridField("idRichPubbl"); 
		idRichPubblField.setHidden(true); 
		idRichPubblField.setCanHide(false);
		
		// Richiesta pubbl. del (data e ora)   
		tsRichiestaPubblicazioneField  = new ListGridField("tsRichiestaPubblicazione", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_list_dataRichiestaField_title());      
		tsRichiestaPubblicazioneField.setType(ListGridFieldType.DATE);
		tsRichiestaPubblicazioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);

		// Richiesta pubbl. effettuata da
		richiedentePubblicazioneField = new ListGridField("richiedentePubblicazione",I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_list_descUtentePubblicazioneField_title());	

		// Tipo atto
		tipoField = new ListGridField("tipo", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_list_tipoAttoField_title());	

		// Numero atto	
		segnaturaField = new ListGridField("segnatura", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_list_numeroAttoField_title());	
		segnaturaField.setHidden(true);
		segnaturaField.setCanHide(false);
		
		segnaturaXOrdField = new ListGridField("segnaturaXOrd", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_list_numeroAttoField_title());	
		segnaturaXOrdField.setDisplayField("segnatura");
		segnaturaXOrdField.setSortByDisplayField(false);
		segnaturaXOrdField.setCanSort(true);
		
		// Data atto
		tsRegistrazioneField  = new ListGridField("tsRegistrazione", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_list_dataAttoField_title());      
		tsRegistrazioneField.setType(ListGridFieldType.DATE);
		tsRegistrazioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);

		// Origine (è il mittente/richiedente)
		mittentiField = new ListGridField("mittenti", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_list_mittenteRichiestaField_title());	

		// Oggetto
		oggettoField = new ListGridField("oggetto", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_list_oggettoField_title());	

		// Data Inizio pubbl.   
		dataInizioPubblicazioneField  = new ListGridField("dataInizioPubblicazione", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_list_dataInizioPubblicazioneField_title());      
		dataInizioPubblicazioneField.setType(ListGridFieldType.DATE);
		dataInizioPubblicazioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
				
		// Termine pubbl.
		dataFinePubblicazioneField  = new ListGridField("dataFinePubblicazione",I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_list_dataFinePubblicazioneField_title());      
		dataFinePubblicazioneField.setType(ListGridFieldType.DATE);
		dataFinePubblicazioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);		
		
		// Giorni pubbl. 
		giorniPubblicazioneField = new ListGridField("giorniPubblicazione", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_list_giorniPubblicazioneField_title());	

		recordClickHandler.removeHandler();
		
		setFields(new ListGridField[] {                                       
				idUdFolderField,
				idRichPubblField,
				tsRichiestaPubblicazioneField,
				richiedentePubblicazioneField,
				tipoField,
				segnaturaField,
				segnaturaXOrdField,
				tsRegistrazioneField,
				mittentiField,
				oggettoField,
				dataInizioPubblicazioneField,
				dataFinePubblicazioneField,
				giorniPubblicazioneField,
		});  
	}
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 100;
	}
	
	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	
	@Override
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUdFolder", record.getAttribute("idUdFolder"));
		lRecordToLoad.setAttribute("idRichPubbl", record.getAttribute("idRichPubbl"));
		getDataSource().performCustomOperation("get", lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = dsResponse.getData()[0];
					layout.getDetail().editRecord(record, recordNum);
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(dsResponse, null, new DSRequest());
				}
				
			}
		}, new DSRequest());
	}
	
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};
	
	@Override
	protected boolean showDetailButtonField() {
		return true;
	}

	
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}