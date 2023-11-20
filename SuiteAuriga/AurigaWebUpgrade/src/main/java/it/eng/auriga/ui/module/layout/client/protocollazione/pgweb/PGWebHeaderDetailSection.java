/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.HeaderDetailSection;

import com.smartgwt.client.widgets.form.DynamicForm;

public class PGWebHeaderDetailSection extends HeaderDetailSection {

	public PGWebHeaderDetailSection(String pTitle, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired, final DynamicForm... forms) {
		super(pTitle, pCanCollapse, pShowOpen, pIsRequired, forms);
	}

	public PGWebHeaderDetailSection(String pTitle, final Integer  pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired, final DynamicForm... forms) {
		super(pTitle,  pHeight, pCanCollapse, pShowOpen, pIsRequired, forms);
	}
		
	public PGWebHeaderDetailSection(String pTitle, final Integer pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired, final String pBackgroundColor, final DynamicForm... forms) {
		super(pTitle,  pHeight, pCanCollapse, pShowOpen, pIsRequired, pBackgroundColor, forms);
	}
	 
	@Override
	public boolean showFirstCanvasWhenEmptyAfterOpen() {
		
		return true;
	}
	
 }