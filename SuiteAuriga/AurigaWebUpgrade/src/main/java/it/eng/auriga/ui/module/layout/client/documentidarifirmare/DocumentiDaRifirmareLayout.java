/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaCommonNameCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;

public class DocumentiDaRifirmareLayout extends CustomLayout {

	protected MultiToolStripButton firmaMultiButton;

	protected RecordList recordSelezionati;

	public DocumentiDaRifirmareLayout() {
		super("documenti_da_rifirmare", new GWTRestDataSource("DocumentiDaRifirmareDatasource", true, "idFile", FieldType.TEXT),
				new ConfigurableFilter("documenti_da_rifirmare"), new DocumentiDaRifirmareList("documenti_da_rifirmare"),
				new CustomDetail("documenti_da_rifirmare"));

		setMultiselect(true);
		newButton.hide();
	}

	@Override
	public boolean showMultiselectButtonsUnderList() {
		return true;
	}

	@Override
	protected MultiToolStripButton[] getMultiselectButtons() {
		// Bottone firma massiva volumi
		if (firmaMultiButton == null) {
			firmaMultiButton = new MultiToolStripButton("file/mini_sign.png", this, "Firma", false) {

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
		return new MultiToolStripButton[] { firmaMultiButton };
	}

	public void firmaMassiva(Record lRecordSelezionati) {
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
		Map<String, Record> lMap = new HashMap<String, Record>();
		for (Record lRecordFile : filesAndUd) {
			lMap.put(lRecordFile.getAttribute("idFile"), lRecordFile);
		}
		
		FirmaUtility.firmaMultiplaCommonName(filesAndUd, new FirmaMultiplaCommonNameCallback() {
			
			@Override
			public void execute(Map<String, Record> files, Record[] filesAndUd, String commonName) {
				manageFirmaCallBack(files, filesAndUd);
				
			}
		});
	}

	protected void manageFirmaCallBack(final Map<String, Record> files, final Record[] filesAndUd) {
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
		lRecord.setAttribute("commonName", recordSelezionati != null && recordSelezionati.getLength() > 0 ? recordSelezionati.get(0).getAttribute("firmatario") : null);
		
		String customOperation = AurigaLayout.getParametroDBAsBoolean("ATTIVA_ARCHIVIAZIONE_DOCUMENTI_DA_RIFIRMARE") ? "aggiornaDocumentoFirmato" : "verificaDocumentoFirmato";
		getDatasource().performCustomOperation(customOperation, lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					//TODO
					Record lCallbackRecord = response.getData()[0];
					Record[] lRecordsInError = lCallbackRecord.getAttributeAsRecordArray("files");
					List<String> documentiInError = new ArrayList<String>();
					if (lRecordsInError.length > 0) {
						for (Record lRecordInError : lRecordsInError) {
							String idFileInError = lRecordInError.getAttribute("idFile");
							for (int i = 0; i < recordSelezionati.getLength(); i++) {
								Record lRecordSelected = recordSelezionati.get(i);
								String idFile = lRecordSelected.getAttribute("idFile");
								if (idFile.equals(idFileInError)) {
									String nomeFile = lRecordSelected.getAttribute("nomeFile");
									documentiInError.add(nomeFile);
								}
							}
						}
						if (documentiInError.size() > 0) {
							if (documentiInError.size() == files.size()) {
								if (documentiInError.size() == 1) {
									Layout.addMessage(new MessageBean("Il documento selezionato non è stato firmato correttamente", "", MessageType.ERROR));
								} else {
									Layout.addMessage(
											new MessageBean("Tutti i documenti selezionati non sono stati firmati correttamente", "", MessageType.ERROR));
								}
							} else {
								String message = null;
								if (documentiInError.size() == 1) {
									message = "Il documento " + documentiInError.get(0) + " non è stato firmato correttamente";
								} else {
									message = "I documenti ";
									boolean first = true;
									for (String lStrDoc : documentiInError) {
										if (first)
											first = false;
										else
											message += ", ";
										message += lStrDoc;
									}
									message += " non sono stati firmati correttamente";
								}
								Layout.addMessage(new MessageBean(message, "", MessageType.WARNING));
								doSearch();
							}
						}
					} else {
						Layout.addMessage(new MessageBean("I documenti selezionati sono stati firmati con successo", "", MessageType.INFO));
						doSearch();
					}
				} else {
					Layout.addMessage(new MessageBean("Si è verificato un errore durante la firma dei documenti", "", MessageType.ERROR));
				}
			}
		}, new DSRequest());
	}

}
