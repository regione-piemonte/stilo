/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;

import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;

public class TaskMsgAttivoDetail extends TaskDetail {

	protected DynamicForm noteForm;
	protected TextAreaItem noteItem;

	protected DetailSection detailSectionNote;

	public TaskMsgAttivoDetail(String nomeEntita) {

		super(nomeEntita, null, null, null, null, null);

	}

	@Override
	public void addDetailMembers() {

		// Note
		noteForm = new DynamicForm();
		noteForm.setValuesManager(vm);
		noteForm.setWidth100();
		noteForm.setPadding(5);
		noteForm.setWrapItemTitles(false);
		noteForm.setNumCols(8);
		noteForm.setColWidths(1, 1, 1, 1, 1, 1, "*", "*");

		noteItem = new TextAreaItem("note");
		noteItem.setShowTitle(false);
		noteItem.setHeight(40);
		noteItem.setWidth(650);
		noteItem.setStartRow(true);
		noteItem.setColSpan(5);

		noteForm.setItems(noteItem);

		detailSectionNote = new DetailSection("Messaggio", true, true, false, noteForm);
		addMember(detailSectionNote);

	}

	@Override
	public void editRecord(Record record) {
		
		super.editRecord(record);
		detailSectionNote.setVisible(false);
	}

	@Override
	public void editNewRecord(Map initialValues) {
		
		super.editNewRecord(initialValues);
		detailSectionNote.setVisible(true);
	}

}
