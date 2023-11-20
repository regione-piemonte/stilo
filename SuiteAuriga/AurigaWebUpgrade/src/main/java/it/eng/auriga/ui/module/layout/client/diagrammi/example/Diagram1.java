/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.diagrammi.widgets.BoxLabel;
import it.eng.auriga.ui.module.layout.client.diagrammi.widgets.LabelWithMenu;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.connection.Connection;

public class Diagram1 extends AbstractDiagram {

	@Override
	public void draw() {

		// Create the elements
		Widget labelHello = new BoxLabel("Hello");
		controller.addWidget(labelHello, 25, 115);

		LabelWithMenu hasMenu = new LabelWithMenu("Context Menu", controller);
		controller.addWidget(hasMenu, 200, 115);

		// Add DnD logic
		PickupDragController dragController = new PickupDragController(controller.getView(), true);
		dragController.makeDraggable(labelHello);
		dragController.makeDraggable(hasMenu);

		controller.registerDragController(dragController);

		// Add the logic
		Connection con = controller.drawStraightArrowConnection(labelHello, hasMenu);
		con.getContextMenu().addItem(new MenuItem("Hello World !", new Command() {

			@Override
			public void execute() {
				Window.alert("Hello Mickey ;)");
			}
		}));
	}

	@Override
	public String getName() {
		return "Example 1";
	}

	@Override
	public String getDescription() {
		return "Simple direct 'Hello World !' Example";
	}

}
