/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.diagrammi.example.AbstractDiagram;
import it.eng.auriga.ui.module.layout.client.diagrammi.example.Diagram1;
import it.eng.auriga.ui.module.layout.client.diagrammi.example.Diagram2;
import it.eng.auriga.ui.module.layout.client.diagrammi.example.Diagram3;
import it.eng.auriga.ui.module.layout.client.diagrammi.example.Diagram4;
import it.eng.auriga.ui.module.layout.client.diagrammi.widgets.DiagramMenuElement;
import it.eng.auriga.ui.module.layout.client.diagrammi.widgets.LabelWithMenu;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.BoundaryDropController;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.DiagramController;
import com.orange.links.client.event.TieLinkEvent;
import com.orange.links.client.event.TieLinkHandler;
import com.orange.links.client.event.UntieLinkEvent;
import com.orange.links.client.event.UntieLinkHandler;
import com.orange.links.client.menu.ContextMenu;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;

public class DiagrammiLayout extends Canvas {

	private int tabWidth = Window.getClientWidth() - 360;
	private int tabHeight = 400;
	private DiagramController currentController;
	private HorizontalPanel globalPanel;
	private PickupDragController dragController;

	// private VerticalPanel eventPanel;

	private AbstractDiagram[] diagrams = { new Diagram1(), new Diagram2(), new Diagram3(), new Diagram4() };

	public DiagrammiLayout() {

		VerticalPanel mainPanel = new VerticalPanel();

		DialogBox d = new DialogBox(false, true);
		d.setWidth("100%");
		d.setHeight("100%");
		d.add(mainPanel);

		HorizontalPanel menuPanel = new HorizontalPanel();
		for (int i = 0; i < diagrams.length; i++) {
			menuPanel.add(new DiagramMenuElement(diagrams[i], this));
		}
		menuPanel.getElement().getStyle().setMargin(10, Unit.PX);
		mainPanel.add(menuPanel);

		globalPanel = new HorizontalPanel();
		mainPanel.add(globalPanel);

		loadDiagram(null);

		VerticalPanel rightPanel = new VerticalPanel();

		dragController = new PickupDragController(currentController.getView(), true) {

			@Override
			protected BoundaryDropController newBoundaryDropController(AbsolutePanel boundaryPanel, boolean allowDroppingOnBoundaryPanel) {
				
				return new BoundaryDropController(boundaryPanel, allowDroppingOnBoundaryPanel) {

					@Override
					public void onDrop(DragContext context) {
						
						for (Widget widget : context.selectedWidgets) {
							if (widget instanceof LabelWithMenu) {
								LabelWithMenu label = (LabelWithMenu) widget;
								currentController.addWidgetAtMousePoint(label);
								PickupDragController dragController = new PickupDragController(currentController.getView(), true);
								dragController.makeDraggable(label);
								currentController.registerDragController(dragController);
								// addEventLabel("Dropped label " + label.getTitle(), "green");
							}
						}
						super.onDrop(context);
					}
				};
			}
		};
		dragController.setBehaviorMultipleSelection(false);
		dragController.setBehaviorDragProxy(false);
		dragController.setBehaviorConstrainedToBoundaryPanel(true);

		PalettePanel palettePanel = new PalettePanel(dragController);
		palettePanel.getElement().getStyle().setProperty("border", "1px solid #cccccc");
		palettePanel.getElement().getStyle().setPadding(5, Unit.PX);
		palettePanel.getElement().getStyle().setMargin(10, Unit.PX);

		LabelWithMenu labelRed = new LabelWithMenu("Label Red", "red", currentController);
		palettePanel.add(labelRed);

		LabelWithMenu labelYellow = new LabelWithMenu("Label Yellow", "yellow", currentController);
		palettePanel.add(labelYellow);

		LabelWithMenu labelBlue = new LabelWithMenu("Label Blue", "blue", currentController);
		palettePanel.add(labelBlue);

		currentController.registerDragController(dragController);

		rightPanel.add(palettePanel);

		// Scroll Panel to show events
		// eventPanel = new VerticalPanel();
		// eventPanel.getElement().getStyle().setProperty("border", "1px solid #cccccc");
		// eventPanel.getElement().getStyle().setPadding(10, Unit.PX);
		// eventPanel.getElement().getStyle().setMargin(10, Unit.PX);
		// eventPanel.getElement().getStyle().setWidth(300, Unit.PX);
		// rightPanel.add(eventPanel);

		currentController.addTieLinkHandler(new TieLinkHandler() {

			@Override
			public void onTieLink(TieLinkEvent event) {
				// addEventLabel("Added link from " + event.getStartWidget().getTitle() + " to " + event.getEndWidget().getTitle(), "green");
			}
		});
		currentController.addUntieLinkHandler(new UntieLinkHandler() {

			@Override
			public void onUntieLink(UntieLinkEvent event) {
				// addEventLabel("Deleted link from " + event.getStartWidget().getTitle() + " to " + event.getEndWidget().getTitle(), "red");
			}
		});

		globalPanel.add(rightPanel);

		setWidth100();
		setHeight100();
		setOverflow(Overflow.HIDDEN);
		addChild(d);

	}

	public void loadDiagram(AbstractDiagram diagram) {
		int index = 0;
		if (currentController != null) {
			currentController.clearDiagram();
			globalPanel.remove(index);
		} else {
			// Create the drawing zone
			currentController = new DiagramController(tabWidth, tabHeight) {

				protected void initMenu() {
					canvasMenu = new ContextMenu();
					canvasMenu.addItem(new MenuItem("Add Label", new Command() {

						@Override
						public void execute() {
							canvasMenu.hide();
							LabelWithMenu label = new LabelWithMenu("Label", currentController);
							currentController.addWidgetAtMousePoint(label);
							PickupDragController dragController = new PickupDragController(currentController.getView(), true);
							dragController.makeDraggable(label);
							currentController.registerDragController(dragController);
							// addEventLabel("Added label " + label.getTitle(), "green");
						}
					}));
				}
			};
			currentController.showGrid(true);
		}
		Widget w;
		if (diagram != null) {
			diagram.setDiagramController(currentController);
			w = diagram.asWidget();
		} else {
			w = currentController.getView();
		}
		w.getElement().getStyle().setMargin(10, Unit.PX);
		w.getElement().getStyle().setProperty("border", "1px solid #cccccc");
		globalPanel.insert(w, index);
		if (diagram != null) {
			diagram.draw();
		}
	}

	// public void addEventLabel(String text, String color) {
	// Label eventLabel = new Label(DateTimeFormat.getMediumDateTimeFormat().format(new Date()) + " | " + text);
	// eventLabel.getElement().getStyle().setColor(color);
	// eventPanel.add(eventLabel);
	// }

	public native int getScrollTop() /*-{
		return $doc.body.scrollTop;
	}-*/;

	public native int getScrollLeft() /*-{
		return $doc.body.scrollLeft;
	}-*/;

}
