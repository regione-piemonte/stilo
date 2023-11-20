/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.InizialiLayout;

import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import it.eng.auriga.ui.module.layout.client.AurigaLayout;

public class InizialeButton extends ToolStripButton {

	private InizialiLayout layout; 
	private String value;
	
	public InizialeButton(String pValue, InizialiLayout pLayout) {		
      
		super("<b>" + pValue + "</b>");
		this.value = pValue;
        this.layout = pLayout;
        setBaseStyle(it.eng.utility.Styles.inizialiButton);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			this.setTabIndex(-1);
        	this.setCanFocus(false);
		}
        
    }

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
