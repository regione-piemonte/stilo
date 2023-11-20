/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.RichTextItem;
import com.smartgwt.client.widgets.layout.VLayout;

public class EditorHtmlMaximizedWindow extends ModalWindow {

	private DynamicForm form;
	private RichTextItem htmlItem;
		
	public EditorHtmlMaximizedWindow(String title, String html, final ServiceCallback<Record> closeCallback) {
		
		super("editor_html_maximized", true, false);
		
		setTitle(title);
		setIcon("blank.png");
		setMaximized(true);		  
		setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);
		
		VLayout lVLayout = new VLayout();
		lVLayout.setHeight100();
		lVLayout.setWidth100();
		
		form = new DynamicForm();
		form.setWidth100();
		form.setHeight100();	
		form.setPadding(5);
		
		htmlItem = new RichTextItem("html");  
		htmlItem.setHeight("100%");
		htmlItem.setWidth("100%");		
		htmlItem.setEndRow(true);
		htmlItem.setColSpan(3);
		htmlItem.setValue(html);
		htmlItem.setControlGroups("fontControls", "formatControls", "styleControls", "colorControls", "insertControls");

		form.setFields(htmlItem);
		
		lVLayout.setMembers(form);
		
		setBody(lVLayout);
		
		addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				
				manageOnCloseClick(closeCallback);
				markForDestroy();
			}
		});
						
	}
	
	public void manageOnCloseClick(ServiceCallback<Record> closeCallback) {
		if(closeCallback != null) {
			Record lRecord = new Record();
			lRecord.setAttribute("html", (String) htmlItem.getValue()); 
			closeCallback.execute(lRecord);
		}
	}
	
}