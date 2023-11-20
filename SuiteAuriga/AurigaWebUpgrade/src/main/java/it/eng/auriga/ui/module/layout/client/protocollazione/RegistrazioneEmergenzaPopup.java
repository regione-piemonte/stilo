/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;

public abstract class RegistrazioneEmergenzaPopup extends Window {
		
	protected RegistrazioneEmergenzaPopup _window;
	protected DynamicForm _form;
	
	protected TextItem rifRegEmergenzaItem;
	protected NumericItem nroRegEmergenzaItem;
	protected DateTimeItem dataRegEmergenzaItem;
	protected ButtonItem okButton;
	
	public RegistrazioneEmergenzaPopup(boolean canEdit){
		
		_window = this;
				
		setTitle(I18NUtil.getMessages().protocollazione_detail_registrazioneEmergenzaButton_prompt());
		setShowTitle(true);		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(580);
		setHeight(90);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowMaximizeButton(true);
		setShowMinimizeButton(true);	
		
		_form = new DynamicForm();													
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(8);
		_form.setColWidths(100,1,1,1,1,1,"*","*");
		_form.setCellPadding(5);
		_form.setWrapItemTitles(false);
		
		rifRegEmergenzaItem = new TextItem("rifRegEmergenza", I18NUtil.getMessages().protocollazione_detail_rifRegEmergenzaItem_title());
		rifRegEmergenzaItem.setWidth(150);   	    	       
        
		nroRegEmergenzaItem = new NumericItem("nroRegEmergenza", I18NUtil.getMessages().protocollazione_detail_nroRegEmergenzaItem_title());
		nroRegEmergenzaItem.setLength(7);
        
		dataRegEmergenzaItem = new DateTimeItem("dataRegEmergenza", I18NUtil.getMessages().protocollazione_detail_dataRegEmergenzaItem_title());  					
						
		okButton = new ButtonItem();
		okButton.setTitle(I18NUtil.getMessages().protocollazione_detail_okButton_title());
		okButton.setIcon("ok.png");
		okButton.setIconHeight(16);
		okButton.setIconWidth(16);
		okButton.setColSpan(8);
		okButton.setWidth(100);
		okButton.setTop(20);
		okButton.setAlign(Alignment.CENTER);
			
		okButton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {			
			@Override
			public void onClick(
					com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				
				onClickOkButton();
				_window.markForDestroy();	
			}
		});
		 
		_form.setFields(new FormItem[]{
	        	new TitleItem(I18NUtil.getMessages().protocollazione_detail_regEmergenzaItem_title()), 
	        	rifRegEmergenzaItem, 
	        	nroRegEmergenzaItem,
	        	dataRegEmergenzaItem,
				okButton});
		
		setAlign(Alignment.CENTER);
		setTop(50);
		
		addItem(_form);		
		setShowTitle(true);
		setHeaderControls(HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);
		
		setCanEdit(canEdit);	
	}
	
	public void setCanEdit(Boolean canEdit) {
		if(canEdit) {
			okButton.show();
		} else {
			okButton.hide();
		}
		setCanEdit(canEdit, _form);
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

	public abstract void onClickOkButton();
	
}
