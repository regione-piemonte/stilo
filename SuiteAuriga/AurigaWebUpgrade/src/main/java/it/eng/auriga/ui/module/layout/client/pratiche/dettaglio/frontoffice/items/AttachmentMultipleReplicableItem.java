/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.callback.UploadMultipleItemCallBackHandler;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.file.FileMultipleUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;

public class AttachmentMultipleReplicableItem extends ReplicableItem{


	FileMultipleUploadItemWithFirmaAndMimeType uploadButton;
	public  InfoFileRecord infoFileRecord;

	@Override
	public ReplicableCanvas getCanvasToReply() {
		return new AttachmentMultipleCanvas();		
	}

	public HLayout createAddButtonsLayout() {
		HLayout addButtonsLayout = new HLayout();
		addButtonsLayout.setMembersMargin(3);
		DynamicForm addButtonsForm = new DynamicForm();
		uploadButton = new FileMultipleUploadItemWithFirmaAndMimeType(new UploadMultipleItemCallBackHandler() {

			@Override
			public void uploadEnd(String displayFileName, String uri, String numFileCaricatiInUploadMultiplo) {
				ReplicableCanvas lReplicableCanvas = onClickNewButton();				
				lReplicableCanvas.getForm()[0].setValue("fileNameAttach", displayFileName);
				lReplicableCanvas.getForm()[0].setValue("uriAttach",  uri);	
				lReplicableCanvas.getForm()[0].setValue("numFileCaricatiInUploadMultiplo", numFileCaricatiInUploadMultiplo);
				((AttachmentMultipleCanvas)lReplicableCanvas).changedEventAfterUpload(displayFileName, uri);
				uploadButton.redrawPrettyFileMultipleUploadInput();

			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file";
				if(error != null && !"".equals(error)) errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));		
				uploadButton.getCanvas().redraw();

			}}, new ManageInfoCallbackHandler() {
				@Override
				public void manageInfo(InfoFileRecord info) {
					infoFileRecord = info;
					//				    	InfoFileRecord precInfo = _form.getValue("infoFile") != null ? new InfoFileRecord(_form.getValue("infoFile")) : null;
					//						String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
					//						_form.setValue("infoFile", info);	
					//						String nomeFileAllegato = _form.getValueAsString("nomeFileAllegato");
					//						String nomeFileAllegatoOrig = _form.getValueAsString("nomeFileAllegatoOrig");
					//						if(precImpronta == null || !precImpronta.equals(info.getImpronta()) || 
					//								(nomeFileAllegato != null && !"".equals(nomeFileAllegato) &&
					//										nomeFileAllegatoOrig != null && !"".equals(nomeFileAllegatoOrig) &&
					//										!nomeFileAllegato.equals(nomeFileAllegatoOrig))) {					
					//							_form.setValue("isChanged", true);
					//						}
					//						if (info.isFirmato() && !info.isFirmaValida()){
					//							GWTRestDataSource.printMessage(new MessageBean("Il file presenta una firma non valida alla data odierna", "", MessageType.WARNING));
					//						}
				}
			});

		uploadButton.setCanFocus(false);
		uploadButton.setTabIndex(-1);	
		addButtonsForm.setItems(uploadButton);
		addButtonsForm.setMargin(0);
		addButtonsLayout.addMember(addButtonsForm);		
		return addButtonsLayout;
	}


	public void downloadFile(ServiceCallback<Record> lDsCallback){

	}

	public InfoFileRecord getInfoFileRecord() {
		return infoFileRecord;
	}
}
