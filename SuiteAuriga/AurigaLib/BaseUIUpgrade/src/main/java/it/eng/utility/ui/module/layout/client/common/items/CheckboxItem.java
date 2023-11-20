/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

public class CheckboxItem extends com.smartgwt.client.widgets.form.fields.CheckboxItem {

	public CheckboxItem() {
		setWidth(250);
		setCellStyle(it.eng.utility.Styles.formCell);
		setShowFocused(false);
		setShowFocusedIcons(false);
	}

	public CheckboxItem(String name) {
		this();
		setName(name);
	}

	public CheckboxItem(String name, String title) {
		this();
		setName(name);
		setTitle(title);
		setShowTitle(false);
	}

	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		setTextBoxStyle(it.eng.utility.Styles.labelAnchor);
		setDisabled(!canEdit);
		setShowDisabled(false);
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			setCanFocus(true);
	//		setCanFocus(canEdit ? true : false);	
 		} else {
 			setCanFocus(canEdit ? true : false);
 		}
	}

}
