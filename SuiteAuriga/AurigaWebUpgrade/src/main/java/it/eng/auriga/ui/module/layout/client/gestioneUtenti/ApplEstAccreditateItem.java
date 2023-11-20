/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.Record;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public abstract class ApplEstAccreditateItem extends ReplicableItem {	
	
	private GestioneUtentiDetail detail;
	private Record detailRecord;
	
	public ApplEstAccreditateItem(GestioneUtentiDetail detail) {
		setDetail(detail);
		setDetailRecord(new Record(detail.getValuesManager().getValues()));
	}
	
	public GestioneUtentiDetail getDetail() {
		return detail;
	}
	
	public void setDetail(GestioneUtentiDetail detail) {
		this.detail = detail;
	}
	
	@Override
	public ReplicableCanvas getCanvasToReply() {		
		ApplEstAccreditateCanvas lApplEstAccreditateCanvas = new ApplEstAccreditateCanvas();
		return lApplEstAccreditateCanvas;
	}
	
	@Override
	public void onClickHandlerNewButton() {
		Record record = new Record(detail.getValuesManager().getValues());
		String denominazioneUtente   = record.getAttribute("cognome") +  " " + record.getAttribute("nome");
		String username = record.getAttribute("username");
		
		String title = "Associazione " + denominazioneUtente + "["+username+"]" +" vs applicazione abilitata ai servizi"; 
		
		
		
		AgganciaUtenteApplicazioneEstPopup agganciaUtenteApplicazioneEstPopup = new AgganciaUtenteApplicazioneEstPopup(record, title, "new") {
			@Override
			public void onClickOkButton(Record formRecord, DSCallback callback) {	
				ApplEstAccreditateCanvas lastCanvas = (ApplEstAccreditateCanvas) getLastCanvas();
				if(lastCanvas != null && !lastCanvas.isChanged()) {
					lastCanvas.setFormValuesFromRecordAfterNew(formRecord);
				} else {
					ApplEstAccreditateCanvas canvas = (ApplEstAccreditateCanvas) onClickNewButton();
					canvas.setFormValuesFromRecordAfterNew(formRecord);
				}	
				markForDestroy();
			}
		};
		agganciaUtenteApplicazioneEstPopup.show();
	}
	
	/*
	@Override
	public void setUpClickRemoveHandler(final VLayout lVLayout, final HLayout lHLayout) {
		int index = -1;
		// Individuo il relativo HLayout
		for (int i = 0; i < lVLayout.getMembers().length; i++) {
			if (lVLayout.getMember(i).getID().equals(lHLayout.getID())) {
				index = i;
			}
		}
		if (index>=0){
			final int indexremove = index;
			SC.ask(I18NUtil.getMessages().gestioneutenti_applEstAccred_deleteButtonAsk_message(), new BooleanCallback() {
				@Override
				public void execute(Boolean value) {
					if(value != null && value) {
						Layout.showWaitPopup("Cancellazione in corso: potrebbe richiedere qualche secondo. Attendere…");
						try {
							HLayout lHLayout = (HLayout) lVLayout.getMember(indexremove);
							ReplicableCanvas lReplicableCanvas = getReplicableCanvas(lHLayout);
							Record recordDel = new Record();
							recordDel = lReplicableCanvas.getFormValuesAsRecord();	
							Record recordToLoad = new Record();
							recordToLoad.setAttribute("codiceApplEst",      (recordDel.getAttribute("codiceApplEst") !=null ? recordDel.getAttributeAsString("codiceApplEst") : null));
							recordToLoad.setAttribute("codiceIstAppl",      (recordDel.getAttribute("codiceIstAppl") !=null ? recordDel.getAttributeAsString("codiceIstAppl") : null));
							RecordList recordListaUtentiXml = new RecordList();
							Record recordUtente = new Record();
							recordUtente.setAttribute("idUser",   recordDel.getAttribute("idUtenteApplEst"));
							recordUtente.setAttribute("desUser",  (String)null);
							recordUtente.setAttribute("username", recordDel.getAttribute("usernameUtenteApplEst"));
							recordListaUtentiXml.add(recordUtente);
							recordToLoad.setAttribute("listaUtentiXml", recordListaUtentiXml);
							final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGestioneUtentiDataSource", "codiceUo", FieldType.TEXT);
							lGwtRestDataSource.executecustom("TogliAccredUsersInApplEst", recordToLoad, new DSCallback() {
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == RPCResponse.STATUS_SUCCESS) {
										Layout.addMessage(new MessageBean("Cancellazione effettuata con successo", "Cancellazione effettuata con successo", MessageType.INFO));
										Layout.hideWaitPopup();
										// Lo rimuovo
										lVLayout.removeMember(lVLayout.getMember(indexremove));
									}
								}
							});
						}
						catch (Exception e) {
							Layout.hideWaitPopup();
						}
					}
				}
			});
		}
	}*/

	public abstract boolean isViewMode();
	public abstract boolean isEditMode();
	public abstract boolean isNewMode();
	public abstract String getMode();
	
	public Record getDetailRecord() {
		return new Record(detail.getValuesManager().getValues());
	}

	public void setDetailRecord(Record detailRecord) {
		this.detailRecord = detailRecord;
	}
}
