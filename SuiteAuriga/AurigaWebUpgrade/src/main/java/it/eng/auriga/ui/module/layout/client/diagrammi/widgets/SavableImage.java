/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.diagrammi.widgets;

import com.google.gwt.user.client.ui.Image;
import com.orange.links.client.save.IsDiagramSerializable;

public class SavableImage extends Image implements IsDiagramSerializable {

	public static String identifier = "image";
	private String content;

	public SavableImage(String url) {
		super(url);
		this.content = url;
	}

	@Override
	public String getType() {
		return this.identifier;
	}

	@Override
	public String getContentRepresentation() {
		return this.content;
	}

	@Override
	public void setContentRepresentation(String contentRepresentation) {
		this.content = contentRepresentation;
	}

}
