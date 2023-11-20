/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class ListaAttributoDinamicoListaWindow extends ModalWindow {
	
	protected ListaAttributoDinamicoListaWindow window;
	protected ListaAttributoDinamicoListaDetail detail;	
	protected ToolStrip detailToolStrip;
	
	public ListaAttributoDinamicoListaWindow(ListaAttributoDinamicoListaItem gridItem, String nomeEntita, final boolean isNew, Record record, boolean canEdit) {
		
		super(nomeEntita, false);
			
		setTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_title());	
		if(record != null) {
			if(canEdit) {
				setTitle(I18NUtil.getMessages().editDetail_titlePrefix() + " " + getTipoEstremiRecord(record));				
			} else {
				setTitle(I18NUtil.getMessages().viewDetail_titlePrefix() + " " + getTipoEstremiRecord(record));	
			}			
		}
		
		window = this;
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		setHeight(500);
		setWidth(900);
		setOverflow(Overflow.AUTO);    			
		
		detail = new ListaAttributoDinamicoListaDetail(nomeEntita, gridItem){
			@Override
			public boolean isListaAttributoDinamicoListaItemGestioneContenutiTabellaTrasp() {
				return isListaAttributoDinamicoListaWindowGestioneContenutiTabellaTrasp();
			}
			
			@Override
			public boolean customValidateItemTrasp() {
				final Record recordValori = detail.getRecordToSave();
				return customValidateWindowTrasp(recordValori);
			}
		};
		
		detail.setHeight100();
		detail.setWidth100();
		
		if(canEdit) {
			
			final DetailToolStripButton saveButton = new DetailToolStripButton(getSaveButtonLabel(), getSaveButtonIcon());
			saveButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					saveButton.focusAfterGroup();
					if(detail.validate()) {
						final Record lRecord = detail.getRecordToSave();
						saveData(lRecord);		
						window.manageOnCloseClick();						
					}
				}
			});
			
			DetailToolStripButton annullaButton = new DetailToolStripButton(I18NUtil.getMessages().undoButton_prompt(), "buttons/undo.png");
			annullaButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {				
					window.manageOnCloseClick();
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
		show();
		
		if(record != null) {
			detail.editRecord(record);
		} else {
			detail.editNewRecord();
		}
		
		if (canEdit) {
			editMode();
		} else {
			viewMode();
		}
		
	}
	
	public void saveData(Record record) {
		
	}
	
	public String getTipoEstremiRecord(Record record) {
		String estremi = "";
		//TODO
		return estremi;
	}
	
	public String getSaveButtonLabel(){
		return I18NUtil.getMessages().formChooser_ok();
	}
	
	public String getSaveButtonIcon(){
		return "ok.png";
	}
	
	
	public void editRecord(Record record) {
		if(record != null) {
			detail.editRecord(record);
		} else {
			detail.editNewRecord();
		}
	}
	
	public void setCanEdit(boolean canEdit) {
		detail.setCanEdit(canEdit);
	}
	
	public void viewMode() {
		detail.viewMode();
	}
	
	public void editMode() {
		detail.editMode();
	}
	
	public boolean isListaAttributoDinamicoListaWindowGestioneContenutiTabellaTrasp(){
		return false;
	}
		
	public boolean customValidateWindowTrasp(Record recordValori){
		return true;
	}	
}