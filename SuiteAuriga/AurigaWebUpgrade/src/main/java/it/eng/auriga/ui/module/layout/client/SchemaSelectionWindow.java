/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;

import it.eng.utility.Styles;

public class SchemaSelectionWindow extends Window {

	// private static Logger logger = java.util.logging.Logger.getLogger("SchemaSelectionWindow");
	private AurigaIndex index;
	private DynamicForm form;

	public SchemaSelectionWindow(AurigaIndex pIndex) {
		index = pIndex;
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(370);
		setHeight(140);
		setKeepInParentRect(true);
		setTitle("Selezione schema");
		setShowModalMask(true);
		setModalMaskStyle(Styles.loginModalMask);
		setShowCloseButton(false);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		form = new SchemaSelectionForm(this);
		addItem(form);
		setShowTitle(true);
	}

	public void proceedAsUsual(String schemaSelected) {
		if (form.validate()) {
			index.proceedAsUsual(schemaSelected);
			this.markForDestroy();
		}
	}

	public void setSchema(LinkedHashMap<String, String> lHashMap, Object defaultSchema) {
		FormItem fi = form.getField("schema");
		fi.setValueMap(lHashMap);
		if (index.schemaSelezionato == null) {
			fi.setValue(defaultSchema);
		} else {
			if (lHashMap.size() > 1) {
				if (lHashMap.get(index.schemaSelezionato) != null) {
					fi.setValue(index.schemaSelezionato);
				} else {
					fi.setValue(defaultSchema);
				}
			} else {
				fi.setValue(index.schemaSelezionato);
			}
		}
	}

}
