/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class AllegatoWindow extends ModalWindow {
	
	protected AllegatoWindow window;
	protected AllegatoDetail detail;	
	protected ToolStrip detailToolStrip;
	
	DetailToolStripButton saveButton;
	DetailToolStripButton annullaButton;
	
	protected boolean canEdit;
	protected boolean isNew;
	
	public AllegatoWindow(AllegatiGridItem gridItem, String nomeEntita, final boolean isNew, Record record, boolean canEdit) {
		
		super(nomeEntita, false);
		
		setTitle("Aggiungi allegati");	
		if(record != null) {
			if(canEdit) {
				setTitle(I18NUtil.getMessages().editDetail_titlePrefix() + " allegato N° " + getTipoEstremiRecord(record));				
			} else {
				setTitle(I18NUtil.getMessages().viewDetail_titlePrefix() + " allegato N° " + getTipoEstremiRecord(record));	
			}			
		}
		
		window = this;
		this.isNew = isNew;
		this.canEdit = canEdit;
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		setHeight(500);
		setWidth(900);
		setOverflow(Overflow.AUTO);    			
		
		detail = new AllegatoDetail(nomeEntita, gridItem, record, canEdit) {
			
			@Override
			public boolean isEnablePreviewModal() {
				return gridItem.isShowModalPreview();
			}
		};		
		detail.setHeight100();
		detail.setWidth100();
		
		if(canEdit) {
			
			saveButton = new DetailToolStripButton(I18NUtil.getMessages().formChooser_ok(), "ok.png");
			saveButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					saveButton.focusAfterGroup();
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			    		
						@Override
						public void execute() {
							manageOnSaveButtonClick();	
						}
			    	});
				}
			});
			
			annullaButton = new DetailToolStripButton(I18NUtil.getMessages().undoButton_prompt(), "buttons/undo.png");
			annullaButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {				
					manageOnAnnullaButtonClick();
				}
			});
			
			ToolStrip detailToolStrip = new ToolStrip();
			detailToolStrip.setWidth100();
			detailToolStrip.setHeight(30);
			detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
			detailToolStrip.addFill(); // push all buttons to the right		
			detailToolStrip.addButton(saveButton);
			detailToolStrip.addButton(annullaButton);
			
			VLayout detailLayout = new VLayout();  
			detailLayout.setOverflow(Overflow.HIDDEN);		
			detailLayout.setHeight100();
			detailLayout.setWidth100();		
			detailLayout.setMembers(detail, detailToolStrip);	
			
			setBody(detailLayout);
		} else {
			setBody(detail);
		}
		
		setIcon("blank.png");		
	}
	
	public void manageOnSaveButtonClick() {
		if(detail.validate()) {
			Layout.showWaitPopup("Aggiunta allegati in corso...");														
			final Record lRecord = detail.getRecordToSave();
			RecordList listaAllegati = lRecord.getAttributeAsRecordList("listaAllegati");
			if(isNew) {
				addAllegati(listaAllegati);
			} else {
				updateAllegato(listaAllegati.get(0));
			}
			window.markForDestroy();	
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}
	
	public void manageOnAnnullaButtonClick() {
		window.markForDestroy();
	}
	
	@Override
	public void manageOnCloseClick() {
		if (getCanEdit()) {
			SC.ask("Vuoi mantenere eventuali dati/file aggiunti o modificati?", new BooleanCallback() {
				
				@Override
				public void execute(Boolean value) {
					if(value) {
						saveButton.focusAfterGroup();
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				    		
							@Override
							public void execute() {
								manageOnSaveButtonClick();	
							}
				    	});
					} else {
						manageOnAnnullaButtonClick();
					}				
				}
			});
		} else {
			manageOnAnnullaButtonClick();
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Layout.hideWaitPopup();
	}
	
	private boolean getCanEdit() {
		return canEdit;
	}

	public void addAllegati(RecordList listaRecord) {
		
	}
	
	public void updateAllegato(Record record) {
		
	}
	
	public String getTipoEstremiRecord(Record record) {	
		return record.getAttribute("numeroProgrAllegato");
	}
	
}
