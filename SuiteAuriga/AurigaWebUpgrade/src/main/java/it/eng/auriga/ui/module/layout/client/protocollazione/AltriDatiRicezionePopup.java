/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

public abstract class AltriDatiRicezionePopup extends Window {
		
	protected AltriDatiRicezionePopup _window;
	protected DynamicForm _form;
	
	protected SelectItem mezzoTrasmissioneItem;
	protected TitleItem raccommandataTitleItem;
	protected TextItem nroRaccomandataItem;
	protected DateItem dataRaccomandataItem;
	protected ButtonItem okButton;
	protected HiddenItem idRegistrazione;
	public AltriDatiRicezionePopup(boolean canEdit, String idRegistrazione){
		
		_window = this;

		setTitle(I18NUtil.getMessages().protocollazione_detail_altriDatiRicezioneButton_prompt());
		setShowTitle(true);		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(600);
		setHeight(150);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowMaximizeButton(true);
		setShowMinimizeButton(true);			
		
		_form = new DynamicForm();													
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(8);
		_form.setColWidths(120,1,1,1,1,1,"*","*");		
		_form.setCellPadding(5);
		_form.setWrapItemTitles(false);
		
		//Mezzi di trasmissione
		GWTRestDataSource mezziTrasmissioneDS = new GWTRestDataSource("LoadComboMezziTrasmissioneDataSource", "key", FieldType.TEXT);
		
		mezziTrasmissioneDS.extraparam.put("idRegistrazione", idRegistrazione != null ? idRegistrazione : null);
		
		mezzoTrasmissioneItem = new SelectItem("mezzoTrasmissione", I18NUtil.getMessages().protocollazione_detail_mezzoTrasmissioneItem_title());   
		mezzoTrasmissioneItem.setOptionDataSource(mezziTrasmissioneDS);  
		mezzoTrasmissioneItem.setAutoFetchData(true);
		mezzoTrasmissioneItem.setDisplayField("value");
		mezzoTrasmissioneItem.setValueField("key");			
		mezzoTrasmissioneItem.setWrapTitle(false);
		mezzoTrasmissioneItem.setStartRow(true);
		if(isRequiredMezzoTrasmissione()) {
			mezzoTrasmissioneItem.setRequired(true);
		} else {
			mezzoTrasmissioneItem.setAllowEmptyValue(true);
		}
		mezzoTrasmissioneItem.setWidth(270);   
		mezzoTrasmissioneItem.setColSpan(8);		
		mezzoTrasmissioneItem.addChangedHandler(new ChangedHandler() {				
			@Override
			public void onChanged(ChangedEvent event) {
				if (!"R".equals(_form.getValueAsString("mezzoTrasmissione"))){
					_form.setValue("nroRaccomandata", "");
					_form.setValue("dataRaccomandata", "");
				}
								
				_form.redraw();
			}
		});			
				
		raccommandataTitleItem = new TitleItem(I18NUtil.getMessages().protocollazione_detail_raccomandataItem_title());
		raccommandataTitleItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return _form.getValue("mezzoTrasmissione") != null && "R".equals(_form.getValueAsString("mezzoTrasmissione"));
			}
		});
        
		nroRaccomandataItem = new TextItem("nroRaccomandata", I18NUtil.getMessages().protocollazione_detail_nroRaccomandataItem_title());
		nroRaccomandataItem.setWidth(100);
		nroRaccomandataItem.setLength(50);
		nroRaccomandataItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return _form.getValue("mezzoTrasmissione") != null && "R".equals(_form.getValueAsString("mezzoTrasmissione"));
			}
		});
        
		dataRaccomandataItem = new DateItem("dataRaccomandata", I18NUtil.getMessages().protocollazione_detail_dataRaccomandataItem_title());  					
		dataRaccomandataItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return _form.getValue("mezzoTrasmissione") != null && "R".equals(_form.getValueAsString("mezzoTrasmissione"));
			}
		});
		
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
				if(_form.validate()) {
					onClickOkButton();
					_window.markForDestroy();
				}
			}
		});
		 
		_form.setFields(new FormItem[]{	        	
	        	mezzoTrasmissioneItem, 
	        	raccommandataTitleItem,
	        	nroRaccomandataItem,
	        	dataRaccomandataItem,
				okButton});
		
		setAlign(Alignment.CENTER);
		setTop(50);
		
		addItem(_form);		
		setShowTitle(true);
		setHeaderControls(HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);
			
		setCanEdit(canEdit);	
	}
	
	public boolean isRequiredMezzoTrasmissione() {
		return false;
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