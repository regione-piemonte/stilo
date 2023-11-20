/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.diagrammi.DiagrammiLayout;
import it.eng.auriga.ui.module.layout.client.diagrammi.example.AbstractDiagram;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;

public class DiagramMenuElement extends LayoutPanel {

	private Anchor nameAnchor;
	private Label descriptionLabel;

	private int width = 240;
	private int height = 80;

	public DiagramMenuElement(final AbstractDiagram diagram, final DiagrammiLayout layout) {
		setWidth(width + "px");
		setHeight(height + "px");

		nameAnchor = new Anchor(diagram.getName());
		add(nameAnchor);
		nameAnchor.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		setWidgetTopHeight(nameAnchor, 2, Unit.PX, 20, Unit.PX);
		nameAnchor.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				layout.loadDiagram(diagram);
			}
		});

		descriptionLabel = new Label(diagram.getDescription());
		add(descriptionLabel);
		setWidgetTopHeight(descriptionLabel, 20, Unit.PX, 50, Unit.PX);

	}

}
