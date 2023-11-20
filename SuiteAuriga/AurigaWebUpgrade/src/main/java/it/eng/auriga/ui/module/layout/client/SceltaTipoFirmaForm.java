/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;

public class SceltaTipoFirmaForm extends DynamicForm {

	private SceltaTipoFirmaPopup window;
	private DynamicForm instance;

	private RadioGroupItem tipoFirmaItem;

	public SceltaTipoFirmaForm(String defaultTipoFirma, final SceltaTipoFirmaPopup pWindow, final ServiceCallback<Record> callback) {

		instance = this;

		window = pWindow;

		setKeepInParentRect(true);
		setWidth(300);
		setHeight100();
		setNumCols(3);
		setCellPadding(5);
		setAlign(Alignment.CENTER);
		setTop(50);
		
		LinkedHashMap<String, String> typeMap = new LinkedHashMap<String, String>();
		typeMap.put("PAdES", "PDF (PAdES)");
		typeMap.put("CAdES", "P7M (CAdES)");
		
		tipoFirmaItem = new RadioGroupItem("tipologiaFirma", "Tipologia di firma");
		tipoFirmaItem.setWrap(false);
		tipoFirmaItem.setWrapTitle(false);
		tipoFirmaItem.setVertical(false);
		tipoFirmaItem.setRequired(true);
		tipoFirmaItem.setStartRow(true);
		tipoFirmaItem.setValueMap(typeMap);
		tipoFirmaItem.setAlign(Alignment.CENTER);
		
		if(defaultTipoFirma.equals("CAdES")) {
			tipoFirmaItem.setDefaultValue("CAdES");
		}
		else if(defaultTipoFirma.equals("PAdES")) {
			tipoFirmaItem.setDefaultValue("PAdES");
		}
		
		SpacerItem spacer = new SpacerItem();
		spacer.setHeight(0);
		spacer.setStartRow(true);

		setFields(spacer, tipoFirmaItem, spacer);
		 
		window.show();

	}

}
