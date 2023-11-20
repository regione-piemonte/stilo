/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class ProtocollazioneWindow extends ModalWindow {
	
	private ProtocollazioneWindow _window;
	
	private CustomDetail portletLayout;
	protected ToolStrip detailToolStrip;
	private ToolStrip detailToolStripRbuild;

	public ProtocollazioneWindow(CustomDetail detail,String icon,String nomeEntita,String title) {
		super(nomeEntita, true);
//		SC.setScreenReaderMode(true);

		_window = this;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		portletLayout=detail;
		portletLayout.setLayout(detail.getLayout());
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		VLayout main_layout = new VLayout ();
		main_layout = (VLayout) portletLayout.getMember("main_layout");
		
		detailToolStrip = (ToolStrip)main_layout.getMember("bottoni");
		detailToolStrip.markForDestroy();
		detailToolStrip.markForRedraw();
		addBackButton();
		
		Canvas[] members = detailToolStrip.getMembers();
		for(Canvas singleButton : members) {
			singleButton.markForRedraw();
			detailToolStripRbuild.addMember(singleButton);
		}
		main_layout.addMember(detailToolStripRbuild);
		portletLayout.addMember(main_layout);
		
		_window.setTitle(title);
		_window.setIcon(icon);
		_window.setTabIndex(null);
		_window.setCanFocus(true);
		setBody(portletLayout);
		
	}
	
	@Override
	public void show() {

		super.show();
		Layout.hideWaitPopup();
		focus();
	}
	
	private void addBackButton() {
//		detailToolStrip = new ToolStrip();
//		detailToolStrip.setWidth100();
//		detailToolStrip.setHeight(30);
//		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStripRbuild = new ToolStrip();
		detailToolStripRbuild.setName("bottoni");
		DetailToolStripButton closeModalWindow = new DetailToolStripButton(I18NUtil.getMessages().backButton_prompt(), "buttons/back.png");
		closeModalWindow.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				_window.close();
			}
		});
		
		detailToolStripRbuild.addButton(closeModalWindow,0);
		detailToolStripRbuild.addFill(); // push all buttons to the right
	}
	
	
}
