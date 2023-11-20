/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.items.StaticTextAreaItem;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 
 * @author Cristiano Daniele
 *
 */

public class PreviewXmlRichiestaWindow extends Window {

	private DynamicForm form;
	protected FormItem lTextAreaItemBody;

	public PreviewXmlRichiestaWindow(String xmlRichiesta) {

		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(750);
		setHeight(500);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowMaximizeButton(true);
		setShowMinimizeButton(true);
		setAutoSize(true);

		VLayout layout = new VLayout();
		layout.setWidth100();
		layout.setHeight100();
		layout.setLayoutLeftMargin(5);
		layout.setLayoutRightMargin(5);
		layout.setLayoutTopMargin(5);
		layout.setLayoutBottomMargin(5);

		form = new DynamicForm();
		form.setHeight(200);
		form.setWidth(728);
		Record record = new Record();
		record.setAttribute("xmlRichiesta", xmlRichiesta);

		lTextAreaItemBody = new StaticTextAreaItem("xmlRichiesta");
		lTextAreaItemBody.setVisible(true);
		lTextAreaItemBody.setShowTitle(false);
		lTextAreaItemBody.setHeight(450);
		lTextAreaItemBody.setWidth(728);
		lTextAreaItemBody.setEndRow(true);
		lTextAreaItemBody.setCanEdit(false);
		// form.setHeight(238);
		form.setFields(lTextAreaItemBody);

		layout.addMember(form);

		addItem(layout);

		form.editRecord(record);

		setTitle("Dettaglio xml di richiesta");

	}

}
