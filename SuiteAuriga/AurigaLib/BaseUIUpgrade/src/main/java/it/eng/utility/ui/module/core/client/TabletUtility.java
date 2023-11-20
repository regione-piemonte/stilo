/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.user.client.Window.Navigator;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;

public class TabletUtility {

	public static boolean detectIfAndroidTablet() {
		String userAgent = Navigator.getUserAgent();
		if (userAgent != null) {
			return userAgent.toUpperCase().contains("ANDROID");
		} else {
			return false;
		}
	}

	public static void resizeForAndroid(SelectItem selectItem, int righeTotali) {
		if ((TabletUtility.detectIfAndroidTablet()) && (righeTotali > 1)) {

			ListGrid pickListProperties = new ListGrid();
			pickListProperties.setAutoFitData(Autofit.VERTICAL);
			pickListProperties.setAutoFitMaxRecords(Math.min(10, righeTotali - 1));
			selectItem.setPickListProperties(pickListProperties);
			selectItem.setAttribute("autoSizePickList", new Boolean(false));
			selectItem.setPickListHeight((Math.min(10, righeTotali - 1)) * 15);
			selectItem.redraw();

			// selectItem.setPickListHeight(40);

			// pickListProperties.setCanHover(true);
			// pickListProperties.setLeaveScrollbarGap(false);
			// pickListProperties.setFixedRecordHeights(true);
			// setAltezzaPicklist((righeTotali - 5) * 17);
			// setPickListHeight(Math.min(10, (righeTotali - 2) * 17));
			// selectItem.redraw();
		}
	}

	public static void resizeForAndroid(ListGrid listGridlistGrid, int righeTotali) {
		if (detectIfAndroidTablet() && (righeTotali > 1)) {
			listGridlistGrid.setAutoFitData(Autofit.VERTICAL);
			listGridlistGrid.setAutoFitMaxRecords(Math.min(10, righeTotali - 1));
		}
	}

	public static void addTabletWorkAround(final SelectItem selectItem) {
		final ListGrid pickListProperties = new ListGrid();
		selectItem.setPickListProperties(pickListProperties);
		boolean tabletMode = TabletUtility.detectIfAndroidTablet();
		if (tabletMode) {

			selectItem.addDataArrivedHandler(new DataArrivedHandler() {

				@Override
				public void onDataArrived(DataArrivedEvent arg0) {
					resizeForAndroid(selectItem, arg0.getData().toArray().length);
				}
			});

		}
	}

	public static void addTabletWorkAround(final FormItem formItem) {
		boolean tabletMode = TabletUtility.detectIfAndroidTablet();
		if (tabletMode) {
			formItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

				@Override
				public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent arg0) {
					Map valori = arg0.getItem().getAttributeAsMap("valueMap");
					if ((valori != null) && (valori.size() > 1) && (arg0.getItem() instanceof com.smartgwt.client.widgets.form.fields.TextItem)) {
						arg0.getItem().setAttribute("pickListHeight", (valori.size() - 1) * 15);
						arg0.getItem().redraw();
					}

				}

			});
		}
	}
}
