/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributoListaItem;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;

public class ListaDatiContabiliDinamicaDetail extends CustomDetail {
	
	protected ListaDatiContabiliDinamicaItem gridItem;
	
	protected DynamicForm mDynamicForm;
	protected AttributoListaItem listaDatiContabiliDinamicaItem;
	
	public ListaDatiContabiliDinamicaDetail(String nomeEntita, final ListaDatiContabiliDinamicaItem gridItem) {
		
		super(nomeEntita);
		
		this.gridItem = gridItem;
		
		mDynamicForm = new DynamicForm();
		mDynamicForm.setWidth100();
		mDynamicForm.setNumCols(18);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setWrapItemTitles(false);
//		mDynamicForm.setCellBorder(1);
		
		listaDatiContabiliDinamicaItem = new AttributoListaItem(null, gridItem.getDatiDettLista()) {
			
			@Override
			public boolean showCanvasSection() {
				return false;
			}
		};
		listaDatiContabiliDinamicaItem.setName("listaDatiContabiliDinamica");
		listaDatiContabiliDinamicaItem.setShowTitle(false);
		listaDatiContabiliDinamicaItem.setNotReplicable(true);
		listaDatiContabiliDinamicaItem.setShowDuplicaRigaButton(false);
		
		mDynamicForm.setFields(listaDatiContabiliDinamicaItem);
		
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();

		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		lVLayout.addMember(mDynamicForm);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);
	}
		
	@Override
	public void editRecord(Record record) {
		RecordList lRecordList = new RecordList();
		lRecordList.add(record);
		listaDatiContabiliDinamicaItem.drawAndSetValue(lRecordList);		
	}
	
	public Record getRecordToSave() {
		Record lRecordToSave = mDynamicForm.getValueAsRecordList("listaDatiContabiliDinamica").get(0);
		return lRecordToSave;
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		listaDatiContabiliDinamicaItem.setCanEdit(canEdit);
	}
	
}