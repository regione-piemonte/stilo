/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.VLayout;

public class VisualizzaXML extends Window {

	public VisualizzaXML(String xml, boolean readOnly){
				
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(450);
		setHeight(500);
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
		
		DynamicForm form = new DynamicForm();
		form.setWidth100();
		form.setHeight100();
		
		Record record = new Record();
		record.setAttribute("xml", xml);
		
		TextAreaItem xmlItem = new TextAreaItem("xml");
		xmlItem.setShowTitle(false);
		xmlItem.setTitleOrientation(TitleOrientation.TOP);
		xmlItem.setWidth(428);
		xmlItem.setHeight(425);
		xmlItem.setCanEdit(!readOnly);
		xmlItem.setTextBoxStyle(!readOnly ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly);
		
		form.editRecord(record);
	
		form.setFields(xmlItem);
		layout.setMembers(form);		
		addItem(layout);
	}

}
