/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class FrecciaDetailToolStripButton extends DetailToolStripButton {

	public FrecciaDetailToolStripButton() {
		this(true);
	}
	
	public FrecciaDetailToolStripButton(boolean showBorder) {
		super("Espandi menu", "menu/menu.png", false);
		setIconWidth(12);
		setIconHeight(16);
		if(showBorder) {
			setBaseStyle(it.eng.utility.Styles.frecciaDetailButton);
		} else {
			setBaseStyle(it.eng.utility.Styles.frecciaDetailButtonNoBorder);
		}		
	}

}