/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.allen_sauer.gwt.dnd.client.HasDragHandle;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.DiagramController;
import com.orange.links.client.menu.ContextMenu;
import com.orange.links.client.menu.HasContextMenu;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Dialog;

public class LabelWithMenu extends BoxLabel implements HasContextMenu, HasDragHandle, CloneableWidget {

	private LabelWithMenu instance;

	private ContextMenu customMenu;
	private DiagramController currentController;

	public LabelWithMenu(String text, DiagramController currentController) {
		this(text, null, currentController);
	}

	public LabelWithMenu(String text, String backgroundColor, DiagramController currentController) {
		super(text);
		instance = this;
		this.currentController = currentController;
		setWordWrap(false);
		setTitle(text);
		if (backgroundColor != null && !"".equals(backgroundColor)) {
			getElement().getStyle().setBackgroundColor(backgroundColor);
		}
	}

	@Override
	public ContextMenu getContextMenu() {
		
		customMenu = new ContextMenu();
		customMenu.addItem(new MenuItem("Rename", new Command() {

			@Override
			public void execute() {
				customMenu.hide();
				// VerticalPanel renameVerticalPanel = new VerticalPanel();
				// final DialogBox renameDialogBox = new DialogBox();
				// renameDialogBox.setWidget(renameVerticalPanel);
				// renameDialogBox.setGlassEnabled(true);
				// renameDialogBox.setAnimationEnabled(true);
				// renameDialogBox.getElement().getStyle().setZIndex(Integer.MAX_VALUE);
				// renameDialogBox.setModal(true);
				// final String name = getInstance().getTitle();
				// final TextBox nameTextBox = new TextBox();
				// nameTextBox.setText(name);
				// Button okButton = new Button("Ok", new ClickHandler() {
				//
				// public void onClick(ClickEvent event) {
				// renameDialogBox.hide();
				// String newName = nameTextBox.getText();
				// getInstance().setText(newName);
				// getInstance().setTitle(newName);
				// addEventLabel("Renamed label " + name + " in " + newName, "blue");
				// }
				// });
				// renameVerticalPanel.add(nameTextBox);
				// renameVerticalPanel.add(okButton);
				// renameDialogBox.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
				//
				// public void setPosition(int offsetWidth, int offsetHeight) {
				// int left = (Window.getClientWidth() - offsetWidth) / 3;
				// int top = (Window.getClientHeight() - offsetHeight) / 3;
				// renameDialogBox.setPopupPosition(left, top);
				// }
				// });
				// renameDialogBox.getElement().getStyle().setZIndex(1000);
				// renameDialogBox.center();
				final String name = getInstance().getTitle();
				Dialog renameDialogProperties = new Dialog();
				renameDialogProperties.setWidth(300);
				SC.askforValue("Rename", "Insert new name", name, new ValueCallback() {

					@Override
					public void execute(String value) {
						
						if (value != null && !"".equals(value)) {
							String newName = value;
							getInstance().setText(newName);
							getInstance().setTitle(newName);
							// addEventLabel("Renamed label " + name + " in " + newName, "blue");
						}
					}
				}, renameDialogProperties);
			}
		}));
		customMenu.addItem(new MenuItem("Delete", new Command() {

			@Override
			public void execute() {
				customMenu.hide();
				currentController.deleteWidget(getInstance());
				// addEventLabel("Deleted label " + getInstance().getTitle(), "red");
			}
		}));
		return customMenu;
	}

	public LabelWithMenu getInstance() {
		return instance;
	}

	@Override
	public Widget getDragHandle() {
		
		return instance;
	}

	@Override
	public Widget clone() {
		LabelWithMenu label = new LabelWithMenu(getText(), getElement().getStyle().getBackgroundColor(), currentController);
		return label;
		// return (LabelWithMenu) LabelWithMenu.wrap(getElement());
	}

}