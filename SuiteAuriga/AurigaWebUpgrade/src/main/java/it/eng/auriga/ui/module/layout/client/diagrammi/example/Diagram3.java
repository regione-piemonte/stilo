/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.diagrammi.widgets.BoxLabel;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.connection.Connection;

public class Diagram3  extends AbstractDiagram{
	
	@Override
	public void draw() {
		// Create the elements
		Widget labelHello = new BoxLabel("Hello");
		controller.addWidget(labelHello,25,115);
		
		Widget labelWorld = new Label("World");
		controller.addWidget(labelWorld,200,115);
		
		Widget img = new Image("http://www.abonnement-forfait-mobile.com/wp-content/uploads/2010/12/logo_orange.png");
		img.setSize("48px", "48px");
		controller.addWidget(img,200,25);
		
		// Add DnD logic
		PickupDragController dragController = new PickupDragController(controller.getView(), true);
		dragController.makeDraggable(labelHello);
		dragController.makeDraggable(labelWorld);
		dragController.makeDraggable(img);
		controller.registerDragController(dragController);
		
		// Add the logic
		controller.drawStraightArrowConnection(labelHello, labelWorld);
		Connection c2 = controller.drawStraightConnection(labelHello, img);
		controller.addPointOnConnection(c2, 50, 50);
		controller.addPointOnConnection(c2, 75, 75);
		controller.addPointOnConnection(c2, 150, 25);
	}

	@Override
	public String getName() {
		return "Example 3";
	}

	@Override
	public String getDescription() {
		return "An arrow and a ... complex connection";
	}

}
