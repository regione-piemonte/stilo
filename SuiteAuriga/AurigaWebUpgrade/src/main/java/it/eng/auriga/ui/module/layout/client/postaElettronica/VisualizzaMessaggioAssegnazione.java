/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.VLayout;

public class VisualizzaMessaggioAssegnazione extends Window{
	
	protected DynamicForm form;
	protected TextAreaItem lTextAreaItemBody;
	
	public VisualizzaMessaggioAssegnazione(String visualizzaMessaggioAssegnazione){
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(750);
		setHeight(250);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowMaximizeButton(true);
		setShowMinimizeButton(true);
		
		VLayout layout = new VLayout();
		layout.setWidth100();
		layout.setHeight100();
		layout.setLayoutLeftMargin(5);
		layout.setLayoutRightMargin(5);
		layout.setLayoutTopMargin(5);
		layout.setLayoutBottomMargin(5);

		form = new DynamicForm();
		
		Record record = new Record();
		record.setAttribute("body", visualizzaMessaggioAssegnazione);

		// Casella testo TEXT
		lTextAreaItemBody = new TextAreaItem("body");
		lTextAreaItemBody.setVisible(true);
		lTextAreaItemBody.setShowTitle(false);				
		lTextAreaItemBody.setHeight(200);
		lTextAreaItemBody.setWidth(728);
		lTextAreaItemBody.setEndRow(true);
		lTextAreaItemBody.setCanEdit(false);		

		form.setFields(lTextAreaItemBody);
		layout.setMembers(form);		
		addItem(layout);
		
		form.editRecord(record);
		
		setTitle("Messaggio di assegnazione");	
	}
}
