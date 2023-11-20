/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.OneCallGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;

public class MenuSceltaBarraDesktop extends Menu {

	private Map<String, MenuItem> menuItems;

	private Layout layout;

	public MenuSceltaBarraDesktop(Layout pLayout, PreferenceMenuBarraDesktop preferenzaEnum) {
		this.layout = pLayout;
		creaMenu(preferenzaEnum);
	    layout.setShowDesktopButtons(preferenzaEnum);
	}
	
	private void creaMenu(final PreferenceMenuBarraDesktop preferenzaEnum) {
//		System.out.println("creaMenu("+preferenzaEnum+")");
		MenuItem menuItem = new MenuItem("Visualizza shortcut anche sul desktop");
		menuItem.setChecked(preferenzaEnum == getPreference());
		menuItem.addClickHandler(new ClickHandler() {  
            public void onClick(MenuItemClickEvent event) {  
            	MenuItem menuItem = event.getItem();
            	menuItem.setChecked(!menuItem.getChecked());
        		selectedPreference(getSelectedPreference());
            }
        });
		addItem(menuItem);
	}
	
	public PreferenceMenuBarraDesktop getSelectedPreference() {
		PreferenceMenuBarraDesktop result = getItem(0).getChecked() ? getPreference() : PreferenceMenuBarraDesktop.BARRA;
//		System.out.println("getSelectedPreference(): "+result);
		return result;
	}
	
	private PreferenceMenuBarraDesktop getPreference() {
		 return PreferenceMenuBarraDesktop.DESKTOP;
	}

	@SuppressWarnings("unused")
	private void creaMenuOLD(PreferenceMenuBarraDesktop preferenzaEnum) {
		menuItems = new HashMap<String, MenuItem>();
		// ListGridField preferiti = new ListGridField("iconpreferiti",15);
		// preferiti.setShowValueIconOnly(true);
		// Map<String, String> valueIcons = new HashMap<String, String>();
		// valueIcons.put("true", "menu/flgFatta.png");
		// preferiti.setValueIcons(valueIcons);
		// preferiti.setValueIconSize(12);
		// preferiti.setWidth(15);
		//
		// ListGridField icon = new ListGridField("icon",16);
		// icon.setType(ListGridFieldType.IMAGE);
		// icon.setImageSize(16);
		//
		// setFields(icon, new ListGridField("title"), preferiti);

		for (PreferenceMenuBarraDesktop preferenze : PreferenceMenuBarraDesktop.values()) {
			MenuItem item = new MenuItem(preferenze.getDescrizione());
			item.setAttribute("shortcut", preferenze.getValore());
			// item.setAttribute("iconpreferiti", preferenza.equalsIgnoreCase(preferenze.getValore()) ? true : false);
			item.setChecked(preferenzaEnum == preferenze);
			item.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					final String shortcut = event.getItem().getAttributeAsString("shortcut");
					for (String key : menuItems.keySet()) {
						if (menuItems.get(key).getAttributeAsString("shortcut").equalsIgnoreCase(shortcut)) {
							// menuItems.get(key).setAttribute("iconpreferiti", true);
							menuItems.get(key).setChecked(true);
						} else {
							// menuItems.get(key).setAttribute("iconpreferiti", false);
							menuItems.get(key).setChecked(false);
						}
					}
					final PreferenceMenuBarraDesktop shortcutEnum = PreferenceMenuBarraDesktop.fromValore(shortcut);
					selectedPreference(shortcutEnum);
				}

			});
			addItem(item);
			menuItems.put(item.getAttribute("shortcut"), item);
		}

	}
	
	public void selectedPreference(final PreferenceMenuBarraDesktop shortcutEnum) {
		layout.setShowDesktopButtons(shortcutEnum);
		if (shortcutEnum != PreferenceMenuBarraDesktop.BARRA) {
			layout.addPortalDesktopButtons();
		}
		preferenceMenuDesktop(shortcutEnum);
	}
	
	private OneCallGWTRestDataSource getShortcutBarraDesktopOneCallGWTRestDataSource() {
		final OneCallGWTRestDataSource shortcutBarraDesktopDS = new OneCallGWTRestDataSource("PreferenceMenuDesktopDataSource");
		shortcutBarraDesktopDS.addParam("prefKey", getConfiguredPrefKeyPrefix() + "shortcutBarraDesktop");
		return shortcutBarraDesktopDS;
	}
	
	private void preferenceMenuDesktop(final PreferenceMenuBarraDesktop shortcutEnum) {
		AdvancedCriteria criteria = new AdvancedCriteria();
		criteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
		getShortcutBarraDesktopOneCallGWTRestDataSource().fetchData(criteria, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record[] data = response.getData();
				
				if (data.length != 0) {
					Record record = data[0];
					record.setAttribute("value", shortcutEnum.getValore());
					getShortcutBarraDesktopOneCallGWTRestDataSource().updateData(record);
				} else {
					Record record = new Record();
					record.setAttribute("prefName", "DEFAULT");
					record.setAttribute("value", shortcutEnum.getValore());
					getShortcutBarraDesktopOneCallGWTRestDataSource().addData(record);
				}
			}
		});
	}

	public Map<String, MenuItem> getMenuItems() {
		return menuItems;
	}

	public static String getConfiguredPrefKeyPrefix() {
		String configPrefKeyPrefix = Layout.getGenericConfig().getPrefKeyPrefix();
		return (UserInterfaceFactory.getPrefKeyPrefix() != null ? UserInterfaceFactory.getPrefKeyPrefix() : "")
				+ (configPrefKeyPrefix != null && !"".equals(configPrefKeyPrefix) ? configPrefKeyPrefix + "." : "");
	}

}
