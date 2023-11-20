/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

public class DetailToolStripButton extends ToolStripButton {

	private boolean showTitle;
	private DetailToolStripButton instance = this;

	public DetailToolStripButton(String title, String icon) {
		this(title, icon, true);
	}

	public DetailToolStripButton(String title, String icon, boolean showTitle) {
		this.showTitle = showTitle;
		setTitle(title);
		setIcon(icon);
		setIconSize(16);
 		if (UserInterfaceFactory.isAttivaAccessibilita()){
			setCanFocus(true);
	//		setTabIndex(-1);
 		} else {
			setCanFocus(false);
			setTabIndex(-1);
		}
		if(showTitle) {
			setBaseStyle(it.eng.utility.Styles.detailButton);
		} else {
			setBaseStyle(it.eng.utility.Styles.detailButtonHiddenText);
		}
		// Alcuni item settano il valore nel form solo dopo aver perso il focus, e al click del DetailToolStripButton non sempre il focus viene perso
		// in un tempo utile alla corretta lettura del valore.
		// Per ovviare a ciò aggiungo un handler che forzatamente cambi il focus al click del bottone
		addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				instance.focusAfterGroup();
			}
		});
//		setBackgroundImage("background/blue_gradient.gif");
//		setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
	}

	@Override
	public void setTitle(String title) {
		if (showTitle) {
			super.setTitle("<font style=\"font-weight: bold\">" + title + "</font>");
			setPrompt(null);
		} else {
			super.setTitle("");
			setPrompt(title);
		}
	}

}