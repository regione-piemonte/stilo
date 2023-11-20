/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Alignment;

public class TitleStaticTextItem extends StaticTextItem {

	public TitleStaticTextItem(String title, int width) {
		setTitle(title);
		setColSpan(1);
		setDefaultValue(title + " :");
		setWidth(width);
		setCanFocus(false);
		setTabIndex(-1);		
		setShowTitle(false);
		setAlign(Alignment.RIGHT);
		setTextBoxStyle(it.eng.utility.Styles.formTitle);									
		setWrap(false);
	}

	@Override
	public void setCanEdit(Boolean canEdit) {
		setTextBoxStyle(it.eng.utility.Styles.formTitle);
	}
}