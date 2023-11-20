/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class FrontendButton extends ToolStripButton {

	public FrontendButton(String title) {
		this(title, null);
	}
	
	public FrontendButton(String title, String icon) {
		setTitle(title);
		setHeight(32);
		setWidth(32);
		setIcon(icon);		
		setIconSize(24);
		setAlign(Alignment.CENTER);
		setOverflow(Overflow.VISIBLE);
//		setBaseStyle(it.eng.utility.Styles.frontEndButton);
		setBaseStyle(it.eng.utility.Styles.detailButton);
	}
	
	@Override
	public void setTitle(String title) {
		super.setTitle("<b>" + title + "</b>");		
	}
	
}
