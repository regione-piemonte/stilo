/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class DestinatariNotificaPECCanvas extends ReplicableCanvas {
	
	protected TextItem descrizioneItem;
	protected TextItem indirizzoPECItem;
	protected TextItem notaItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public DestinatariNotificaPECCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(20);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		descrizioneItem = new TextItem("descrizione", ((DestinatariNotificaPECItem) getItem()).getTitleDescrizioneDestinatariNotificaPEC());
		descrizioneItem.setWidth(200);
		descrizioneItem.setColSpan(1);
		descrizioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestinatariNotificaPECItem)getItem()).isRequiredDescrizioneDestinatariNotificaPEC()) {
					descrizioneItem.setAttribute("obbligatorio", true);
					descrizioneItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestinatariNotificaPECItem)getItem()).getTitleDescrizioneDestinatariNotificaPEC()));
				} else {					
					descrizioneItem.setAttribute("obbligatorio", false);
					descrizioneItem.setTitle(((DestinatariNotificaPECItem)getItem()).getTitleDescrizioneDestinatariNotificaPEC());					
				}								
				return ((DestinatariNotificaPECItem) getItem()).showDescrizioneDestinatariNotificaPEC();
			}
		});
		descrizioneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestinatariNotificaPECItem) getItem()).isRequiredDescrizioneDestinatariNotificaPEC();
			}
		}));	
		
		indirizzoPECItem = new TextItem("indirizzoPEC", ((DestinatariNotificaPECItem) getItem()).getTitleIndirizzoPECDestinatariNotificaPEC());
		indirizzoPECItem.setWidth(200);
		indirizzoPECItem.setColSpan(1);
		indirizzoPECItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestinatariNotificaPECItem)getItem()).isRequiredIndirizzoPECDestinatariNotificaPEC()) {
					indirizzoPECItem.setAttribute("obbligatorio", true);
					indirizzoPECItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestinatariNotificaPECItem)getItem()).getTitleIndirizzoPECDestinatariNotificaPEC()));
				} else {					
					indirizzoPECItem.setAttribute("obbligatorio", false);
					indirizzoPECItem.setTitle(((DestinatariNotificaPECItem)getItem()).getTitleIndirizzoPECDestinatariNotificaPEC());					
				}								
				return ((DestinatariNotificaPECItem) getItem()).showIndirizzoPECDestinatariNotificaPEC();
			}
		});
		indirizzoPECItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestinatariNotificaPECItem) getItem()).isRequiredIndirizzoPECDestinatariNotificaPEC();
			}
		}), new CustomValidator() {			
			@Override
			protected boolean condition(Object value) {		
				if(((DestinatariNotificaPECItem) getItem()).showIndirizzoPECDestinatariNotificaPEC()) {
					if(value == null || "".equals((String) value)) return true;
					RegExp regExp = RegExp.compile(RegExpUtility.indirizzoEmailRegExp());
					return regExp.test((String) value);		
				}
				return true;
			}
		});
		
		notaItem = new TextItem("nota", ((DestinatariNotificaPECItem) getItem()).getTitleNotaDestinatariNotificaPEC());
		notaItem.setDefaultValue(((DestinatariNotificaPECItem) getItem()).getDefaultValueNotaDestinatariNotificaPEC());
		notaItem.setWidth(280);
		notaItem.setColSpan(1);
		notaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestinatariNotificaPECItem)getItem()).isRequiredNotaDestinatariNotificaPEC()) {
					notaItem.setAttribute("obbligatorio", true);
					notaItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((DestinatariNotificaPECItem)getItem()).getTitleNotaDestinatariNotificaPEC()));
				} else {					
					notaItem.setAttribute("obbligatorio", false);
					notaItem.setTitle(((DestinatariNotificaPECItem)getItem()).getTitleNotaDestinatariNotificaPEC());					
				}								
				return ((DestinatariNotificaPECItem) getItem()).showNotaDestinatariNotificaPEC();
			}
		});
		notaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestinatariNotificaPECItem) getItem()).isRequiredNotaDestinatariNotificaPEC();
			}
		}));	
		
		mDynamicForm.setFields(descrizioneItem, indirizzoPECItem, notaItem);
					
		addChild(mDynamicForm);		
	}
	
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		descrizioneItem.setCanEdit(((DestinatariNotificaPECItem)getItem()).isEditableDescrizioneDestinatariNotificaPEC() ? canEdit : false);
		indirizzoPECItem.setCanEdit(((DestinatariNotificaPECItem)getItem()).isEditableIndirizzoPECDestinatariNotificaPEC() ? canEdit : false);
		notaItem.setCanEdit(((DestinatariNotificaPECItem)getItem()).isEditableNotaDestinatariNotificaPEC() ? canEdit : false);		
	}
	
}
