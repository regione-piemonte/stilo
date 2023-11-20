/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.menu.MenuItem;

public class MenuUtil {

	/**
	 * Da utilizzare quando il menuItem[] di partenza
	 * contiene riferimenti al javascript e non agli oggetti
	 * istanziati
	 * 
	 * @return Array di MenuItem gestibile (con le proprietà inizializzate.
	 */
	public static MenuItem[] retrieveMenuFromJavascriptMenu(MenuItem[] lMenuItems){
		MenuItem[] lMenuItemsRetrieved = new MenuItem[lMenuItems.length];
		int k = 0;
		for (MenuItem lMenuItem : lMenuItems){
			MenuItem lMenuItemFound = new MenuItem(lMenuItem.getJsObj());
//			System.out.println(lMenuItemFound.getTitle());
			lMenuItemsRetrieved[k] = lMenuItemFound;
			k++;
		}
		return lMenuItemsRetrieved;
	}

	/**
	 * Recupera il submenu dal menuItem 
	 * @param lMenuItem
	 * @return
	 */
	public static MenuItem[] retrieveSubMenuFromJavascriptMenu(MenuItem lMenuItem){
		JavaScriptObject[] lMenus = JSOHelper.getAttributeAsJavaScriptObjectArray(JSOHelper.getAttributeAsJavaScriptObject(lMenuItem.getJsObj(), "submenu"),
		"items");
		MenuItem[] lSubMenu = new MenuItem[lMenus.length];
		int i=0;
		for (JavaScriptObject lObj : lMenus){
			MenuItem lMenuItem2 = new MenuItem(lObj);
			lSubMenu[i] = lMenuItem2;
			i++;
		}
		return lSubMenu;
	}
}
