/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.form.DynamicForm;

public class HeaderDetailSection extends DetailSection {

	public HeaderDetailSection(String pTitle, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired, final DynamicForm... forms) {
		this(pTitle,  null, pCanCollapse, pShowOpen, pIsRequired, null, forms);
	}

	public HeaderDetailSection(String pTitle, final Integer  pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired, final DynamicForm... forms) {
		this(pTitle,  pHeight, pCanCollapse, pShowOpen, pIsRequired, null, forms);
	}
	
	public HeaderDetailSection(String pTitle, final Integer pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired, final String pBackgroundColor, final DynamicForm... forms) {
		super(pTitle,  pHeight, pCanCollapse, pShowOpen, pIsRequired, pBackgroundColor, forms);
	}
	
}