/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.widgets.IconButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.RibbonBar;
import com.smartgwt.client.widgets.toolbar.RibbonGroup;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class DettaglioDocumentoWindow extends ModalWindow {

	private DettaglioDocumentoWindow _window;

	/**
	 * Layout & Detail della maschera da cui provengo
	 */
	private CustomLayout _layoutArchivio;

	/**
	 * Detail della maschera corrente
	 */
	private CustomDetail portletLayout;
	
	protected ToolStrip detailToolStrip;
	private boolean isOpen;
	
	public DettaglioDocumentoWindow(final Record record,final CustomDetail detail,String title,List<ToolStripButton> customBottomListButtons) {
		super("dettagliodocumento", true);
//		SC.setScreenReaderMode(true);
		isOpen = true;
		setTitle("Dettaglio documento");

		_window = this;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		loadPortletLayout(record, detail, customBottomListButtons);
		setBody(portletLayout);
		_window.setTitle(title);
		_window.setIcon("menu/archivio.png");
		_window.show();
		
		Layout.hideWaitPopup();
		
	}
	
	public void updateContentDocumentWindow ( Record record, CustomDetail detail,String title,List<ToolStripButton> customBottomListButtons) {
		loadPortletLayout(record, detail, customBottomListButtons);
		setBody(portletLayout);
		_window.markForRedraw();
		Layout.hideWaitPopup();
	}
	
	private void loadPortletLayout (final Record record,final CustomDetail detail,List<ToolStripButton> customBottomListButtons) {
		portletLayout=detail;
		portletLayout.setLayout(detail.getLayout());
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.editRecord(record);
		createMainLayout(customBottomListButtons);
		
//		RibbonGroup group = new RibbonGroup();
//		group.setTitle("<h5>Azioni</h5>");
//		group.setShowTitle(true);
//		group.setTitleAlign(Alignment.LEFT);  
//		group.setNumRows(0);
//		detailToolStrip.setDragAppearance(DragAppearance.TARGET);
//		group.setWidth100();
//		group.setHeight(0);
//		group.addMember(detailToolStrip);
//		group.setMemberOverlap(-10);
//		group.setRowHeight(0);
		
//		Label label = new Label();
//		label.setContents("<h5 style=\"margin-left: 9px\">Azioni</h5>");
//		label.setWidth100();
//		label.setHeight(20);
//		portletLayout.addMember(label);
		portletLayout.addMember(detailToolStrip);
	}
	
	protected VLayout createMainLayout(List<ToolStripButton> customBottomListButtons) {

		createDetailToolstrip(customBottomListButtons);

		VLayout mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();
//		mainLayout.addMember(group);
		return mainLayout;

	}

	private void createDetailToolstrip(List<ToolStripButton> customBottomListButtons) {
		detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
//		detailToolStrip.setIsGroup(true);
//		detailToolStrip.setGroupTitle("<h5>Azioni</h5>");
		DetailToolStripButton closeModalWindow = new DetailToolStripButton(I18NUtil.getMessages().backButton_prompt(),"buttons/back.png");
		closeModalWindow.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				isOpen = false;
				_window.close();
			}
		});
		
		detailToolStrip.addButton(closeModalWindow,0);

		detailToolStrip.addFill(); // push all buttons to the right
		for (ToolStripButton singleButton: customBottomListButtons)
		{
			detailToolStrip.addButton(singleButton);
		}

	}
	

	@Override
	public void manageOnCloseClick() {
		super.manageOnCloseClick();
		isOpen = false;
	}
	
	
	public CustomLayout get_layoutPostaElettronica() {
		return _layoutArchivio;
	}

	public void set_layoutPostaElettronica(CustomLayout _layoutPostaElettronica) {
		this._layoutArchivio = _layoutPostaElettronica;
	}

	
	public boolean isOpen() {
		return isOpen;
	}

	
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	
}
