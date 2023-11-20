/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

/**
 * Maschera di compilazione modulo
 * 
 * @author Mattia Zanin
 *
 */

public class ProtocollazioneDetailModelli extends ProtocollazioneDetailBozze {

	protected ProtocollazioneDetailModelli instance;
	
	protected String nomeTipoDocumento;
	protected String flgTipoProtModulo;
	
	public ProtocollazioneDetailModelli(String nomeEntita) {
		
		super(nomeEntita);		
		
		instance = this;
	}
	
	@Override
	public boolean isModalitaWizard() {
		return false;
	}
	
	@Override
	public boolean showModelliSelectItem() {
		return true;
	}
	
	@Override
	public boolean showUoProtocollanteSelectItem() {
		return false;
	}
	
	@Override
	public boolean showDetailSectionTipoDocumento() {
		return false;
	}

	@Override
	public VLayout getLayoutDatiDocumento() {

		VLayout layoutDatiDocumento = new VLayout(5);

		createDetailSectionRegistrazione();
		detailSectionRegistrazione.setVisible(false);
		layoutDatiDocumento.addMember(detailSectionRegistrazione);

		createDetailSectionNuovaRegistrazione();
		detailSectionNuovaRegistrazione.setVisible(false);
		layoutDatiDocumento.addMember(detailSectionNuovaRegistrazione);
		
		if(showDetailSectionNuovaRegistrazioneProtGenerale()) {
			createDetailSectionNuovaRegistrazioneProtGenerale();			
			layoutDatiDocumento.addMember(detailSectionNuovaRegistrazioneProtGenerale);
		}

		if(showDetailSectionTipoDocumento()) {
			createDetailSectionTipoDocumento();
			detailSectionTipoDocumento.setVisible(tipoDocumento != null && !"".equals(tipoDocumento));
			layoutDatiDocumento.addMember(detailSectionTipoDocumento);
		}

//		createDetailSectionMittenti();
//		layoutDatiDocumento.addMember(detailSectionMittenti);
//
//		if (showDetailSectionDestinatari()) {
//			createDetailSectionDestinatari();
//			layoutDatiDocumento.addMember(detailSectionDestinatari);
//		}

		createDetailSectionContenuti();
		layoutDatiDocumento.addMember(detailSectionContenuti);

		createDetailSectionAllegati();
		layoutDatiDocumento.addMember(detailSectionAllegati);
		detailSectionAllegati.setVisible(false);		

		if (showDetailSectionDatiRicezione()) {
			createDetailSectionDatiRicezione();
			layoutDatiDocumento.addMember(detailSectionDatiRicezione);
		}

		if(showDetailSectionDocCollegato()) {
			createDetailSectionDocCollegato();
			layoutDatiDocumento.addMember(detailSectionDocCollegato);
		}

		if (showDetailSectionRegEmergenza()) {
			createDetailSectionRegEmergenza();
			layoutDatiDocumento.addMember(detailSectionRegEmergenza);
		}

		if (showDetailSectionCollocazioneFisica()) {
			createDetailSectionCollocazioneFisica();
			layoutDatiDocumento.addMember(detailSectionCollocazioneFisica);
		}
		
		createDetailSectionAltriDati();
		layoutDatiDocumento.addMember(detailSectionAltriDati);

		return layoutDatiDocumento;
	}
	
	@Override
	public boolean showFilePrimarioForm() {
		return true; //TODO devo mettere il form oppure non passa in salvataggio il file primario generato da modello
	}
	
	@Override
	public String getTitleRegistraButton() {
		return I18NUtil.getMessages().saveButton_prompt();
//		return "Salva in bozza";
	}	
	
	@Override
	public String getTitleAnteprimaModelloButton() {
		return "Anteprima modulo compilato";
	}
	
	public void generaDaModelloFilePrimarioXProt(final ServiceCallback<Record> callback) {
		SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource(
				"LoadComboGeneraDaModelloDataSource", new String[] { "codice", "descrizione" }, true);
		lGwtRestDataSource.addParam("idTipoDocumento", tipoDocumento);
		lGwtRestDataSource.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				RecordList data = response.getDataAsRecordList();
				if (data.getLength() > 1) {
					SelezionaGeneraDaModelloWindow generaDaModelloWindow = new SelezionaGeneraDaModelloWindow(
							data) {

						@Override
						public void caricaModelloSelezionato(String idModello, String tipoModello,
								String flgConvertiInPdf) {
							caricaModelloFilePrimarioXProt(idModello, tipoModello, callback);
						}
					};
					generaDaModelloWindow.show();
				} else if (data.getLength() == 1) {
					String idModello = data.get(0).getAttribute("idModello");
					if (idModello != null && !"".equals(idModello)) {
						caricaModelloFilePrimarioXProt(idModello, data.get(0).getAttribute("tipoModello"), callback);
					}
				} else {
					Layout.addMessage(new MessageBean("Nessun modello da caricare!", "", MessageType.INFO));
				}

			}
		});
	}
	
	public void caricaModelloFilePrimarioXProt(String idModello, String tipoModello, final ServiceCallback<Record> callback) {
		final GWTRestDataSource lGeneraDaModelloDataSource = new GWTRestDataSource("GeneraDaModelloDataSource");
		lGeneraDaModelloDataSource.executecustom("caricaModello", getRecordCaricaModello(idModello, tipoModello),
				new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							manageAfterCaricaModelloFilePrimarioXProt(response.getData()[0], callback);
						}
					}
				});
	}
	
	public void manageAfterCaricaModelloFilePrimarioXProt(Record record, ServiceCallback<Record> callback) {
		if(callback != null) {
			Record lRecordToSave = getRecordToSave();
			lRecordToSave.setAttribute("nomeFilePrimario", record.getAttribute("nomeFilePrimario"));
			lRecordToSave.setAttribute("uriFilePrimario", record.getAttribute("uriFilePrimario"));
			lRecordToSave.setAttribute("infoFile", record.getAttributeAsRecord("infoFile"));
			callback.execute(lRecordToSave);
		}	
	}
	
	@Override
	public void clickProtocollazioneEntrata(final CustomLayout layout) {	
		//TODO va generato il file primario da modello e vanno sistemati gli altri dati per la protocollazione (mitt, dest)
		generaDaModelloFilePrimarioXProt(new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				clickProtocollazioneEntrata(layout, object);
			}			
		});		
	}
	
	@Override
	public void clickProtocollazioneUscita(final CustomLayout layout) {	
		//TODO va generato il file primario da modello e vanno sistemati gli altri dati per la protocollazione (mitt, dest)
		generaDaModelloFilePrimarioXProt(new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				clickProtocollazioneUscita(layout, object);
			}			
		});		
	}
	
	@Override
	public void clickProtocollazioneInterna(final CustomLayout layout) {
		//TODO va generato il file primario da modello e vanno sistemati gli altri dati per la protocollazione (mitt, dest)
		generaDaModelloFilePrimarioXProt(new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				clickProtocollazioneInterna(layout, object);
			}			
		});		
	}
	
	@Override
	public void manageAfterCaricaModello(Record record) {
		String nomeFile = record.getAttribute("nomeFilePrimario");
		String uri = record.getAttribute("uriFilePrimario");		
		InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));		
		if (nomeFile != null && nomeFile.endsWith(".pdf")) {
			new PreviewWindow(uri, false, info, "FileToExtractBean",	nomeFile);
		} else {
			Record lRecord = new Record();
			lRecord.setAttribute("displayFilename", nomeFile);
			lRecord.setAttribute("uri", uri);
			lRecord.setAttribute("sbustato", "false");
			lRecord.setAttribute("remoteUri", "false");
			DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");			
		}
	}
	
	@Override
	public Map<String, Object> getNuovaProtInitialValues() {
		Map<String, Object> initialValues = super.getNuovaProtInitialValues();
		initialValues.put("tipoDocumento", tipoDocumento);
		initialValues.put("nomeTipoDocumento", nomeTipoDocumento);	
		initialValues.put("flgTipoProtModulo", flgTipoProtModulo);	
		return initialValues;
	}
		
	@Override
	public void editNewRecord(Map initialValues) {
		super.editNewRecord(initialValues);
		this.tipoDocumento = new Record(initialValues).getAttribute("tipoDocumento");
		this.nomeTipoDocumento = new Record(initialValues).getAttribute("nomeTipoDocumento");
		this.flgTipoProtModulo = new Record(initialValues).getAttribute("flgTipoProtModulo");
		redrawTitleOfPortlet();
	}
	
	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
		this.tipoDocumento = record.getAttribute("tipoDocumento");
		this.nomeTipoDocumento = record.getAttribute("nomeTipoDocumento");
		this.flgTipoProtModulo = record.getAttribute("flgTipoProtModulo");
		redrawTitleOfPortlet();
	}
	
	@Override
	public Record getRecordToSave() {
		Record lRecordToSave = super.getRecordToSave();
		lRecordToSave.setAttribute("flgCompilazioneModulo", true);
		return lRecordToSave;
	}
	
	@Override
	public void newMode() {
		super.newMode();
		anteprimaModelloButton.show();
		stampaEtichettaButton.hide();
		frecciaStampaEtichettaButton.hide();
		stampaRicevutaButton.hide();
		stampaCopertinaButton.hide();		
		frecciaModificaButton.hide();
		invioPECButton.hide();
		invioPEOButton.hide();
		verificaRegistrazioneButton.hide();		
		assegnaCondividiButton.hide();
		frecciaAssegnaCondividiButton.hide();
//		assegnazioneButton.hide();
//		frecciaAssegnazioneButton.hide();
		rispondiButton.hide();
//		condivisioneButton.hide();
//		frecciaCondivisioneButton.hide();
		osservazioniNotificheButton.hide();		
		downloadDocZipButton.hide();
		apposizioneFirmaButton.hide();
		rifiutoApposizioneFirmaButton.hide();
		apposizioneVistoButton.hide();
		rifiutoApposizioneVistoButton.hide();
		revocaAttoButton.hide();
		// al momento i bottoni di protocollazione li faccio vedere solo dopo il salvataggio della bozza
//		if (flgTipoProtModulo != null && "E".equals(flgTipoProtModulo)){
//			protocollazioneEntrataButton.show();
//		}else{
//			protocollazioneEntrataButton.hide();
//		}
//		if (flgTipoProtModulo != null && "U".equals(flgTipoProtModulo)){
//			protocollazioneUscitaButton.show();
//		}else{
//			protocollazioneUscitaButton.hide();
//		}
//		if (flgTipoProtModulo != null && "U".equals(flgTipoProtModulo)){
//			protocollazioneInternaButton.show();
//		}else{
//			protocollazioneInternaButton.hide();
//		}		
		if(filePrimarioForm != null) {
			filePrimarioForm.hide();
		}
		redrawTitleOfPortlet();
	}
	
	@Override
	public void viewMode() {
		super.viewMode();
		anteprimaModelloButton.show();
		stampaEtichettaButton.hide();
		frecciaStampaEtichettaButton.hide();
		stampaRicevutaButton.hide();
		stampaCopertinaButton.hide();		
		frecciaModificaButton.hide();
		invioPECButton.hide();
		invioPEOButton.hide();
		verificaRegistrazioneButton.hide();		
		assegnaCondividiButton.hide();
		frecciaAssegnaCondividiButton.hide();
//		assegnazioneButton.hide();
//		frecciaAssegnazioneButton.hide();
		rispondiButton.hide();
//		condivisioneButton.hide();
//		frecciaCondivisioneButton.hide();
		osservazioniNotificheButton.hide();		
		downloadDocZipButton.hide();
		apposizioneFirmaButton.hide();
		rifiutoApposizioneFirmaButton.hide();
		apposizioneVistoButton.hide();
		rifiutoApposizioneVistoButton.hide();
		revocaAttoButton.hide();
		Record detailRecord = new Record(vm.getValues());
		if (showProtocollazioneEntrataButton(detailRecord)){
			protocollazioneEntrataButton.show();
		}else{
			protocollazioneEntrataButton.hide();
		}
		if (showProtocollazioneUscitaButton(detailRecord)){
			protocollazioneUscitaButton.show();
		}else{
			protocollazioneUscitaButton.hide();
		}
		if (showProtocollazioneInternaButton(detailRecord)){
			protocollazioneInternaButton.show();
		}else{
			protocollazioneInternaButton.hide();
		}		
		if(filePrimarioForm != null) {
			if (showFilePrimarioForm() && isConProtocolloGenerale()) {
				filePrimarioForm.show();
			} else {
				filePrimarioForm.hide();
			}
		}
		redrawTitleOfPortlet();
	}
	
	@Override
	public void editMode() {
		super.editMode();
		anteprimaModelloButton.show();
		stampaEtichettaButton.hide();
		frecciaStampaEtichettaButton.hide();
		stampaRicevutaButton.hide();
		stampaCopertinaButton.hide();		
		frecciaModificaButton.hide();
		invioPECButton.hide();
		invioPEOButton.hide();
		verificaRegistrazioneButton.hide();		
		assegnaCondividiButton.hide();
		frecciaAssegnaCondividiButton.hide();
//		assegnazioneButton.hide();
//		frecciaAssegnazioneButton.hide();
		rispondiButton.hide();
//		condivisioneButton.hide();
//		frecciaCondivisioneButton.hide();
		osservazioniNotificheButton.hide();		
		downloadDocZipButton.hide();
		apposizioneFirmaButton.hide();
		rifiutoApposizioneFirmaButton.hide();
		apposizioneVistoButton.hide();
		rifiutoApposizioneVistoButton.hide();
		revocaAttoButton.hide();
//		protocollazioneEntrataButton.hide();
//		protocollazioneUscitaButton.hide();
//		protocollazioneInternaButton.hide();
		if(filePrimarioForm != null) {
			if (showFilePrimarioForm() && isConProtocolloGenerale()) {
				filePrimarioForm.show();
			} else {
				filePrimarioForm.hide();
			}
		}
		redrawTitleOfPortlet();
	}
	
	@Override
	public void redrawTitleOfPortlet() {
		String title = "";
		if (mode.equals("new")) {
			title = getNewDetailTitle();
		} else if (mode.equals("edit")) {
			title = getEditDetailTitle();
		} else if (mode.equals("view")) {
			title = getViewDetailTitle();
		}
		Layout.changeTitleOfPortlet(nomeEntita, title);
	}
	
	public String getNewDetailTitle() {		
		return "Compilazione modulo " + nomeTipoDocumento;
	}

	public String getEditDetailTitle() {
		return "Modifica modulo " + nomeTipoDocumento + " " + getTipoEstremiDocumento();
	}

	public String getViewDetailTitle() {
		return "Dettaglio modulo " + nomeTipoDocumento + " " + getTipoEstremiDocumento();
	}
	
}