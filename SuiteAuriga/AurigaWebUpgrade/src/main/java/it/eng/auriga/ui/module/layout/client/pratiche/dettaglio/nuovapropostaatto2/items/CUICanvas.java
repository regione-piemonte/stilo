/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.FormItemInputTransformer;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class CUICanvas extends ReplicableCanvas {
	
	private TextItem codiceCUIItem;
	private AnnoItem annoRifItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public CUICanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(5);
		
		codiceCUIItem = new TextItem("codiceCUI", "CUI");
		if(!((CUIItem) getItem()).showAnnoRif()) {
			codiceCUIItem.setShowTitle(false);
		}
		if(AurigaLayout.isAttivoClienteCOTO()) {
			codiceCUIItem.setWidth(350);
			codiceCUIItem.setLength(21);
		} else {
			codiceCUIItem.setWidth(160);
			codiceCUIItem.setLength(10);
		}
		codiceCUIItem.setInputTransformer(new FormItemInputTransformer() {
			
			@Override
			public Object transformInput(DynamicForm form, FormItem item, Object value, Object oldValue) {
				return value != null ? ((String)value).trim().replaceAll(" ", "") : null;
			}
		});
		codiceCUIItem.setAttribute("obbligatorio", true);
		codiceCUIItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				Boolean notReplicable = getItem().getNotReplicable() != null ? getItem().getNotReplicable() : false;
				Boolean obbligatorio = getItem().getAttributeAsBoolean("obbligatorio") != null ? getItem().getAttributeAsBoolean("obbligatorio") : false;
				return !notReplicable || obbligatorio;
			}
		}));
		
		annoRifItem = new AnnoItem("annoRif", ((CUIItem) getItem()).getTitleAnnoRif() != null ? ((CUIItem) getItem()).getTitleAnnoRif() : "Anno rif.");
		annoRifItem.setDefaultValue(((CUIItem) getItem()).getDefaultValueAnnoRif());
		annoRifItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((CUIItem) getItem()).showAnnoRif();
			}
		});
		if(((CUIItem) getItem()).isRequiredAnnoRif()) {
			annoRifItem.setAttribute("obbligatorio", true);
			annoRifItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
	
				@Override
				public boolean execute(FormItem formItem, Object value) {
					Boolean notReplicable = getItem().getNotReplicable() != null ? getItem().getNotReplicable() : false;
					Boolean obbligatorio = getItem().getAttributeAsBoolean("obbligatorio") != null ? getItem().getAttributeAsBoolean("obbligatorio") : false;
					return !notReplicable || obbligatorio;
				}
			}));
		}
					
		mDynamicForm.setFields(codiceCUIItem, annoRifItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
}