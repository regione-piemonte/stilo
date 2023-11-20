/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.SelectItemFiltrabileEditorData;
import it.eng.utility.ui.module.layout.client.common.SelectItemFiltrabileEditorNumber;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.fields.FormItem;

public class WindowNumberChooser extends Window{
	
	private FormNumberChooser form;
	private SelectItemFiltrabileEditorNumber father;
	public WindowNumberChooser(String field, SelectItemFiltrabileEditorNumber formItem){
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(350);
		setHeight(125);
		setKeepInParentRect(true);
		setTitle(I18NUtil.getMessages().windowNumbereChooser_title() + field);
		setShowModalMask(true);
		setShowMaximizeButton(true);
		setShowMinimizeButton(true);
		form = new FormNumberChooser(this, formItem);
		form.setCanSubmit(true);
		addItem(form);
		father = formItem;
		setShowTitle(true);
		
	}
	public void setFather(SelectItemFiltrabileEditorNumber father) {
		this.father = father;
	}
	public FormItem getFather() {
		return father;
	}
	public FormNumberChooser getForm() {
		return form;
	}
	public void setForm(FormNumberChooser form) {
		this.form = form;
	}
	
}
