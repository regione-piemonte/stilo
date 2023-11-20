/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

public abstract class OggettoPopup extends Window {
		
	protected OggettoPopup _window;
	protected ValuesManager vm;
	
	protected DynamicForm oggettoForm;
	private TextAreaItem oggettoItem;
	
	protected Button okButton;

	public OggettoPopup(String title, Record record, boolean canEdit) {
		
		_window = this;
		
		vm = new ValuesManager();
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setOverflow(Overflow.VISIBLE);
		setAutoSize(true);
		setAutoDraw(false);
		setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);

		setTitle(title);
		setShowTitle(true);
		setHeaderIcon("buttons/altriDati.png");
		
		setAutoCenter(true);
		setHeight(120);
		setWidth(680);
		
		oggettoForm = new DynamicForm();	
		oggettoForm.setValuesManager(vm);
		oggettoForm.setKeepInParentRect(true);
		oggettoForm.setWidth100();
		oggettoForm.setHeight100();
		oggettoForm.setNumCols(8);
		oggettoForm.setColWidths(120,1,1,1,1,1,"*","*");		
		oggettoForm.setCellPadding(5);
		oggettoForm.setWrapItemTitles(false);
		
		oggettoItem = new TextAreaItem("oggetto");
		oggettoItem.setShowTitle(false);
		oggettoItem.setLength(4000);
		oggettoItem.setHeight(40);
		oggettoItem.setWidth(650);
		
		oggettoForm.setItems(oggettoItem);
		
		okButton = new Button("Ok");
		okButton.setIcon("ok.png");
		okButton.setIconSize(16);
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				onClickOkButton(new Record(vm.getValues()));
				_window.markForDestroy();
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(okButton);
		_buttons.setAutoDraw(false);
	
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		lVLayout.setPadding(5);
		lVLayout.setMembers(oggettoForm, _buttons);

		addItem(lVLayout);

		draw();
					
		editRecord(record);
		setCanEdit(canEdit);	
	}
	
	public void editRecord(Record record) {		
		oggettoForm.editRecord(record);
	}
	
	public void setCanEdit(Boolean canEdit) {
		if(canEdit) {
			okButton.show();
		} else {
			okButton.hide();
		}
		for (DynamicForm form : vm.getMembers()) {
			setCanEdit(canEdit, form);
		}
	}
	
	public void setCanEdit(boolean canEdit, DynamicForm form) {
		if (form != null) {
			for (FormItem item : form.getFields()) {
				if (!(item instanceof HeaderItem) && !(item instanceof ImgButtonItem) && !(item instanceof TitleItem)) {
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

	public abstract void onClickOkButton(Record record);
	
	@Override
	protected void onDestroy() {
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {
			}
		}
		super.onDestroy();
	}
	
}
