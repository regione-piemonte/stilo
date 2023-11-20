/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.ComboBoxItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class IndirizziEmailXlsCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;

//	private TextItem campoXlsItem;
	private HiddenItem  tipoMittenteItem;
	private ComboBoxItem campoXlsItem;
	private TextItem colonnaXlsItem;
	
	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(4);
		
		tipoMittenteItem = new HiddenItem("tipoMittente");
		
		campoXlsItem = new ComboBoxItem("campoXls", I18NUtil.getMessages().dettagli_xls_indirizzi_email_canvas_campoXlsItem());
		campoXlsItem.setWidth(150);
		campoXlsItem.setAttribute("obbligatorio", true);
		campoXlsItem.setAddUnknownValues(true);
		LinkedHashMap<String, String> mappaCampoXls = new LinkedHashMap<String, String>();
		mappaCampoXls.put("IndirizziEmailTo", "IndirizziEmailTo");
		mappaCampoXls.put("IndirizziEmailCC", "IndirizziEmailCC");
		if(((IndirizziEmailXlsItem) getItem()) != null && !((IndirizziEmailXlsItem) getItem()).isCasellaPec()){
			mappaCampoXls.put("IndirizziEmailCCN", "IndirizziEmailCCN");
		}
		campoXlsItem.setValueMap(mappaCampoXls);
		
		CustomValidator lColonnaXlsValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if (value == null || "".equals((String) value))
					return true;
				RegExp regExp = RegExp.compile("^[A-Z]");
				return regExp.test((String) value);
			}
		};
		
		colonnaXlsItem = new TextItem("colonnaXls", I18NUtil.getMessages().dettagli_xls_indirizzi_email_canvas_colonnaXlsItem());
		colonnaXlsItem.setWidth(90);
		colonnaXlsItem.setLength(1);
		colonnaXlsItem.setCharacterCasing(CharacterCasing.UPPER);
		colonnaXlsItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				if(tipoMittenteItem != null && !"PEC".equals((String)tipoMittenteItem.getValue())) {
					
					LinkedHashMap<String, String> mappaCampoXls = new LinkedHashMap<String, String>();
					mappaCampoXls.put("IndirizziEmailTo", "IndirizziEmailTo");
					mappaCampoXls.put("IndirizziEmailCC", "IndirizziEmailCC");
					mappaCampoXls.put("IndirizziEmailCCN", "IndirizziEmailCCN");
					
					campoXlsItem.setValueMap(mappaCampoXls);
				}
				return "IndirizziEmailTo".equals(campoXlsItem.getValueAsString());
			}
		}), lColonnaXlsValidator);
		
		mDynamicForm.setFields(tipoMittenteItem, campoXlsItem, colonnaXlsItem);
		mDynamicForm.setColWidths("50", "100", "50", "100");
		addChild(mDynamicForm);
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}
}