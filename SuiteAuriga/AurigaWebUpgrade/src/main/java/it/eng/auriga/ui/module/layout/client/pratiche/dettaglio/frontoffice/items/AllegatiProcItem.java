/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.callback.UploadMultipleItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ChangeCanEditEvent;
import it.eng.utility.ui.module.layout.client.common.NestedFormItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.file.FileMultipleUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;

public class AllegatiProcItem extends ReplicableItem{
	
	private ImgButtonItem addButton;
	private FileMultipleUploadItemWithFirmaAndMimeType uploadButton;
	private Record detailRecord;
	
	@Override
	public ReplicableCanvas getCanvasToReply() {
		AllegatoProcCanvas lAllegatoCanvas = new AllegatoProcCanvas();
		return lAllegatoCanvas;
	}
	
	public HLayout createAddButtonsLayout() {
		
		HLayout addButtonsLayout = new HLayout();
		addButtonsLayout.setMembersMargin(3);
		
		DynamicForm addButtonsForm = new DynamicForm();
		
		NestedFormItem addButtons = new NestedFormItem("add");
		addButtons.setWidth(40);
		addButtons.setNumCols(3);
		addButtons.setColWidths(16,16);
		
		addButton = new ImgButtonItem("addButton", "[SKIN]actions/add.png", I18NUtil.getMessages().newButton_prompt());
		addButton.setShowFocusedIcons(false);   
		addButton.setShowOverIcons(false);      
		addButton.setIconHeight(16);
		addButton.setIconWidth(16); 
		addButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				
				onClickNewButton();
			}
		});
		
		uploadButton = new FileMultipleUploadItemWithFirmaAndMimeType(new UploadMultipleItemCallBackHandler() {
			@Override
			public void uploadEnd(String displayFileName, String uri, String numFileCaricatiInUploadMultiplo ) {
				ReplicableCanvas lReplicableCanvas = onClickNewButton();				
				lReplicableCanvas.getForm()[0].setValue("displayName", displayFileName);
				lReplicableCanvas.getForm()[0].setValue("uri",  uri);	
				lReplicableCanvas.getForm()[0].setValue("displayNameTif", "");
				lReplicableCanvas.getForm()[0].setValue("uriTif",  "");				
				lReplicableCanvas.getForm()[0].setValue("remoteUri", false);
				lReplicableCanvas.getForm()[0].setValue("numFileCaricatiInUploadMultiplo", numFileCaricatiInUploadMultiplo);
				((AllegatoProcCanvas)lReplicableCanvas).changedEventAfterUpload(displayFileName, uri);
//				uploadButton.redrawPrettyFileMultipleUploadInput();
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
					AllegatoProcCanvas lLastReplicableCanvas = (AllegatoProcCanvas) getLastCanvas();
					InfoFileRecord precInfo = lLastReplicableCanvas.getForm()[0].getValue("infoFile") != null ? new InfoFileRecord(lLastReplicableCanvas.getForm()[0].getValue("infoFile")) : null;
					String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
					lLastReplicableCanvas.getForm()[0].setValue("infoFile", info);	
					String displayName = lLastReplicableCanvas.getForm()[0].getValueAsString("displayName");
					String displayNameOrig = lLastReplicableCanvas.getForm()[0].getValueAsString("displayNameOrig");
					if(precImpronta == null || !precImpronta.equals(info.getImpronta()) || 
							(displayName != null && !"".equals(displayName) &&
									displayNameOrig != null && !"".equals(displayNameOrig) &&
									!displayName.equals(displayNameOrig))) {					
						lLastReplicableCanvas.getForm()[0].setValue("isChanged", true);
					}
					if (info.isFirmato() && !info.isFirmaValida()){
						GWTRestDataSource.printMessage(new MessageBean("Il file presenta una firma non valida alla data odierna", "", MessageType.WARNING));
					}
					lLastReplicableCanvas.getAllegatoButtons().redraw();
					
				}
			}
		);
		uploadButton.setCanFocus(false);
		uploadButton.setTabIndex(-1);	
		
		addButtons.setNestedFormItems(addButton, uploadButton);
		
		addButtonsForm.setItems(addButtons);
		addButtonsForm.setMargin(0);
		addButtonsLayout.addMember(addButtonsForm);
		
		return addButtonsLayout;
	}

	public boolean canBeEditedByApplet(){
		return false;
	}

	public boolean showUpload() {
		return true;
	}

	public boolean showAcquisisciDaScanner() {
		return true;
	}

	public boolean sonoInMail() {
		return false;
	}

	public Record getDetailRecord() {
		return detailRecord;
	}

	public void setDetailRecord(Record detailRecord) {
		this.detailRecord = detailRecord;
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		
		setEditing(canEdit);
		if(getCanvas() != null) {
			final VLayout lVLayout = getVLayout();  		
			for (int i=0;i<lVLayout.getMembers().length; i++){
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if(lVLayoutMember instanceof HLayout) {
					for(Canvas lHLayoutMember : ((HLayout)lVLayoutMember).getMembers()) {
						if((lHLayoutMember instanceof ImgButton) || (lHLayoutMember instanceof DynamicForm)) {
							if(canEdit) {
								lHLayoutMember.show();
							} else {
								lHLayoutMember.hide();
							}					
						} else if(lHLayoutMember instanceof ReplicableCanvas) {
							ReplicableCanvas lReplicableCanvas = (ReplicableCanvas)lHLayoutMember;
							lReplicableCanvas.setCanEdit(canEdit);
						}
					}	
				}			
			}
		}
		this.fireEvent(new ChangeCanEditEvent(instance));
	}
	
	public Date getDataRifValiditaFirma() {
		return null;
	}

}
