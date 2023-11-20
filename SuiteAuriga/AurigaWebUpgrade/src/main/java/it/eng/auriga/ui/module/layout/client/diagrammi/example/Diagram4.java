/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.diagrammi.widgets.BoxLabel;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Widget;

public class Diagram4 extends AbstractDiagram{

	@Override
	public void draw() {		
		// Create the elements
		Widget labelHello = new BoxLabel("Hello");
		controller.addWidget(labelHello,25,115);
		
		Widget labelWorld = new BoxLabel("World");
		controller.addWidget(labelWorld,200,115);
		
		// Add DnD logic
		PickupDragController dragController = new PickupDragController(controller.getView(), true);
		dragController.makeDraggable(labelHello);
		dragController.makeDraggable(labelWorld);
		controller.registerDragController(dragController);
		
		// Add the logic
		controller.drawStraightArrowConnection(labelHello, labelWorld);
	}

	@Override
	public String getName() {
		return "Example 4";
	}

	@Override
	public String getDescription() {
		return "An arrow and a scroll panel";
	}

	@Override
	public Widget asWidget() {
		Widget widgetPanel = controller.getView();
		widgetPanel.getElement().getStyle().setMargin(0, Unit.PX);
		widgetPanel.getElement().getStyle().setProperty("border", "0px");
		
		controller.setFrameSize(300, 300);
		return controller.getViewAsScrollPanel();
	}

}