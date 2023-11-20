/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.diagrammi.widgets.DiagramSaveFactoryImpl;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.GWT;
import com.orange.links.client.event.NewFunctionEvent;
import com.orange.links.client.event.NewFunctionHandler;

public class Diagram2 extends AbstractDiagram {

	@Override
	public void draw() {
		final PickupDragController dragController = new PickupDragController(controller.getView(), true);
		controller.registerDragController(dragController);
		controller.addNewFunctionHandler(new NewFunctionHandler() {

			@Override
			public void onNewFunction(NewFunctionEvent event) {
				dragController.makeDraggable(event.getFunction());
			}
		});

		String xmlDiagram = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<diagram width=\"400\" heigth=\"400\" grid=\"true\">"
				+ "	<function left=\"280\" top=\"26\" id=\"2\" identifier=\"image\">" + "		http://coria09.univ-tln.fr/public/logo_orange.jpg" + "	</function>"
				+ "	<function left=\"285\" top=\"265\" id=\"1\" identifier=\"boxlabel\">" + "		World" + "	</function>"
				+ "	<function left=\"26\" top=\"116\" id=\"3\" identifier=\"boxlabel\">" + "		Hello" + "	</function>"
				+ "	<link startid=\"3\" endid=\"2\" type=\"straight\" >" + "		<point x=\"50\" y=\"50\"/>" + "		<point x=\"300\" y=\"135\"/>" + "	</link>"
				+ "	<link startid=\"3\" endid=\"1\" type=\"straightarrow\">" + "		<decoration identifier=\"savabledecorationlabel\">" + "			Mickey"
				+ "		</decoration>" + "		<point x=\"50\" y=\"285\"/>" + "	</link>" + "</diagram>";

		controller.importDiagram(xmlDiagram, new DiagramSaveFactoryImpl());

		GWT.log(controller.exportDiagram());
	}

	@Override
	public String getName() {
		return "Example 2";
	}

	@Override
	public String getDescription() {
		return "Two connections and a decoration";
	}

}
