/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;

import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.gestioneProcedimenti.AssegnazioneProcPopup;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;

public class ProposteOrganigrammaInIterLayout extends CustomLayout {

	protected MultiToolStripButton assegnaMultiButton;
	protected MultiToolStripButton presaInCaricoMultiButton;

	public ProposteOrganigrammaInIterLayout(String nomeEntita) {
		this(nomeEntita, null, null, null);
	}

	public ProposteOrganigrammaInIterLayout(String nomeEntita, String finalita) {
		this(nomeEntita, finalita, null, null);
	}

	public ProposteOrganigrammaInIterLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola) {
		this(nomeEntita, finalita, flgSelezioneSingola, null);
	}

	public ProposteOrganigrammaInIterLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super(nomeEntita, 
				new GWTRestDataSource("ProposteOrganigrammaInIterDatasource", "idProcedimento", FieldType.TEXT), 
				new ProposteOrganigrammaInIterFilter(nomeEntita), 
				new ProposteOrganigrammaInIterList(nomeEntita), 
				new ProposteOrganigrammaInIterDetail(nomeEntita),
				finalita, flgSelezioneSingola, showOnlyDetail);

		setMultiselect(true);
		newButton.hide();
	}

	@Override
	public boolean getDefaultDetailAuto() {
		return false;
	}

	@Override
	protected MultiToolStripButton[] getMultiselectButtons() {

		// Bottone assegna
		if (assegnaMultiButton == null) {
			assegnaMultiButton = new MultiToolStripButton("archivio/assegna.png", this, "Assegna", false) {

				@Override
				public boolean toShow() {
					return Layout.isPrivilegioAttivo("IAC/ASS");
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					final AssegnazioneProcPopup assegnazioneProcPopup = new AssegnazioneProcPopup(listaRecord) {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							Record record = new Record();
							record.setAttribute("listaRecord", listaRecord);
							record.setAttribute("listaAssegnazione", _form.getValueAsRecordList("listaAssegnazione"));
							Layout.showWaitPopup("Assegnazione in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneProcDataSource");
							try {
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaRecord, "idProcedimento", "numeroProposta",
												"Assegnazione effettuata con successo", "Tutti i record selezionati per l'assegnazione sono andati in errore!",
												"Alcuni dei record selezionati per l'assegnazione sono andati in errore!", callback);
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}
						}
					};
					assegnazioneProcPopup.show();
				}
			};
		}
		
		// Bottone prendi in carico 
		if (presaInCaricoMultiButton == null) {
			presaInCaricoMultiButton = new MultiToolStripButton("archivio/prendiInCarico.png", this, "Prendi in carico", false) {

				@Override
				public boolean toShow() {
					return Layout.isPrivilegioAttivo("IAC/PC");
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					Record record = new Record();
					record.setAttribute("listaRecord", listaRecord);
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneProcDataSource");
					try {
						lGwtRestDataSource.executecustom("presaInCarico", record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								massiveOperationWithoutCallback(response, listaRecord, "idProcedimento", "numeroProposta",
									"Presa in carico effettuata con successo", "Tutti i record selezionati per la presa in carico sono andati in errore!",
									"Alcuni dei record selezionati per la presa in carico sono andati in errore!");
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			};
		}
		
		return new MultiToolStripButton[] { assegnaMultiButton, presaInCaricoMultiButton};
	}

	public void massiveOperationCallback(DSResponse response, RecordList lista, String pkField, String nameField, String successMessage,
			String completeErrorMessage, String partialErrorMessage, DSCallback callback) {
		
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			int[] recordsToSelect = null;
			RecordList listaErrori = new RecordList();
			if (errorMessages != null && errorMessages.size() > 0) {
				recordsToSelect = new int[errorMessages.size()];
				if (lista.getLength() > errorMessages.size()) {
					errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
				} else {
					errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
				}
				int rec = 0;
				for (int i = 0; i < lista.getLength(); i++) {
					Record record = lista.get(i);
					if (errorMessages.get(record.getAttribute(pkField)) != null) {
						Record recordErrore = new Record();
						recordsToSelect[rec++] = list.getRecordIndex(record);
						errorMsg += "<br/>" + record.getAttribute(nameField) + ": " + errorMessages.get(record.getAttribute(pkField));
						recordErrore.setAttribute("idError", record.getAttribute(nameField));
						recordErrore.setAttribute("descrizione", errorMessages.get(record.getAttribute(pkField)));
						listaErrori.add(recordErrore);
					}
				}
			}
			doSearchAndSelectRecords(recordsToSelect);
			Layout.hideWaitPopup();
			if(listaErrori != null && listaErrori.getLength() > 0) {
				ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "Estremi", listaErrori, lista.getLength(), 600, 300);
				errorePopup.show();
			} else if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				if (callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		} else {
			Layout.hideWaitPopup();
		}
	}
	
	public void massiveOperationWithoutCallback(DSResponse response, RecordList lista, String pkField, String nameField, String successMessage,
			String completeErrorMessage, String partialErrorMessage) {
		
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			int[] recordsToSelect = null;
			RecordList listaErrori = new RecordList();
			if (errorMessages != null && errorMessages.size() > 0) {
				recordsToSelect = new int[errorMessages.size()];
				if (lista.getLength() > errorMessages.size()) {
					errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
				} else {
					errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
				}
				int rec = 0;
				for (int i = 0; i < lista.getLength(); i++) {
					Record record = lista.get(i);
					Record recordErrore = new Record();
					if (errorMessages.get(record.getAttribute(pkField)) != null) {
						recordsToSelect[rec++] = list.getRecordIndex(record);
						errorMsg += "<br/>" + record.getAttribute(nameField) + ": " + errorMessages.get(record.getAttribute(pkField));
						recordErrore.setAttribute("idError", record.getAttribute(pkField));
					}
					recordErrore.setAttribute("descrizione", errorMessages.get(record.getAttribute(pkField)));
					listaErrori.add(recordErrore);
				}
			}
			doSearchAndSelectRecords(recordsToSelect);
			Layout.hideWaitPopup();
			if(listaErrori != null && listaErrori.getLength() > 0) {
				ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "Estremi", listaErrori, lista.getLength(), 600, 300);
				errorePopup.show();
			} else if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				doSearch();
			}
		} else {
			Layout.hideWaitPopup();
		}
	}

}