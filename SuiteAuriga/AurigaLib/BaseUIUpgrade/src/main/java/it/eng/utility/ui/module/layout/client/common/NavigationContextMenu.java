/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;

import java.util.List;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class NavigationContextMenu extends Menu {

	private CustomAdvancedTreeLayout layout;
	private TreeNodeBean node;
	private NavigationContextMenuFrom from;
	private Integer treeRecordIndex;

	public NavigationContextMenu(CustomAdvancedTreeLayout pLayout, TreeNodeBean pNode, final NavigationContextMenuFrom pFrom) {
		this(pLayout, pNode, pFrom, null);
	}

	public NavigationContextMenu(CustomAdvancedTreeLayout pLayout, TreeNodeBean pNode, final NavigationContextMenuFrom pFrom, final Integer pTreeRecordIndex) {

		this.layout = pLayout;
		this.node = pNode;
		this.from = pFrom;
		this.treeRecordIndex = pTreeRecordIndex;

		this.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {

			}
		});

		addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				
				markForDestroy();
			}
		});

		setWidth(200);

		MenuItem cercaMenuItem = new MenuItem(I18NUtil.getMessages().navigationContextMenu_cercaMenuItem_title());
		cercaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				if (from.equals(NavigationContextMenuFrom.LIST_RECORD)) {
					layout.cercaFromList(node.getIdFolder());
				} else {
					if (pTreeRecordIndex != null) {
						layout.getTree().selectSingleRecord(pTreeRecordIndex);
					} else {
						layout.getTree().deselectAllRecords();
					}
					layout.cerca(node.getIdNode());
				}
			}
		});
		cercaMenuItem.setIcon("buttons/search.png");
		cercaMenuItem.setIconWidth(16);
		cercaMenuItem.setIconHeight(16);
		addItem(cercaMenuItem);

		MenuItem ricaricaAlberoMenuItem = new MenuItem(I18NUtil.getMessages().navigationContextMenu_ricaricaAlberoMenuItem_title());
		ricaricaAlberoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				layout.ricaricaAlberoAPartireDa(node.getIdNode());
			}
		});
		ricaricaAlberoMenuItem.setIcon("blank.png");
		ricaricaAlberoMenuItem.setIconWidth(16);
		ricaricaAlberoMenuItem.setIconHeight(16);
		addItem(ricaricaAlberoMenuItem);

		if (from.equals(NavigationContextMenuFrom.TREE_NODE)) {
			MenuItem ricaricaSottoAlberoMenuItem = new MenuItem("Ricarica sotto-albero");
			ricaricaSottoAlberoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					
					layout.ricaricaSottoAlberoAPartireDa(node.getIdNode(), true);
				}
			});
			ricaricaSottoAlberoMenuItem.setIcon("blank.png");
			ricaricaSottoAlberoMenuItem.setIconWidth(16);
			ricaricaSottoAlberoMenuItem.setIconHeight(16);
			addItem(ricaricaSottoAlberoMenuItem);
		}

		if (from.equals(NavigationContextMenuFrom.TREE_NODE) || from.equals(NavigationContextMenuFrom.NAVIGATOR_LEVEL)) {
			MenuItem impostaComeRadiceDefaultMenuItem = new MenuItem(I18NUtil.getMessages().navigationContextMenu_impostaComeRadiceDefaultMenuItem_title());
			impostaComeRadiceDefaultMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					
					final GWTRestDataSource impostaComeRadiceDefaultDS = UserInterfaceFactory.getPreferenceDataSource();
					impostaComeRadiceDefaultDS.addParam("prefKey", layout.getPrefKeyPrefix() + "idRootNode");

					AdvancedCriteria criteria = new AdvancedCriteria();
					criteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
					impostaComeRadiceDefaultDS.fetchData(criteria, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record[] data = response.getData();
							
							if (data.length != 0) {
								Record record = data[0];
								record.setAttribute("value", node.getIdNode());
								impostaComeRadiceDefaultDS.updateData(record);
							} else {
								Record record = new Record();
								record.setAttribute("prefName", "DEFAULT");
								record.setAttribute("value", node.getIdNode());
								impostaComeRadiceDefaultDS.addData(record);
							}
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterImpostaComeRadiceDefault_message(), "", MessageType.INFO));
						}
					});
				}
			});
			impostaComeRadiceDefaultMenuItem.setIcon("blank.png");
			impostaComeRadiceDefaultMenuItem.setIconWidth(16);
			impostaComeRadiceDefaultMenuItem.setIconHeight(16);
			addItem(impostaComeRadiceDefaultMenuItem);
		}

		List<MenuItem> customNavigationContextMenuItems = layout.getCustomNavigationContextMenuItems(node, from, treeRecordIndex);
		if (customNavigationContextMenuItems != null) {
			for (MenuItem item : customNavigationContextMenuItems) {
				addItem(item);
			}
		}

		addSort(new SortSpecifier("title", SortDirection.ASCENDING));

	}

	public Boolean showContextMenu() {
		
		Boolean ret = super.showContextMenu();
		moveTo(getLeft() - 10, getTop() - 10);
		return ret;
	}

	public enum NavigationContextMenuFrom {
		TREE_NODE, LIST_RECORD, NAVIGATOR_LEVEL
	};

}
