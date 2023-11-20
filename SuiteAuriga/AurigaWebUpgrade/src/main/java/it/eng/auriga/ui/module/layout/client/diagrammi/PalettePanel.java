/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.diagrammi.widgets.CloneableWidget;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Example of a panel that immediately replaces any {@link PaletteWidget} children that removed with widget copies of the original.
 */
public class PalettePanel extends VerticalPanel {

	private final PickupDragController dragController;

	public PalettePanel(PickupDragController dragController) {

		this.dragController = dragController;

		setSpacing(5);
		setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		// Label header = new Label("Widget Palette");
		// add(header);
	}

	/**
	 * Overloaded method that makes widgets draggable.
	 * 
	 * @param w
	 *            the widget to be added are made draggable
	 */
	public void add(Widget w) {
		dragController.makeDraggable(w);
		super.add(w);
	}

	/**
	 * Removed widgets that are instances of {@link PaletteWidget} are immediately replaced with a cloned copy of the original.
	 * 
	 * @param w
	 *            the widget to remove
	 * @return true if a widget was removed
	 */
	@Override
	public boolean remove(Widget w) {
		int index = getWidgetIndex(w);
		if (index != -1 && w instanceof CloneableWidget) {
			Widget clone = ((CloneableWidget) w).clone();
			dragController.makeDraggable(clone);
			insert(clone, index);
		}
		return super.remove(w);
	}
}