/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.SelectItemFiltrabileEditorData;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;


public class WindowDateChooser extends Window{

	private SelectItemFiltrabileEditorData father;
	private FormDateChooser form;
	private StaticTextItem lStaticTextItem;
	public WindowDateChooser(String field, SelectItemFiltrabileEditorData formItem, StaticTextItem lStaticTextItem){
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(350);
		setHeight(125);
		setKeepInParentRect(true);
		setTitle(I18NUtil.getMessages().windowDateChooser_title() + field);
		setShowModalMask(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		form = new FormDateChooser(this, formItem, lStaticTextItem);
		form.setCanSubmit(true);
		addItem(form);
		father = formItem;
		setShowTitle(true);
	}
	public void setFather(SelectItemFiltrabileEditorData father) {
		this.father = father;
	}
	public SelectItemFiltrabileEditorData getFather() {
		return father;
	}
	
}
