/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;

import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;

/**
 * 
 * @author dbe4235
 *
 */

public class TSOInIterLayout extends CustomLayout {
	
	protected MultiToolStripButton assegnazioneMultiButton;

	public TSOInIterLayout(String nomeEntita) {
		this(nomeEntita, null, null, null);
	}

	public TSOInIterLayout(String nomeEntita, String finalita) {
		this(nomeEntita, finalita, null, null);
	}

	public TSOInIterLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola) {
		this(nomeEntita, finalita, flgSelezioneSingola, null);
	}

	public TSOInIterLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super(nomeEntita, 
				new GWTRestDataSource("TSOInIterDatasource", "idProcedimento", FieldType.TEXT), 
				new TSOInIterFilter(nomeEntita),
				new TSOInIterList(nomeEntita), 
				new CustomDetail(nomeEntita), finalita, flgSelezioneSingola, showOnlyDetail);

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
		if (assegnazioneMultiButton == null) {
			assegnazioneMultiButton = new MultiToolStripButton("archivio/assegna.png", this, "Assegnazione", false) {

				@Override
				public boolean toShow() {
					return Layout.isPrivilegioAttivo("TSO/ASS");
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					final AssegnatarioTSOVisurePopup assegnatarioTSOVisurePopup = new AssegnatarioTSOVisurePopup(
							(listaRecord.getLength() == 1) ? listaRecord.get(0) : null) {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							Record record = new Record();
							record.setAttribute("listaRecord", listaRecord);
							record.setAttribute("idAssegnatario", _form.getValue("idAssegnatario"));
							record.setAttribute("descrizioneAssegnatario", _form.getValue("descrizioneAssegnatario"));
							Layout.showWaitPopup("Assegnazione in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("SelezionaAssegnatarioTSODataSource");
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
					assegnatarioTSOVisurePopup.show();
				}
			};
		}
		
		return new MultiToolStripButton[] { assegnazioneMultiButton};
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
