/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;

public class LibroFirmaMassivaLayout extends CustomLayout {

	protected MultiToolStripButton apponiFirmaMultiButton;
	protected MultiToolStripButton rifiutaFirmaMultiButton;

	protected RecordList recordSelezionati;

	public LibroFirmaMassivaLayout() {
		super("libro_firma", new GWTRestDataSource("LibroFirmaMassivaDatasource", true, "idFile", FieldType.TEXT),
				new ConfigurableFilter("libro_firma"), new LibroFirmaMassivaList("libro_firma"),
				new CustomDetail("libro_firma"));

		setMultiselect(true);
		newButton.hide();
	}

	@Override
	public boolean showMultiselectButtonsUnderList() {
		return true;
	}

	@Override
	protected MultiToolStripButton[] getMultiselectButtons() {
		// Bottone firma massiva
		if (apponiFirmaMultiButton == null) {
			apponiFirmaMultiButton = new MultiToolStripButton("file/mini_sign.png", this, "Firma", false) {

				@Override
				public boolean toShow() {
					return true;
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					Record lRecordSelezionati = new Record();
					lRecordSelezionati.setAttribute("listaRecord", listaRecord);
					firmaMassiva(lRecordSelezionati);
				}
			};
		}

		// Bottone rifiuta firma massiva
		if (rifiutaFirmaMultiButton == null) {
			rifiutaFirmaMultiButton = new MultiToolStripButton("attiInLavorazione/azioni/rimuoviSottoscrizioneCons.png", this, "Rifiuta firma", false) {

				@Override
				public boolean toShow() {
					return true;
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					Record lRecordSelezionati = new Record();
					lRecordSelezionati.setAttribute("listaRecord", listaRecord);
					rifiutaFirma(lRecordSelezionati, "rifiuto_firma");
				}
			};
		}
		return new MultiToolStripButton[] { apponiFirmaMultiButton, rifiutaFirmaMultiButton };
	}

	protected void rifiutaFirma(Record lRecordSelezionati, String string) {
		Criteria[] lCriteriaArray =getFilter().getCriteria().getCriteria();
		String firmatario = "";
		if (lCriteriaArray.length > 0) {
			for (Criteria lCritetria : lCriteriaArray) {
				String fieldName = lCritetria.getAttributeAsString("fieldName");
				if (fieldName.equalsIgnoreCase("firmatario")) {
					firmatario = lCritetria.getAttributeAsString("value");
				}
			}
		}
		getDatasource().extraparam.put("firmatario", firmatario);
		recordSelezionati = lRecordSelezionati.getAttributeAsRecordList("listaRecord");
		getDatasource().performCustomOperation("rifiutaFirma", lRecordSelezionati, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData() != null && response.getData().length > 0 ? response.getData()[0] : new Record();
					Map errorMessages = data.getAttribute("errorMessages") != null ? data.getAttributeAsMap("errorMessages") : new HashMap<>();
					customMassiveOperationCallback(errorMessages, recordSelezionati.getLength());
					doSearch();
				}
			}
		}, new DSRequest());
		
	}

	public void firmaMassiva(final Record lRecordSelezionati) {
		recordSelezionati = lRecordSelezionati.getAttributeAsRecordList("listaRecord");
		getDatasource().performCustomOperation("getListaPerFirmaMassiva", lRecordSelezionati, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					firmaFile(response.getData()[0]);
				}
			}
		}, new DSRequest());
	}

	private void firmaFile(Record record) {
		final Record[] filesAndUd = record.getAttributeAsRecordArray("files");
		final Record[] filesInError = record.getAttributeAsRecordArray("daNonTrasmettere");
		Map<String, Record> lMap = new HashMap<String, Record>();
		for (Record lRecordFile : filesAndUd) {
			lMap.put(lRecordFile.getAttribute("idFile"), lRecordFile);
		}
		final Map<String, String> errorMessagesIter = new HashMap<>();
		for (Record lRecordInError : filesInError) {
			String idFileInError = lRecordInError.getAttribute("idFile");
			for (int i = 0; i < recordSelezionati.getLength(); i++) {
				Record lRecordSelected = recordSelezionati.get(i);
				String idFile = lRecordSelected.getAttribute("idFile");
				if (idFile.equals(idFileInError)) {
					String nomeFile = lRecordSelected.getAttribute("estremiNroAtto") != null && !"".equals(lRecordSelected.getAttributeAsString("estremiNroAtto")) ? lRecordSelected.getAttributeAsString("estremiNroAtto") : lRecordSelected.getAttributeAsString("estremiNroProvvisorio");
					errorMessagesIter.put(nomeFile, "Errore durante il recupero del file");
				}
			}
		}
		String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
		String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
		FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, filesAndUd, new FirmaMultiplaCallback() {

			@Override
			public void execute(Map<String, Record> files, Record[] filesAndUd) {
				manageFirmaCallBack(files, filesAndUd, errorMessagesIter);

			}
		});
	}

	protected void manageFirmaCallBack(final Map<String, Record> files, final Record[] filesAndUd, final Map<String, String> errorMessagesIter) {
		Layout.showWaitPopup("Elaborazione dei documenti firmati in corso...");
		final Record lRecord = new Record();
		Record[] lRecords = new Record[files.size()];
		int i = 0;
		for (String lString : files.keySet()) {
			Record recordToInsert = files.get(lString);
			for (Record recordConUd : filesAndUd) {
				if (recordConUd.getAttribute("idFile").equals(lString)) {
					recordToInsert.setAttribute("idUd", recordConUd.getAttribute("idUd"));
					//					recordToInsert.setAttribute("uriVerPreFirma", recordConUd.getAttribute("uri"));
					//					recordToInsert.setAttribute("nomeFileVerPreFirma", recordConUd.getAttribute("nomeFile"));
					//					InfoFileRecord infoFile = recordConUd.getAttribute("infoFile") != null ? new InfoFileRecord(recordConUd.getAttribute("infoFile")) : null;
					//					recordToInsert.setAttribute("infoFileVerPreFirma", infoFile);
				}
			}
			lRecords[i] = recordToInsert;
			i++;
		}
		lRecord.setAttribute("files", lRecords);
		Criteria[] lCriteriaArray =getFilter().getCriteria().getCriteria();
		String firmatario = null;
		if (lCriteriaArray.length > 0) {
			for (Criteria lCritetria : lCriteriaArray) {
				String fieldName = lCritetria.getAttributeAsString("fieldName");
				if (fieldName.equalsIgnoreCase("firmatario")) {
					firmatario = lCritetria.getAttributeAsString("value");
				}
			}
		}
		lRecord.setAttribute("commonName", recordSelezionati != null && recordSelezionati.getLength() > 0 ? firmatario : null);
		getDatasource().performCustomOperation("verificaDocumentoFirmato", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lCallbackRecord = response.getData()[0];
					Record[] lRecordsKO = lCallbackRecord.getAttributeAsRecordArray("daNonTrasmettere");
					
					for (Record lRecordInError : lRecordsKO) {
						String idFileInError = lRecordInError.getAttribute("idFile");
						for (int i = 0; i < recordSelezionati.getLength(); i++) {
							Record lRecordSelected = recordSelezionati.get(i);
							String idFile = lRecordSelected.getAttribute("idFile");
							if (idFile.equals(idFileInError)) {
								String nomeFile = lRecordSelected.getAttribute("estremiNroAtto") != null && !"".equals(lRecordSelected.getAttributeAsString("estremiNroAtto")) ? lRecordSelected.getAttributeAsString("estremiNroAtto") : lRecordSelected.getAttributeAsString("estremiNroProvvisorio");
								errorMessagesIter.put(nomeFile, "Errore durante la firma del file");
							}
						}
					}
					
					getDatasource().extraparam.put("firmatario", lRecord.getAttributeAsString("commonName"));
					getDatasource().performCustomOperation("apponiFirma", lCallbackRecord, new DSCallback() {

						@Override
						public void execute(DSResponse dsResponse, Object rawData, DSRequest dsRequest) {
							if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record data = dsResponse.getData() != null && dsResponse.getData().length > 0 ? dsResponse.getData()[0] : new Record();
								Map errorMessages = data.getAttribute("errorMessages") != null ? data.getAttributeAsMap("errorMessages") : new HashMap<>();
								errorMessages.putAll(errorMessagesIter);
								customMassiveOperationCallback(errorMessages, recordSelezionati.getLength());
								doSearch();
							}
						}
					});

				} else {
					Layout.addMessage(new MessageBean("Si è verificato un errore durante la firma dei documenti", "", MessageType.ERROR));
				}
			}
		}, new DSRequest());
	}
	
	
	public void customMassiveOperationCallback(Map errorMessages, int recorSelezionati) {
		
		RecordList listaErrori = new RecordList();
		List<String> listKey = new ArrayList<String>(errorMessages.keySet());

		for(String keyRecordError : listKey) {
			Record recordErrore = new Record();
			recordErrore.setAttribute("idError", keyRecordError);
			recordErrore.setAttribute("descrizione",errorMessages.get(keyRecordError));
			listaErrori.add(recordErrore);
		}

		if (listaErrori != null && listaErrori.getLength() > 0) {
			ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "Atto",
					listaErrori, recorSelezionati, 600, 300);
			errorePopup.show();
		} else { 
			Layout.addMessage(new MessageBean("Operazione effettuato con successo", "", MessageType.INFO));
		}
	}
	
	@Override
	protected Record[] extractRecords(String[] fields) {
		// Se sono in overflow i dati verranno recuperati con il metodo asincrono,
		// altrimenti utilizzo quelli nella lista a GUI
		if (overflow){
			return new Record[0];
		}else{
			return super.extractRecords(fields);
		}
	}
	
	@Override
	protected GWTRestDataSource createNroRecordDatasource() {
		GWTRestDataSource libroFirmaMassivaDS = new GWTRestDataSource("LibroFirmaMassivaDatasource", "idFile", FieldType.TEXT);
		return libroFirmaMassivaDS;
	}
	
}
