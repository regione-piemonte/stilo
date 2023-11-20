/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.FormItemInputTransformer;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class CIGCanvas extends ReplicableCanvas {
	
	private TextItem codiceCIGItem;
	private TextItem codiceCUPItem;
	private TextItem numGaraItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public CIGCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(7);
		
		CustomValidator codiceCIGLengthValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value != null && !"".equals((String) value)) {
					String valore = (String) value;
					return valore.length() == 10;
				}
				return true;
			}
		};
		codiceCIGLengthValidator.setErrorMessage("Il codice CIG, se indicato, deve essere di 10 caratteri");
		
		codiceCIGItem = new TextItem("codiceCIG", "CIG");
		if(!((CIGItem) getItem()).showCUP()) {
			codiceCIGItem.setShowTitle(false);
		}
		codiceCIGItem.setWidth(160);
		codiceCIGItem.setLength(10);
		codiceCIGItem.setInputTransformer(new FormItemInputTransformer() {
			
			@Override
			public Object transformInput(DynamicForm form, FormItem item, Object value, Object oldValue) {
				return value != null ? ((String)value).trim().replaceAll(" ", "") : null;
			}
		});
		codiceCIGItem.setAttribute("obbligatorio", true);
		codiceCIGItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				Boolean notReplicable = getItem().getNotReplicable() != null ? getItem().getNotReplicable() : false;
				Boolean obbligatorio = getItem().getAttributeAsBoolean("obbligatorio") != null ? getItem().getAttributeAsBoolean("obbligatorio") : false;
				return !notReplicable || obbligatorio;
			}
		}), codiceCIGLengthValidator);
		
		CustomValidator codiceCUPLengthValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value != null && !"".equals((String) value)) {
					String valore = (String) value;
					return valore.length() == 15;
				}
				return true;
			}
		};
		codiceCUPLengthValidator.setErrorMessage("Il codice CUP, se indicato, deve essere di 15 caratteri");	
		
		codiceCUPItem = new TextItem("codiceCUP", "CUP");
		codiceCUPItem.setWidth(160);
		codiceCUPItem.setLength(15);
		codiceCUPItem.setInputTransformer(new FormItemInputTransformer() {
			
			@Override
			public Object transformInput(DynamicForm form, FormItem item, Object value, Object oldValue) {
				return value != null ? ((String)value).trim().replaceAll(" ", "") : null;
			}
		});
		codiceCUPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((CIGItem) getItem()).showCUP();
			}
		});
		codiceCUPItem.setValidators(codiceCUPLengthValidator);	
		
		numGaraItem = new TextItem("numGara", ((CIGItem) getItem()).getTitleNumGara());
		numGaraItem.setWidth(150);
		numGaraItem.setDefaultValue(((CIGItem) getItem()).getDefaultValueNumGara());		
		if(((CIGItem) getItem()).isRequiredNumGara()) {
			numGaraItem.setAttribute("obbligatorio", true);
		}
		numGaraItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((CIGItem) getItem()).isRequiredNumGara();
			}
		}));
		numGaraItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((CIGItem) getItem()).showNumGara();
			}
		});
					
		mDynamicForm.setFields(codiceCIGItem, codiceCUPItem, numGaraItem);	
		
		addChild(mDynamicForm);
		
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
}