/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class AvvioProcedimentoWindow extends ModalWindow{
	protected AvvioProcedimentoWindow _window;

	private AvvioProcedimentoDetail detail;

	protected  ToolStrip detailToolStrip;
	protected  DetailToolStripButton editButton;
	public     DetailToolStripButton saveButton;
	protected  DetailToolStripButton reloadDetailButton;
	protected  DetailToolStripButton undoButton;
	protected String mode;

	public AvvioProcedimentoWindow() {
		super("avvio_procedimento", true);

		_window = this;
		
		setTitle("Avvio procedimento");
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		//TODO
		detail = new AvvioProcedimentoDetail("AvvioProcedimentoDetail");
		detail.setHeight100();

		/*
		 * BUTTON DETAIL
		 */

		editButton = new DetailToolStripButton(I18NUtil.getMessages().modifyButton_prompt(), "buttons/modify.png");
		editButton.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {   
				editMode();        		
			}   
		}); 

		saveButton = new DetailToolStripButton(I18NUtil.getMessages().saveButton_prompt(), "buttons/save.png");
		saveButton.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) { 
				onSaveButtonClick();				
			}   
		}); 		

		reloadDetailButton = new DetailToolStripButton(I18NUtil.getMessages().reloadDetailButton_prompt(), "buttons/reloadDetail.png");
		reloadDetailButton.addClickHandler(new ClickHandler() {   
			@Override
			public void onClick(ClickEvent event) {  
				reload(new DSCallback() {					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						editMode();
					}
				});				
			}   
		}); 

		undoButton = new DetailToolStripButton(I18NUtil.getMessages().undoButton_prompt(), "buttons/undo.png");
		undoButton.addClickHandler(new ClickHandler() {   
			@Override
			public void onClick(ClickEvent event) {   
				reload(new DSCallback() {					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						viewMode();				
					}
				});				
			}   
		}); 

		detailToolStrip = new ToolStrip();   
		detailToolStrip.setWidth100();       
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); //push all buttons to the right 
		detailToolStrip.addButton(editButton);
		detailToolStrip.addButton(saveButton);
		detailToolStrip.addButton(reloadDetailButton);
		detailToolStrip.addButton(undoButton);		

		VLayout detailLayout = new VLayout();  
		detailLayout.setOverflow(Overflow.HIDDEN);		
		setOverflow(Overflow.AUTO);    			
		
		detailLayout.setMembers(detail, detailToolStrip);		
		
		detailLayout.setHeight100();
		detailLayout.setWidth100();		
		setBody(detailLayout);
		
		newMode();
		setHeight(300);
		
//		portletLayout.setHeight100();
//		portletLayout.setWidth100();		
//		setBody(portletLayout);
//	
//		portletLayout.newMode();								
		
        setIcon("menu/avvio_procedimento.png");   
        show();
	}
	
	public void nuovoDettaglio() {
		setTitle("Nuovo procedimento");
		detail.editNewRecord();	
		newMode();		
	}	
	
	public void visualizzaDettaglio(Record record) {
		setTitle("Dettaglio procedimento " + getTipoEstremiRecord(record));
		detail.editRecord(record);	
		viewMode();		
	}	
	
	public void modificaDettaglio(Record record) {
		setTitle("Modifica procedimento " + getTipoEstremiRecord(record));
		detail.editRecord(record);	
		editMode();		
	}	
	
	public void onSaveButtonClick() {
		final Record record = new Record(detail.getValuesManager().getValues());
		if(detail.validate()) {
				realSave(record);
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}
	
	protected void realSave(final Record record) {
		Layout.showWaitPopup("Avvio procedimento in corso...");
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AvvioProcedimentoActivitiDatasource");
		lGwtRestService.call(record, new ServiceCallback<Record>() {
			@Override
			public void execute(Record object) {
				Layout.hideWaitPopup();
				Layout.addMessage(new MessageBean("Procedimento avviato con successo", "", MessageType.INFO));
				markForDestroy();
				Record lRecord = new Record();
				lRecord.setAttribute("idFolder", object.getAttribute("idFolderPadre"));
				lRecord.setAttribute("idDetail", object.getAttribute("idFolder"));
				FascicoloProcedimentoWindow lFascicoloProcedimentoWindow = new FascicoloProcedimentoWindow(lRecord);
				lFascicoloProcedimentoWindow.show();
			}
		});
		
	}
	
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		((GWTRestDataSource)detail.getDataSource()).getData(record, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
					Record record = response.getData()[0];
					detail.editRecord(record, recordNum);	
					detail.getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				} 				
			}
		});
	}	
	
	public String getTipoEstremiRecord(Record record) {		
		return record.getAttribute("nomeProfilo");
	}
	
	public void reload(final DSCallback callback) {
		if(this.mode.equals("new")) {
			detail.editNewRecord();
			detail.getValuesManager().clearErrors(true);
		} else {
			Record record = new Record(detail.getValuesManager().getValues());			
			((GWTRestDataSource)detail.getDataSource()).getData(record, new DSCallback() {															
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						detail.editRecord(record);	
						detail.getValuesManager().clearErrors(true);
						callback.execute(response, null, new DSRequest());
					} 				
				}
			});	
		}		
	}
	
	public boolean isAbilToMod() {
		
		return Layout.isPrivilegioAttivo("SIC/PR;M");
	}
	
	public void newMode() {
		this.mode = "new";
		detail.setCanEdit(true);		
		editButton.hide();
		saveButton.show();
		reloadDetailButton.hide();
		undoButton.hide();	
	}

	public void viewMode() {
		this.mode = "view";
		detail.setCanEdit(false);			
		saveButton.hide();
		reloadDetailButton.hide();
		undoButton.hide();		
		if(isAbilToMod()) {
			editButton.show();					
		} else {
			editButton.hide();
		}
	}

	public void editMode() {
		this.mode = "edit";
		detail.setCanEdit(true);		
		editButton.hide();
		saveButton.show();
		reloadDetailButton.show();
		undoButton.show();
	}
	
	@Override
	public void manageOnCloseClick() {
		
		if(getIsModal()) {
			markForDestroy();
		} else {
			Layout.removePortlet(getNomeEntita());
		}	
	}

}
