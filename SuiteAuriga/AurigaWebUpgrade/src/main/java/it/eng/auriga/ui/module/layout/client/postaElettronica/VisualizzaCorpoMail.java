/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.items.HtmlItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextAreaItem;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.layout.VLayout;

public class VisualizzaCorpoMail extends Window {

	protected RadioGroupItem style;
	protected FormItem lTextAreaItemBody;
	protected DynamicForm form;

	public VisualizzaCorpoMail(String body, String messageId, boolean isHtml) {

		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(750);
		setHeight(250);
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
		record.setAttribute("body", body);

		// Casella testo TEXT
		if (isHtml) {
			lTextAreaItemBody = new HtmlItem();
			lTextAreaItemBody.setName("body");
		} else {
			lTextAreaItemBody = new StaticTextAreaItem("body");
		}
		lTextAreaItemBody.setVisible(true);
		lTextAreaItemBody.setShowTitle(false);
		lTextAreaItemBody.setHeight(200);
		lTextAreaItemBody.setWidth(728);
		lTextAreaItemBody.setEndRow(true);
		lTextAreaItemBody.setCanEdit(false);

		form.setFields(lTextAreaItemBody);

		layout.addMember(form);

		addItem(layout);

		form.editRecord(record);

		setTitle("Dettaglio corpo e-mail " + (messageId != null && messageId.trim().length() > 0 ? messageId : ""));
	}

}
