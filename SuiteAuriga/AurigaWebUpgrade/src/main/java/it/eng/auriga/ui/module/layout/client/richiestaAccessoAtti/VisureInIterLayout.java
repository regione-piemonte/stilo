/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;

import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;

public class VisureInIterLayout extends CustomLayout {

	//protected MultiToolStripButton assegnaMultiButton;
	protected MultiToolStripButton impostaRespIstruttoria;

	public VisureInIterLayout(String nomeEntita) {
		this(nomeEntita, null, null, null);
	}

	public VisureInIterLayout(String nomeEntita, String finalita) {
		this(nomeEntita, finalita, null, null);
	}

	public VisureInIterLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola) {
		this(nomeEntita, finalita, flgSelezioneSingola, null);
	}

	public VisureInIterLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super(nomeEntita, 
				new GWTRestDataSource("VisureInIterDatasource", "idProcedimento", FieldType.TEXT), 
				new VisureInIterFilter(nomeEntita),
				new VisureInIterList(nomeEntita), 
				new VisureInIterDetail(nomeEntita), finalita, flgSelezioneSingola, showOnlyDetail);

		setMultiselect(true);
		newButton.hide();
	}

	@Override
	public boolean getDefaultDetailAuto() {
		return false;
	}

	@Override
	protected MultiToolStripButton[] getMultiselectButtons() {

		// Bottone assegnazione massiva visure
//		if (assegnaMultiButton == null) {
//			assegnaMultiButton = new MultiToolStripButton("archivio/assegna.png", this, "Assegna", false) {
//
//				@Override
//				public boolean toShow() {
//					return true;
//				}
//
//				@Override
//				public void doSomething() {
//					final RecordList listaRecord = new RecordList();
//					for (int i = 0; i < list.getSelectedRecords().length; i++) {
//						listaRecord.add(list.getSelectedRecords()[i]);
//					}
//					final AssegnazioneVisurePopup assegnazioneVisurePopup = new AssegnazioneVisurePopup((listaRecord.getLength() == 1) ? listaRecord.get(0) : null) {
//
//						@Override
//						public void onClickOkButton(final DSCallback callback) {
//							
//							Record record = new Record();
//							record.setAttribute("listaRecord", listaRecord);
//							record.setAttribute("listaAssegnazione", _form.getValueAsRecordList("listaAssegnazione"));
//							Layout.showWaitPopup("Assegnazione in corso: potrebbe richiedere qualche secondo. Attendere…");
//							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneVisureDataSource");
//							try {
//								lGwtRestDataSource.addData(record, new DSCallback() {
//
//									@Override
//									public void execute(DSResponse response, Object rawData, DSRequest request) {
//										massiveOperationCallback(response, listaRecord, "idProcedimento", "numeroProposta",
//												"Assegnazione effettuata con successo", "Tutti i record selezionati per l'assegnazione sono andati in errore!",
//												"Alcuni dei record selezionati per l'assegnazione sono andati in errore!", callback);
//									}
//								});
//							} catch (Exception e) {
//								Layout.hideWaitPopup();
//							}
//						}
//					};
//					assegnazioneVisurePopup.show();
//				}
//			};
//		}
		if(impostaRespIstruttoria == null) {
			impostaRespIstruttoria = new MultiToolStripButton("protocollazione/flagSoggettiGruppo/O2.png", this, I18NUtil.getMessages().visure_in_iter_impostaRespIstruttoriaButtonMassivo(),
					false) {

				@Override
				public boolean toShow() {
					return Layout.isPrivilegioAttivo("RAA/SMR");
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					final SelezioneRicercatoreVisurePopup responsabiliIstruttorieVisurePopup = new SelezioneRicercatoreVisurePopup(
							(listaRecord.getLength() == 1) ? listaRecord.get(0) : null) {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							
							Record record = new Record();
							record.setAttribute("listaRecord", listaRecord);
							record.setAttribute("idUtenteRicercatore", _form.getValue("idUtenteRicercatore"));
							record.setAttribute("cognomeNomeRicercatore", _form.getValue("cognomeNomeRicercatore"));
							Layout.showWaitPopup("Assegnazione a ricercatore in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("SelezionaRicercatoreVisureDataSource");
							try {
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaRecord, "idProcedimento", "numeroProposta",
												"Assegnazione a ricercatore effettuata con successo", "Tutti i record selezionati per l'assegnazione a ricercatore sono andati in errore!",
												"Alcuni dei record selezionati per l'assegnazione a ricercatore sono andati in errore!", callback);
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}
						}
					};
					responsabiliIstruttorieVisurePopup.show();
				}
			};
		}
		return new MultiToolStripButton[] {/*assegnaMultiButton,*/impostaRespIstruttoria};

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
	
	@Override
	protected GWTRestDataSource createNroRecordDatasource() {

		GWTRestDataSource dataSource = (GWTRestDataSource) getList().getDataSource();
		dataSource.setForceToShowPrompt(false);

		return dataSource;
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
}