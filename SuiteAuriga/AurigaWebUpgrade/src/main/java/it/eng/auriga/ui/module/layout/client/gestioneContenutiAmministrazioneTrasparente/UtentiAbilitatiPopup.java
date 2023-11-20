/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.types.Alignment;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;

import it.eng.utility.ui.module.layout.client.Layout;

public abstract class UtentiAbilitatiPopup extends ModalWindow {

	protected UtentiAbilitatiPopup window;
	
	protected ValuesManager vm;

	// DynamicForm
	private DynamicForm formMain;
	
	protected ListaUtentiAbilitatiContenutiAmmTraspItem listaPrivilegiEntitaAbilitateItem;
	protected HiddenItem idSezioneItem;
	
	public UtentiAbilitatiPopup(String titleIn, String iconIn) {

		super("utenti_abilitati_popup", true);

		window = this;
		
		vm = new ValuesManager();	
		
		setTitle(titleIn);
		setIcon(iconIn);
		
		setAutoCenter(true);
		setHeight(350);
		setWidth(650);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		formMain = new DynamicForm();
		formMain.setValuesManager(vm);
		formMain.setWidth("100%");
		formMain.setHeight("5");
		formMain.setPadding(5);
		formMain.setNumCols(5);
		formMain.setColWidths(10, 10, 10, 10, "*");
		formMain.setWrapItemTitles(false);		

		idSezioneItem = new HiddenItem("idSezione");
		
		listaPrivilegiEntitaAbilitateItem = new ListaUtentiAbilitatiContenutiAmmTraspItem("listaPrivilegiEntitaAbilitate") {
			
			@Override
			public String getIdSezione() {
				return (idSezioneItem.getValue() !=null ? idSezioneItem.getValue().toString() : null);
			}
			
			@Override
			public boolean isShowEditButtons() {
				return true;
			}
			
			@Override
			public boolean isShowNewButton() {
				return false;
			}
			
			@Override
			public boolean isShowDeleteButton() {
				return true;
			}
			
			@Override
			public boolean isShowModifyButton() {
				return false;
			}
			
			@Override
			public boolean isEditable() {
				return true;
			}
			
			@Override
			public boolean isShowPreference() {
				return true;
			}
		};
		
		listaPrivilegiEntitaAbilitateItem.setStartRow(true);
		listaPrivilegiEntitaAbilitateItem.setShowTitle(false);
		listaPrivilegiEntitaAbilitateItem.setHeight(245);
		formMain.setItems(idSezioneItem,listaPrivilegiEntitaAbilitateItem);
				
		final Button okButton = new Button("Ok");   
		okButton.setIcon("ok.png");
		okButton.setIconSize(16); 
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				okButton.focusAfterGroup();				
				if(formMain.validate()) {						
					final Record formRecord = getRecordToSave();
					onClickOkButton(formRecord, new DSCallback() {	
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							window.markForDestroy();
						}
					});
				}				
			}
		});
		
		Button annullaButton = new Button("Annulla");   
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16); 
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {	
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				window.markForDestroy();	
			}
		});

				
		HStack _buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(okButton);
		_buttons.addMember(annullaButton);	
		_buttons.setAutoDraw(true);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET 
		VLayout portletLayout = new VLayout();  
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.AUTO);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		
		portletLayout.addMember(formMain);	
		
		portletLayout.addMember(spacerLayout);
		portletLayout.addMember(_buttons);
		
		setBody(portletLayout);		
	}
	
	public Record getFormValuesAsRecord() {
		return formMain.getValuesAsRecord();
	}

	public Record getRecordToSave() {
		Record lRecordToSave = new Record();
		// Salvo i form
		addFormValues(lRecordToSave, formMain);
		return lRecordToSave;
	}
	
	protected static void addFormValues(Record record, DynamicForm form) {
		if (form != null) {
			try {
				JSOHelper.addProperties(record.getJsObj(), form.getValuesAsRecord().getJsObj());
			} catch (Exception e) {
			}
		}
	}
	
	public void setCanEdit(boolean canEdit) {
		for (DynamicForm form : vm.getMembers()) {
			setCanEdit(canEdit, form);
		}
	}
		
	public void setCanEdit(boolean canEdit, DynamicForm form) {
		if (form != null) {
			form.setEditing(canEdit);
			for (FormItem item : form.getFields()) {
				if (!(item instanceof HeaderItem) && !(item instanceof ImgButtonItem) && !(item instanceof TitleItem)) {
					if (item instanceof DateItem || item instanceof DateTimeItem) {
						TextItem textItem = new TextItem();
						textItem.setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly);
						if (item instanceof DateItem) {
							((DateItem) item).setTextFieldProperties(textItem);
						} else if (item instanceof DateTimeItem) {
							((DateTimeItem) item).setTextFieldProperties(textItem);
						}
					} else {
						item.setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly);
					}
					item.setCanEdit(canEdit);
					item.redraw();
				}
				if (item instanceof ImgButtonItem || item instanceof PrettyFileUploadItem) {
					item.setCanEdit(canEdit);
					item.redraw();
				}
			}
		}
	}
		
	@Override
	public void manageOnCloseClick() {		
		if(getIsModal()) {
			markForDestroy();
		} else {
			Layout.removePortlet(getNomeEntita());
		}	
	}
	
	public void setValues(Record values) {
		formMain.editRecord(values);
		vm.editRecord(values);
		markForRedraw();
	}
	
	public abstract void onClickOkButton(Record formRecord, DSCallback callback);
}