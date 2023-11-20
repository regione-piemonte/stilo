/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class StringaFullTextRestricted extends CustomDataSourceField {
	
	public StringaFullTextRestricted() {
		super(FieldType.CUSTOM);
	}

	public StringaFullTextRestricted(String name) {
		super(name, FieldType.CUSTOM);
	}

	public StringaFullTextRestricted(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}

	public StringaFullTextRestricted(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public StringaFullTextRestricted(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {
				
		setAttribute("customType", "stringa_full_text_restricted");
		
		TextItem editorType = new TextItem();
		editorType.setTextBoxStyle(it.eng.utility.Styles.luceneTextBox);
		editorType.setPrompt(I18NUtil.getMessages().filterFullTextItem_parole_prompt());
		editorType.setWidth(200);
		
		setEditorType(editorType);				        
	}

}
