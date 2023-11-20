/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class GenericWindow extends ModalWindow {

	private GenericWindow _window;
	protected ToolStrip detailToolStrip;
	private CustomDetail portletLayout;
	
	public GenericWindow(CustomLayout layout,String nomeEntita,String title,String icon) {
		super(nomeEntita, true);
	//	SC.setScreenReaderMode(true);
	
		_window = this;
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		_window.setTitle(title);
		_window.setIcon(icon);
		_window.setTabIndex(null);
		_window.setCanFocus(true);
		createDetailToolstrip();
//		detailToolStrip.setIsGroup(true);
//		detailToolStrip.setGroupTitle("<h5>Azioni</h5>");
		Label label = new Label();
		label.setContents("<h5 style=\"margin-left: 9px\">Azioni</h5>");
		label.setWidth100();
		label.setHeight(30);
		layout.addMember(label);
		layout.addMember(detailToolStrip);
		setBody(layout);
		
	}
	
	public GenericWindow(CustomDetail detail,String nomeEntita,String title,String icon) {
		super(nomeEntita, true);
//		SC.setScreenReaderMode(true);

		_window = this;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		portletLayout=detail;
		portletLayout.setLayout(detail.getLayout());
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		_window.setTitle(title);
		_window.setIcon(icon);
		_window.setTabIndex(null);
		_window.setCanFocus(true);
		
		createDetailToolstrip();
//		detailToolStrip.setIsGroup(true);
//		detailToolStrip.setGroupTitle("<h5>Azioni</h5>");
		Label label = new Label();
		label.setContents("<h5 style=\"margin-left: 9px\">Azioni</h5>");
		label.setWidth100();
		label.setHeight(30);
		portletLayout.addMember(label);
		portletLayout.addMember(detailToolStrip);
		setBody(portletLayout);
		
	}
	
	private void createDetailToolstrip() {
		detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		
		DetailToolStripButton closeModalWindow = new DetailToolStripButton(I18NUtil.getMessages().backButton_prompt(), "buttons/back.png");
		closeModalWindow.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				_window.close();
			}
		});
		
		detailToolStrip.addButton(closeModalWindow);

		detailToolStrip.addFill(); // push all buttons to the right
	}

	
}

