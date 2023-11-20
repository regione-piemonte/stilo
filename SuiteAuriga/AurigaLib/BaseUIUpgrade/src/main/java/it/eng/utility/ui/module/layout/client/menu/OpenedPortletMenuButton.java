/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * Item menu dei preferiti
 * @author michele
 *
 */
public class OpenedPortletMenuButton extends ToolStripButton {
	
	public static int _WIDTH = 200;
	
	private String nomeEntita;
	
	public OpenedPortletMenuButton(String nomeEntita, String title, String icon) {
		super();
		setBaseStyle(it.eng.utility.Styles.openedPortletMenuButton);
		setHeight100();				
		setWidth(_WIDTH);			
		setAutoFit(false);
		if (title != null && title.length() > 30) {
			setTitle(title.substring(0, 30) + "...");			
		} else {
			setTitle(title);
		}
		setIcon(icon);
		setIconSize(16);			
		setNomeEntita(nomeEntita);
		setPrompt(title);
	}
	
    public void setNomeEntita(String nomeEntita) {
    	OpenedPortletMenuButton.this.nomeEntita = nomeEntita;
    }
    
    public String getNomeEntita()  {
        return nomeEntita;
    }
	
}