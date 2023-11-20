/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

public abstract class FeedbackSuPropostaAssegnazionePopup extends Window {
		
	protected FeedbackSuPropostaAssegnazionePopup _window;
	protected DynamicForm _form;
	
	
	protected CheckboxItem flgConfermaItem;
	protected CheckboxItem flgVariazioneItem;
	protected CheckboxItem flgMancataAssegnazioneDefItem;
	protected NumericItem giorniTrascorsiItem;
	protected ButtonItem okButton;
	
	public FeedbackSuPropostaAssegnazionePopup(Boolean canEdit){
		
		_window = this;
				
		setTitle(I18NUtil.getMessages().protocollazione_feedbackSuPropostaAssegnazionePopup_title());
		setShowTitle(true);		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(500);
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
		_form.setColWidths(1,1,1,1,1,1,"*","*");
		_form.setCellPadding(5);
		_form.setWrapItemTitles(false);
		
		flgConfermaItem = new CheckboxItem("flgConferma", I18NUtil.getMessages().protocollazione_feedbackSuPropostaAssegnazionePopup_flgConfermaItem_title());
		flgConfermaItem.setColSpan(1);
		flgConfermaItem.setStartRow(true);
		
		flgVariazioneItem = new CheckboxItem("flgVariazione",   I18NUtil.getMessages().protocollazione_feedbackSuPropostaAssegnazionePopup_flgVariazioneItem_title());
		flgVariazioneItem.setColSpan(1);
		flgVariazioneItem.setStartRow(true);
		
		flgMancataAssegnazioneDefItem = new CheckboxItem("flgMancataAssegnazioneDef",   I18NUtil.getMessages().protocollazione_feedbackSuPropostaAssegnazionePopup_flgMancataAssegnazioneDefItem_title());
		flgMancataAssegnazioneDefItem.setColSpan(1);
		flgMancataAssegnazioneDefItem.setWidth(1);
		flgMancataAssegnazioneDefItem.setStartRow(true);
		flgMancataAssegnazioneDefItem.addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {
				
				_form.redraw();
			}
		});
		
		giorniTrascorsiItem = new NumericItem("giorniTrascorsi", I18NUtil.getMessages().protocollazione_feedbackSuPropostaAssegnazionePopup_giorniTrascorsiItem_title());  					
		giorniTrascorsiItem.setColSpan(1);
		giorniTrascorsiItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return flgMancataAssegnazioneDefItem.getValueAsBoolean();
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
				
				onClickOkButton();
				_window.markForDestroy();	
			}
		});	
		 
		_form.setFields(new FormItem[]{
				flgConfermaItem, 
				flgVariazioneItem,
				flgMancataAssegnazioneDefItem,
	        	giorniTrascorsiItem,
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
