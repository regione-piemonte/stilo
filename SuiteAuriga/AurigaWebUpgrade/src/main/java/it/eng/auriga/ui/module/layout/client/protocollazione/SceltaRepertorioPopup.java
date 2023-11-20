/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class SceltaRepertorioPopup extends Window {

	private SceltaRepertorioPopup instance;
	private SceltaRepertorioForm form;

	public SceltaRepertorioPopup(String flgTipoProv, String repertorioDefault, Record[] listaRepertori, final ServiceCallback<Record> callback) {

		instance = this;

		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(400);
		setHeight(100);
		setKeepInParentRect(true);
		setTitle("Seleziona repertorio");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);

		form = new SceltaRepertorioForm(flgTipoProv, repertorioDefault, listaRepertori, instance, callback);
		form.setHeight100();
		form.setAlign(Alignment.CENTER);

		addItem(form);

		if (AurigaLayout.getIsAttivaAccessibilita()) {
			addBackButton();	
			setCanFocus(true);
			setTabIndex(null);
			setHeight(150);
		}

		setShowTitle(true);
		setHeaderIcon("blank.png");
	}
	
	private void addBackButton() {
		DetailToolStripButton closeModalWindow = new DetailToolStripButton(I18NUtil.getMessages().backButton_prompt(), "buttons/back.png");
		closeModalWindow.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				instance.close();
			}
		});
		closeModalWindow.setAlign(Alignment.RIGHT);
		instance.addItem(closeModalWindow);
	}
	
	@Override
	public void show() {

		super.show();
		Layout.hideWaitPopup();
		if (UserInterfaceFactory.isAttivaAccessibilita()){
//			focus();
			focusInNextTabElement();
		}
	}
}