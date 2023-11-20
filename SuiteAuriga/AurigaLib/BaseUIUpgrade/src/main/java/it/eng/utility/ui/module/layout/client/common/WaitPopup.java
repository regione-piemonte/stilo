/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Stack;

import com.smartgwt.client.types.ImageStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

public class WaitPopup {

	private final Stack<String> stack = new Stack<String>();
	private final Canvas canvas = createModalMessage();
	private Label label;

	public void show(String actionName) {
		if (actionName == null)
			actionName = "";
		stack.push(actionName);
		setMessage();
		canvas.show();
	}

	private void setMessage() {
		String msg = "Loading";
		for (String action : stack) {
			if (!action.equals("")) {
				msg = action;
				break;
			}
		}
		label.setContents(msg);
		label.setBaseStyle(it.eng.utility.Styles.waitmessage);
	}

	public void hide() {
		if (!stack.isEmpty()) {
			stack.pop();
			if (stack.isEmpty())
				canvas.hide();
		}
	}

	public void hideFinal() {
		stack.removeAllElements();
		canvas.clear();
		canvas.destroy();
	}

	private Window createModalMessage() {
		Img loadingIcon = new Img("file/" + "wait.gif", 16, 16);
		loadingIcon.setShowEdges(false);
		loadingIcon.setImageType(ImageStyle.NORMAL);
		loadingIcon.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hideFinal();
			}
		});

		label = new Label();
		label.setWidth(200);
		label.setHeight100();
		label.setValign(VerticalAlignment.CENTER);

		HLayout hLayout = new HLayout();
		hLayout.setLayoutMargin(20);
		hLayout.setMembersMargin(5);
		VLayout vLayout = new VLayout();
		vLayout.setMembers(new LayoutSpacer(), loadingIcon, new LayoutSpacer());
		hLayout.setMembers(new LayoutSpacer(), vLayout, label, new LayoutSpacer());
		Window window = new Window();
		window.setShowHeader(false);
		window.setShowHeaderBackground(false);
		window.setShowHeaderIcon(false);
		window.setIsModal(true);
		window.setShowModalMask(true);
		window.setWidth(300);
		window.setHeight(120);
		window.addItem(hLayout);
		// window.setRect(0, 0, 300, 120);
		window.centerInPage();
		window.setHeaderStyle(it.eng.utility.Styles.dialogHeader);
		window.setBodyStyle(it.eng.utility.Styles.dialogBody);
		return window;
	}

	public void setZIndex(int zIndex) {
		canvas.setZIndex(zIndex + 100);

	}

}